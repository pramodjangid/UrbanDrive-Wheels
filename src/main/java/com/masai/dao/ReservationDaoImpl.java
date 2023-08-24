package com.masai.dao;

import java.util.Date;
import java.util.List;

import com.masai.entity.Customer;
import com.masai.entity.Reservation;
import com.masai.entity.Vehicle;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.ReservationNotFoundException;
import com.masai.utility.EMUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


public class ReservationDaoImpl implements ReservationDao {

    @Override
    public Reservation save(Reservation reservation) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
            return reservation;
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to save reservation");
        } finally {
            em.close();
        }
    }

    @Override
    public Reservation findById(int id) throws ReservationNotFoundException, DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation == null) {
                throw new ReservationNotFoundException("Reservation not found with ID: " + id);
            }
            return reservation;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reservation> findByCustomer(Customer customer) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer = :customer", Reservation.class)
                    .setParameter("customer", customer)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Reservation> findByVehicle(Vehicle vehicle) throws DataAccessException {
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
    public void delete(Reservation reservation) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            reservation.setDeleted(true); // Mark as deleted
            
            em.merge(reservation);
            em.getTransaction().commit();
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to soft delete reservation");
        } finally {
            em.close();
        }
    }

	
	@Override
    public List<Reservation> findPastReservations(Customer customer) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer = :customer AND r.endDate < :currentDate", Reservation.class)
                    .setParameter("customer", customer)
                    .setParameter("currentDate", new Date())
                    .getResultList();
        } finally {
            em.close();
        }
    }

}

