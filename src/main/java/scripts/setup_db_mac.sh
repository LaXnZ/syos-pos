#!/bin/bash

# Exit script if any command fails
set -e

# Define the project root manually based on the current script directory
PROJECT_ROOT="/Users/sumuditha/Desktop/APIIT/SYOS_POS"

# Set the path to the SQL dump file relative to the project root
SQL_DUMP_FILE="$PROJECT_ROOT/src/main/resources/outputfile.sql"

# Ensure the SQL file exists
if [ ! -f "$SQL_DUMP_FILE" ]; then
    echo "Error: SQL dump file not found at $SQL_DUMP_FILE"
    exit 1
fi

# Install necessary packages
echo "Installing necessary packages..."

# Update system packages
sudo apt-get update || brew update

# Install Docker if it's not installed
if ! [ -x "$(command -v docker)" ]; then
    echo "Docker is not installed. Installing Docker..."
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        sudo apt-get install -y docker.io
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        brew install --cask docker
    fi
else
    echo "Docker is already installed"
fi

# Start Docker if not running
if (! docker stats --no-stream ); then
    echo "Starting Docker..."
    sudo systemctl start docker || open -a Docker
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
