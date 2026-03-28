package com.haneen.appointmentsystem.domain.model;

import com.haneen.appointmentsystem.domain.exception.AppointmentAlreadyCancelledException;
import com.haneen.appointmentsystem.domain.exception.AppointmentAlreadyCompletedException;
import com.haneen.appointmentsystem.domain.exception.InvalidAppointmentTimeException;

import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private User user;
    private Provider provider;

    public Appointment( LocalDateTime startTime, LocalDateTime endTime, User user, Provider provider) {

        if (startTime == null || endTime == null) {
            throw new InvalidAppointmentTimeException();
        }

        if (!endTime.isAfter(startTime)) {
            throw new InvalidAppointmentTimeException();
        }

        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.provider = provider;
        this.status = AppointmentStatus.BOOKED;
    }

    //getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Provider getProvider() {
        return provider;
    }

    public void cancel(){
        if(this.status == AppointmentStatus.COMPLETED){
            throw new AppointmentAlreadyCompletedException();
        }
        if(this.status == AppointmentStatus.CANCELLED){
            throw new AppointmentAlreadyCancelledException();
        }
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete(){
        if (this.status == AppointmentStatus.COMPLETED) {
                throw new AppointmentAlreadyCompletedException();
        }
        if (this.status == AppointmentStatus.CANCELLED) {
                throw new AppointmentAlreadyCancelledException();
        }
        this.status = AppointmentStatus.COMPLETED;
    }
    public void reschedule(LocalDateTime newStart, LocalDateTime newEnd){
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new AppointmentAlreadyCompletedException();
        }
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new AppointmentAlreadyCancelledException();
        }
        if(newStart == null || newEnd == null){
            throw new InvalidAppointmentTimeException();
        }
        if (!newEnd.isAfter(newStart)) {
            throw new InvalidAppointmentTimeException();
        }
        this.startTime = newStart;
        this.endTime = newEnd;
    }


}
