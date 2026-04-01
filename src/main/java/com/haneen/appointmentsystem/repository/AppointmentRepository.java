package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Optional<Appointment> findById(Long id);

    Appointment save(Appointment appointment);
    /**
     * Finds appointments that overlap with the given time range
     * for a specific provider, excluding a specific appointment.
     */
    List<Appointment> findOverlappingAppointments(
            Long providerId,
            LocalDateTime start,
            LocalDateTime end,
            Long excludeAppointmentId
    );

    List<Appointment> findAll();
}
