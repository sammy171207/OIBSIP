package com.example.brs.service;

import com.example.brs.dto.request.CreateBusRequest;
import com.example.brs.modal.Bus;
import com.example.brs.modal.User;

import java.util.List;
import java.util.Optional;

public interface BusService {
    Bus saveBus(CreateBusRequest createBusRequest, User user) throws Exception;
    Bus updateBus(Long id, CreateBusRequest createBusRequest) throws Exception;
    void deleteBus(Long id) throws Exception;
    Optional<Bus> getBusById(Long id) throws Exception;
    Optional<Bus> getBusByNumber(String busNumber) throws Exception;
    List<Bus> getAllBuses();
}
