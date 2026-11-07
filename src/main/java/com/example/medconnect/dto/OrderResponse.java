package com.example.medconnect.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String patientName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String deliveryAddress;
    private List<Item> items;

    public static class Item {
        private String medicineName;
        private Integer quantity;
        private BigDecimal priceAtOrder;

        public Item(String medicineName, Integer quantity, BigDecimal priceAtOrder) {
            this.medicineName = medicineName;
            this.quantity = quantity;
            this.priceAtOrder = priceAtOrder;
        }
        public String getMedicineName() { return medicineName; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    }

    public OrderResponse(Long id, String patientName, LocalDateTime orderDate, BigDecimal totalAmount,
                         String status, String deliveryAddress, List<Item> items) {
        this.id = id;
        this.patientName = patientName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
    }

    public Long getId() { return id; }
    public String getPatientName() { return patientName; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public List<Item> getItems() { return items; }
}