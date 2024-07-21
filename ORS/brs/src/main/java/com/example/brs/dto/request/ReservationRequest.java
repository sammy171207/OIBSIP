package com.example.brs.dto.request;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long busId;
    private int numberOfSeats;
    private double price;
}
