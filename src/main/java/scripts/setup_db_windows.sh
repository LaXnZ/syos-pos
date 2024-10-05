#!/bin/bash

# Exit script if any command fails
set -e

# Detect if running in Git Bash or WSL
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
    echo "Running on Windows (Git Bash or Cygwin)"
    # Use /c/ instead of C:\
    PROJECT_ROOT="/c/Users/YourUser/Desktop/APIIT/SYOS_POS"
else
    echo "Running on Linux/Mac"
    PROJECT_ROOT="/Users/sumuditha/Desktop/APIIT/SYOS_POS"
fi

# Set the path to the SQL dump file relative to the project root
SQL_DUMP_FILE="$PROJECT_ROOT/src/main/resources/outputfile.sql"

# Ensure the SQL file exists
if [ ! -f "$SQL_DUMP_FILE" ]; then
    echo "Error: SQL dump file not found at $SQL_DUMP_FILE"
    exit 1
fi

# Install necessary packages
echo "Installing necessary packages..."

# Check for Docker installation (Docker Desktop for Windows or Docker on WSL)
if ! [ -x "$(command -v docker)" ]; then
    echo "Docker is not installed. Please install Docker Desktop on Windows."
    exit 1
else
    echo "Docker is already installed"
fi

# Start Docker if not running
if (! docker stats --no-stream ); then
    echo "Starting Docker..."
    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
        echo "Please start Docker Desktop manually in Windows."
        exit 1
    else
        sudo systemctl start docker || open -a Docker
    fi
fi

# Pull latest PostgreSQL image
echo "Pulling the latest PostgreSQL Docker image..."
docker pull postgres:latest

# Run PostgreSQL container
echo "Starting PostgreSQL container..."
docker run --name postgres-db -e POSTGRES_PASSWORD=bootcamp -d -p 5432:5432 postgres:latest

# Wait for PostgreSQL to start
echo "Waiting for PostgreSQL to initialize..."
sleep 5

# Create the database and restore the SQL dump
echo "Creating the database and restoring data..."

# Copy the SQL dump file into the Docker container
docker cp "$SQL_DUMP_FILE" postgres-db:/tmp/outputfile.sql

# Run commands inside the PostgreSQL container to create and populate the database
docker exec -i postgres-db psql -U postgres -c "CREATE DATABASE syos_db;"
docker exec -i postgres-db psql -U postgres -d syos_db -f /tmp/outputfile.sql

echo "Database created and populated successfully!"

# Optional: Print container status
docker ps

echo "Setup completed!"
