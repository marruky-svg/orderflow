package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.User;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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
}
