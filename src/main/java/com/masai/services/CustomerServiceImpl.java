package com.masai.services;


import java.time.LocalDate;
import java.util.List;

import com.masai.dao.*;
import com.masai.exceptions.*;
import com.masai.entity.*;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private VehicleDao vehicleDao;
    private ReservationDao reservationDao;

    public CustomerServiceImpl(CustomerDao customerDao, VehicleDao vehicleDao, ReservationDao reservationDao) {
        this.customerDao = customerDao;
        this.vehicleDao = vehicleDao;
        this.reservationDao = reservationDao;
    }

    @Override
    public Customer registerCustomer(String firstName, String lastName, String email, String username,
            String password, String phoneNumber, String address, boolean isDeleted)
            throws DataAccessException {
        // Check if customer with the provided username already exists
//        Customer existingCustomer = customerDao.findByUsername(username);
//        if (existingCustomer != null) {
//            throw new CustomerAlreadyExistsException("Customer already exists with username: " + username);
//        }

        // Create a new customer object with the provided details
        Customer newCustomer = new Customer(firstName, lastName, email, username, password, phoneNumber, address, isDeleted);

        // Save the new customer in the database
        try {
            return customerDao.save(newCustomer);
        } catch (DataAccessException e) {
            // Handle any exception that might occur during data access
            throw new DataAccessException("Failed to register customer");
        }
    }


    @Override
    public Customer loginCustomer(String username, String password)
            throws DataAccessException, EntityNotFoundException {
        Customer customer = customerDao.findByUsername(username);
        if (customer == null || !customer.getPassword().equals(password)) {
        	System.out.println("Invalid username or password");
        }
        return customer;
    }

    @Override
    public List<Vehicle> searchAvailableVehicles(boolean availability, int minYear, int minSeatingCapacity)
            throws DataAccessException {
        return vehicleDao.findByAvailabilityAndYearGreaterThanEqualAndSeatingCapacityGreaterThanEqual(availability, minYear, minSeatingCapacity);
    }

    @Override
    public Vehicle getVehicleById(int id) throws DataAccessException, CarNotFoundException {
        Vehicle vehicle = vehicleDao.findById(id);
        if (vehicle == null) {
            throw new CarNotFoundException("Vehicle not found with ID: " + id);
        }
        return vehicle;
    }


    @Override
    public Reservation makeReservation(Customer customer, Vehicle vehicle, LocalDate startDate, LocalDate endDate)
            throws DataAccessException {
        // Create and save a new reservation
        Reservation reservation = new Reservation(customer, vehicle, startDate, endDate, false);
        return reservationDao.save(reservation);
    }

    @Override
    public void cancelReservation(Reservation reservation) throws DataAccessException {
        reservationDao.delete(reservation);
    }

    @Override
    public List<Reservation> getCustomerReservations(Customer customer) throws DataAccessException {
        return reservationDao.findByCustomer(customer);
    }

    @Override
    public void provideFeedback(Reservation reservation, String feedback, int rating) throws DataAccessException {
//        reservation.setFeedback(feedback);
//        reservation.setRating(rating);
//        reservationDao.update(reservation);
    }

    @Override
    public List<Reservation> getPastReservations(Customer customer) throws DataAccessException {
        return reservationDao.findPastReservations(customer);
    }

    @Override
    public Reservation getReservationById(int reservationId) throws DataAccessException, ReservationNotFoundException {
        Reservation reservation = reservationDao.findById(reservationId);
		if (reservation == null) {
		    throw new ReservationNotFoundException("Reservation not found with ID: " + reservationId);
		}
		return reservation;
    }
}


