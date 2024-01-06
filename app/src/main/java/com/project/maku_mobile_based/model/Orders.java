package com.project.maku_mobile_based.model;

import android.os.Parcelable;

import java.util.List;

public class Orders  {
    private int orderId;
    private List<OrderItem> orderItems;
    private String orderStatus;
    private String username,location,totalPrice,phoneNumber;

    public Orders(){};
    public Orders(int orderId, List<OrderItem> orderItems, String orderStatus, String username, String location, String totalPrice, String phoneNumber) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.username = username;
        this.location = location;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

