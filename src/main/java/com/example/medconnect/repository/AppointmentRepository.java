package com.example.medconnect.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medconnect.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorIdAndAppointmentDateAndAppointmentTime(Long doctorId, LocalDate date, LocalTime time);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, Appointment.Status status);
    List<Appointment> findByPatientIdAndStatus(Long patientId, Appointment.Status status);
    
    List<Appointment> findByDoctorId(Long doctorId);

}
