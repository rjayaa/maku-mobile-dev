package com.project.maku_mobile_based.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private String foodName,foodDesc,foodUrlImage,foodPrice;
   private int quantity = 0;

   public Food(){

   }

    public Food(String foodName, String foodDesc, String foodUrlImage, String foodPrice) {
        this.foodName = foodName;
        this.foodDesc = foodDesc;
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

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
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

    protected Food(Parcel in) {
        foodName = in.readString();
        foodDesc = in.readString();
        foodUrlImage = in.readString();
        foodPrice = in.readString();
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeString(foodDesc);
        dest.writeString(foodUrlImage);
        dest.writeString(foodPrice);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
