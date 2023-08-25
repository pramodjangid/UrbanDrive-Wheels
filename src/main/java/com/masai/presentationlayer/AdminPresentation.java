package com.masai.presentationlayer;

import java.util.List;
import java.util.Scanner;

import com.masai.entity.Customer;
import com.masai.entity.Reservation;
import com.masai.entity.Transaction;
import com.masai.entity.Vehicle;
import com.masai.exceptions.CarNotFoundException;
import com.masai.exceptions.DataAccessException;
import com.masai.exceptions.VehicleAlreadyExistsException;
import com.masai.services.AdminService;

public class AdminPresentation {
    private AdminService adminService;

    public AdminPresentation(AdminService adminService) {
        this.adminService = adminService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Administrator Menu");
            System.out.println("1. View Cars");
            System.out.println("2. Add Car");
            System.out.println("3. Update Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Generate Reports");
            System.out.println("6. Log out");
            System.out.print("Enter your choice: ");

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
//                    generateReports();
                    break;
                case 6:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewCars() {
        try {
            System.out.println("List of available cars:");
            List<Vehicle> vehicles = adminService.listAllVehicles();
            for (Vehicle vehicle : vehicles) {
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
        } catch (DataAccessException  e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        } catch (CarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
            adminService.deleteVehicle(vehicleId);
            System.out.println("Vehicle deleted successfully.");
        } catch (DataAccessException | CarNotFoundException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }

//    private void generateReports() {
//        try {
//            List<Customer> allCustomers = customerService.findAll(); // Fetch all customers
//            
//            for (Customer customer : allCustomers) {
//                System.out.println("Customer: " + customer.getFullName());
//                
//                List<Reservation> reservations = customerService.getCustomerReservations(customer);
//                for (Reservation reservation : reservations) {
//                    System.out.println("Reservation ID: " + reservation.getId());
//                    System.out.println("Vehicle: " + reservation.getVehicle().getBrand() + " " + reservation.getVehicle().getModel());
//                    System.out.println("Start Date: " + reservation.getStartDate());
//                    System.out.println("End Date: " + reservation.getEndDate());
//                    System.out.println("Feedback: " + reservation.getFeedback());
//                    System.out.println("Rating: " + reservation.getRating());
//                    System.out.println("---");
//                }
//                
//                List<Transaction> transactions = customerService.getCustomerTransactions(customer);
//                for (Transaction transaction : transactions) {
//                    System.out.println("Transaction ID: " + transaction.getId());
//                    System.out.println("Amount: " + transaction.getAmount());
//                    System.out.println("Date: " + transaction.getDate());
//                    System.out.println("---");
//                }
//                
//                System.out.println("=====");
//            }
//        } catch (DataAccessException e) {
//            System.out.println("Error generating reports: " + e.getMessage());
//        }
//    }

}

