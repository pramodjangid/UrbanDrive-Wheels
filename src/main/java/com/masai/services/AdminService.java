package com.masai.services;


import java.util.List;

import com.masai.dao.*;
import com.masai.exceptions.*;
import com.masai.entity.*;


public interface AdminService {

    
    public Vehicle addVehicle(Vehicle vehicle) throws DataAccessException, CarNotFoundException ;
    	  

    public void updateVehicle(Vehicle updatedVehicle) throws DataAccessException ;
    

    public void deleteVehicle(int vehicleId) throws DataAccessException, CarNotFoundException ;
    public List<Vehicle> listAllVehicles() throws DataAccessException ;
    public List<Vehicle> listAvailableVehicles() throws DataAccessException ;
    
    public Vehicle findVehicleById(int vehicleId) throws DataAccessException, CarNotFoundException ;
    public void generateReports() ;



    
}

