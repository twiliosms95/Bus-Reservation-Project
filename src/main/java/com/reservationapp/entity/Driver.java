package com.reservationapp.entity;
import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId; // Primary key

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @Column(name = "adhaar_number", unique = true)
    private String adhaarNumber;

    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "alternate_contact_number")
    private String alternateContactNumber;

    @Column(name = "email_id", unique = true)
    private String emailId;



}

