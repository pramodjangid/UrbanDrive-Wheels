package com.masai.exceptions;

public class VehicleAlreadyReservedException extends Exception {
    public VehicleAlreadyReservedException(String msg) {
    	super(msg);
    }
}
