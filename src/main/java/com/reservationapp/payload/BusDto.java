package com.reservationapp.payload;

import lombok.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {

    private String busNumber;
    private String busType;
    private double price;
    private int totalSeats;
    private int availableSeats;


}
