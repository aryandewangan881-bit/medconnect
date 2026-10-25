package com.example.medconnect.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.medconnect.dto.DoctorRequest;
import com.example.medconnect.dto.DoctorResponse;
import com.example.medconnect.entity.Doctor;
import com.example.medconnect.entity.User;
import com.example.medconnect.repository.DoctorRepository;
import com.example.medconnect.repository.UserRepository;
@Service
public class DoctorService {

	
	 @Autowired
	    private DoctorRepository doctorRepository;

	    @Autowired
	    private UserRepository userRepository;

	    // Naya doctor add karna
	    public DoctorResponse addDoctor(DoctorRequest request) {
	        User user = userRepository.findById(request.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

	        // User ka role DOCTOR set kar do
	        user.setRole(User.Role.DOCTOR);
	        userRepository.save(user);

	        Doctor doctor = new Doctor();
	        doctor.setUser(user);
	        doctor.setSpecialization(request.getSpecialization());
	        doctor.setQualification(request.getQualification());
	        doctor.setExperienceYears(request.getExperienceYears());
	        doctor.setConsultationFee(request.getConsultationFee());
	        doctor.setAvailableFrom(request.getAvailableFrom());
	        doctor.setAvailableTo(request.getAvailableTo());
	        doctor.setIsActive(true);

	        Doctor saved = doctorRepository.save(doctor);

	        return mapToResponse(saved);
	    }

	    // Sab active doctors list karna
	    public List<DoctorResponse> getAllDoctors() {
	        return doctorRepository.findAll()
	                .stream()
	                .filter(Doctor::getIsActive)
	                .map(this::mapToResponse)
	                .collect(Collectors.toList());
	    }

	    // Specialization se filter karna
	    public List<DoctorResponse> getDoctorsBySpecialization(String specialization) {
	        return doctorRepository.findBySpecializationContainingIgnoreCaseAndIsActiveTrue(specialization)
	                .stream()
	                .map(this::mapToResponse)
	                .collect(Collectors.toList());
	    }

	    // Ek doctor ki detail nikalna
	    public DoctorResponse getDoctorById(Long id) {
	        Doctor doctor = doctorRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
	        return mapToResponse(doctor);
	    }

	    // Entity ko Response DTO mein convert karne wala helper method
	    private DoctorResponse mapToResponse(Doctor doctor) {
	        return new DoctorResponse(
	                doctor.getId(),
	                doctor.getUser().getName(),
	                doctor.getUser().getEmail(),
	                doctor.getSpecialization(),
	                doctor.getQualification(),
	                doctor.getExperienceYears(),
	                doctor.getConsultationFee(),
	                doctor.getAvailableFrom(),
	                doctor.getAvailableTo(),
	                doctor.getIsActive()
	        );
	    }
}
