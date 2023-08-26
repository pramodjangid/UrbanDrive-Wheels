package com.masai.dao;

import java.util.List;

import com.masai.entity.*;
import com.masai.exceptions.*;

public interface TransactionDao {
    Transaction save(Transaction transaction) throws DataAccessException;
    Transaction findById(int id) throws EntityNotFoundException, DataAccessException;
    List<Transaction> findByCustomer(Customer customer) throws DataAccessException;
    List<Transaction> findByVehicle(Vehicle vehicle) throws DataAccessException;
	void update(Transaction transaction) throws DataAccessException;
	List<Transaction> getAllTransactions() throws DataAccessException;
    
}
