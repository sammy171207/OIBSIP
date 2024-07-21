package com.example.brs.service.impl;

import com.example.brs.modal.Bus;
import com.example.brs.modal.Reservation;
import com.example.brs.modal.User;
import com.example.brs.repository.BusRepository;
import com.example.brs.repository.ReservationRepository;
import com.example.brs.repository.UserRepository;
import com.example.brs.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;
    @Override
    public Reservation createReservation(Reservation reservationRequest, User user) throws Exception {
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        // Find bus by ID
        Optional
                <Bus> busOpt = busRepository.findById(reservationRequest.getBus().getId());

        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setBus(bus);
            reservation.setReservationTime(new Date());
            reservation.setNumberOfSeats(reservationRequest.getNumberOfSeats());
            reservation.setPrice(reservationRequest.getPrice());

            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Bus not found");
        }
    }

    @Override
    public Reservation updateReservation(Long reservationId, Reservation reservationRequest) throws Exception {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();

            // Update reservation details
            reservation.setReservationTime(new Date());
            reservation.setNumberOfSeats(reservationRequest.getNumberOfSeats());
            reservation.setPrice(reservationRequest.getPrice());

            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    @Override
    public void cancelReservation(Long reservationId) throws Exception {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> getUsersReservations(Long userId) throws Exception {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public Reservation getReservationById(Long reservationId) throws Exception {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Override
    public List<Reservation> getAllReservations() throws Exception {
        return reservationRepository.findAll();
    }
}
