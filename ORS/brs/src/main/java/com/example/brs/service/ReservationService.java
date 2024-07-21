package com.example.brs.service;

import com.example.brs.modal.Reservation;
import com.example.brs.modal.User;
import com.example.brs.repository.ReservationRepository;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reservation reservationRequest, User user) throws Exception;
    Reservation updateReservation(Long reservationId, Reservation reservationRequest) throws Exception;
    void cancelReservation(Long reservationId) throws Exception;
    List<Reservation> getUsersReservations(Long userId) throws Exception;
    Reservation getReservationById(Long reservationId)throws Exception;
    List<Reservation> getAllReservations() throws Exception;

}
