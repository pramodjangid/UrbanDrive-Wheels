package com.masai.ui;

import com.masai.presentationlayer.*;
import com.masai.services.*;
import com.masai.utility.EMUtils;
import com.masai.dao.*;
import jakarta.persistence.EntityManager;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    runCustomerModule(scanner);
                    break;
                case 2:
                    runAdminModule(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("Welcome to the Car Rental System");
        System.out.println("1. Customer");
        System.out.println("2. Administrator");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void runCustomerModule(Scanner scanner) {
        EntityManager entityManager = EMUtils.getEntityManager(); // Replace with your EntityManager creation logic
        
        CustomerDao customerDao = new CustomerDaoImpl();
        ReservationDao reservationDao = new ReservationDaoImpl();
        TransactionDao transactionDao = new TransactionDaoImpl();
        VehicleDao vehicleDao = new VehicleDaoImpl();
        
        CustomerService customerService = new CustomerServiceImpl(customerDao, vehicleDao, reservationDao, transactionDao);
        AdminService adminService = new AdminServiceImpl(vehicleDao, transactionDao, customerDao, reservationDao);
        
        CustomerPresentation customerPresentation = new CustomerPresentation(customerService, adminService);
        customerPresentation.showMenu();
    }

    private static void runAdminModule(Scanner scanner) {
        EntityManager entityManager = EMUtils.getEntityManager(); // Replace with your EntityManager creation logic
        
        CustomerDao customerDao = new CustomerDaoImpl();
        ReservationDao reservationDao = new ReservationDaoImpl();
        TransactionDao transactionDao = new TransactionDaoImpl();
        VehicleDao vehicleDao = new VehicleDaoImpl();
        
        CustomerService customerService = new CustomerServiceImpl(customerDao, vehicleDao, reservationDao, transactionDao);
        AdminService adminService = new AdminServiceImpl(vehicleDao, transactionDao, customerDao, reservationDao);
        
        AdminPresentation adminPresentation = new AdminPresentation(adminService, customerService);
        adminPresentation.showMenu();
    }
}
