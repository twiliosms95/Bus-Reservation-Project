package com.reservationapp.payload;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    private long id;
    private String name;
    private String email;
    private String password;


    private byte[] profilePicture;
}
