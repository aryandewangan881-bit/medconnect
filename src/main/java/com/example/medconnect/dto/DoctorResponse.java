package com.example.medconnect.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public class DoctorResponse {

	
	private Long id;
    private String name;       
    private String email;      
    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private BigDecimal consultationFee;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private Boolean isActive;
    
    
    public  DoctorResponse (Long id, String name, String email, String specialization,
            String qualification, Integer experienceYears, BigDecimal consultationFee,
            LocalTime availableFrom, LocalTime availableTo, Boolean isActive) {
this.id = id;
this.name = name;
this.email = email;
this.specialization = specialization;
this.qualification = qualification;
this.experienceYears = experienceYears;
this.consultationFee = consultationFee;
this.availableFrom = availableFrom;
this.availableTo = availableTo;
this.isActive = isActive;
}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
    
}
