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
            System.out.println("Welcome to the Car Rental System");
            System.out.println("1. Customer");
            System.out.println("2. Administrator");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    // Create instances of Dao and Service classes for Customer
                    EntityManager customerEntityManager = EMUtils.getEntityManager(); // Replace with your EntityManager creation logic
                    CustomerDao customerDao = new CustomerDaoImpl();
                    ReservationDao reservationDao = new ReservationDaoImpl();
                    
                    VehicleDao vehicleDaoForAdmin = new VehicleDaoImpl();
                    VehicleDao vehicleDaoForCustomer = new VehicleDaoImpl();
                    AdminServiceImpl adminService = new AdminServiceImpl(vehicleDaoForAdmin);
                    CustomerServiceImpl customerService = new CustomerServiceImpl(customerDao, vehicleDaoForCustomer, reservationDao);
                    CustomerPresentation customerPresentation = new CustomerPresentation(customerService, adminService);
                    customerPresentation.showMenu();
                    break;
                case 2:
                    // Create instance of Dao and Service classes for Admin
                    EntityManager adminEntityManager = EMUtils.getEntityManager(); // Replace with your EntityManager creation logic
                    VehicleDao vehicleDaoForAdmin1 = new VehicleDaoImpl();
                    
                    AdminServiceImpl adminService1 = new AdminServiceImpl(vehicleDaoForAdmin1);
                    AdminPresentation adminPresentation = new AdminPresentation(adminService1);
                    adminPresentation.showMenu();
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
}
