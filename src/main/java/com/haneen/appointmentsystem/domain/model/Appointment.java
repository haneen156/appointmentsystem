package com.haneen.appointmentsystem.domain.model;

import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private User user;
    private Provider provider;

    public Appointment( LocalDateTime startTime, LocalDateTime endTime, User user, Provider provider) {
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

}
