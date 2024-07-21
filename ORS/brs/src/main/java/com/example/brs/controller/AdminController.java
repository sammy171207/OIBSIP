package com.example.brs.controller;

import com.example.brs.dto.request.CreateBusRequest;
import com.example.brs.modal.Bus;
import com.example.brs.modal.Reservation;
import com.example.brs.modal.User;
import com.example.brs.service.BusService;
import com.example.brs.service.ReservationService;
import com.example.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestHeader("Authorization") String jwt, @RequestBody CreateBusRequest createBusRequest) {
        try {
            User user = userService.findByUserByJwtToken(jwt);
            if (user == null) {
                logger.warning("User not found for JWT: " + jwt);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Bus bus = busService.saveBus(createBusRequest, user);
            return new ResponseEntity<>(bus, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.severe("Error adding bus: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable("id") Long id, @RequestBody CreateBusRequest createBusRequest) {
        try {
            Bus updatedBus = busService.updateBus(id, createBusRequest);
            if (updatedBus == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedBus, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error updating bus with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable("id") Long id) {
        try {
            busService.deleteBus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.severe("Error deleting bus with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwtToken) {
        try {
            User user = userService.findByUserByJwtToken(jwtToken);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error finding user by JWT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allreservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error retrieving all reservations: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error retrieving all users: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.severe("Error deleting user with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
