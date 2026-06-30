package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Role;
import com.elderdev.orderFlow.entity.User;
import com.elderdev.orderFlow.exception.AlreadyExistsException;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByid(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("User not found");
    }

    public User register(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new AlreadyExistsException("This username already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new AlreadyExistsException("This email already in use");
        }

        var hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }
}
