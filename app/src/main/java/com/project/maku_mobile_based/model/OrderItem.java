package com.project.maku_mobile_based.model;

public class OrderItem {

        private String foodName;
        private int foodPrice;

        // Constructor
        public OrderItem(String foodName, int foodPrice) {
            this.foodName = foodName;
            this.foodPrice = foodPrice;
        }

        // Getter dan Setter
        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public int getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(int foodPrice) {
            this.foodPrice = foodPrice;
        }


}
