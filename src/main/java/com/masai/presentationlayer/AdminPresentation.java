package com.masai.presentationlayer;

import java.util.List;
import java.util.Scanner;

import com.masai.entity.Customer;
import com.masai.entity.Reservation;
import com.masai.entity.Transaction;
import com.masai.entity.Vehicle;
import com.masai.exceptions.CarNotFoundException;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.EntityNotFoundException;
import com.masai.exceptions.VehicleAlreadyExistsException;
import com.masai.services.AdminService;
import com.masai.services.CustomerService;

public class AdminPresentation {
    private AdminService adminService;
    private CustomerService customerService;

    public AdminPresentation(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService=customerService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
        	System.out.println();
            System.out.println("Administrator Menu");
            System.out.println("1. View Cars");
            System.out.println("2. Add Car");
            System.out.println("3. Update Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Show All Customers");
            System.out.println("6. Show All Reservations");
            System.out.println("7. Show Revenue");
            System.out.println("8. Generate Reports");
            System.out.println("9. Delete a Customer");
            System.out.println("10. Cancel Reservation");
            System.out.println("11. Log out");
            System.out.print("Enter your choice: ");
            System.out.println();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewCars();
                    break;
                case 2:
                    addCar(scanner);
                    break;
                case 3:
                    updateCar(scanner);
                    break;
                case 4:
                    deleteCar(scanner);
                    break;
                case 5:
                    showAllCustomers();
                    break;
                case 6:
                    showAllReservations();
                    break;
                case 7:
                    showRevenue();
                    break;
                case 8:
                    generateReports();
                    break;
                case 9:
                    deleteCustomerbyUsername(scanner);
                    break;
                case 10:
                    cancelReservation(scanner);
                    break;
                case 11:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void cancelReservation(Scanner scanner) {
        

        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            adminService.cancelReservation(reservationId);
            System.out.println("Reservation with ID " + reservationId + " cancelled successfully.");
        } catch (DataAccessException e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
        } catch (EntityNotFoundException e) {
			System.out.println("No Reservation available with ID: " + reservationId);
			e.printStackTrace();
		}
    }
    


    private void deleteCustomerbyUsername(Scanner scanner) {
        System.out.print("Enter username of the customer to delete: ");
        String username = scanner.nextLine();

        try {
            adminService.deleteCustomerByUsername(username);
            System.out.println("Customer with username '" + username + "' deleted successfully.");
        } catch (DataAccessException  e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        } catch (EntityNotFoundException e) {
        	System.out.println("Customer Not Found: " + e.getMessage());
			e.printStackTrace();
		}
    }



    private void showRevenue() {
        try {
            double totalRevenue = adminService.calculateTotalRevenue();
            System.out.println("Total Revenue: ₹" + totalRevenue);
        } catch (DataAccessException e) {
            System.out.println("Error calculating total revenue: " + e.getMessage());
        }
    }

	private void showAllReservations() {
	    try {
	        List<Reservation> reservations = adminService.getAllReservations();

	        if (reservations.isEmpty()) {
	            System.out.println("No reservations found.");
	        } else {
	            System.out.println("List of Reservations:");
	            for (Reservation reservation : reservations) {
	            	System.out.println();
	                System.out.println("Reservation ID: " + reservation.getReservationId());
	                System.out.println("Customer ID: " + reservation.getCustomer().getCustomerId());
	                System.out.println("Vehicle ID: " + reservation.getVehicle().getVehicleId());
	                System.out.println("Start Date: " + reservation.getStartDate());
	                System.out.println("End Date: " + reservation.getEndDate());
	                System.out.println("Soft Delete: " + reservation.isDeleted());
	                Transaction transaction = reservation.getTransaction();
	                if (transaction != null) {
	                    System.out.println("Transaction ID: " + transaction.getTransactionId());
	                    System.out.println("Transaction Amount: " + transaction.getAmount());
	                } else {
	                    System.out.println("Transaction ID: N/A");
	                }
	                
	                System.out.println();
	            }
	        }
	    } catch (DataAccessException e) {
	        System.out.println("Error accessing data: " + e.getMessage());
	    }
	}



	private void showAllCustomers() {
	    try {
	        List<Customer> customers = adminService.getAllCustomers();

	        if (customers.isEmpty()) {
	            System.out.println("No customers found.");
	        } else {
	            System.out.println("List of Customers:");
	            for (Customer customer : customers) {
	            	System.out.println();
	                System.out.println("Customer ID: " + customer.getCustomerId());
	                System.out.println("Username: " + customer.getUsername());
	                System.out.println("Full Name: " + customer.getFirstName()+" "+ customer.getLastName());
	                System.out.println("Email: " + customer.getEmail());
	                System.out.println("Phone: " + customer.getPhoneNumber());
	                System.out.println("Address: " + customer.getAddress());
	                System.out.println();
	            }
	        }
	    } catch (DataAccessException e) {
	        System.out.println("Error accessing data: " + e.getMessage());
	    }
	}


	private void viewCars() {
        try {
            System.out.println("List of available cars:");
            List<Vehicle> vehicles = adminService.listAllVehicles();
            for (Vehicle vehicle : vehicles) {
            	System.out.println();
                System.out.println(vehicle);
            }
        } catch (DataAccessException e) {
            System.out.println("Error fetching vehicle list: " + e.getMessage());
        }
    }

    private void addCar(Scanner scanner) {
        // Collect vehicle details from user
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter mileage: ");
        double mileage = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter seating capacity: ");
        int seatingCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter rental rate: ");
        double rentalRate = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        boolean availability = true; // Assuming newly added vehicles are available

        Vehicle newVehicle = new Vehicle(brand, model, year, mileage, availability, seatingCapacity, rentalRate);

        try {
            Vehicle addedVehicle = adminService.addVehicle(newVehicle);
            System.out.println("Vehicle added successfully: " + addedVehicle);
        } catch (DataAccessException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        } 
    }


    private void updateCar(Scanner scanner) {
        try {
            // Collect vehicle ID from user
            System.out.print("Enter vehicle ID to update: ");
            int vehicleId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Vehicle existingVehicle = adminService.findVehicleById(vehicleId);
            if (existingVehicle != null) {
                // Collect updated vehicle details from user
                System.out.print("Enter updated brand: ");
                String updatedBrand = scanner.nextLine();

                System.out.print("Enter updated model: ");
                String updatedModel = scanner.nextLine();

                System.out.print("Enter updated year: ");
                int updatedYear = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter updated mileage: ");
                double updatedMileage = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter updated seating capacity: ");
                int updatedSeatingCapacity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter updated rental rate: ");
                double updatedRentalRate = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                existingVehicle.setBrand(updatedBrand);
                existingVehicle.setModel(updatedModel);
                existingVehicle.setYear(updatedYear);
                existingVehicle.setMileage(updatedMileage);
                existingVehicle.setSeatingCapacity(updatedSeatingCapacity);
                existingVehicle.setRentalRate(updatedRentalRate);

                try {
                    adminService.updateVehicle(existingVehicle);
                    System.out.println("Vehicle updated successfully.");
                } catch (DataAccessException e) {
                    System.out.println("Error updating vehicle: " + e.getMessage());
                }
            } else {
                System.out.println("Vehicle not found with ID: " + vehicleId);
            }
        } catch (DataAccessException | CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void deleteCar(Scanner scanner) {
        // Collect vehicle ID from user
        System.out.print("Enter vehicle ID to delete: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Vehicle vehicle = adminService.findVehicleById(vehicleId);

            if (vehicle != null) {
                List<Reservation> reservations = adminService.findByVehicle(vehicle);

                if (reservations.isEmpty()) {
                    adminService.deleteVehicle(vehicleId);
                    System.out.println("Vehicle deleted successfully.");
                } else {
                    System.out.println("Cannot delete the vehicle as it has reservations.");
                }
            } else {
                System.out.println("Vehicle not found with ID: " + vehicleId);
            }
        } catch (DataAccessException | CarNotFoundException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }
    

    private void generateReports() {
        try {
            List<Customer> allCustomers = customerService.getAllCustomers(); // Fetch all customers
            
            for (Customer customer : allCustomers) {
                System.out.println("Customer: " + customer.getFirstName());
                
                List<Reservation> reservations = customerService.getCustomerReservations(customer);
                for (Reservation reservation : reservations) {
                	System.out.println();
                    System.out.println("Reservation ID: " + reservation.getReservationId());
                    System.out.println("Vehicle: " + reservation.getVehicle().getBrand() + " " + reservation.getVehicle().getModel());
                    System.out.println("Start Date: " + reservation.getStartDate());
                    System.out.println("End Date: " + reservation.getEndDate());
                    System.out.println("Soft Delete: " + reservation.isDeleted());
                    System.out.println("---");
                }
                
                List<Transaction> transactions = customerService.getCustomerTransactions(customer);
                for (Transaction transaction : transactions) {
                	System.out.println();
                    System.out.println("Transaction ID: " + transaction.getTransactionId());
                    System.out.println("Amount: " + transaction.getAmount());
                    System.out.println("Date: " + transaction.getTransactionDate());
                    System.out.println("---");
                }
                
                System.out.println("=====");
            }
        } catch (DataAccessException e) {
            System.out.println("Error generating reports: " + e.getMessage());
        }
    }

}

