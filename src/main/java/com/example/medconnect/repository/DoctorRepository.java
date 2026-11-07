package com.example.medconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.example.medconnect.entity.Appointment;
import com.example.medconnect.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
		
	
	List<Doctor> findBySpecializationContainingIgnoreCaseAndIsActiveTrue(String specialization);
	
	Optional<Doctor> findByUserId(Long userId);
	
	
	
	
}
