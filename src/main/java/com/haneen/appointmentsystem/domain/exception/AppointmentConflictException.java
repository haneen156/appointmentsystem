package com.haneen.appointmentsystem.domain.exception;

public class AppointmentConflictException extends RuntimeException {
    public AppointmentConflictException() {
        super("Appointment time conflicts with an existing appointment");
    }
}
