package com.haneen.appointmentsystem.interfaces;

import com.haneen.appointmentsystem.domain.model.Appointment;
import com.haneen.appointmentsystem.domain.model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentServiceInterface {

    public Appointment createAppointment(Long userId, Long providerId, LocalDateTime startTime, LocalDateTime endTime);

    public Appointment cancelAppointment(Long appointmentId);

    public Appointment completeAppointment(Long appointmentId);

    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newStart, LocalDateTime newEnd);

    public List<Appointment> getAppointmentsByUser(Long userId, AppointmentStatus status);

    public List<Appointment> getAppointmentsByProvider(Long providerId, AppointmentStatus status);
}
