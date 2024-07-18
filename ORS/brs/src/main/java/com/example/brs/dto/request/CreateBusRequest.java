package com.example.brs.dto.request;

import lombok.Data;

@Data
public class CreateBusRequest {
    private String busNumber;
    private String driverName;
    private String route;
    private int capacity;
}
