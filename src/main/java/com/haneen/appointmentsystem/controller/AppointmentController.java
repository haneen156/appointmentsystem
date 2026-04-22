package com.haneen.appointmentsystem.controller;

import com.haneen.appointmentsystem.domain.model.Appointment;
import com.haneen.appointmentsystem.service.AppointmentService;

import java.time.LocalDateTime;

public class AppointmentController {

    private AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    public Appointment createAppointment(Long userId, Long providerId, LocalDateTime startTime, LocalDateTime endTime){
        return service.createAppointment(userId, providerId, startTime, endTime);
    }

    public Appointment cancelAppointment( Long appointmentId ){
        return service.cancelAppointment(appointmentId);
    }

    public Appointment completeAppointment( Long appointmentId ){
        return service.completeAppointment(appointmentId);
    }

    public Appointment rescheduleAppointment( Long appointmentId, LocalDateTime newStart , LocalDateTime newEnd){
        return service.rescheduleAppointment(appointmentId, newStart, newEnd);
    }
}
