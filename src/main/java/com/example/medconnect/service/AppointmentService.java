package com.example.medconnect.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.medconnect.dto.AppointmentRequest;
import com.example.medconnect.dto.AppointmentResponse;
import com.example.medconnect.entity.Appointment;
import com.example.medconnect.entity.Doctor;
import com.example.medconnect.entity.User;
import com.example.medconnect.repository.AppointmentRepository;
import com.example.medconnect.repository.DoctorRepository;
import com.example.medconnect.repository.UserRepository;

@Service
public class AppointmentService {

	@Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    // Currently logged-in user ko JWT se nikalna
    private User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged in user not found"));
    }
    
    private ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
    
    
    private Doctor getLoggedInDoctor() {
        User user = getLoggedInUser();
        return doctorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Doctor profile not found for this user"));
    }

    // Doctor ki apni appointments (optional status filter)
    public List<AppointmentResponse> getDoctorAppointments(Appointment.Status status) {
        Doctor doctor = getLoggedInDoctor();
        List<Appointment> list = (status == null)
                ? appointmentRepository.findByDoctorId(doctor.getId())
                : appointmentRepository.findByDoctorIdAndStatus(doctor.getId(),status);
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
        
    }

    // Doctor apni appointment ka status badle
    public AppointmentResponse updateStatus(Long appointmentId, Appointment.Status status) {
        Doctor doctor = getLoggedInDoctor();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("This appointment does not belong to you");
        }

        appointment.setStatus(status);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    // Appointment book karna
    public AppointmentResponse bookAppointment(AppointmentRequest request) {
        User patient = getLoggedInUser();

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + request.getDoctorId()));

        if (!Boolean.TRUE.equals(doctor.getIsActive())) {
            throw bad("Ye doctor abhi available nahi hai");
        }

        LocalDate date = request.getAppointmentDate();
        LocalTime time = request.getAppointmentTime();
        if (date == null || time == null) {
            throw bad("Date aur time zaroori hai");
        }

        if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
            throw bad("Past ke time ki appointment book nahi ho sakti");
        }

        LocalTime from = doctor.getAvailableFrom();
        LocalTime to = doctor.getAvailableTo();
        if (from != null && to != null && (time.isBefore(from) || !time.isBefore(to))) {
            throw bad("Doctor sirf " + from + " se " + to + " ke beech available hai");
        }
        // Double-booking check
        List<Appointment> existing = appointmentRepository
                .findByDoctorIdAndAppointmentDateAndAppointmentTime(
                        request.getDoctorId(), request.getAppointmentDate(), request.getAppointmentTime());

        if (!existing.isEmpty()) {
            throw new RuntimeException("This time slot is already booked for the selected doctor");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(Appointment.Status.PENDING);

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    // Logged-in patient ki apni appointments
    public List<AppointmentResponse> getMyAppointments() {
        User patient = getLoggedInUser();
        return appointmentRepository.findByPatientId(patient.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Appointment cancel karna
    public AppointmentResponse cancelAppointment(Long appointmentId) {
        User patient = getLoggedInUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Security check: sirf apni hi appointment cancel kar sakta hai
        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new RuntimeException("You are not authorized to cancel this appointment");
        }

        appointment.setStatus(Appointment.Status.CANCELLED);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    // Admin/Doctor ke liye — sab appointments
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Entity ko Response DTO mein convert karna
    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getName(),
                appointment.getPatient().getEmail(),
                appointment.getPatient().getPhone(),
                appointment.getDoctor().getUser().getName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus().name(),
                appointment.getReason(),
                appointment.getCreatedAt()
        );
    }
	
}
