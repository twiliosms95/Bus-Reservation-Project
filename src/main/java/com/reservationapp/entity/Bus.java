package com.reservationapp.entity;

import com.reservationapp.entity.Route;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(name = "bus_number",unique = true)
    private String busNumber;
    private String busType;
    private double price;
    private int totalSeats;
    private int availableSeats;

}

