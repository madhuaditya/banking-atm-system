# Banking and ATM System

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Structure](#database-structure)
- [Setup Instructions](#setup-instructions)
- [How to Use](#how-to-use)


## Overview
The **Banking and ATM System** is a Java-based project that simulates essential banking operations such as creating accounts, performing transactions, and managing account details. It interacts with a MySQL database for storing user information, accounts, transactions, and feedback.

This project is designed to:
- Offer a secure and reliable platform for performing banking operations.
- Implement user roles (Admin and Customer) for better access control.
- Allow users to view their transaction history, change passwords, and submit feedback.

## Features
- **User Management**: Registration and login with role-based access (customer/admin).
- **Account Management**: Create new accounts, view balance, update account information.
- **Transactions**: Withdraw and deposit money, with balance validation.
- **Transaction History**: Retrieve all transactions or only the most recent ones.
- **Feedback System**: Users can submit feedback and rate their experience.
- **Admin Privileges**: Admin can manage users, view feedback, and manage the ATM's status.
- ** Loans**: In future.

## Technologies Used
- **Programming Language**: Java
- **Database**: MySQL
- **Libraries/Tools**: JDBC (Java Database Connectivity)
- **IDE**: IntelliJ IDEA / Eclipse

## Database Structure
### Tables
- **Users**: Stores user details such as ID, username, password, role (admin/customer), email, and phone number.
- **Accounts**: Manages user account details like balance, account type (savings/checking), and user ID.
- **Transactions**: Stores transaction data like amount, type (withdraw/deposit), and timestamp.
- **Feedback**: Collects user feedback with a rating system and feedback messages.
- **ATMs**: Manages ATM details such as location, cash available, and status.
- **Loans**: Manges user Loans details

### Example of Table Structure
```sql
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phoneNo BIGINT NOT NULL,
    email VARCHAR(50) NOT NULL,
    role ENUM('customer', 'admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_type ENUM('savings', 'checking') NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


```
## Setup Instructions

### Prerequisites
Before you begin, ensure you have the following installed on your machine:

- **Java** (version 8 or higher): [Download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **MySQL** (version 5.7 or higher): [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **JDBC Driver** for MySQL: [Download MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- An Integrated Development Environment (IDE) such as **IntelliJ IDEA** or **Eclipse**.

### Steps to Run the Project

1. **Clone the Repository**:
   Open your terminal or command prompt and run the following command to clone the project repository to your local machine:

   ```bash
   git clone https://github.com/yourusername/banking-atm-system.git
   cd banking-atm-system
   ```
2. To set up the MySQL database for the Banking and ATM System, follow the steps below:

### Prerequisites
- Ensure that you have **MySQL Server** installed on your machine. If not, download it from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).

### Steps to Create the Database and Tables

1. **Open MySQL Command Line Client**:
   - You can use the MySQL Command Line Client, MySQL Workbench, or any other MySQL client tool you prefer.

2. **Log in to MySQL**:
   - Enter your MySQL username and password. The default username is usually `root`. For example:

   ```bash
   mysql -u root -p
   ```
3. Copy data from 'bank-schama.sql'
4. run code.

### **Summary**:
This section provides clear instructions for setting up MySQL for your project, including commands for creating the database and tables. Make sure to customize it if you have any specific setup requirements!

## How to Use the Code

To effectively use the Banking and ATM System project, follow the steps below:

### 1. Clone the Repository
Open your terminal or command prompt and run the following command to clone the project repository (replace `yourusername` with your GitHub username):

```bash
git clone https://github.com/yourusername/banking-atm-system.git

```
   Open Connections.java file and replace `username` with your mysql username and `password` with your mysql password
   
### 2. Next step
```bash
cd banking-atm-system.git
cd Java-SQL
cd src
javac Main
java Main
```







