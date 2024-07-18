package com.example.brs.repository;

import com.example.brs.modal.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus,Long> {
    Optional<Bus> findByBusNumber(String busNumber);
}
