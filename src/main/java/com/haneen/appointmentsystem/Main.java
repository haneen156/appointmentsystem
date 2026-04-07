package com.haneen.appointmentsystem;

import com.haneen.appointmentsystem.domain.model.*;
import com.haneen.appointmentsystem.repository.*;
import com.haneen.appointmentsystem.service.AppointmentService;

import java.time.LocalDateTime;

public class Main {
        public static void main(String[] args) {
            System.out.println("System started...");

            // 1. Create repositories
            InMemoryUserRepository userRepo = new InMemoryUserRepository();
            InMemoryProviderRepository providerRepo = new InMemoryProviderRepository();
            InMemoryAppointmentRepository appointmentRepo = new InMemoryAppointmentRepository();

            // 2. Create and save user
            User user = new User("Haneen", "haneen@email.com");
            userRepo.save(user);

            // 3. Create and save provider
            Provider provider = new Provider("Dr. Smith", "Dentist");
            providerRepo.save(provider);

            // 4. Create service (inject dependencies)
            AppointmentService service = new AppointmentService(
                    appointmentRepo,
                    userRepo,
                    providerRepo
            );

            //5. Call service method (test)
//            Appointment appointment = service.createAppointment(
//                    user.getId(),
//                    provider.getId(),
//                    LocalDateTime.of(2026, 4, 1, 10, 0),
//                    LocalDateTime.of(2026, 4, 1, 11, 0)
//            );
//
//            System.out.println("Created appointment with id: " + appointment.getId());

//            //7.test reschedule
//            service.rescheduleAppointment(
//                    appointment.getId(),
//                    LocalDateTime.of(2026, 4, 1, 12, 0),
//                    LocalDateTime.of(2026, 4, 1, 13, 0)
//            );
//
//            System.out.println("Rescheduled successfully");
//
//            //8.test cancel
//            service.cancelAppointment(appointment.getId());
//            System.out.println("Cancelled successfully");

            //9. Edge cases (Manual tests)
            //Cancel Twice
//            try {
//                service.cancelAppointment(appointment.getId());
//                service.cancelAppointment(appointment.getId()); // second cancel
//            } catch (Exception e) {
//                System.out.println("Cancel twice: " + e.getMessage());
//            }

            //Reschedule after cancelling appointment
//            try {
//                service.cancelAppointment(appointment.getId());
//
//                service.rescheduleAppointment(
//                        appointment.getId(),
//                        LocalDateTime.of(2026, 4, 2, 10, 0),
//                        LocalDateTime.of(2026, 4, 2, 11, 0)
//                );
//            } catch (Exception e) {
//                System.out.println("Reschedule after cancel: " + e.getMessage());
//            }

            //Overlap Conflict
//            try {
//                service.createAppointment(
//                        user.getId(),
//                        provider.getId(),
//                        LocalDateTime.of(2026, 4, 1, 10, 30),
//                        LocalDateTime.of(2026, 4, 1, 11, 30)
//                );
//            } catch (Exception e) {
//                System.out.println("Expected conflict: " + e.getMessage());
//            }

            //Invalid time start > end
//            try {
//                service.createAppointment(
//                        user.getId(),
//                        provider.getId(),
//                        LocalDateTime.of(2026, 4, 3, 14, 0),
//                        LocalDateTime.of(2026, 4, 3, 13, 0)
//                );
//            } catch (Exception e) {
//                System.out.println("Invalid time: " + e.getMessage());
//            }

            //Non_existent appointment
//            try {
//                service.cancelAppointment(999L);
//            } catch (Exception e) {
//                System.out.println("Not found: " + e.getMessage());
//            }

            //null inputs
//            try {
//                service.createAppointment(null, null, null, null);
//            } catch (Exception e) {
//                System.out.println("Null input: " + e.getMessage());
//            }

            //complete state
//            service.completeAppointment(appointment.getId());
//            System.out.println("Completed successfully");

            //complete twice
//            try {
//                service.completeAppointment(appointment.getId());
//            } catch (Exception e) {
//                System.out.println("Complete twice: " + e.getMessage());
//            }

            //trying to cancel a completed appointment
//            service.cancelAppointment(appointment.getId());
//            try {
//                service.completeAppointment(appointment.getId());
//            } catch (Exception e) {
//                System.out.println("Complete after cancel: " + e.getMessage());
//            }

            //rescheduling an already completed appointment
//            service.completeAppointment(appointment.getId());
//            try {
//                service.rescheduleAppointment(
//                        appointment.getId(),
//                        LocalDateTime.of(2026, 4, 2, 10, 0),
//                        LocalDateTime.of(2026, 4, 2, 11, 0)
//                );
//            } catch (Exception e) {
//                System.out.println("Reschedule after complete: " + e.getMessage());
//            }
        }

}
