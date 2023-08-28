package com.masai.presentationlayer;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.masai.services.AdminService;
import com.masai.services.CustomerService;
import com.masai.entity.Vehicle;
import com.masai.entity.Customer;
import com.masai.entity.Transaction;
import com.masai.entity.Reservation;
import com.masai.exceptions.CarNotFoundException;
import com.masai.exceptions.CustomerAlreadyExistsException;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.InvalidCredentialsException;
import com.masai.exceptions.ReservationNotFoundException;
import com.masai.exceptions.ReservationUnavailableException;
import com.masai.exceptions.VehicleAlreadyReservedException;
import com.masai.exceptions.EntityNotFoundException;

public class CustomerPresentation {
    private CustomerService customerService;
    private Customer loggedInCustomer;
    private AdminService adminService;

    public CustomerPresentation(CustomerService customerService, AdminService adminService) {
        this.customerService = customerService;
        this.adminService = adminService;
        this.loggedInCustomer = null;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
        	System.out.println();
            System.out.println("Customer Menu");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. show All Vehicles");
            System.out.println("4. View Vehicle Details");
            System.out.println("5. view Available Vehicles");
            System.out.println("6. Make Reservation");
            System.out.println("7. Cancel Reservation");
            System.out.println("8. View Reservations");
            System.out.println("9. Provide Feedback");
            System.out.println("10. View Past Reservations");
            System.out.println("11. search By seating capaciy/VehicleModelYear");
            System.out.println("12. Sort By Rental Rate");
            System.out.println("13. Log out");
            System.out.print("Enter your choice: ");
            System.out.println();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    loginCustomer(scanner);
                    break;
                case 2:
                    registerCustomer(scanner);
                    break;
                case 3:
                    showAllVehicles();
                    break;
                case 4:
                    viewVehicleDetails(scanner);
                    break;
                case 5:
                	getAvailableVehicle(scanner);
                    break;
                case 6:
                    makeReservation(scanner);
                    break;
                case 7:
                    cancelReservation(scanner);
                    break;
                case 8:
                    viewReservations();
                    break;
                case 9:
                    provideFeedback(scanner);
                    break;
                case 10:
                	viewPastReservations(scanner);
                    break;
                case 11:
                	searchAvailableVehicles(scanner);
                    break;
                case 12:
                	sortByRentalRate(scanner);
                    break;
                case 13:
                    System.out.println("Logged out.");
                    loggedInCustomer = null; // Log out the customer
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void getAvailableVehicle(Scanner scanner) {
        try {
            List<Vehicle> availableVehicles = customerService.getAllVehicles(); // Replace with your method to get available vehicles

            if (availableVehicles.isEmpty()) {
                System.out.println("No available vehicles found.");
                return;
            }

            System.out.println("Available Vehicles:");
            for (Vehicle vehicle : availableVehicles) {
            	if(vehicle.isAvailability()) {
                    System.out.println("ID: " + vehicle.getVehicleId() +
                                       ", Brand: " + vehicle.getBrand() +
                                       ", Model: " + vehicle.getModel());	
            	}
            	
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }


	private void sortByRentalRate(Scanner scanner) {
        try {
            List<Vehicle> vehicles = customerService.getAllVehicles(); // Replace with your method to get vehicles

            if (vehicles.isEmpty()) {
                System.out.println("No vehicles found.");
                return;
            }

            // Sort vehicles by rental rate using a custom comparator
            vehicles.sort(Comparator.comparingDouble(Vehicle::getRentalRate));

            System.out.println("Vehicles sorted by rental rate:");
            for (Vehicle vehicle : vehicles) {
            	
                System.out.println("Vehicle Id: " + vehicle.getVehicleId() +
                                   ", Brand: " + vehicle.getBrand() +
                                   ", Model: " + vehicle.getModel() +
                                   ", Rental Rate: " + vehicle.getRentalRate());
                
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        }
    }


	public void showAllVehicles() {
        try {
            List<Vehicle> vehicleList = customerService.getAllVehicles();
            
            if (vehicleList.isEmpty()) {
                System.out.println("No vehicles available.");
            } else {
                System.out.println("Available Vehicles:");
                for (Vehicle vehicle : vehicleList) {
                	
                    System.out.println("Vehicle Id: " + vehicle.getVehicleId() +
                                       ",Brand: " + vehicle.getBrand() +
                                       ",Model: " + vehicle.getModel());
                }
            }
        } catch (DataAccessException e) {
            System.out.println("Error fetching vehicles: " + e.getMessage());
        }
    }

	private void registerCustomer(Scanner scanner) {
        // Implementation for registering a new customer
        // Collect customer details from user
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        try {
            Customer newCustomer = customerService.registerCustomer(firstName, lastName, email, username, password, phoneNumber, address, false);
            System.out.println("Customer registered successfully: " + newCustomer.getFirstName());
        } catch (DataAccessException   e) {
            System.out.println("Error registering customer: " + e.getMessage());
        }
    }

	private void loginCustomer(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            loggedInCustomer = customerService.loginCustomer(username, password);
            System.out.println("Logged in successfully as: " + loggedInCustomer.getFirstName());
        } catch (DataAccessException | EntityNotFoundException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
    }

   
	
	private void makeReservation(Scanner scanner) {
	    if (loggedInCustomer == null) {
	        System.out.println("Please log in before making a reservation.");
	        return;
	    }

	    System.out.print("Enter vehicle ID to make a reservation: ");
	    int vehicleId = scanner.nextInt();
	    scanner.nextLine(); // Consume newline

	    try {
	        Vehicle vehicle = customerService.getVehicleById(vehicleId);

	        if (!vehicle.isAvailability()) {
	            System.out.println("Vehicle with ID " + vehicleId + " is not available for reservation.");
	            return;
	        }

	        System.out.print("Enter start date (YYYY-MM-DD): ");
	        String startDateStr = scanner.nextLine();
	        LocalDate startDate = LocalDate.parse(startDateStr);

	        System.out.print("Enter end date (YYYY-MM-DD): ");
	        String endDateStr = scanner.nextLine();
	        LocalDate endDate = LocalDate.parse(endDateStr);

	        try {
	            Reservation reservation = customerService.makeReservation(loggedInCustomer, vehicle, startDate, endDate);
	            System.out.println("Reservation made successfully: " + reservation);

	            // Update the availability of the booked vehicle to false
	            vehicle.setAvailability(false);
	            adminService.updateVehicle(vehicle);

	            // Calculate total amount for the transaction
	            double rentalRate = vehicle.getRentalRate();
	            double totalAmount = calculateTotalAmount(startDate, endDate, rentalRate);

	            // Create a new Transaction entity
	            Transaction transactionEntity = new Transaction();
	            transactionEntity.setCustomer(loggedInCustomer);
	            transactionEntity.setReservation(reservation);
	            transactionEntity.setTransactionDate(new Date());
	            transactionEntity.setAmount(totalAmount);
	            transactionEntity.setDeleted(false);
	            adminService.addTransaction(transactionEntity);

	        } catch (DataAccessException e) {
	            System.out.println("Error making reservation: " + e.getMessage());
	        }

	    } catch (DataAccessException | CarNotFoundException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	private double calculateTotalAmount(LocalDate startDate, LocalDate endDate, double rentalRate) {
	    // Calculate the duration in days
	    long duration = ChronoUnit.DAYS.between(startDate, endDate);

	    // Calculate the total amount by multiplying the rental rate with the duration
	    double totalAmount = rentalRate * duration;

	    return totalAmount;
	}









    private void searchAvailableVehicles(Scanner scanner) {
    	
        // Implementation for searching available vehicles
        System.out.println("Enter minimum year: ");
        int minYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter minimum seating capacity: ");
        int minSeatingCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            List<Vehicle> availableVehicles = customerService.searchAvailableVehicles(true, minYear, minSeatingCapacity);
            System.out.println("Available Vehicles:");
            for (Vehicle vehicle : availableVehicles) {
                System.out.println(vehicle);
            }
        } catch (DataAccessException e) {
            System.out.println("Error searching available vehicles: " + e.getMessage());
        }
    }

    private void viewVehicleDetails(Scanner scanner) {
        System.out.print("Enter vehicle ID to view details: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Vehicle vehicle = customerService.getVehicleById(vehicleId);
            System.out.println("+     Vehicle Details:");
            System.out.println("+     Brand: " + vehicle.getBrand());
            System.out.println("+     Model: " + vehicle.getModel());
            System.out.println("+     Year: " + vehicle.getYear());
            System.out.println("+     Mileage: " + vehicle.getMileage());
            System.out.println("+     Seating Capacity: " + vehicle.getSeatingCapacity());
            System.out.println("+     Rental Rate: " + vehicle.getRentalRate());
            System.out.println("+     Availability: " + (vehicle.isAvailability() ? "Available" : "Not Available"));
            System.out.println();
        } catch (DataAccessException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    
    private void cancelReservation(Scanner scanner) {
        if (loggedInCustomer == null) {
            System.out.println("Please log in before cancelling a reservation.");
            return;
        }
        
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Reservation reservation = customerService.getReservationById(reservationId);
            
            if (reservation != null) {
                // Mark the reservation as cancelled
                reservation.setDeleted(true);
                customerService.updateReservation(reservation);

                // Mark the vehicle as available again
                Vehicle vehicle = reservation.getVehicle();
                vehicle.setAvailability(true);
                adminService.updateVehicle(vehicle);

                // Soft delete the associated transaction
                Transaction transaction = reservation.getTransaction();
                if (transaction != null) {
                    transaction.setDeleted(true);
                    adminService.updateTransaction(transaction);
                }

                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Reservation is already cancelled.");
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data: " + e.getMessage());
        } catch (ReservationNotFoundException e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
        }
    }



    private void viewReservations() {
    	if (loggedInCustomer == null) {
	        System.out.println("Please log in before making a reservation.");
	        return;
	    }
        try {
            List<Reservation> reservations = customerService.getCustomerReservations(loggedInCustomer);
            System.out.println("Your Reservations:");
            System.out.println();
            if(reservations.isEmpty()) {
            	System.out.println(" No Past Reservation available");
            }else {
            	for (Reservation reservation : reservations) {
                	System.out.println();
                    System.out.println(reservation);
                }
            }
        } catch (DataAccessException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }
    }
    
    
    private void provideFeedback(Scanner scanner) {
    	if (loggedInCustomer == null) {
	        System.out.println("Please log in before making a reservation.");
	        return;
	    }
        System.out.print("Enter reservation ID to provide feedback: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Reservation reservation = customerService.getReservationById(reservationId);
            if (reservation.getCustomer().getCustomerId() == loggedInCustomer.getCustomerId()) {
                System.out.print("Enter feedback: ");
                String feedback = scanner.nextLine();

                System.out.print("Enter rating (1-5): ");
                int rating = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                try {
                    customerService.provideFeedback(reservation, feedback, rating);
                    System.out.println("Feedback provided successfully.");
                } catch (DataAccessException e) {
                    System.out.println("Error providing feedback: " + e.getMessage());
                }
            } else {
                System.out.println("You are not authorized to provide feedback for this reservation.");
            }
        } catch (DataAccessException | ReservationNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    private void viewPastReservations(Scanner scanner) {
    	if (loggedInCustomer == null) {
	        System.out.println("Please log in before making a reservation.");
	        return;
	    }
        try {
            List<Reservation> pastReservations = customerService.getPastReservations(loggedInCustomer);
            System.out.println("Your Past Reservations:");
            if(pastReservations.isEmpty()) {
            	System.out.println(" No Past Reservation available");
            }else {
            	for (Reservation reservation : pastReservations) {
                	System.out.println();
                    System.out.println(reservation);
                }
            }
            
        } catch (DataAccessException e) {
            System.out.println("Error fetching past reservations: " + e.getMessage());
        }
    }
}    
