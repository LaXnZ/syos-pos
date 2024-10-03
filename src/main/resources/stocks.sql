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
