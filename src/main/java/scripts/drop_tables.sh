#!/bin/bash

DB_NAME="syos_db"
DB_USER="postgres"
SQL_FILE="/Users/sumuditha/Desktop/APIIT/SYOS_POS/src/main/resources/dropTables.sql"

echo "Running drop all tables script..."
psql -h localhost -p 5432 -U $DB_USER -d $DB_NAME -f $SQL_DROP_FILE
echo "All tables dropped."
