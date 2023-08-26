package com.masai.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Transaction transaction;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    private boolean isDeleted;

	public Reservation() {
		super();
	}
	

	public Reservation(Customer customer, Vehicle vehicle, LocalDate startDate2, LocalDate endDate2, boolean isDeleted) {
		super();
		this.customer = customer;
		this.vehicle = vehicle;
		this.startDate = startDate2;
		this.endDate = endDate2;
		this.isDeleted = isDeleted;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean date) {
		this.isDeleted = date;
	}
	
	

	public Transaction getTransaction() {
		return transaction;
	}


	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", customer=" + customer + ", vehicle=" + vehicle
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", isDeleted=" + isDeleted + "]";
	}

    
}

