package com.nikita.bulak.mediaplatform.user.controller;

import com.nikita.bulak.mediaplatform.user.configuration.jwt.JwtUtils;
import com.nikita.bulak.mediaplatform.user.dto.JwtResponseDto;
import com.nikita.bulak.mediaplatform.user.dto.LoginRequestDto;
import com.nikita.bulak.mediaplatform.user.dto.MessageResponseDto;
import com.nikita.bulak.mediaplatform.user.dto.SignupRequestDto;
import com.nikita.bulak.mediaplatform.user.model.ERole;
import com.nikita.bulak.mediaplatform.user.model.Role;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.RoleRepository;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import com.nikita.bulak.mediaplatform.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDto(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Username exists"));
        }

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Email exists"));
        }

        User user = new User(signupRequestDto.getUsername(),
                passwordEncoder.encode(signupRequestDto.getPassword()),
                signupRequestDto.getEmail()
                );

        Set<String> reqRoles = signupRequestDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findRoleByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findRoleByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository
                                .findRoleByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponseDto("User CREATED"));
    }
}
