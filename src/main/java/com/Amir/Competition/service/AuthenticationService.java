package com.Amir.Competition.service;


import com.Amir.Competition.dtos.UserLoginDto;
import com.Amir.Competition.entity.user;
import com.Amir.Competition.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import com.Amir.Competition.repository.userRepository;
import com.Amir.Competition.repository.UserRoleRepository;


import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private userRepository UserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public user register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserRole userRole = userRoleRepository.findByAuthority("USER").get();
        Set<UserRole> authorities = new HashSet<>();

        authorities.add(userRole);

        return UserRepository.save(new user(email, encodedPassword, authorities));
    }

    public UserLoginDto login(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            user User = UserRepository.findByEmail(email).orElse(null);
            if (User != null) {
                return new UserLoginDto(User.getId(), User, token);
            } else {
                return new UserLoginDto(null, null, "");
            }

        } catch (AuthenticationException e) {
            return new UserLoginDto(null, null, "");
        }
    }
}
