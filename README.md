# SYOS_POS Project Setup

## Overview

This project includes a setup for a Point-of-Sale (POS) system. It contains the necessary scripts to install and configure the PostgreSQL database using Docker. There are two setup scripts: one for Mac and one for Windows, to make the process seamless across different operating systems.

The project ensures that the database is properly set up with tables, relationships, and pre-populated data. After running the appropriate setup script, you will have the entire system configured with all the required data.

## Pre-requisites

Before running the setup, ensure the following software is installed:

1. **Docker**: Docker is required to run the PostgreSQL container.
    - For **Mac**: You can install Docker via Homebrew or directly from the [Docker website](https://www.docker.com/get-started).
    - For **Windows**: Install Docker Desktop from the [Docker website](https://www.docker.com/get-started).

2. **Git Bash** (for Windows users): You'll need **Git Bash** to run the `setup_db_windows.sh` script. Download it from [here](https://gitforwindows.org/).

---

## How to Set Up the Database

### For Mac Users:

1. Open **Terminal**.
2. Navigate to the directory where the script is located. For example:
   ```bash
   cd /path/to/your/project/src/main/java/scripts/
   ```
3. Run the setup script:
   ```bash
   ./setup_db_mac.sh
   ```

What happens:
- Docker will be installed if not already installed.
- A PostgreSQL Docker container will be created.
- The `syos_db` database will be created, and data from the SQL file will be loaded.

---

### For Windows Users:

1. Open **Git Bash**.
2. Navigate to the script folder. For example:
   ```bash
   cd /c/Users/YourUser/Desktop/APIIT/SYOS_POS/src/main/java/scripts/
   ```
3. Run the setup script:
   ```bash
   ./setup_db_windows.sh
   ```

What happens:
- Docker Desktop is checked and started (if needed).
- A PostgreSQL container will be set up and started.
- The `syos_db` database is created, and all tables and data are loaded.

---

## What the Script Does:
- **Docker Installation**: The scripts will ensure Docker is installed and running.
- **PostgreSQL Container**: A PostgreSQL container named `postgres-db` will be created and exposed on port 5432.
- **Database Creation**: A database named `syos_db` is created in PostgreSQL.
- **SQL Data Import**: The provided SQL dump (`outputfile.sql`) is loaded into the database, populating all the required tables with pre-defined data.

---

## Optional Post-Setup

To check if everything was set up properly, you can:

- Run `docker ps` to check if the PostgreSQL container is running.
- Log into the database by using:
  ```bash
  docker exec -it postgres-db psql -U postgres -d syos_db
  ```

---

## Notes

- Ensure that you have sufficient permissions to run Docker commands and scripts.
- Adjust the paths inside the scripts if you move them to another location within the project directory.

