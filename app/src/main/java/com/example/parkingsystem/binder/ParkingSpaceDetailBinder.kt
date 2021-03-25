package com.example.parkingsystem.binder

import com.example.parkingsystem.entity.Parking

interface ParkingSpaceDetailBinder {
    fun chosenParkingSpaceId(parkingId: String)
}