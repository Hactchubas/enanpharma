package com.ecommerce.enanpharma.service;

import com.ecommerce.enanpharma.dto.UserDTO;
import com.ecommerce.enanpharma.dto.UserResponseDTO;
import com.ecommerce.enanpharma.exception.ResourceNotFoundException;
import com.ecommerce.enanpharma.exception.ConflictException;
import com.ecommerce.enanpharma.entity.User;
import com.ecommerce.enanpharma.repository.RoleRepository;
import com.ecommerce.enanpharma.repository.UserRepository;
import com.ecommerce.enanpharma.entity.Role;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO userDTO) {
        userDTO.setRoles(Set.of("USER"));

        User user = toEntity(userDTO);
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public UserDTO findById(Long id) {
        return toDTO(
                userRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found"))
        );
    }

    public UserDTO findByUsername(String username) {
        return toDTO(
                userRepository.findByUsername(username)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found"))
        );
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (existingUser == null) {
            return null;
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());

        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        }
        User updatedUser = userRepository.save(existingUser);
        return toDTO(updatedUser);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Response methods that exclude password
    public List<UserResponseDTO> findAllForResponse() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findByIdForResponse(Long id) {
        return toResponseDTO(
                userRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found"))
        );
    }

    public UserResponseDTO findByUsernameForResponse(String username) {
        return toResponseDTO(
                userRepository.findByUsername(username)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found"))
        );
    }

    public UserResponseDTO updateForResponse(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (existingUser == null) {
            return null;
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());

        if (userDTO.getRoles() != null) {
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        }
        User updatedUser = userRepository.save(existingUser);
        return toResponseDTO(updatedUser);
    }


    // Helpers


    private User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            roles = userDTO.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
        } else {
            // Default to USER role if no roles specified
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Default USER role not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);
        return user;
    }


    private UserDTO toDTO(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                roleNames,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    private UserResponseDTO toResponseDTO(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                roleNames,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
