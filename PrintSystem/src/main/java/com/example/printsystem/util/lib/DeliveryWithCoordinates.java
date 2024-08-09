package com.example.printsystem.util.lib;

import com.example.printsystem.models.entity.Delivery;

public class DeliveryWithCoordinates {
    private Delivery delivery;
    private Double latitude;
    private Double longitude;

    public DeliveryWithCoordinates(Delivery delivery, Double latitude, Double longitude) {
        this.delivery = delivery;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Delivery getDelivery() { return delivery; }
    public void setDelivery(Delivery delivery) { this.delivery = delivery; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
