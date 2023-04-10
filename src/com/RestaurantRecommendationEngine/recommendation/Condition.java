package com.RestaurantRecommendationEngine.recommendation;

import com.RestaurantRecommendationEngine.enums.Cuisine;
import com.RestaurantRecommendationEngine.enums.SpecialCondition;

import java.util.List;

public class Condition {
    private List<Cuisine> cuisines;
    private List<Integer> costBrackets;
    private float minRating;
    private float maxRating;
    private boolean isFeaturedMandatory;
    private SpecialCondition specialCondition;

    public Condition(List<Cuisine> cuisines, List<Integer> costBrackets, float minRating, float maxRating, boolean isFeaturedMandatory) {
        this.cuisines = cuisines;
        this.costBrackets = costBrackets;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.isFeaturedMandatory = isFeaturedMandatory;
        this.specialCondition = null;
    }

    public Condition(SpecialCondition specialCondition) {
        this.specialCondition = specialCondition;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public List<Integer> getCostBrackets() {
        return costBrackets;
    }

    public float getMinRating() {
        return minRating;
    }

    public float getMaxRating() {
        return maxRating;
    }

    public boolean isFeaturedMandatory() {
        return isFeaturedMandatory;
    }

    public SpecialCondition getSpecialCondition() {
        return specialCondition;
    }
}