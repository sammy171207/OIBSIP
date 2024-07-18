package com.example.brs.controller;

import com.example.brs.dto.request.CreateBusRequest;
import com.example.brs.modal.Bus;
import com.example.brs.modal.User;
import com.example.brs.service.BusService;
import com.example.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bus")
public class BusController {


    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestHeader("Authorization") String jwt, @RequestBody CreateBusRequest createBusRequest) throws Exception {
        User user=userService.findByUserByJwtToken(jwt);
        Bus bus=busService.saveBus(createBusRequest,user);
        return  new ResponseEntity<>(bus,HttpStatus.CREATED);


    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable("id") Long id, @RequestBody CreateBusRequest createBusRequest) {
        try {
            Bus updatedBus = busService.updateBus(id, createBusRequest);
            return new ResponseEntity<>(updatedBus, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable("id") Long id) {
        try {
            busService.deleteBus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable("id") Long id) throws Exception {
        Optional<Bus> bus = busService.getBusById(id);
        return bus.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/number/{busNumber}")
    public ResponseEntity<Bus> getBusByNumber(@PathVariable("busNumber") String busNumber) throws Exception {
        Optional<Bus> bus = busService.getBusByNumber(busNumber);
        return bus.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAllBuses() {
        List<Bus> buses = busService.getAllBuses();
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }
}
