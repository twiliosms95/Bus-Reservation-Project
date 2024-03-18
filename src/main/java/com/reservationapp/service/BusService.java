package com.reservationapp.service;

import com.reservationapp.entity.*;
import com.reservationapp.exception.ResourceNotFoundException;
import com.reservationapp.payload.*;
import com.reservationapp.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Import statements

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus addBus(BusDto busDto) {
        Bus bus = new Bus();
        bus.setBusNumber(busDto.getBusNumber());
        bus.setBusType(busDto.getBusType());
        bus.setPrice(busDto.getPrice());
        bus.setTotalSeats(busDto.getTotalSeats());
        bus.setAvailableSeats(busDto.getAvailableSeats());


        Bus savedBus = busRepository.save(bus);
        return savedBus;
    }

    public void deleteBus(long id) {
        busRepository.deleteById(id);
    }

        public BusDto getBusById(long id) {
            Bus bus = busRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Bus NOT Found with id:" + id)
            );
            BusDto busDto = new BusDto();
            busDto.setBusNumber(bus.getBusNumber());
            busDto.setBusType(bus.getBusType());
            busDto.setPrice(bus.getPrice());
            busDto.setTotalSeats(bus.getTotalSeats());
            busDto.setAvailableSeats(bus.getAvailableSeats());

            return busDto;
        }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus updateBus(long id, BusDto busDto) {
        Bus bus = busRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bus Not Found with id:" + id)
                );
        // Copy updated fields from DTO to entity
        BeanUtils.copyProperties(busDto, bus, "id");

        // Save the updated entity
        return busRepository.save(bus);
    }
}
