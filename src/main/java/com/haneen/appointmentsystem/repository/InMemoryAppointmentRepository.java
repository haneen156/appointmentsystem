package com.haneen.appointmentsystem.repository;

import com.haneen.appointmentsystem.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryAppointmentRepository implements AppointmentRepository{
    private Map<Long, Appointment> storage = new HashMap<>();

    // auto-increment ID generator
    private Long idCounter = 1L;

    // retrieve appointment or return empty if not found
    @Override
    public Optional<Appointment> findById(Long id) {

        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Appointment save(Appointment appointment) {
        // if new appointment → assign ID
        if(appointment.getId() == null){
            appointment.setId(idCounter++);
        }
        // store or update appointment
        storage.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public List<Appointment> findOverlappingAppointments(Long providerId,
                                                         LocalDateTime start,
                                                         LocalDateTime end,
                                                         Long excludeAppointmentId) {
        List<Appointment> result = new ArrayList<>();

        for(Appointment appointment : storage.values()){

            if(appointment.getProvider().getId().equals(providerId)
            && excludeAppointmentId == null || !appointment.getId().equals(excludeAppointmentId)
            && appointment.overlaps(start, end)){

                result.add(appointment);
            }
        }
        return result;
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(storage.values());
    }
}
