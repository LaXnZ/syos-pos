-- Drop tables if they exist (for testing purposes)
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Bill CASCADE;
DROP TABLE IF EXISTS Transaction CASCADE;

-- Create the Customer table
CREATE TABLE Customer (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(10),
    email VARCHAR(255),
    loyalty_points INT DEFAULT 0,
    total_spent DECIMAL(10, 2) DEFAULT 0.0,
    last_purchase_date DATE
);

-- Create the Bill table
CREATE TABLE Bill (
    bill_id SERIAL PRIMARY KEY,
    bill_date DATE,
    total_price DECIMAL(10, 2),
    discount_amount DECIMAL(10, 2),
    tax_amount DECIMAL(10, 2),
    final_price DECIMAL(10, 2),
    customer_id INT,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
        REFERENCES Customer (customer_id)
        ON DELETE CASCADE
);

-- Create the Transaction table
CREATE TABLE Transaction (
    transaction_id SERIAL PRIMARY KEY,
    bill_id INT,
    item_id INT,
    quantity INT,
    total_price DECIMAL(10, 2),
    transaction_date DATE,
    transaction_type VARCHAR(255),
    CONSTRAINT fk_bill
        FOREIGN KEY (bill_id)
        REFERENCES Bill (bill_id)
        ON DELETE CASCADE
);
