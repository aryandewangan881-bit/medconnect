package com.example.medconnect.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentResponse {

	
	 private Long id;
	    private String patientName;
	    private String doctorName;
	    private String patientEmail;
	    private String patientPhone;
	    private String specialization;
	    private LocalDate appointmentDate;
	    private LocalTime appointmentTime;
	    private String status;
	    private String reason;
	    private LocalDateTime createdAt;
		public AppointmentResponse(Long id, String patientName, String patientEmail, String PateintPhone, String doctorName, String specialization,
				LocalDate appointmentDate, LocalTime appointmentTime, String status, String reason,
				LocalDateTime createdAt) {
			super();
			this.id = id;
			this.patientName = patientName;
			this.doctorName = doctorName;
			this.specialization = specialization;
			this.appointmentDate = appointmentDate;
			this.appointmentTime = appointmentTime;
			this.status = status;
			this.reason = reason;
			this.createdAt = createdAt;
		}
		public String getPatientEmail() {
			return patientEmail;
		}
		public void setPatientEmail(String patientEmail) {
			this.patientEmail = patientEmail;
		}
		public String getPatientPhone() {
			return patientPhone;
		}
		public void setPatientPhone(String patientPhone) {
			this.patientPhone = patientPhone;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPatientName() {
			return patientName;
		}
		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}
		public String getDoctorName() {
			return doctorName;
		}
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		public LocalDate getAppointmentDate() {
			return appointmentDate;
		}
		public void setAppointmentDate(LocalDate appointmentDate) {
			this.appointmentDate = appointmentDate;
		}
		public LocalTime getAppointmentTime() {
			return appointmentTime;
		}
		public void setAppointmentTime(LocalTime appointmentTime) {
			this.appointmentTime = appointmentTime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		
	    
	    
}
