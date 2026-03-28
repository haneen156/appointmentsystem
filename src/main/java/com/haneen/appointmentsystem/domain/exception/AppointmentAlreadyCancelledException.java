package com.haneen.appointmentsystem.domain.exception;

public class AppointmentAlreadyCancelledException extends RuntimeException {
    public AppointmentAlreadyCancelledException(){
        super("Cannot perform action: this appointment is already cancelled");
    }
}
