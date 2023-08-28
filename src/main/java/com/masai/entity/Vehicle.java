package com.masai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "mileage")
    private double mileage;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "seating_capacity")
    private int seatingCapacity;

    @Column(name = "rental_rate")
    private double rentalRate;


	public Vehicle() {
		super();
	}

	public Vehicle(String brand, String model, int year, double mileage, boolean availability, int seatingCapacity,
			double rentalRate) {
		super();
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
		this.availability = availability;
		this.seatingCapacity = seatingCapacity;
		this.rentalRate = rentalRate;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	@Override
	public String toString() {
	    return "Vehicle Information:\n" +
	           "Vehicle ID: " + vehicleId + "\n" +
	           "Brand: " + brand + "\n" +
	           "Model: " + model + "\n" +
	           "Year: " + year + "\n" +
	           "Mileage: " + mileage + " miles\n" +
	           "Availability: " + (availability ? "Available" : "Not Available") + "\n" +
	           "Seating Capacity: " + seatingCapacity + " seats\n" +
	           "Rental Rate: ₹" + rentalRate + " per day";
	}

    
    
}
