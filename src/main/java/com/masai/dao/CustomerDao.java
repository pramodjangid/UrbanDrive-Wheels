package com.masai.dao;

import java.util.List;

import com.masai.entity.Customer;
import com.masai.exceptions.*;

public interface CustomerDao {
    Customer save(Customer customer) throws DataAccessException;
    Customer findById(int id) throws EntityNotFoundException, DataAccessException;
    Customer findByUsername(String username) throws EntityNotFoundException, DataAccessException;
    List<Customer> findAll() throws DataAccessException;
	void delete(Customer customer) throws DataAccessException;
    
}
