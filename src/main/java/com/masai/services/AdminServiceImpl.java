package com.masai.services;

import java.util.List;


import com.masai.dao.VehicleDao;
import com.masai.exceptions.*;

import com.masai.entity.*;


public class AdminServiceImpl implements AdminService{

    private VehicleDao vehicleDao;

    public AdminServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public Vehicle addVehicle(Vehicle vehicle) throws DataAccessException, CarNotFoundException {
        try {
            Vehicle existingVehicle = vehicleDao.findById(vehicle.getVehicleId());
            if (existingVehicle != null) {
                throw new VehicleAlreadyExistsException("Vehicle already exists with ID: " + vehicle.getVehicleId());
            }

            return vehicleDao.save(vehicle);
        } catch (VehicleAlreadyExistsException e) {
            // Handle the exception here or re-throw a more generic exception
            throw new DataAccessException("Error adding vehicle: " + e.getMessage());
        }
    }

    public void updateVehicle(Vehicle updatedVehicle) throws DataAccessException {
        vehicleDao.update(updatedVehicle);
    }
    

    public void deleteVehicle(int vehicleId) throws DataAccessException, CarNotFoundException {
        Vehicle vehicle = vehicleDao.findById(vehicleId);
        if (vehicle != null) {
            // Additional logic if needed
            vehicleDao.delete(vehicle);
        } else {
            throw new CarNotFoundException("Vehicle not found with ID: " + vehicleId);
        }
    }

    public List<Vehicle> listAllVehicles() throws DataAccessException {
        return vehicleDao.findAll();
    }

    public List<Vehicle> listAvailableVehicles() throws DataAccessException {
        return vehicleDao.findByAvailability(true);
    }
    
    public Vehicle findVehicleById(int vehicleId) throws DataAccessException, CarNotFoundException {
        return vehicleDao.findById(vehicleId);
    }

    public void generateReports() {
        // Logic to generate reports
    }



    
}

