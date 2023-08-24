package com.masai.services;

import java.sql.Date;
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
            throws DataAccessException, EntityNotFoundException {
        // Check if customer with the provided username already exists
        Customer existingCustomer = customerDao.findByUsername(username);
        if (existingCustomer != null) {
            System.out.println("Customer already exists with username: " + username);
        }

        // Create and save the new customer
        Customer newCustomer = new Customer();
        return customerDao.save(newCustomer);
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
    public Reservation makeReservation(Customer customer, Vehicle vehicle, Date startDate, Date endDate)
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
}


