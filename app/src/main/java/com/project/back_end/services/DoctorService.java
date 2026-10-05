package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class DoctorService {
    private final Map<Long, Doctor> doctorRepositoryMock = new HashMap<>();

    public DoctorService() {
        Doctor doctor = new Doctor();
        doctor.setId(1L); doctor.setName("Dr. Emily Adams");
        doctor.setEmail("dr.adams@example.com"); doctor.setPassword("passEmily1");
        doctor.setSpecialty("Cardiologist"); doctor.setPhone("5551012020");
        doctor.setAvailableTimes(List.of("09:00-10:00","10:00-11:00","11:00-12:00","14:00-15:00"));
        doctorRepositoryMock.put(doctor.getId(), doctor);
    }

    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        if (doctorId == null || date == null)
            throw new IllegalArgumentException("Doctor ID and Date must be valid parameters.");
        Doctor doctor = doctorRepositoryMock.get(doctorId);
        if (doctor == null) throw new IllegalArgumentException("No practitioner found with ID: " + doctorId);
        return new ArrayList<>(doctor.getAvailableTimes());
    }

    public List<String> getAvailableTimeSlots(Long doctorId, String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty())
            throw new IllegalArgumentException("Date string parameter cannot be null or empty.");
        try { return getAvailableTimeSlots(doctorId, LocalDate.parse(dateStr)); }
        catch (DateTimeParseException e) { throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD."); }
    }

    public Map<String, Object> validateDoctorLogin(String email, String rawPassword) {
        Map<String, Object> response = new HashMap<>();
        Doctor doctor = doctorRepositoryMock.values().stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
        if (doctor != null && doctor.getPassword().equals(rawPassword)) {
            response.put("authenticated", true); response.put("doctorId", doctor.getId());
            response.put("name", doctor.getName()); response.put("email", doctor.getEmail());
            response.put("specialty", doctor.getSpecialty());
            response.put("message", "Credentials are authenticated successfully.");
        } else {
            response.put("authenticated", false);
            response.put("message", "Incorrect credentials or doctor does not exist.");
        }
        return response;
    }
}
