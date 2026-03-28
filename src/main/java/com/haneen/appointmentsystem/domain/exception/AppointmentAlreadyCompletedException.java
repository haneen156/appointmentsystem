package com.haneen.appointmentsystem.domain.exception;

public class AppointmentAlreadyCompletedException extends RuntimeException{

    public AppointmentAlreadyCompletedException(){
        super("Cannot perform action: appointment is already completed");
    }
}
