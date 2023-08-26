package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.EntityNotFoundException;
import com.masai.utility.EMUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;


public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Customer save(Customer customer) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            throw new DataAccessException("Failed to save customer");
        } finally {
            em.close();
        }
    }

    @Override
    public Customer findById(int id) throws EntityNotFoundException, DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                throw new EntityNotFoundException("Customer not found with ID: " + id);
            }
            return customer;
        } finally {
            em.close();
        }
    }

    @Override
    public Customer findByUsername(String username) throws EntityNotFoundException, DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            Customer customer = em.createQuery("SELECT c FROM Customer c WHERE c.username = :username", Customer.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (customer == null) {
                throw new EntityNotFoundException("Customer not found with username: " + username);
            }
            return customer;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Customer not found with username: " + username);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Customer customer) throws DataAccessException {
        EntityManager em = EMUtils.getEntityManager();

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            // Merge the customer object with the entity manager to attach it to the persistence context
            Customer mergedCustomer = em.merge(customer);

            // Remove the customer
            em.remove(mergedCustomer);

            transaction.commit();
        } catch (Exception e) {
            throw new DataAccessException("Error deleting customer: " + e.getMessage());
        } finally {
            em.close();
        }
    }


	

    
}
