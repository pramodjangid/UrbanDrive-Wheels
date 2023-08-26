package com.masai.services;

import java.util.List;

import com.masai.dao.CustomerDao;
import com.masai.dao.ReservationDao;
import com.masai.dao.TransactionDao;
import com.masai.dao.VehicleDao;
import com.masai.exceptions.*;
import com.masai.utility.EMUtils;

import jakarta.persistence.EntityManager;

import com.masai.entity.*;


public class AdminServiceImpl implements AdminService{

    private VehicleDao vehicleDao;
    private TransactionDao transactionDao;
    private CustomerDao customerDao;
    private ReservationDao reservationDao;

    public AdminServiceImpl(VehicleDao vehicleDao,TransactionDao transactionDao,CustomerDao customerDao, ReservationDao reservationDao) {
        this.vehicleDao = vehicleDao;
        this.transactionDao=transactionDao;
        this.customerDao=customerDao;
        this.reservationDao=reservationDao;
    }

    public Vehicle addVehicle(Vehicle vehicle) throws DataAccessException {
        try {
            return vehicleDao.save(vehicle);
        } catch (DataAccessException e) {
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

	@Override
	public List<Reservation> findByVehicle(Vehicle vehicle) {
	    EntityManager em = EMUtils.getEntityManager();

	    try {
	        return em.createQuery("SELECT r FROM Reservation r WHERE r.vehicle = :vehicle", Reservation.class)
	                .setParameter("vehicle", vehicle)
	                .getResultList();
	    } finally {
	        em.close();
	    }
	}

	@Override
	public void addTransaction(Transaction transactionEntity) throws DataAccessException {
	    try {
	        transactionDao.save(transactionEntity);
	    } catch (Exception e) {
	        throw new DataAccessException("Error adding transaction: " + e.getMessage());
	    }
	}

	@Override
	public void updateTransaction(Transaction transaction) {
        try {
            transactionDao.update(transaction);
        } catch (DataAccessException e) {
            System.out.println("Error updating transaction: " + e.getMessage());
        }
    }


	@Override
    public List<Customer> getAllCustomers() throws DataAccessException {
        try {
            return customerDao.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessException("Error fetching customers: " + e.getMessage());
        }
    }

	@Override
	public List<Reservation> getAllReservations() throws DataAccessException {
	    try {
	        return reservationDao.getAllReservations();
	    } catch (DataAccessException e) {
	        throw new DataAccessException("Error accessing data: " + e.getMessage());
	    }
	}

	@Override
	public void deleteCustomerByUsername(String username) throws DataAccessException, EntityNotFoundException {
	    try {
	        Customer customer = customerDao.findByUsername(username);
	        if (customer != null) {
	            customerDao.delete(customer);
	        } else {
	            throw new EntityNotFoundException("Customer with username '" + username + "' not found.");
	        }
	    } catch (EntityNotFoundException e) {
	        throw new DataAccessException("Error deleting customer: " + e.getMessage());
	    }
	}

	@Override
	public void cancelReservation(int reservationId) throws DataAccessException, EntityNotFoundException {
	    try {
	        Reservation reservation = reservationDao.findById(reservationId);
	        if (reservation == null) {
	            throw new EntityNotFoundException("Reservation not found with ID: " + reservationId);
	        }

	        // Soft delete the transaction associated with the reservation (if any)
	        if (reservation.getTransaction() != null) {
	            Transaction transaction = reservation.getTransaction();
	            transaction.setDeleted(true);
	            transactionDao.update(transaction);
	        }

	        // Update the availability of the reserved vehicle
	        Vehicle reservedVehicle = reservation.getVehicle();
	        reservedVehicle.setAvailability(true);
	        vehicleDao.update(reservedVehicle);

	        // Delete the reservation
	        reservationDao.delete(reservation);
	    } catch (DataAccessException e) {
	        throw new DataAccessException("Error cancelling reservation: " + e.getMessage());
	    } catch (ReservationNotFoundException e) {
			System.out.println("No reservation with this ID" + e.getMessage());
			e.printStackTrace();
		}
	}

	
	@Override
	public double calculateTotalRevenue() throws DataAccessException {
	    double totalRevenue = 0.0;

	    try {
	        List<Transaction> allTransactions = transactionDao.getAllTransactions();
	        for (Transaction transaction : allTransactions) {
	        	if(!transaction.isDeleted()) {
	        		totalRevenue += transaction.getAmount();
	        	}
	        }
	    } catch (DataAccessException e) {
	        // Handle any exceptions that might occur while accessing data
	        throw new DataAccessException("Error calculating total revenue: " + e.getMessage());
	    }

	    return totalRevenue;
	}





    
}

