package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    public AppointmentService(AppointmentRepository appointmentRepository) { this.appointmentRepository = appointmentRepository; }

    public Appointment bookAppointment(Doctor doctor, Patient patient, LocalDateTime time) {
        if (doctor == null || patient == null || time == null)
            throw new IllegalArgumentException("Doctor, Patient, and Appointment Time are mandatory fields.");
        if (!time.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Appointment time must occur in the future.");
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor); appointment.setPatient(patient);
        appointment.setAppointmentTime(time); appointment.setStatus(0);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        if (doctorId == null || date == null)
            throw new IllegalArgumentException("Doctor ID and Date must be valid parameters.");
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return appointmentRepository.findAppointmentsForDoctorOnDate(doctorId, startOfDay, endOfDay);
    }
}
