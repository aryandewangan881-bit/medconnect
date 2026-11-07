package com.example.medconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medconnect.dto.AdminDoctorRequest;
import com.example.medconnect.dto.DoctorRequest;
import com.example.medconnect.dto.DoctorResponse;
import com.example.medconnect.service.DoctorService;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	
	 @Autowired
	    private DoctorService doctorService;

	    // Naya doctor add karna (Admin ke liye)
	    @PostMapping
	    public ResponseEntity<DoctorResponse> addDoctor(@RequestBody DoctorRequest request) {
	        return ResponseEntity.ok(doctorService.addDoctor(request));
	    }

	    // Sab doctors list karna, optional specialization filter ke saath
	    @GetMapping
	    public ResponseEntity<List<DoctorResponse>> getDoctors(
	            @RequestParam(required = false) String specialization) {

	        if (specialization != null && !specialization.isEmpty()) {
	            return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
	        }
	        return ResponseEntity.ok(doctorService.getAllDoctors());
	    }

	    // Ek doctor ki detail
	    @GetMapping("/{id}")
	    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
	        return ResponseEntity.ok(doctorService.getDoctorById(id));
	    }
	    
	    @PostMapping("/create-with-user")
	    public ResponseEntity<DoctorResponse> createWithUser(@RequestBody AdminDoctorRequest request) {
	        return ResponseEntity.ok(doctorService.createDoctorWithUser(request));
	    }
}
