package com.reservationapp.entity;
import javax.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_registrations")
public class UserRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;

    @Lob
    @Column(name = "profile_picture", length = 100000)
    private byte[] profilePicture;
}
