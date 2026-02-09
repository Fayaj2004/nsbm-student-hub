package lk.nsbm.studenthub.config;

import lk.nsbm.studenthub.entity.AppUser;
import lk.nsbm.studenthub.entity.Role;
import lk.nsbm.studenthub.repository.RoleRepository;
import lk.nsbm.studenthub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_ADMIN").build()));
            Role userRole  = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_USER").build()));

            // Create default admin if not exists
            if (!userRepo.existsByUsername("admin")) {
                AppUser admin = AppUser.builder()
                        .username("admin")
                        .password(encoder.encode("admin123"))
                        .roles(Set.of(adminRole))
                        .build();
                userRepo.save(admin);
            }

            // Create default user if not exists
            if (!userRepo.existsByUsername("user")) {
                AppUser user = AppUser.builder()
                        .username("user")
                        .password(encoder.encode("user123"))
                        .roles(Set.of(userRole))
                        .build();
                userRepo.save(user);
            }
        };
    }
}
