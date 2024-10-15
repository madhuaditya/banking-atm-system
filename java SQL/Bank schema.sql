CREATE DATABASE BANKING;
USE BANKING;
-- USER DATA BASE FOR SIMPLE DATA ;

CREATE TABLE Users (
    user_id int AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255) ,
    phoneNo bigint not null,
    email varchar(50) not null,
    role ENUM('customer', 'admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ACCCOUNT DATA TO STORE DATA FO ACCOUNT IN TABLE 

CREATE TABLE Accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY ,
    user_id INT NOT NULL,
    account_type ENUM('savings', 'checking') NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- ALL TYPE TRANSECTION DATA IN A TABLE

CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY ,
    account_id INT NOT NULL,
    user_id INT NOT NULL,
    transaction_type ENUM('withdrawal', 'deposit') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
-- drop table Transactions;

-- DATA AT ATM 

CREATE TABLE ATMs (
    atm_id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    cash_available DECIMAL(10, 2) NOT NULL,
    status ENUM('active', 'inactive', 'maintenance') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--  if user have any loan then 

CREATE TABLE Loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    loan_amount DECIMAL(15, 2) NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('active', 'paid', 'defaulted') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

select * from feedback;
-- user feedback table
CREATE TABLE Feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5), -- Rating between 1 and 5
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


