package com.haneen.appointmentsystem.controller;

import com.haneen.appointmentsystem.domain.model.Appointment;
import com.haneen.appointmentsystem.domain.model.AppointmentStatus;
import com.haneen.appointmentsystem.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    //dependency injection
    private final AppointmentService appointmentService;

    public ProviderController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{providerId}/appointments")
    public ResponseEntity<List<Appointment>> getProviderAppointments(@PathVariable Long providerId,
                                                                     @RequestParam(required = false) AppointmentStatus status){
        List<Appointment> appointments = appointmentService.getAppointmentsByProvider(providerId, status);

        return ResponseEntity.ok(appointments);
    }

}
