package com.example.medconnect.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public class DoctorRequest {

	private Long userId;         
    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private BigDecimal consultationFee;
    private LocalTime availableFrom;
    private LocalTime availableTo;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getExperienceYears() {
		return experienceYears;
	}
	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}
	public BigDecimal getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(BigDecimal consultationFee) {
		this.consultationFee = consultationFee;
	}
	public LocalTime getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(LocalTime availableFrom) {
		this.availableFrom = availableFrom;
	}
	public LocalTime getAvailableTo() {
		return availableTo;
	}
	public void setAvailableTo(LocalTime availableTo) {
		this.availableTo = availableTo;
	}
    
}
