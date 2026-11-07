package com.example.medconnect.dto;



import java.util.List;

public class OrderRequest {
    private String deliveryAddress;
    private List<Item> items;

    public static class Item {
        private Long medicineId;
        private Integer quantity;

        public Long getMedicineId() { return medicineId; }
        public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}