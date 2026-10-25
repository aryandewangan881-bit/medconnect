package com.example.medconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medconnect.dto.AppointmentRequest;
import com.example.medconnect.dto.AppointmentResponse;
import com.example.medconnect.entity.Appointment;
import com.example.medconnect.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

	
	 @Autowired
	    private AppointmentService appointmentService;

	    // Appointment book karna (Patient)
	    @PostMapping
	    public ResponseEntity<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest request) {
	        return ResponseEntity.ok(appointmentService.bookAppointment(request));
	    }

	    // Apni appointments dekhna (Patient)
	    @GetMapping("/my")
	    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {
	        return ResponseEntity.ok(appointmentService.getMyAppointments());
	    }

	    // Appointment cancel karna (Patient)
	    @PutMapping("/{id}/cancel")
	    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id) {
	        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
	    }

	    // Sab appointments dekhna (Admin/Doctor)
	    @GetMapping
	    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
	        return ResponseEntity.ok(appointmentService.getAllAppointments());
	    }
	    
	 // Doctor: apni appointments + patient detail
	    @GetMapping("/doctor")
	    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments(
	            @RequestParam(required = false) Appointment.Status status) {
	        return ResponseEntity.ok(appointmentService.getDoctorAppointments(status));
	    }

	    // Doctor: status update
	    @PutMapping("/{id}/status")
	    public ResponseEntity<AppointmentResponse> updateStatus(
	            @PathVariable Long id, @RequestParam Appointment.Status status) {
	        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
	    }
}
