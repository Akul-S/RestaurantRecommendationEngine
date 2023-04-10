import com.RestaurantRecommendationEngine.enums.Cuisine;
import com.RestaurantRecommendationEngine.models.CostTracking;
import com.RestaurantRecommendationEngine.models.CuisineTracking;
import com.RestaurantRecommendationEngine.models.Restaurant;
import com.RestaurantRecommendationEngine.models.User;
import com.RestaurantRecommendationEngine.recommendation.RestaurantRecommendationEngine;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        Random random = new Random();

        // Create a user with random cuisine and cost preferences
        CuisineTracking[] cuisineTrackings = new CuisineTracking[Cuisine.values().length];
        for (int i = 0; i < cuisineTrackings.length; i++) {
            cuisineTrackings[i] = new CuisineTracking(Cuisine.values()[i], random.nextInt(20) + 1);
        }

        CostTracking[] costTrackings = new CostTracking[5];
        for (int i = 0; i < costTrackings.length; i++) {
            costTrackings[i] = new CostTracking(i + 1, random.nextInt(20) + 1);
        }

        User user = new User(cuisineTrackings, costTrackings);

        // Create an array of random restaurants
        int numRestaurants = 500;
        Restaurant[] availableRestaurants = new Restaurant[numRestaurants];
        HashMap<String, Restaurant> restaurantMap = new HashMap<>();
        for (int i = 0; i < numRestaurants; i++) {
            String restaurantId = "restaurant_" + i;
            Cuisine cuisine = Cuisine.values()[random.nextInt(Cuisine.values().length)];
            int costBracket = random.nextInt(5) + 1;
            float rating = random.nextFloat() * 5;
            boolean isRecommended = random.nextBoolean();
            Date onboardedTime = new Date(System.currentTimeMillis() - random.nextInt(5 * 24 * 60 * 60 * 1000));
            availableRestaurants[i] = new Restaurant(restaurantId, cuisine, costBracket, rating, isRecommended, onboardedTime);
            restaurantMap.put(restaurantId, availableRestaurants[i]);
        }

        // Get restaurant recommendations
        List<String> recommendations = RestaurantRecommendationEngine.getRestaurantRecommendations(user, Arrays.asList(availableRestaurants));

        //System.out.println(user.getCuisines() + user.getCostBracket());
        // Print recommendations
        for (String recommendation : recommendations) {
            Restaurant recommendedRestaurant = restaurantMap.get(recommendation);
            System.out.println("ID: " + recommendation +
                    ", Cuisine: " + recommendedRestaurant.getCuisine() +
                    ", Cost Bracket: " + recommendedRestaurant.getCostBracket() +
                    ", Rating: " + recommendedRestaurant.getRating() +
                    ", Recommended: " + recommendedRestaurant.isRecommended() +
                    ", Onboarded: " + recommendedRestaurant.getOnboardedTime()
            );
        }
    }
}


