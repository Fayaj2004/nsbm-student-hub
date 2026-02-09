package lk.nsbm.studenthub.service;

import lk.nsbm.studenthub.dto.RegisterRequest;
import lk.nsbm.studenthub.entity.AppUser;
import lk.nsbm.studenthub.entity.Role;
import lk.nsbm.studenthub.repository.RoleRepository;
import lk.nsbm.studenthub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already exists: " + req.username());
        }

        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not found in DB"));

        AppUser user = AppUser.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .roles(Set.of(userRole))
                .build();

        userRepo.save(user);
        return "User registered: " + req.username();
    }
}
