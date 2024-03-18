package com.reservationapp.contoller;

import com.reservationapp.entity.UserRegistration;
import com.reservationapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserReservationController {

    @Autowired
    private UserServiceImpl userService;


    //http://localhost:8080/api/v1/user
    @PostMapping
        public String createUser(
                @RequestParam("name") String name,
                @RequestParam("email") String email,
                @RequestParam("password") String password,
                @RequestParam("profilePicture")MultipartFile profilePicture
                ){
            try{
                UserRegistration userRegistration = new UserRegistration();
                userRegistration.setName(name);
                userRegistration.setEmail(email);
                userRegistration.setPassword(password);
                userRegistration.setProfilePicture(profilePicture.getBytes());

                userService.createUser(userRegistration);
                }catch (Exception e){
                    e.printStackTrace();
                }
            return "Done...!";
    }

    //http://localhost:8080/api/v1/user/1
    @GetMapping("/{id}")
    public ResponseEntity<UserRegistration> getUserById(@PathVariable Long id) {
        UserRegistration user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/user/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User is deleted successfully",HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/user/allusers
    @GetMapping("/allusers")
    public ResponseEntity<List<UserRegistration>> getAllUsers() {
        List<UserRegistration> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}











