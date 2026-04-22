package com.haneen.appointmentsystem.controller;

import com.haneen.appointmentsystem.domain.model.Appointment;
import com.haneen.appointmentsystem.domain.model.AppointmentStatus;
import com.haneen.appointmentsystem.domain.model.Provider;
import com.haneen.appointmentsystem.domain.model.User;
import com.haneen.appointmentsystem.repository.*;
import com.haneen.appointmentsystem.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentControllerTest {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private ProviderRepository providerRepository;

    private AppointmentService service;
    private AppointmentController controller;

    private User user;
    private Provider provider;

    @BeforeEach
    void setup(){
        appointmentRepository = new InMemoryAppointmentRepository();
        userRepository = new InMemoryUserRepository();
        providerRepository = new InMemoryProviderRepository();


        service = new AppointmentService(
                appointmentRepository,
                userRepository,
                providerRepository
        );

        controller = new AppointmentController(service);

        user = new User("User Test", "test@email.com");
        userRepository.save(user);

        provider = new Provider("Dr Test", "Dentist");
        providerRepository.save(provider);
    }

    @Test
    void shouldCreateAppointmentThroughController(){
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        //Act
        Appointment appointment = controller.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //assert
        assertNotNull(appointment);
        assertNotNull(appointment.getId());
        assertEquals(user.getId(), appointment.getUser().getId());
        assertEquals(provider.getId(), appointment.getProvider().getId());
        assertEquals(start, appointment.getStartTime());
        assertEquals(end, appointment.getEndTime());
    }

    @Test
    void shouldRescheduleAppointmentThroughController(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime newStart = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime newEnd = LocalDateTime.of(2026, 4, 1, 13, 0);

        Appointment createdAppointment = controller.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = controller.rescheduleAppointment(
                createdAppointment.getId(),
                newStart,
                newEnd
        );

        //Assert
        assertNotNull(appointment.getId());
        assertEquals(createdAppointment.getId(), appointment.getId());
        assertEquals(newStart, appointment.getStartTime());
        assertEquals(newEnd,appointment.getEndTime());
    }

    @Test
    void shouldCancelAppointmentThroughController(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = controller.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = controller.cancelAppointment(
                createdAppointment.getId()
        );

        //Assert
        assertEquals(createdAppointment.getId(),appointment.getId());
        assertEquals(AppointmentStatus.CANCELLED,appointment.getStatus());
    }

    @Test
    void shouldCompleteAppointmentThroughController(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = controller.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = controller.completeAppointment(
                createdAppointment.getId()
        );

        //Assert
        assertNotNull(appointment);
        assertEquals(createdAppointment.getId(),appointment.getId());
        assertEquals(AppointmentStatus.COMPLETED,appointment.getStatus());
    }
}
