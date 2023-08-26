package com.masai.dao;

import java.util.List;

import com.masai.entity.Vehicle;
import com.masai.exceptions.*;



public interface VehicleDao {
    Vehicle save(Vehicle vehicle) throws DataAccessException;
    Vehicle findById(int id) throws DataAccessException,CarNotFoundException;
    List<Vehicle> findAll() throws DataAccessException;
    List<Vehicle> findByAvailability(boolean availability) throws DataAccessException;
    List<Vehicle> findByBrandAndModel(String brand, String model) throws DataAccessException, CarNotFoundException;
    List<Vehicle> findByAvailabilityAndYearGreaterThanEqualAndSeatingCapacityGreaterThanEqual(
            boolean availability, int minYear, int minSeatingCapacity) throws DataAccessException;
	void delete(Vehicle vehicle) throws DataAccessException;
	public void update(Vehicle vehicle) throws DataAccessException;
}


