package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.entity.Transaction;
import com.masai.entity.Vehicle;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.EntityNotFoundException;
import com.masai.utility.EMUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


public class TransactionDaoImpl implements TransactionDao {

    @Override
    public Transaction save(Transaction transaction) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
            return transaction;
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to save transaction");
        } finally {
            em.close();
        }
    }

    @Override
    public Transaction findById(int id) throws EntityNotFoundException, DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            Transaction transaction = em.find(Transaction.class, id);
            if (transaction == null) {
                throw new EntityNotFoundException("Transaction not found with ID: " + id);
            }
            return transaction;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findByCustomer(Customer customer) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT t FROM Transaction t WHERE t.customer = :customer", Transaction.class)
                    .setParameter("customer", customer)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findByVehicle(Vehicle vehicle) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT t FROM Transaction t WHERE t.vehicle = :vehicle", Transaction.class)
                    .setParameter("vehicle", vehicle)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    
}

