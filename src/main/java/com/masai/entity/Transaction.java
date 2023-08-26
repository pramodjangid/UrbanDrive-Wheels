package com.masai.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private double amount;
    private boolean isDeleted;
    
	public Transaction() {
		super();
	}

	public Transaction(Customer customer, Reservation reservation, Date transactionDate, double amount,
			boolean isDeleted) {
		super();
		this.customer = customer;
		this.reservation = reservation;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.isDeleted = isDeleted;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customer=" + customer + ", reservation=" + reservation
				+ ", transactionDate=" + transactionDate + ", amount=" + amount + ", isDeleted=" + isDeleted + "]";
	}

    
}

