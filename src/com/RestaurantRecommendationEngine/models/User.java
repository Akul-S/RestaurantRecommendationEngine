package com.RestaurantRecommendationEngine.models;

public class User {
    private CuisineTracking[] cuisines;
    private CostTracking[] costBracket;

    public User(CuisineTracking[] cuisines, CostTracking[] costBracket) {
        this.cuisines = cuisines;
        this.costBracket = costBracket;
    }

    public CuisineTracking[] getCuisines() {
        return cuisines;
    }

    public CostTracking[] getCostBracket() {
        return costBracket;
    }
}
