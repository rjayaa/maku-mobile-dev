package com.project.maku_mobile_based.model;

import java.util.List;

public class Orders {
    private int orderId;
    private List<OrderItem> orderItems;
    private String orderStatus;

    // Constructor
    public Orders(int orderId, List<OrderItem> orderItems, String orderStatus) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    // Getter dan Setter
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
}

