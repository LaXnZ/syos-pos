-- Disable foreign key checks to avoid errors when dropping tables with foreign key dependencies
SET session_replication_role = 'replica';

-- Drop the tables in the correct order, using CASCADE to drop dependent objects as well
DROP TABLE IF EXISTS CustomerTransactionHistory CASCADE;
DROP TABLE IF EXISTS LoyaltyProgram CASCADE;
DROP TABLE IF EXISTS CardPayment CASCADE;
DROP TABLE IF EXISTS CashPayment CASCADE;
DROP TABLE IF EXISTS Payment CASCADE;
DROP TABLE IF EXISTS Shelf CASCADE;
DROP TABLE IF EXISTS OnlineInventory CASCADE;
DROP TABLE IF EXISTS StoreInventory CASCADE;
DROP TABLE IF EXISTS Stock CASCADE;
DROP TABLE IF EXISTS Transaction CASCADE;
DROP TABLE IF EXISTS Bill CASCADE;
DROP TABLE IF EXISTS Sales CASCADE;
DROP TABLE IF EXISTS Item CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Category CASCADE;

-- Re-enable foreign key checks
SET session_replication_role = 'origin';
