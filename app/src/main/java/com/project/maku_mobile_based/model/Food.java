package com.project.maku_mobile_based.model;

public class Food {

    String foodName,foodTenants,foodUrl;
    Integer foodPrice;

//    Constructor for firebase
    Food(){

    }

    public Food(String foodName, String foodTenants, String foodUrl, Integer foodPrice) {
        this.foodName = foodName;
        this.foodTenants = foodTenants;
        this.foodUrl = foodUrl;
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodTenants() {
        return foodTenants;
    }

    public void setFoodTenants(String foodTenants) {
        this.foodTenants = foodTenants;
    }

    public String getFoodUrl() {
        return foodUrl;
    }

    public void setFoodUrl(String foodUrl) {
        this.foodUrl = foodUrl;
    }

    public Integer getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Integer foodPrice) {
        this.foodPrice = foodPrice;
    }
}
