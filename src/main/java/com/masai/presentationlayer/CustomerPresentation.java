package com.masai.presentationlayer;


import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

import com.masai.services.AdminService;
import com.masai.services.CustomerService;
import com.masai.entity.Vehicle;
import com.masai.entity.Customer;
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
            System.out.println("Customer Menu");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Search Available Vehicles");
            System.out.println("4. View Vehicle Details");
            System.out.println("5. Make Reservation");
            System.out.println("6. Cancel Reservation");
            System.out.println("7. View Reservations");
            System.out.println("8. Provide Feedback");
            System.out.println("9. View Past Reservations");
            System.out.println("10. Log out");
            System.out.print("Enter your choice: ");

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
                    searchAvailableVehicles(scanner);
                    break;
                case 4:
                    viewVehicleDetails(scanner);
                    break;
                case 5:
                    makeReservation(scanner);
                    break;
                case 6:
                    cancelReservation(scanner);
                    break;
                case 7:
                    viewReservations();
                    break;
                case 8:
                    provideFeedback(scanner);
                    break;
                case 9:
                    viewPastReservations();
                    break;
                case 10:
                    System.out.println("Logged out.");
                    loggedInCustomer = null; // Log out the customer
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
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

	        } catch (DataAccessException e) {
	            System.out.println("Error making reservation: " + e.getMessage());
	        }

	    } catch (DataAccessException | CarNotFoundException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
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
            System.out.println("Vehicle Details:");
            System.out.println("Brand: " + vehicle.getBrand());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("Year: " + vehicle.getYear());
            System.out.println("Mileage: " + vehicle.getMileage());
            System.out.println("Seating Capacity: " + vehicle.getSeatingCapacity());
            System.out.println("Rental Rate: " + vehicle.getRentalRate());
            System.out.println("Availability: " + (vehicle.isAvailability() ? "Available" : "Not Available"));

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
            customerService.cancelReservation(reservation);
            System.out.println("Reservation cancelled successfully.");
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
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
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
    
    
    private void viewPastReservations() {
    	if (loggedInCustomer == null) {
	        System.out.println("Please log in before making a reservation.");
	        return;
	    }
        try {
            List<Reservation> pastReservations = customerService.getPastReservations(loggedInCustomer);
            System.out.println("Your Past Reservations:");
            for (Reservation reservation : pastReservations) {
                System.out.println(reservation);
            }
        } catch (DataAccessException e) {
            System.out.println("Error fetching past reservations: " + e.getMessage());
        }
    }
}    
