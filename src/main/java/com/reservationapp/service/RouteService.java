package com.reservationapp.service;

import com.reservationapp.entity.Bus;
import com.reservationapp.entity.Route;
import com.reservationapp.exception.ResourceNotFoundException;
import com.reservationapp.repository.BusRepository;
import com.reservationapp.repository.RouteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    public Route createRoute(long busId,Route route){
        Bus bus = busRepository.findById(busId).orElseThrow(
                () -> new ResourceNotFoundException("Bus not Added...!")
        );

        Route r = routeRepository.findByBusId(route.getBusId());
        if (r != null){
            throw new ResourceNotFoundException("Route Already Added...!");
        }
        if (r == null) {
            routeRepository.save(route);
            return route;
        }
        return null;
    }


    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public void deleteRouteById(long routeId) {
        routeRepository.deleteById(routeId);
    }

    public Route updateRoute(Long routeId, Route updatedRoute) {
        Route existingRoute = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route not found with id: " + routeId)
        );

//        existingRoute.setFromLocation(updatedRoute.getFromLocation());
//        existingRoute.setToLocation(updatedRoute.getToLocation());
//        existingRoute.setFromDate(updatedRoute.getFromDate());
//        existingRoute.setToDate(updatedRoute.getToDate());
//        existingRoute.setTotalDuration(updatedRoute.getTotalDuration());
//        existingRoute.setFromTime(updatedRoute.getFromTime());
//        existingRoute.setToTime(updatedRoute.getToTime());
//        existingRoute.setBusId(updatedRoute.getBusId());

//        OR

        // Copy updated properties from updatedRoute to existingRoute
        BeanUtils.copyProperties(updatedRoute, existingRoute, "id");
        return routeRepository.save(existingRoute);
    }


}
