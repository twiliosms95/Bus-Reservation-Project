package com.reservationapp.contoller;


import com.reservationapp.entity.Route;
import com.reservationapp.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
public class RouteController {

    @Autowired
    private RouteService routeService;


    //http://localhost:8080/api/v1/route
    @PostMapping("/{busId}")
    public ResponseEntity<Route> addRoute(@PathVariable long busId, @RequestBody Route route){
        Route r = routeService.createRoute(busId, route);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/route/all
    @GetMapping("/all")
    public ResponseEntity<List<Route>> getAllRoutes(){
        List<Route> routes = routeService.getAllRoutes();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/route/id
    @DeleteMapping("/{routeId}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long routeId){
        routeService.deleteRouteById(routeId);
        return new ResponseEntity<>("Route with ID " + routeId + " has been deleted.", HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/route/{routeId}
    @PutMapping("/{routeId}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long routeId, @RequestBody Route updatedRoute) {
        Route updated = routeService.updateRoute(routeId, updatedRoute);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


}

