package com.masai.ui;

import com.masai.presentationlayer.*;
import com.masai.services.*;
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
                    CustomerServiceImpl customerService = new CustomerServiceImpl(); // Replace with actual service
                    CustomerPresentation customerPresentation = new CustomerPresentation(customerService);
                    customerPresentation.showMenu();
                    break;
                case 2:
                    AdminServiceImpl adminService = new AdminServiceImpl(); // Replace with actual service
                    AdminPresentation adminPresentation = new AdminPresentation(adminService);
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

