package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.RoleEntity;
import org.example.entities.UserEntity;
import org.example.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    public boolean registerUser(String username, String password, String imageFilename) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(imageFilename);

        Optional<RoleEntity> userRoleOpt = roleRepository.findByName("User");

        if (userRoleOpt.isPresent()) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(userRoleOpt.get());
            user.setRoles(roles);
        }

        userRepository.save(user);
        return true;
    }

    public List<UserEntity> GetAllUsers() {
        return userRepository.findAll();
    }
}
