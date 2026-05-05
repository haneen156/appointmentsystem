package com.haneen.appointmentsystem.controller;

import com.haneen.appointmentsystem.domain.model.Appointment;
import com.haneen.appointmentsystem.dto.*;
import com.haneen.appointmentsystem.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    //dependency injection 
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto){
        Appointment appointment = appointmentService.createAppointment(
                appointmentRequestDto.getUserId(),
                appointmentRequestDto.getProviderId(),
                appointmentRequestDto.getStartTime(),
                appointmentRequestDto.getEndTime()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointment);
    }

    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<Appointment> cancelAppointment( @PathVariable Long appointmentId){

        Appointment appointment = appointmentService.cancelAppointment(appointmentId);

        return ResponseEntity.ok(appointment);
    }

    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity<Appointment> completeAppointment( @PathVariable Long appointmentId){
        Appointment appointment = appointmentService.completeAppointment(appointmentId);

        return ResponseEntity.ok(appointment);
    }

    @PatchMapping("/{appointmentId}/reschedule")
    public ResponseEntity<Appointment> rescheduleAppointment( @PathVariable Long appointmentId, @RequestBody AppointmentRescheduleRequestDto request){

        Appointment appointment = appointmentService.rescheduleAppointment(
                appointmentId,
                request.getNewStart(),
                request.getNewEnd()
        );

        return ResponseEntity.ok(appointment);
    }
}
