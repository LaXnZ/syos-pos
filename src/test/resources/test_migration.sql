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






-- Inserting 5 customers into the Customer table
INSERT INTO Customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date)
VALUES ('John Doe', '0711234567', 'john.doe@example.com', 100, 1200.50, '2023-12-15');

INSERT INTO Customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date)
VALUES ('Jane Smith', '0729876543', 'jane.smith@example.com', 200, 2500.75, '2023-11-20');

INSERT INTO Customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date)
VALUES ('Amal Silva', '0789876543', 'amal.silva@example.com', 150, 3750.25, '2024-01-11');

INSERT INTO Customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date)
VALUES ('Nimal Perera', '0774567890', 'nimal.perera@example.com', 300, 4700.30, '2023-12-01');

INSERT INTO Customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date)
VALUES ('Kamal Fernando', '0761239876', 'kamal.fernando@example.com', 50, 600.00, '2023-10-25');


-- Insert Categories
INSERT INTO Category (category_name) VALUES ('Fresh Vegetables');
INSERT INTO Category (category_name) VALUES ('Fresh Fruits');
INSERT INTO Category (category_name) VALUES ('Canned Foods');
INSERT INTO Category (category_name) VALUES ('Sauces');
INSERT INTO Category (category_name) VALUES ('Various Groceries');
INSERT INTO Category (category_name) VALUES ('Spices & Herbs');
INSERT INTO Category (category_name) VALUES ('Oils/Vinegars');
INSERT INTO Category (category_name) VALUES ('Refrigerated Items');
INSERT INTO Category (category_name) VALUES ('Dairy');
INSERT INTO Category (category_name) VALUES ('Cheese');
INSERT INTO Category (category_name) VALUES ('Frozen');
INSERT INTO Category (category_name) VALUES ('Meat');
INSERT INTO Category (category_name) VALUES ('Seafood');
INSERT INTO Category (category_name) VALUES ('Baked Goods');
INSERT INTO Category (category_name) VALUES ('Baking');
INSERT INTO Category (category_name) VALUES ('Snacks');
INSERT INTO Category (category_name) VALUES ('Personal Care');
INSERT INTO Category (category_name) VALUES ('Medicine');
INSERT INTO Category (category_name) VALUES ('Kitchen');
INSERT INTO Category (category_name) VALUES ('Cleaning Products');
INSERT INTO Category (category_name) VALUES ('Other Stuff');
INSERT INTO Category (category_name) VALUES ('Pets');
INSERT INTO Category (category_name) VALUES ('Baby');
INSERT INTO Category (category_name) VALUES ('Office Supplies');
INSERT INTO Category (category_name) VALUES ('Alcohol');
INSERT INTO Category (category_name) VALUES ('Themed Meals');

-- Insert Items for Fresh Vegetables (Category ID 1)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FV001', 'Asparagus', 1000.00, 1);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FV002', 'Beets', 700.00, 1);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FV003', 'Broccoli', 550.00, 1);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FV004', 'Carrots', 250.00, 1);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FV005', 'Celery', 300.00, 1);

-- Insert Items for Fresh Fruits (Category ID 2)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FF001', 'Apples', 300.00, 2);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FF002', 'Avocado', 400.00, 2);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FF003', 'Bananas', 150.00, 2);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FF004', 'Berries', 650.00, 2);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FF005', 'Cherries', 800.00, 2);

-- Insert Items for Canned Foods (Category ID 3)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CF001', 'Applesauce', 500.00, 3);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CF002', 'Baked Beans', 350.00, 3);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CF003', 'Beans', 250.00, 3);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CF004', 'Carrots', 300.00, 3);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CF005', 'Corn', 300.00, 3);

-- Insert Items for Sauces (Category ID 4)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SC001', 'BBQ Sauce', 500.00, 4);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SC002', 'Hot Sauce', 350.00, 4);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SC003', 'Salsa', 500.00, 4);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SC004', 'Soy Sauce', 300.00, 4);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SC005', 'Steak Sauce', 600.00, 4);

-- Insert Items for Various Groceries (Category ID 5)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('VG001', 'Bottled Water', 200.00, 5);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('VG002', 'Cereal', 400.00, 5);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('VG003', 'Coffee', 1200.00, 5);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('VG004', 'Honey', 500.00, 5);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('VG005', 'Peanut Butter', 450.00, 5);

-- Insert Items for Spices & Herbs (Category ID 6)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SH001', 'Basil', 200.00, 6);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SH002', 'Black Pepper', 400.00, 6);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SH003', 'Cinnamon', 300.00, 6);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SH004', 'Garlic', 250.00, 6);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SH005', 'Oregano', 350.00, 6);

-- Insert Items for Oils/Vinegars (Category ID 7)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OV001', 'Apple Cider Vinegar', 600.00, 7);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OV002', 'Olive Oil', 1200.00, 7);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OV003', 'Vegetable Oil', 800.00, 7);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OV004', 'White Vinegar', 500.00, 7);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OV005', 'Balsamic Vinegar', 900.00, 7);

-- Insert Items for Refrigerated Items (Category ID 8)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('RI001', 'Chip Dip', 400.00, 8);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('RI002', 'Eggs', 250.00, 8);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('RI003', 'Juice', 350.00, 8);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('RI004', 'Tofu', 500.00, 8);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('RI005', 'Tortillas', 200.00, 8);

-- Insert Items for Dairy (Category ID 9)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('DR001', 'Butter', 500.00, 9);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('DR002', 'Milk', 100.00, 9);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('DR003', 'Sour Cream', 400.00, 9);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('DR004', 'Yogurt', 300.00, 9);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('DR005', 'Whipped Cream', 350.00, 9);

-- Insert Items for Cheese (Category ID 10)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CH001', 'Cheddar', 800.00, 10);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CH002', 'Cottage Cheese', 600.00, 10);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CH003', 'Cream Cheese', 550.00, 10);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CH004', 'Mozzarella', 750.00, 10);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CH005', 'Swiss', 850.00, 10);

-- Insert Items for Frozen (Category ID 11)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FR001', 'Burritos', 300.00, 11);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FR002', 'Ice Cream', 500.00, 11);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FR003', 'Frozen Pizza', 700.00, 11);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FR004', 'Popsicles', 250.00, 11);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('FR005', 'Veggie Burgers', 600.00, 11);

-- Insert Items for Meat (Category ID 12)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MT001', 'Bacon', 900.00, 12);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MT002', 'Chicken', 750.00, 12);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MT003', 'Beef', 1200.00, 12);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MT004', 'Sausage', 650.00, 12);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MT005', 'Ham', 800.00, 12);

-- Insert Items for Seafood (Category ID 13)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SF001', 'Salmon', 1500.00, 13);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SF002', 'Shrimp', 1300.00, 13);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SF003', 'Tilapia', 900.00, 13);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SF004', 'Tuna', 1100.00, 13);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SF005', 'Crab', 1700.00, 13);

-- Insert Items for Baked Goods (Category ID 14)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BG001', 'Bagels', 300.00, 14);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BG002', 'Buns', 200.00, 14);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BG003', 'Cookies', 250.00, 14);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BG004', 'Fresh Bread', 150.00, 14);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BG005', 'Pastries', 350.00, 14);

-- Insert Items for Baking (Category ID 15)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BK001', 'Baking Powder', 200.00, 15);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BK002', 'Brown Sugar', 250.00, 15);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BK003', 'Cake Icing', 300.00, 15);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BK004', 'Cocoa', 400.00, 15);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BK005', 'Flour', 200.00, 15);

-- Insert Items for Snacks (Category ID 16)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SN001', 'Potato Chips', 200.00, 16);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SN002', 'Popcorn', 150.00, 16);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SN003', 'Candy', 250.00, 16);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SN004', 'Pretzels', 300.00, 16);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('SN005', 'Granola Bars', 400.00, 16);

-- Insert Items for Personal Care (Category ID 17)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PC001', 'Bath Soap', 150.00, 17);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PC002', 'Shampoo', 200.00, 17);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PC003', 'Toothpaste', 120.00, 17);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PC004', 'Facial Tissue', 100.00, 17);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PC005', 'Moisturizing Lotion', 350.00, 17);

-- Insert Items for Medicine (Category ID 18)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MD001', 'Aspirin', 150.00, 18);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MD002', 'Cold Medicine', 250.00, 18);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MD003', 'Antacid', 180.00, 18);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MD004', 'Band-Aids', 100.00, 18);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('MD005', 'Vitamins', 300.00, 18);

-- Insert Items for Kitchen (Category ID 19)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('KT001', 'Aluminum Foil', 150.00, 19);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('KT002', 'Dish Soap', 100.00, 19);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('KT003', 'Coffee Filters', 50.00, 19);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('KT004', 'Napkins', 80.00, 19);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('KT005', 'Sponges', 70.00, 19);

-- Insert Items for Cleaning Products (Category ID 20)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CP001', 'Air Freshener', 200.00, 20);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CP002', 'Bleach', 150.00, 20);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CP003', 'Laundry Detergent', 500.00, 20);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CP004', 'Fabric Softener', 300.00, 20);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('CP005', 'Mop Head', 250.00, 20);

-- Insert Items for Other Stuff (Category ID 21)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OS001', 'Batteries', 200.00, 21);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OS002', 'Candles', 150.00, 21);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OS003', 'Fresh Flowers', 500.00, 21);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OS004', 'Greeting Cards', 50.00, 21);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OS005', 'Insect Repellent', 350.00, 21);

-- Insert Items for Pets (Category ID 22)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PT001', 'Dog Food', 400.00, 22);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PT002', 'Cat Food', 350.00, 22);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PT003', 'Cat Litter', 200.00, 22);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PT004', 'Dog Treats', 150.00, 22);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('PT005', 'Pet Shampoo', 250.00, 22);

-- Insert Items for Baby (Category ID 23)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BB001', 'Diapers', 500.00, 23);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BB002', 'Baby Food', 300.00, 23);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BB003', 'Formula', 600.00, 23);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BB004', 'Baby Wipes', 200.00, 23);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('BB005', 'Bottles', 150.00, 23);

-- Insert Items for Office Supplies (Category ID 24)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OSU001', 'Notepads', 100.00, 24);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OSU002', 'Pens', 50.00, 24);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OSU003', 'Paper', 150.00, 24);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OSU004', 'Envelopes', 75.00, 24);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('OSU005', 'Glue', 40.00, 24);

-- Insert Items for Alcohol (Category ID 25)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('AL001', 'Beer', 500.00, 25);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('AL002', 'Champagne', 2000.00, 25);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('AL003', 'Red Wine', 1500.00, 25);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('AL004', 'Vodka', 2500.00, 25);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('AL005', 'Whiskey', 3000.00, 25);

-- Insert Items for Themed Meals (Category ID 26)
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('TM001', 'Burger Night Kit', 600.00, 26);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('TM002', 'Chili Night Kit', 700.00, 26);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('TM003', 'Pizza Night Kit', 800.00, 26);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('TM004', 'Spaghetti Night Kit', 500.00, 26);
INSERT INTO Item (item_code, item_name, item_price, category_id) VALUES ('TM005', 'Taco Night Kit', 750.00, 26);
-- Insert into Stock table
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES ('BCH001', 'FV001', 100, 10, 200, 50, '2024-01-01', '2024-06-01', 'Aisle 1');
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES ('BCH002', 'FF002', 150, 15, 200, 75, '2024-01-05', '2024-06-05', 'Aisle 2');
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES ('BCH003', 'CF003', 200, 20, 300, 100, '2024-01-10', '2024-07-01', 'Aisle 3');
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES ('BCH004', 'SC004', 50, 5, 100, 25, '2024-01-15', '2024-06-15', 'Aisle 4');
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES ('BCH005', 'VG005', 250, 25, 400, 150, '2024-01-20', '2024-07-20', 'Aisle 5');

-- Insert into Shelf table
INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code)
VALUES ('FV001', 10, '2024-01-02', '2024-06-01', 'BCH001');
INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code)
VALUES ('FF002', 15, '2024-01-06', '2024-06-05', 'BCH002');
INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code)
VALUES ('CF003', 20, '2024-01-11', '2024-07-01', 'BCH003');
INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code)
VALUES ('SC004', 5, '2024-01-16', '2024-06-15', 'BCH004');
INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code)
VALUES ('VG005', 25, '2024-01-21', '2024-07-20', 'BCH005');

-- Insert into StoreInventory table
INSERT INTO StoreInventory (item_code, quantity_in_stock, date_of_purchase, expiry_date)
VALUES ('FV001', 100, '2024-01-01', '2024-06-01');
INSERT INTO StoreInventory (item_code, quantity_in_stock, date_of_purchase, expiry_date)
VALUES ('FF002', 150, '2024-01-05', '2024-06-05');
INSERT INTO StoreInventory (item_code, quantity_in_stock, date_of_purchase, expiry_date)
VALUES ('CF003', 200, '2024-01-10', '2024-07-01');
INSERT INTO StoreInventory (item_code, quantity_in_stock, date_of_purchase, expiry_date)
VALUES ('SC004', 50, '2024-01-15', '2024-06-15');
INSERT INTO StoreInventory (item_code, quantity_in_stock, date_of_purchase, expiry_date)
VALUES ('VG005', 250, '2024-01-20', '2024-07-20');

-- Insert into OnlineInventory table
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES (1, 50, '2024-01-02');
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES (2, 100, '2024-01-06');
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES (3, 150, '2024-01-12');
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES (4, 25, '2024-01-17');
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES (5, 200, '2024-01-22');
