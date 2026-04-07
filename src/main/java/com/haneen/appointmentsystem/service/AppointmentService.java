package com.haneen.appointmentsystem.service;

import com.haneen.appointmentsystem.domain.exception.*;
import com.haneen.appointmentsystem.domain.model.*;
import com.haneen.appointmentsystem.repository.*;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {


    //These references cannot be changed after initialization
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              ProviderRepository providerRepository) {

        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }

    public Appointment createAppointment(Long userId,
                                         Long providerId,
                                         LocalDateTime startTime,
                                         LocalDateTime endTime){

        if(userId == null || providerId == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Invalid Input");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider with id " + providerId + " not found"));

        List<Appointment> conflicts = appointmentRepository.findOverlappingAppointments(providerId, startTime, endTime,null);

        if(!conflicts.isEmpty()) {
            throw new AppointmentConflictException();
        }

        Appointment appointment = new Appointment(startTime, endTime, user, provider);

        appointmentRepository.save(appointment);

        return appointment;
    }

    public Appointment cancelAppointment( Long appointmentId ){

        if(appointmentId == null ) {
            throw new IllegalArgumentException("Invalid Input");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with id " + appointmentId + " not found"));

        appointment.cancel();

        appointmentRepository.save(appointment);

        return appointment;
    }

    public Appointment rescheduleAppointment( Long appointmentId,
                                              LocalDateTime newStart ,
                                              LocalDateTime newEnd ){

        if(appointmentId == null || newStart == null || newEnd == null) {
            throw new IllegalArgumentException("Invalid Input");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with id " + appointmentId + " not found"));

        Long providerId = appointment.getProvider().getId();

        //conflict logic

        List<Appointment> conflicts = appointmentRepository.findOverlappingAppointments(providerId, newStart, newEnd, appointmentId);

        if(!conflicts.isEmpty()) {
            throw new AppointmentConflictException();
        }

        appointment.reschedule(newStart , newEnd);

        appointmentRepository.save(appointment);

        return appointment;
    }

    public Appointment completeAppointment(Long appointmentId) {

        if (appointmentId == null) {
            throw new IllegalArgumentException("Invalid Input");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment with id " + appointmentId + " not found"
                ));

        appointment.complete();

        appointmentRepository.save(appointment);

        return appointment;
    }
}
