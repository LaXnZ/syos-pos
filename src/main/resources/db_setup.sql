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

-- Create the Item table
CREATE TABLE Item (
    item_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) UNIQUE NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_price DECIMAL(10, 2) NOT NULL
);

-- Create the Bill table
CREATE TABLE Bill (
    bill_id SERIAL PRIMARY KEY,
    bill_date DATE NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    discount_amount DECIMAL(10, 2) DEFAULT 0.0,
    tax_amount DECIMAL(10, 2) DEFAULT 0.0,
    final_price DECIMAL(10, 2) NOT NULL,
    cash_tendered DECIMAL(10, 2) NOT NULL,
    change_amount DECIMAL(10, 2) NOT NULL,
    customer_id INT REFERENCES Customer(customer_id)
);

-- Create the Transaction table
CREATE TABLE Transaction (
    transaction_id SERIAL PRIMARY KEY,
    bill_id INT REFERENCES Bill(bill_id),
    item_id INT REFERENCES Item(item_id),
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    transaction_date DATE DEFAULT CURRENT_DATE,
    transaction_type VARCHAR(50) NOT NULL
);

-- Create the StoreInventory table
CREATE TABLE StoreInventory (
    stock_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) REFERENCES Item(item_code),
    quantity_in_stock INT NOT NULL,
    date_of_purchase DATE NOT NULL,
    expiry_date DATE
);

-- Create the OnlineInventory table
CREATE TABLE OnlineInventory (
    stock_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) REFERENCES Item(item_code),
    quantity_in_stock INT NOT NULL,
    date_of_purchase DATE NOT NULL,
    expiry_date DATE
);

-- Create the Shelf table
CREATE TABLE Shelf (
    shelf_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) REFERENCES Item(item_code),
    quantity INT NOT NULL,
    moved_date DATE NOT NULL,
    expiry_date DATE
);

-- Create the Payment table
CREATE TABLE Payment (
    payment_id SERIAL PRIMARY KEY,
    bill_id INT REFERENCES Bill(bill_id),
    payment_type VARCHAR(50) NOT NULL,
    payment_amount DECIMAL(10, 2) NOT NULL
);

-- Create the CashPayment table (extends Payment)
CREATE TABLE CashPayment (
    payment_id INT REFERENCES Payment(payment_id),
    cash_received DECIMAL(10, 2) NOT NULL,
    cash_change DECIMAL(10, 2) NOT NULL
);

-- Create the CardPayment table (extends Payment)
CREATE TABLE CardPayment (
    payment_id INT REFERENCES Payment(payment_id),
    card_number VARCHAR(20),
    card_type VARCHAR(20)
);

-- Create the LoyaltyProgram table
CREATE TABLE LoyaltyProgram (
    loyalty_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES Customer(customer_id),
    points_earned INT NOT NULL,
    points_redeemed INT DEFAULT 0,
    date DATE NOT NULL
);

-- Create the CustomerTransactionHistory table for ML training
CREATE TABLE CustomerTransactionHistory (
    history_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES Customer(customer_id),
    total_purchases INT,
    total_spent DECIMAL(10, 2),
    avg_spent_per_purchase DECIMAL(10, 2),
    purchase_frequency INT,
    last_purchase_date DATE,
    data_as_of DATE NOT NULL
);
