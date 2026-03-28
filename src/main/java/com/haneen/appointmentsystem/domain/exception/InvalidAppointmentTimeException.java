package com.haneen.appointmentsystem.domain.exception;

public class InvalidAppointmentTimeException extends RuntimeException {
    public InvalidAppointmentTimeException(){
        super("Cannot perform action: can't reschedule appointment to this time!");
    }
}
