package com.masai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String make;
    private String model;
    private int year;
    private String color;
    private double dailyRate;
    private String availabilityStatus;
    private boolean isDeleted;
    
	public Vehicle() {
		super();
	}

	public Vehicle(String make, String model, int year, String color, double dailyRate, String availabilityStatus,
			boolean isDeleted) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.dailyRate = dailyRate;
		this.availabilityStatus = availabilityStatus;
		this.isDeleted = isDeleted;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", make=" + make + ", model=" + model + ", year=" + year + ", color="
				+ color + ", dailyRate=" + dailyRate + ", availabilityStatus=" + availabilityStatus + ", isDeleted="
				+ isDeleted + "]";
	}

    
}

