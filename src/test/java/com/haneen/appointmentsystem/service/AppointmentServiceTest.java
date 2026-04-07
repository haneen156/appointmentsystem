package com.haneen.appointmentsystem.service;

import com.haneen.appointmentsystem.domain.exception.*;
import com.haneen.appointmentsystem.domain.model.*;
import com.haneen.appointmentsystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class AppointmentServiceTest {

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private ProviderRepository providerRepository;

    private AppointmentService service;

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

        user = new User("User Test", "test@email.com");
        userRepository.save(user);

        provider = new Provider("Dr Test", "Dentist");
        providerRepository.save(provider);
    }

    @Test
    void shouldCreateAppointmentSuccessfully() {
        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        //Act
        Appointment appointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Assert
        assertNotNull(appointment.getId());
        assertEquals(user.getId(),appointment.getUser().getId());
        assertEquals(provider.getId(),appointment.getProvider().getId());
        assertEquals(start, appointment.getStartTime());
        assertEquals(end,appointment.getEndTime());
    }

    @Test
    void shouldRescheduleAppointmentSuccessfully(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime newStart = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime newEnd = LocalDateTime.of(2026, 4, 1, 13, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = service.rescheduleAppointment(
                createdAppointment.getId(),
                newStart,
                newEnd
        );

        //Assert
        assertNotNull(appointment.getId());
        assertEquals(newStart, appointment.getStartTime());
        assertEquals(newEnd,appointment.getEndTime());
    }

    @Test
    void shouldCancelAppointmentSuccessfully(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = service.cancelAppointment(
                createdAppointment.getId()
        );

        //Assert
        assertEquals(createdAppointment.getId(),appointment.getId());
        assertEquals(AppointmentStatus.CANCELLED,appointment.getStatus());
    }

    @Test
    void shouldCompleteAppointmentSuccessfully(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        Appointment appointment = service.completeAppointment(
                createdAppointment.getId()
        );

        //Assert
        assertNotNull(appointment);
        assertEquals(createdAppointment.getId(),appointment.getId());
        assertEquals(AppointmentStatus.COMPLETED,appointment.getStatus());
    }



    //Edge Cases
    //1. Cancel Twice

    @Test
    void shouldThrowWhenCancellingAlreadyCancelledAppointment(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        service.cancelAppointment(createdAppointment.getId());

        //Assert
        assertThrows(AppointmentAlreadyCancelledException.class, ()-> {
            service.cancelAppointment(createdAppointment.getId());
        });
    }

    //2. complete twice
    @Test
    void shouldThrowWhenCompletingAlreadyCompletedAppointment(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        service.completeAppointment(createdAppointment.getId());

        //Assert
        assertThrows(AppointmentAlreadyCompletedException.class, ()-> {
            service.completeAppointment(createdAppointment.getId());
        });
    }

    //3. Attempting to Reschedule a cancelled appointment
    @Test
    void  shouldThrowWhenReschedulingCancelledAppointment(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime newStart = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime newEnd = LocalDateTime.of(2026, 4, 1, 13, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        service.cancelAppointment(createdAppointment.getId());

        //Assert
        assertThrows(AppointmentAlreadyCancelledException.class, ()-> {
            service.rescheduleAppointment(
                    createdAppointment.getId(),
                    newStart,
                    newEnd
            );
        });
    }

    //4. Attempting to Reschedule a completed appointment
    @Test
    void  shouldThrowWhenReschedulingCompletedAppointment(){

        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime newStart = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime newEnd = LocalDateTime.of(2026, 4, 1, 13, 0);

        Appointment createdAppointment = service.createAppointment(
                user.getId(),
                provider.getId(),
                start,
                end
        );

        //Act
        service.completeAppointment(createdAppointment.getId());

        //Assert
        assertThrows(AppointmentAlreadyCompletedException.class, ()-> {
            service.rescheduleAppointment(
                    createdAppointment.getId(),
                    newStart,
                    newEnd
            );
        });
    }

    //5. Overlap conflict
    @Test
    void shouldThrowWhenAppointmentsOverlap() {
        //Arrange
        LocalDateTime start1 = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 4, 1, 10, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 4, 1, 11, 30);

        //Act
        Appointment appointment1 = service.createAppointment(
                user.getId(),
                provider.getId(),
                start1,
                end1
        );

        //Assert
        assertNotNull(appointment1);
        assertThrows(AppointmentConflictException.class, ()-> {
            service.createAppointment(
                    user.getId(),
                    provider.getId(),
                    start2,
                    end2
            );
        });
    }

    //6. start > end time
    @Test
    void shouldThrowWhenStartTimeIsAfterEndTime() {
        //Arrange
        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 14, 0); // after end
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 13, 0);

        //Act + Assert
        assertThrows(InvalidAppointmentTimeException.class, () -> {
            service.createAppointment(
                    user.getId(),
                    provider.getId(),
                    start,
                    end
            );
        });
    }

    //7.null case
    @Test
    void shouldThrowWhenAllInputsAreNull() {

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.createAppointment(
                    null,
                    null,
                    null,
                   null
            );
        });
    }

    //8. cancelling a non-existent appointment

    @Test
    void shouldThrowWhenCancellingNonExistentAppointment() {

        // Arrange
        Long nonExistentAppointmentId = 999L;

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.cancelAppointment(nonExistentAppointmentId);
        });
    }

    //10. back to back Appointments
    @Test
    void shouldAllowBackToBackAppointments() {

        LocalDateTime start1 = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 4, 1, 11, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 4, 1, 11, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 4, 1, 12, 0);

        service.createAppointment(user.getId(), provider.getId(), start1, end1);

        assertDoesNotThrow(() -> {
            service.createAppointment(user.getId(), provider.getId(), start2, end2);
        });
    }

    @Test
    void shouldNotConflictAcrossDifferentProviders() {

        Provider anotherProvider = new Provider("Dr B", "Dentist");
        providerRepository.save(anotherProvider);

        LocalDateTime start = LocalDateTime.of(2026, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 1, 11, 0);

        // Appointment for provider A
        service.createAppointment(user.getId(), provider.getId(), start, end);

        // SAME TIME but different provider → should be allowed
        assertDoesNotThrow(() -> {
            service.createAppointment(user.getId(), anotherProvider.getId(), start, end);
        });
    }

}
