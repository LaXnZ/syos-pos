-- Insert into Stock Table
INSERT INTO Stock (batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location)
VALUES
    ('BATCH001', 'VEG001', 100, 20, 50, 10, '2024-01-01', '2024-12-31', 'Warehouse A'),
    ('BATCH002', 'VEG002', 150, 30, 60, 15, '2024-01-05', '2024-11-30', 'Warehouse B'),
    ('BATCH003', 'FRU001', 200, 40, 80, 20, '2024-02-01', '2024-12-31', 'Warehouse C'),
    ('BATCH004', 'CAN001', 120, 25, 40, 12, '2024-01-15', '2025-01-15', 'Warehouse A'),
    ('BATCH005', 'SNK001', 90, 15, 30, 8, '2024-01-20', '2024-10-31', 'Warehouse D');

-- Insert into StoreInventory Table
INSERT INTO StoreInventory (stock_id, quantity_in_stock, last_reshelved_date)
VALUES
    (1, 50, '2024-01-10'),
    (2, 60, '2024-01-12'),
    (3, 80, '2024-02-03'),
    (4, 40, '2024-01-18'),
    (5, 30, '2024-01-22');

-- Insert into OnlineInventory Table
INSERT INTO OnlineInventory (stock_id, quantity_in_stock, last_updated_date)
VALUES
    (1, 50, '2024-01-10'),
    (2, 90, '2024-01-12'),
    (3, 120, '2024-02-04'),
    (4, 80, '2024-01-19'),
    (5, 60, '2024-01-23');
