package com.masai.services;


import java.sql.Date;
import java.util.List;


import com.masai.exceptions.*;
import com.masai.entity.*;

public interface CustomerService {

   
    public Customer registerCustomer(String firstName, String lastName, String email,String username,String password, String phoneNumber, String address,
			boolean isDeleted) throws DataAccessException, EntityNotFoundException ;
    public Customer loginCustomer(String username, String password) throws DataAccessException, EntityNotFoundException ;
    public List<Vehicle> searchAvailableVehicles(boolean availability, int minYear, int minSeatingCapacity) throws DataAccessException ;
    public Vehicle getVehicleById(int id) throws DataAccessException, CarNotFoundException ;
    public Reservation makeReservation(Customer customer, Vehicle vehicle, Date startDate, Date endDate) throws DataAccessException ;
    public void cancelReservation(Reservation reservation) throws DataAccessException ;
    public List<Reservation> getCustomerReservations(Customer customer) throws DataAccessException ;
    public void provideFeedback(Reservation reservation, String feedback, int rating) throws DataAccessException ;
    public List<Reservation> getPastReservations(Customer customer) throws DataAccessException ;
}

