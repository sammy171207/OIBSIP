package com.example.brs.service.impl;

import com.example.brs.dto.request.CreateBusRequest;
import com.example.brs.modal.Bus;
import com.example.brs.modal.User;
import com.example.brs.repository.BusRepository;
import com.example.brs.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;
    @Override
    public Bus saveBus(CreateBusRequest createBusRequest, User user) throws Exception {
        if (busRepository.findByBusNumber(createBusRequest.getBusNumber()).isPresent()) {
            throw new Exception("Bus with this number already exists");
        }

        Bus bus = new Bus();
        bus.setBusNumber(createBusRequest.getBusNumber());
        bus.setDriverName(createBusRequest.getDriverName());
        bus.setRoute(createBusRequest.getRoute());
        bus.setCapacity(createBusRequest.getCapacity());
        bus.setCreator(user);
        if (createBusRequest.getImages() != null && !createBusRequest.getImages().isEmpty()) {
            bus.setImages(createBusRequest.getImages());
        }
        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Long id, CreateBusRequest createBusRequest) throws Exception {
        Optional<Bus> existingBusOptional = busRepository.findById(id);
        if (!existingBusOptional.isPresent()) {
            throw new Exception("Bus not found");
        }

        Bus existingBus = existingBusOptional.get();
        existingBus.setBusNumber(createBusRequest.getBusNumber());
        existingBus.setDriverName(createBusRequest.getDriverName());
        existingBus.setRoute(createBusRequest.getRoute());
        existingBus.setCapacity(createBusRequest.getCapacity());
        existingBus.setImages(createBusRequest.getImages());

        // Do not change the creator field
        return busRepository.save(existingBus);
    }

    @Override
    public void deleteBus(Long id) throws Exception {
        if (!busRepository.existsById(id)) {
            throw new Exception("Bus not found");
        }
        busRepository.deleteById(id);
    }

    @Override
    public Optional<Bus> getBusById(Long id) throws Exception {
        return busRepository.findById(id);
    }

    @Override
    public Optional<Bus> getBusByNumber(String busNumber) throws Exception {
        return busRepository.findByBusNumber(busNumber);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }
}
