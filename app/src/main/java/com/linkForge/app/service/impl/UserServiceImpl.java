package com.linkForge.app.service.impl;
import com.linkForge.app.model.User;
import com.linkForge.app.repository.UserRepository;
import com.linkForge.app.service.UserService;
import com.linkForge.app.util.JwtUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if(existingUser == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;

    }

    public String loginUser(User user) {

        User existingUser = userRepository
                .findByUsername(user.getUsername())
                .orElse(null);

        if(existingUser == null) {
            return null;
        }

        boolean isPasswordCorrect =
                passwordEncoder.matches(
                        user.getPassword(),
                        existingUser.getPassword()
                );

        if(!isPasswordCorrect){
            return null;
        }

        return jwtUtil.generateToken(existingUser.getUsername());


    }


}
