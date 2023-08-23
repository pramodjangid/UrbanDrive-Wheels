package com.masai.presentationlayer;

import com.masai.services.AdminService;
import java.util.*;

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
                    adminService.viewCars();
                    break;
                case 2:
                    adminService.addCar();
                    break;
                case 3:
                    adminService.updateCar();
                    break;
                case 4:
                    adminService.deleteCar();
                    break;
                case 5:
                    adminService.generateReports();
                    break;
                case 6:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

