package com.reservationapp.service;

import com.reservationapp.entity.UserRegistration;
import com.reservationapp.exception.ResourceNotFoundException;
import com.reservationapp.payload.UserRegistrationDto;
import com.reservationapp.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public UserRegistration getUserById(Long id) {
        UserRegistration userRegistration = userRegistrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not Found with id :" + id)
        );
        return userRegistration;
    }


    public UserRegistrationDto createUser(UserRegistration userRegistration){
        userRegistrationRepository.save(userRegistration);
        return null;
    }

    public void deleteUser(Long id) {
        userRegistrationRepository.deleteById(id);
    }

    public List<UserRegistration> getAllUsers() {
        return userRegistrationRepository.findAll();
    }
}
