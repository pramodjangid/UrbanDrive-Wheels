package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.entity.Reservation;
import com.masai.entity.Vehicle;
import com.masai.exceptions.*;

public interface ReservationDao {
    Reservation save(Reservation reservation) throws DataAccessException;
    Reservation findById(int id) throws ReservationNotFoundException, DataAccessException;
    List<Reservation> findByCustomer(Customer customer) throws DataAccessException;
    List<Reservation> findByVehicle(Vehicle vehicle) throws DataAccessException;
	void delete(Reservation reservation) throws DataAccessException;
	List<Reservation> findPastReservations(Customer customer) throws DataAccessException;
    
}
