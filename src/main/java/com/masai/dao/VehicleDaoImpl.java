package com.masai.dao;

import java.util.List;

import com.masai.entity.Vehicle;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.CarNotFoundException;
import com.masai.utility.EMUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


public class VehicleDaoImpl implements VehicleDao {

    @Override
    public Vehicle save(Vehicle vehicle) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(vehicle);
            em.getTransaction().commit();
            return vehicle;
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to save vehicle");
        } finally {
            em.close();
        }
    }

    @Override
    public Vehicle findById(int id) throws CarNotFoundException, DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            Vehicle vehicle = em.find(Vehicle.class, id);
            if (vehicle == null) {
                throw new CarNotFoundException("Car not found with ID: " + id);
            }
            return vehicle;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehicle> findAll() throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehicle> findByAvailability(boolean availability) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM Vehicle v WHERE v.availability = :availability", Vehicle.class)
                    .setParameter("availability", availability)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehicle> findByBrandAndModel(String brand, String model) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT v FROM Vehicle v WHERE v.brand = :brand AND v.model = :model", Vehicle.class)
                    .setParameter("brand", brand)
                    .setParameter("model", model)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehicle> findByAvailabilityAndYearGreaterThanEqualAndSeatingCapacityGreaterThanEqual(
            boolean availability, int minYear, int minSeatingCapacity) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT v FROM Vehicle v WHERE v.availability = :availability AND v.year >= :minYear AND v.seatingCapacity >= :minSeatingCapacity",
                    Vehicle.class)
                    .setParameter("availability", availability)
                    .setParameter("minYear", minYear)
                    .setParameter("minSeatingCapacity", minSeatingCapacity)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Vehicle vehicle) throws DataAccessException {
    	EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.contains(vehicle) ? vehicle : em.merge(vehicle));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to delete vehicle");
        } finally {
            em.close();
        }
    }

    public void update(Vehicle vehicle) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();

            Vehicle existingVehicle = em.find(Vehicle.class, vehicle.getVehicleId());
            if (existingVehicle != null) {
                existingVehicle.setBrand(vehicle.getBrand());
                existingVehicle.setModel(vehicle.getModel());
                existingVehicle.setYear(vehicle.getYear());
                existingVehicle.setMileage(vehicle.getMileage());
                existingVehicle.setAvailability(vehicle.isAvailability());
                // ... update other fields as needed

                em.getTransaction().commit();
            } else {
                throw new DataAccessException("Vehicle not found for update");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to update vehicle");
        } finally {
            em.close();
        }
    }

		
   
}

