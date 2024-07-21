package com.example.brs.repository;

import com.example.brs.modal.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    public List<Reservation> findByUserId(Long userId);

    public List<Reservation> findByBusId(Long busId );
}
