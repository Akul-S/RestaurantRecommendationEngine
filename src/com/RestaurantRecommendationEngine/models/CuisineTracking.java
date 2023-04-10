package com.RestaurantRecommendationEngine.models;

import com.RestaurantRecommendationEngine.enums.Cuisine;

public class CuisineTracking {
    private Cuisine type;
    private int noOfOrders;

    public CuisineTracking(Cuisine type, int noOfOrders) {
        this.noOfOrders = noOfOrders;
        this.type = type;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }

    public Cuisine getType() {
        return type;
    }

}
