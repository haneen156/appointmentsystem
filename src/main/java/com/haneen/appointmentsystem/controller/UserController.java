package com.haneen.appointmentsystem.controller;

import com.haneen.appointmentsystem.domain.model.*;
import com.haneen.appointmentsystem.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //dependency injection
    private final AppointmentService appointmentService;

    public UserController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{userId}/appointments")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long userId,
                                                                 @RequestParam(required = false) AppointmentStatus status ){
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(userId,status);

        return ResponseEntity.ok(appointments);

    }

}
