package com.reservationapp.contoller;

import com.reservationapp.entity.Bus;
import com.reservationapp.entity.Passenger;
import com.reservationapp.entity.Route;
import com.reservationapp.entity.SubRoute;
import com.reservationapp.exception.ResourceNotFoundException;
import com.reservationapp.payload.ReservationDto;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.PassengerRepository;
import com.reservationapp.repository.RouteRepository;
import com.reservationapp.repository.SubRouteRepository;
import com.reservationapp.util.EmailService;
import com.reservationapp.util.ExcelService;
import com.reservationapp.util.PdfTicketGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SubRouteRepository subRouteRepository;

    @Autowired
    private PdfTicketGeneratorService pdfTicketGeneratorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelService excelService;

    //http://localhost:8080/api/v1/reservation?busId=1&routeId=1
    @PostMapping
    public ResponseEntity<String> bookTicket(
            @RequestParam long busId,
            @RequestParam long routeId,
            @RequestBody Passenger passenger
            ){

        boolean busIsPresent=false;
        boolean routeIsPresent=false;
        boolean SubRouteIsPresent=false;

        Optional<Bus> byId = busRepository.findById(busId);

        if (byId.isPresent()){
            busIsPresent=true;
            Bus bus = byId.get();

        }
        Optional<Route> byRouteId = routeRepository.findById(routeId);

        if (byRouteId.isPresent()){
            routeIsPresent=true;
            Route route = byRouteId.get();
        }

        Optional<SubRoute> bySubRouteId = subRouteRepository.findById(routeId);

        if (bySubRouteId.isPresent()){
            SubRouteIsPresent=true;
            SubRoute subRoute = bySubRouteId.get();
        }

        if (busIsPresent&&routeIsPresent || busIsPresent&&SubRouteIsPresent){
        //Add Passenger Details 
        Passenger p = new Passenger();
        p.setFirstName(passenger.getFirstName());
        p.setLastName(passenger.getLastName());
        p.setEmail(passenger.getEmail());
        p.setMobile(passenger.getMobile());
        p.setBusId(busId);
        p.setRouteId(routeId);

            Passenger savedPassenger = passengerRepository.save(p);
            byte[] pdfBytes = pdfTicketGeneratorService.generatePDFTicket(savedPassenger, byRouteId.get().getFromLocation(), byRouteId.get().getToLocation(), byRouteId.get().getFromDate());
            emailService.sendEmailWithAttachment(passenger.getEmail(),"Booking Confirmed...!","Your Reservation id"+savedPassenger.getId(),pdfBytes,"ticket");

        }

        return new ResponseEntity<>("Record Saved and Pdf Generated...!", HttpStatus.CREATED);


    }

    //http://localhost:8080/api/v1/reservation/generate-excel
    @GetMapping("/generate-excel")
    public ResponseEntity<byte[]> generateExcelFile() {
        try {
            List<Passenger> passengers = fetchPassengersFromDatabase(); // Fetch passengers from database
            byte[] excelBytes = excelService.generateExcel(passengers);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "passenger_data.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Fetch passengers from the database
    private List<Passenger> fetchPassengersFromDatabase() {
        return passengerRepository.findAll(); // Placeholder return, replace with actual implementation
    }

}
