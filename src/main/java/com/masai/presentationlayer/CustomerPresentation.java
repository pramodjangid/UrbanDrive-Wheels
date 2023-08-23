package com.masai.presentationlayer;

import com.masai.services.*;
import java.util.*;

public class CustomerPresentation {
    private CustomerService customerService; 
    
    public CustomerPresentation(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Customer Menu");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Search Cars");
            System.out.println("4. View Car Details");
            System.out.println("5. Make Reservation");
            System.out.println("6. Manage Reservations");
            System.out.println("7. Provide Feedback");
            System.out.println("8. View Reservations");
            System.out.println("9. Log out");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    customerService.login();
                    break;
                case 2:
                    customerService.register();
                    break;
                case 3:
                    customerService.searchCars();
                    break;
                case 4:
                    customerService.viewCarDetails();
                    break;
                case 5:
                    customerService.makeReservation();
                    break;
                case 6:
                    customerService.manageReservations();
                    break;
                case 7:
                    customerService.provideFeedback();
                    break;
                case 8:
                    customerService.viewReservations();
                    break;
                case 9:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

