package com.haneen.appointmentsystem;

import com.haneen.appointmentsystem.domain.model.Provider;
import com.haneen.appointmentsystem.domain.model.User;
import com.haneen.appointmentsystem.repository.ProviderRepository;
import com.haneen.appointmentsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppointmentsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentsystemApplication.class, args);
    }
    @Bean
    CommandLineRunner seedData(
            UserRepository userRepository,
            ProviderRepository providerRepository) {

        return args -> {
            userRepository.save(
                    new User("Haneen", "haneen@email.com")
            );

            providerRepository.save(
                    new Provider("Dr Test", "Dentist")
            );
        };
    }

}
