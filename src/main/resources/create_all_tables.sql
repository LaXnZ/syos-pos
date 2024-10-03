-- Create the Customer table
CREATE TABLE Customer (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(10) UNIQUE NOT NULL, -- Ensure phone number is unique
    email VARCHAR(255),
    loyalty_points INT DEFAULT 0,
    total_spent DECIMAL(10, 2) DEFAULT 0.0,
    last_purchase_date DATE
    customer_id INT REFERENCES Customer(customer_id) ON DELETE CASCADE
);

-- Create the Category table
CREATE TABLE Category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE
);

-- Update the Item table to include a foreign key reference to the Category table
CREATE TABLE Item (
    item_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) UNIQUE NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_price DECIMAL(10, 2) NOT NULL,
    category_id INT REFERENCES Category(category_id)  -- Foreign key to Category
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
    customer_id INT REFERENCES Customer(customer_id) ON DELETE CASCADE
);

-- Create the updated Transaction table
CREATE TABLE Transaction (
    transaction_id SERIAL PRIMARY KEY,
    bill_id INT REFERENCES Bill(bill_id),
    item_id INT REFERENCES Item(item_id),
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    transaction_date DATE DEFAULT CURRENT_DATE,
    transaction_type VARCHAR(50) NOT NULL DEFAULT 'cash', -- Default to 'cash', allow 'card'
);



-- Create the LoyaltyProgram table
CREATE TABLE LoyaltyProgram (
    loyalty_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES Customer(customer_id) ON DELETE CASCADE,
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


CREATE TABLE Stock (
    stock_id SERIAL PRIMARY KEY,
    batch_code VARCHAR(50) NOT NULL,
    item_code VARCHAR(10) NOT NULL REFERENCES Item(item_code),
    quantity_in_stock INT NOT NULL,
    reshelf_quantity INT NOT NULL,
    shelf_capacity INT NOT NULL,
    reorder_level INT NOT NULL,
    date_of_purchase DATE NOT NULL,
    expiry_date DATE,
    stock_location VARCHAR(255) NOT NULL,
    UNIQUE(batch_code, item_code)
);



CREATE TABLE Shelf (
    shelf_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) REFERENCES Item(item_code),
    quantity INT NOT NULL,
    moved_date DATE NOT NULL,
    expiry_date DATE,
    batch_code VARCHAR(50) NOT NULL
);

-- StoreInventory table
CREATE TABLE StoreInventory (
    stock_id SERIAL PRIMARY KEY,
    item_code VARCHAR(10) REFERENCES Item(item_code),
    quantity_in_stock INT NOT NULL,
    date_of_purchase DATE NOT NULL,
    expiry_date DATE
);

-- Updated OnlineInventory Table (links to Stock table)
CREATE TABLE OnlineInventory (
    stock_id INT REFERENCES Stock(stock_id),  -- Links to the Stock table
    quantity_in_stock INT NOT NULL,  -- How much of this stock is in the online inventory
    last_updated_date DATE  -- Tracks when this stock was last updated for online sales
);


