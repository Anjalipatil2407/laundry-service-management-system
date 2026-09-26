# laundry-service-management-system
 
# Overview

The Laundry Management System is a Java-based console application developed to manage customers, garments, laundry services, orders, billing, and order tracking. The system provides an organized way to handle the complete laundry process from customer registration to order delivery.

# Features

Maintain customer information

Register garment details

Select laundry service types

Create and manage laundry orders

Calculate service charges

Calculate GST automatically

Search orders using Order ID

Update order status

Maintain order status history

Sort orders according to due date

Generate customer bills

Generate summary reports

Validate user input

Handle invalid inputs using exception handling

# Technologies Used

Java

Java Collections Framework

Java OOP

Scanner for user input

LocalDate / LocalDateTime for date management

# Java Concepts Used

Object-Oriented Programming

The project uses classes and objects to represent customers, garments, laundry orders, services, and other system components.

Encapsulation

Data members are kept private and accessed using appropriate methods to provide better data security and organization.

Inheritance

Inheritance can be used to create relationships between common and specialized classes where required.

Polymorphism

Methods can be overloaded or overridden to provide different implementations according to the requirements of the system.

Collections

Java collections such as ArrayList are used to store and manage customers, orders, garments, and status history dynamically.

CRUD Operations

The system demonstrates CRUD operations:

Create – Add customers and create laundry orders

Read – View and search customer/order information

Update – Update order information and status

Delete – Remove records where required

Searching

Orders can be searched using their unique Order ID.

Sorting

Laundry orders can be sorted according to their due dates to identify which orders need to be completed first.

Validation

The system validates important inputs such as customer details, quantities, service selections, order IDs, and other required information.

Exception Handling

Exception handling is used to prevent the application from terminating when invalid input or unexpected errors occur.

Order Status Flow

The order passes through different stages during the laundry process:

RECEIVED → WASHING → IRONING → READY → DELIVERED

The system also maintains the status history of each order.

Billing

The bill is calculated according to the selected laundry service, garment type, and quantity.

The system calculates:

Subtotal = Service Charge × Quantity

GST = Subtotal × GST Rate

Final Amount = Subtotal + GST

The generated bill displays customer information, order details, service charges, GST, and the final payable amount.

Main Modules

Customer Management

Garment Management

Laundry Service Management

Order Management

Order Search

Order Status Tracking

Billing and GST Calculation

Sorting by Due Date

Summary Report

Validation and Exception Handling

How to Run

Make sure Java is installed on the system.

Open the project folder in VS Code or Terminal.

Compile the Java program:

javac LaundryManagementSystem.java

Run the program:

java LaundryManagementSystem

Replace LaundryManagementSystem with the actual name of the main Java class if it is different.

# Project Objective

The main objective of this project is to develop a console-based Laundry Management System that efficiently manages customer information, garments, laundry services, orders, billing, and order status. The project also demonstrates the practical implementation of Java OOP, collections, CRUD operations, searching, sorting, validation, and exception handling.

# Conclusion

The Laundry Management System provides a simple and efficient solution for managing laundry operations. It reduces manual work by organizing customer and order information, calculating bills automatically, tracking order progress, and generating useful reports. The project demonstrates how core Java programming concepts can be applied to develop a practical real-world application.
