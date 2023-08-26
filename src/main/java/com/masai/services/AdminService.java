package com.masai.services;


import java.util.List;

import com.masai.dao.*;
import com.masai.exceptions.*;
import com.masai.entity.*;


public interface AdminService {

    
    public Vehicle addVehicle(Vehicle vehicle) throws DataAccessException ;
    	  

    public void updateVehicle(Vehicle updatedVehicle) throws DataAccessException ;
    

    public void deleteVehicle(int vehicleId) throws DataAccessException, CarNotFoundException ;
    public List<Vehicle> listAllVehicles() throws DataAccessException ;
    public List<Vehicle> listAvailableVehicles() throws DataAccessException ;
    
    public Vehicle findVehicleById(int vehicleId) throws DataAccessException, CarNotFoundException ;
    public void generateReports() ;


	public List<Reservation> findByVehicle(Vehicle vehicle);


	public void addTransaction(Transaction transactionEntity) throws DataAccessException;


	public void updateTransaction(Transaction transaction) throws DataAccessException;


	List<Customer> getAllCustomers() throws DataAccessException;


	public List<Reservation> getAllReservations()throws DataAccessException;


	public void deleteCustomerByUsername(String username) throws DataAccessException, EntityNotFoundException;


	public void cancelReservation(int reservationId)throws DataAccessException, EntityNotFoundException;


	public double calculateTotalRevenue() throws DataAccessException;



    
}

