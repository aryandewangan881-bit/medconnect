package com.example.medconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medconnect.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
