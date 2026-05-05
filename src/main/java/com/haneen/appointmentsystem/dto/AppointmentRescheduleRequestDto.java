package com.haneen.appointmentsystem.dto;

import java.time.LocalDateTime;

public class AppointmentRescheduleRequestDto {


    private LocalDateTime newStart;
    private LocalDateTime newEnd;

    public LocalDateTime getNewStart() {
        return newStart;
    }

    public void setNewStart(LocalDateTime newStart) {
        this.newStart = newStart;
    }

    public LocalDateTime getNewEnd() {
        return newEnd;
    }

    public void setNewEnd(LocalDateTime newEnd) {
        this.newEnd = newEnd;
    }
}
