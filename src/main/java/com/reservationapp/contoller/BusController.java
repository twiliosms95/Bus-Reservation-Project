package com.reservationapp.contoller;


import com.reservationapp.entity.Bus;
import com.reservationapp.entity.Route;
import com.reservationapp.entity.SubRoute;
import com.reservationapp.exception.ResourceNotFoundException;
import com.reservationapp.payload.BusDto;
import com.reservationapp.payload.SearchListOfBusesDto;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.RouteRepository;
import com.reservationapp.repository.SubRouteRepository;
import com.reservationapp.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SubRouteRepository subRouteRepository;


    //http://localhost:8080/api/v1/bus/add
    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestBody BusDto busDto) throws ParseException{

        Bus bus = busService.addBus(busDto);
        return new ResponseEntity<>(bus,HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/bus?fromLocation=&toLocation=&fromDate
    //http://localhost:8080/api/v1/bus?fromLocation=Cty%20A&toLocation=City%20B&fromDate=2024-03-10

    @GetMapping
    public List<SearchListOfBusesDto> getAllBuses(@RequestParam String fromLocation,
                                 @RequestParam String toLocation,
                                 @RequestParam String fromDate){
        List<Route> routes = routeRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);
        List<SubRoute> subRoutes = subRouteRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);

        List<SearchListOfBusesDto> buses = new ArrayList<>();

        if(routes != null) {
            for (Route route : routes) {
                Bus bus = busRepository.findById(route.getBusId()).get();
                SearchListOfBusesDto searchListOfBusesDto = mapToSearchListOfBusesDto(bus, route);
                buses.add(searchListOfBusesDto);
            }
            return buses;
        }

        if(subRoutes != null) {
            for (SubRoute route : subRoutes) {
                Bus bus = busRepository.findById(route.getBusId()).get();
                SearchListOfBusesDto searchListOfBusesDto = mapToSearchListOfBusesDto(bus, route);
                buses.add(searchListOfBusesDto);
            }
            return buses;
        }


        return null;
    }

        SearchListOfBusesDto mapToSearchListOfBusesDto(Bus bus,Route route){
            SearchListOfBusesDto searchListOfBusesDto = new SearchListOfBusesDto();
            searchListOfBusesDto.setBusId(bus.getId());
            searchListOfBusesDto.setBusNumber(bus.getBusNumber());
            searchListOfBusesDto.setPrice(bus.getPrice());
            searchListOfBusesDto.setBusType(bus.getBusType());
            searchListOfBusesDto.setTotalSeats(bus.getTotalSeats());
            searchListOfBusesDto.setAvailableSeats(bus.getAvailableSeats());

            searchListOfBusesDto.setRouteId(route.getId());
            searchListOfBusesDto.setFromLocation(route.getFromLocation());
            searchListOfBusesDto.setToLocation(route.getToLocation());
            searchListOfBusesDto.setFromDate(route.getFromDate());
            searchListOfBusesDto.setToDate(route.getToDate());
            searchListOfBusesDto.setFromTime(route.getFromTime());
            searchListOfBusesDto.setToTime(route.getToTime());
            searchListOfBusesDto.setTotalDuration(route.getTotalDuration());
            return searchListOfBusesDto;

        }


    SearchListOfBusesDto mapToSearchListOfBusesDto(Bus bus,SubRoute route){
        SearchListOfBusesDto searchListOfBusesDto = new SearchListOfBusesDto();
        searchListOfBusesDto.setBusId(bus.getId());
        searchListOfBusesDto.setBusNumber(bus.getBusNumber());
        searchListOfBusesDto.setPrice(bus.getPrice());
        searchListOfBusesDto.setBusType(bus.getBusType());
        searchListOfBusesDto.setTotalSeats(bus.getTotalSeats());
        searchListOfBusesDto.setAvailableSeats(bus.getAvailableSeats());

        searchListOfBusesDto.setRouteId(route.getId());
        searchListOfBusesDto.setFromLocation(route.getFromLocation());
        searchListOfBusesDto.setToLocation(route.getToLocation());
        searchListOfBusesDto.setFromDate(route.getFromDate());
        searchListOfBusesDto.setToDate(route.getToDate());
        searchListOfBusesDto.setFromTime(route.getFromTime());
        searchListOfBusesDto.setToTime(route.getToTime());
        searchListOfBusesDto.setTotalDuration(route.getTotalDuration());
        return searchListOfBusesDto;

    }


    //http://localhost:8080/api/v1/bus/id
      @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteBus(@PathVariable long id){
            busService.deleteBus(id);
            return new ResponseEntity<>("Record Deleted Successfully",HttpStatus.OK);
      }

      //http://localhost:8080/api/v1/bus/particular?id=1
      @GetMapping("/particular")
        public ResponseEntity<BusDto> getBusById(@RequestParam long id){
          BusDto dto = busService.getBusById(id);
          return new ResponseEntity<>(dto,HttpStatus.OK);

      }

    //http://localhost:8080/api/v1/bus/all
    @GetMapping("/all")
    public List<Bus> getAllBuses() {
        return busService.getAllBuses();
    }


    //http://localhost:8080/api/v1/bus/1
    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable long id, @RequestBody BusDto busDto) {
        Bus updatedBus = busService.updateBus(id, busDto);
        return new ResponseEntity<>(updatedBus, HttpStatus.OK);
    }

}



