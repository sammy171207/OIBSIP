package com.example.brs.controller;

import com.example.brs.modal.Reservation;
import com.example.brs.modal.User;
import com.example.brs.service.ReservationService;
import com.example.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservationRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findByUserByJwtToken(jwt);
        Reservation reservation = reservationService.createReservation(reservationRequest, user);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<List<Reservation>> getUsersReservations(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findByUserByJwtToken(jwt);
        List<Reservation> reservations = reservationService.getUsersReservations(user.getId());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationId, @RequestBody Reservation reservationRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findByUserByJwtToken(jwt);
        Reservation updatedReservation = reservationService.updateReservation(reservationId, reservationRequest);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) throws Exception {
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long reservationId) throws Exception {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() throws Exception {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
