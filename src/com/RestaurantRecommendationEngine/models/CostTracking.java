package com.RestaurantRecommendationEngine.models;

public class CostTracking {
    private int type;
    private int noOfOrders;

    public CostTracking(int type, int noOfOrders) {
        this.noOfOrders = noOfOrders;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }

}
