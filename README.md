# UrbanDrive Wheels

# UrbanDrive Wheels Car Rental Backend System

## Overview

Welcome to the UrbanDrive Wheels Car Rental Backend System project documentation! This project offers a comprehensive console-based backend system for managing car rentals. Developed by Pramod Kumar Jangid during the Construct Week at Masai School, this project provides efficient tools for administrators, and customers to handle various car rental tasks.

## Author

Pramod Kumar Jangid
- LinkedIn Profile: [pramodjangid](https://www.linkedin.com/in/pramodjangid)

## Introduction

The UrbanDrive Wheels Car Rental Backend System project is built using Java to provide users in the car rental industry with a streamlined solution for data management and processing. This documentation will guide you through the features, functionalities, and usage of the system.

### Getting Started

The UrbanDrive Wheels Car Rental Backend System is designed to cater to two primary roles:

1. Administrator
2. Customer

## Roles and Functionalities

### Admin Role

- Manage customers
- Maintain and update vehicle information
- Monitor reservations and transactions
- Add new vehicles to the inventory
- Update vehicle details
- Delete vehicles from the inventory
- View all reservations and transactions
- Calculate total revenue

### Customer Role

- Signup for an account
- Log in to the platform
- Search available vehicles
- View vehicle details
- Make reservations
- Cancel reservations
- Provide feedback
- View past reservations
- Sort Vehicles by rental rate



## Entity-Relationship (ER) Diagram for Car Rental Backend System

### Entities:
1. **Customer**
2. **Vehicle**
3. **Reservation**
4. **Transaction**

### Relationships:

1. **Customer - Vehicle (One-to-Many):**
   - A customer can have multiple reservations (one-to-many).
   - Each reservation is associated with a vehicle.

2. **Customer - Reservation (One-to-Many):**
   - A customer can make multiple reservations (one-to-many).
   - Each reservation belongs to a customer.

3. **Reservation - Vehicle (Many-to-One):**
   - Multiple reservations can be made for vehicles (many-to-one).
   - Each reservation is associated with a vehicle.

4. **Reservation - Transaction (One-to-One):**
   - Each reservation can have one transaction (one-to-one).
   - Each transaction is associated with a reservation.

5. **Customer - Transaction (One-to-Many):**
   - A customer can have multiple transactions (one-to-many).
   - Each transaction is associated with a customer.




     
![ER Diagram Visual Representation](https://github.com/pramodjangid/statuesque-animal-5788/blob/main/ER%20Diagram.png)






### Entities Attributes:

1. **Customer:**
   - customerId (Primary Key)
   - firstName
   - lastName
   - email
   - username
   - password
   - phoneNumber
   - address
   - isDeleted

2. **Vehicle:**
   - vehicleId (Primary Key)
   - brand
   - model
   - year
   - mileage
   - availability
   - seatingCapacity
   - rentalRate

3. **Reservation:**
   - reservationId (Primary Key)
   - customer (Foreign Key: customerId)
   - vehicle (Foreign Key: vehicleId)
   - startDate
   - endDate
   - isDeleted

4. **Transaction:**
   - transactionId (Primary Key)
   - customer (Foreign Key: customerId)
   - reservation (Foreign Key: reservationId)
   - transactionDate
   - amount
   - isDeleted



## Tech Stack

The UrabanDrive Wheels Car Rental Backend System is developed using Java, a versatile programming language known for its performance and flexibility.


![Java Logo](https://raw.githubusercontent.com/github/explore/5b3600551e122a3277c2c5368af2ad5725ffa9a1/topics/java/java.png)

## Tools

Various tools and technologies were employed during the development of the Car Rental System.


![Tools](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlfELtjrat8Rdge4RUNtjh7Nt6_vO4TEZoE83FmDf-&s)


## Learnings


Throughout this project, I encountered challenges that led to valuable learnings:

- Writing efficient and maintainable code
- Structuring a project and planning its execution
- Handling exceptions and error scenarios


## Source Code


The source code for the Car Rental System project is available on GitHub. You can explore the code, contribute to its development, and use it as a foundation for similar projects.

[GitHub Repository](https://github.com/pramodjangid/statuesque-animal-5788)

Thank you for your interest in the UrabanDrive Wheels Car Rental Backend System!










