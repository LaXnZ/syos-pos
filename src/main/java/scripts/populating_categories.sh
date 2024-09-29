#!/bin/bash

DB_NAME="syos_db"
DB_USER="postgres"
SQL_FILE="/Users/sumuditha/Desktop/APIIT/SYOS_POS/src/main/resources/categories.sql"

echo "Populating categories into db..."
psql -h localhost -p 5432 -U $DB_USER -d $DB_NAME -f $SQL_FILE
echo "Populating completed."
