package com.example.medconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medconnect.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPatientId(Long patientId);
}
