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

            // 5. Call service method (test)
            Appointment appointment = service.createAppointment(
                    user.getId(),
                    provider.getId(),
                    LocalDateTime.of(2026, 4, 1, 10, 0),
                    LocalDateTime.of(2026, 4, 1, 11, 0)
            );

            System.out.println("Created appointment with id: " + appointment.getId());

            //6.test overlap
            try {
                service.createAppointment(
                        user.getId(),
                        provider.getId(),
                        LocalDateTime.of(2026, 4, 1, 10, 30),
                        LocalDateTime.of(2026, 4, 1, 11, 30)
                );
            } catch (Exception e) {
                System.out.println("Expected conflict: " + e.getMessage());
            }

            //7.test reschedule
            service.rescheduleAppointment(
                    appointment.getId(),
                    LocalDateTime.of(2026, 4, 1, 12, 0),
                    LocalDateTime.of(2026, 4, 1, 13, 0)
            );

            System.out.println("Rescheduled successfully");

            //8.test cancel
            service.cancelAppointment(appointment.getId());
            System.out.println("Cancelled successfully");
        }

}
