package com.haneen.appointmentsystem.interfaces;

import com.haneen.appointmentsystem.domain.model.Appointment;

import java.time.LocalDateTime;

public interface AppointmentServiceInterface {

    public Appointment createAppointment(Long userId, Long providerId, LocalDateTime startTime, LocalDateTime endTime);

    public Appointment cancelAppointment(Long appointmentId);

    public Appointment completeAppointment(Long appointmentId);

    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newStart, LocalDateTime newEnd);
}
