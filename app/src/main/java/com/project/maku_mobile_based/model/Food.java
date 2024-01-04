package com.project.maku_mobile_based.model;

public class Food {

   private String foodName,foodUrlImage,foodPrice;
   private int quantity = 1;


   public Food(){

   }

    public Food(String foodName, String foodUrlImage, String foodPrice) {
        this.foodName = foodName;
        this.foodUrlImage = foodUrlImage;
        this.foodPrice = foodPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodUrlImage() {
        return foodUrlImage;
    }

    public void setFoodUrlImage(String foodUrlImage) {
        this.foodUrlImage = foodUrlImage;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}
