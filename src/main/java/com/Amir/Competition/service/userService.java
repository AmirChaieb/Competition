package com.Amir.Competition.service;
import com.Amir.Competition.entity.user;
import com.Amir.Competition.repository.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.Amir.Competition.dtos.UserDto;
import com.Amir.Competition.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class userService implements UserDetailsService{
    @Autowired
    private userRepository UserRepository;
    public List<user> getUsers(){
        return UserRepository.findAll();
    }

    public Optional<user> getUserById(Long id)
    {
        return UserRepository.findById(id);
    }

    public user save (user User)
    {
        return UserRepository.saveAndFlush(User);
    }

    public boolean existById(Long id){
        return UserRepository.existsById(id);
    }

    public void deleteUser(Long id){
        UserRepository.deleteById(id);
    }




    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserRepository.findByEmail(email).orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
    }



    public user getUserById(Long id, Authentication authentication) {
        user User = UserRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
        if (!User.getEmail().equals(authentication.getName())) {
            throw new AppException("Access denied.", HttpStatus.BAD_REQUEST);
        }
        return User;
    }


    public user updateUserById(Long id, UserDto userDto, Authentication authentication) {
        user User = UserRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        if (!User.getEmail().equals(authentication.getName())) {
            throw new AppException("Access denied", HttpStatus.BAD_REQUEST);
        }
        String newEmail = userDto.getEmail();
        String newPassword = userDto.getPassword();

        if (newEmail != null && !newEmail.isEmpty()) {
            User.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedNewPassword = passwordEncoder.encode(newPassword);
            User.setPassword(hashedNewPassword);
        }
        UserRepository.save(User);
        return User;
    }



}
