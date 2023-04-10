package com.RestaurantRecommendationEngine.recommendation;

import com.RestaurantRecommendationEngine.enums.*;
import com.RestaurantRecommendationEngine.models.*;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantRecommendationEngine {

    public static final int MAX_RECOMMENDATIONS = 100;
    public static final int MAX_NEW_RESTAURANTS = 4;// 48 hours in milliseconds
    public static final long TIME_WINDOW_TO_CONSIDER_AS_NEW = 48 * 60 * 60 * 1000L;

    public static List<String> getRestaurantRecommendations(User user, List<Restaurant> availableRestaurants) {
        List<Cuisine> topCuisines = getTopCuisines(user.getCuisines(), 3);
        System.out.println("Top Cuisines for user");
        for (Cuisine cusine : topCuisines) {
            System.out.println(cusine);
        }

        List<Integer> topCostBrackets = getTopCostBrackets(user.getCostBracket(), 3);
        System.out.println("Top Cost Brackets for user");
        for (Integer costBracket : topCostBrackets) {
            System.out.println(costBracket);
        }

        // Define conditions
        List<Condition> conditions = InitializeConditions(topCuisines, topCostBrackets);

        Set<String> recommendations = new HashSet<>();

        // Iterate through conditions and add matching restaurants to recommendations
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            List<Restaurant> matchedRestaurants;

            if (condition.getSpecialCondition() == null) {
                matchedRestaurants = availableRestaurants.stream()
                        .filter(restaurant -> doesRestaurantMatchCondition(restaurant, condition.getCuisines(), condition.getCostBrackets(), condition.getMinRating(), condition.getMaxRating(), condition.isFeaturedMandatory()))
                        .collect(Collectors.toList());
            } else {
                matchedRestaurants = HandleSpecialConditionCases(availableRestaurants, condition);
            }

            // Sort matched restaurants by rating in descending order
            matchedRestaurants.sort(Comparator.comparing(Restaurant::getRating).reversed());
            int count = 0;
            // Add matched restaurant IDs to recommendations
            for (Restaurant restaurant : matchedRestaurants) {
                if (recommendations.size() < MAX_RECOMMENDATIONS && !recommendations.contains(restaurant.getRestaurantId())) {
                    recommendations.add(restaurant.getRestaurantId());
                    count++;
                }
            }
            System.out.println("Added " + count + " Restaurants for condition number " + i);
            // If we have enough recommendations, break the loop
            if (recommendations.size() >= MAX_RECOMMENDATIONS) {
                break;
            }
        }

        return recommendations.stream().toList();
    }

    private static List<Restaurant> HandleSpecialConditionCases(List<Restaurant> availableRestaurants, Condition condition) {
        List<Restaurant> matchedRestaurants;
        // com.RestaurantRecommendationEngine.recommendation.Condition for new restaurants (last 48 hours)
        switch (condition.getSpecialCondition()) {
            case NEW_RESTAURANTS:
                long currentTime = System.currentTimeMillis();

                matchedRestaurants = availableRestaurants.stream()
                        .filter(restaurant -> currentTime - restaurant.getOnboardedTime().getTime() <= TIME_WINDOW_TO_CONSIDER_AS_NEW)
                        .sorted(Comparator.comparing(Restaurant::getRating).reversed())
                        .limit(MAX_NEW_RESTAURANTS)
                        .collect(Collectors.toList());
                break;
            default:
                throw new IllegalArgumentException("Unknown Special Condition case found");
        }
        return matchedRestaurants;
    }

    private static List<Condition> InitializeConditions(List<Cuisine> topCuisines, List<Integer> topCostBrackets) {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition(topCuisines.subList(0, 1), topCostBrackets.subList(0, 1), 0, Float.MAX_VALUE, true));
        conditions.add(new Condition(topCuisines.subList(0, 1), topCostBrackets.subList(0, 1), 4, Float.MAX_VALUE, false));
        conditions.add(new Condition(topCuisines.subList(0, 1), topCostBrackets.subList(1, 3), 4.5f, Float.MAX_VALUE, false));
        conditions.add(new Condition(topCuisines.subList(1, 3), topCostBrackets.subList(0, 1), 4.5f, Float.MAX_VALUE, false));
        conditions.add(new Condition(SpecialCondition.NEW_RESTAURANTS)); // Placeholder for new restaurants condition
        conditions.add(new Condition(topCuisines.subList(0, 1), topCostBrackets.subList(0, 1), 0, 4, false));
        conditions.add(new Condition(topCuisines.subList(0, 1), topCostBrackets.subList(1, 3), 0, 4.5f, false));
        conditions.add(new Condition(topCuisines.subList(1, 3), topCostBrackets.subList(0, 1), 0, 4.5f, false));
        conditions.add(new Condition(Arrays.asList(Cuisine.values()), Arrays.asList(1, 2, 3, 4, 5), 0, Float.MAX_VALUE, false));
        return conditions;
    }

    // Helper method to get primary and secondary user preferences
    private static List<Cuisine> getTopCuisines(CuisineTracking[] cuisines, int count) {
        return Arrays.stream(cuisines)
                .sorted(Comparator.comparingInt(CuisineTracking::getNoOfOrders).reversed())
                .limit(count)
                .map(x -> x.getType())
                .collect(Collectors.toList());
    }

    private static List<Integer> getTopCostBrackets(CostTracking[] costBrackets, int count) {
        return Arrays.stream(costBrackets)
                .sorted(Comparator.comparingInt(CostTracking::getNoOfOrders).reversed())
                .limit(count)
                .map(x -> x.getType())
                .collect(Collectors.toList());
    }


    // Helper method to check if the restaurant matches the condition
    private static boolean doesRestaurantMatchCondition(Restaurant restaurant, List<Cuisine> cuisines, List<Integer> costBrackets, float minRating, float maxRating, boolean isFeatured) {
        return cuisines.contains(restaurant.getCuisine()) &&
                costBrackets.contains(restaurant.getCostBracket()) &&
                restaurant.getRating() >= minRating &&
                restaurant.getRating() < maxRating &&
                (restaurant.isRecommended() == isFeatured || !isFeatured);
    }
}
