# 🕵️‍♂️ Crime Mapper

### 📘 Overview
Crime Mapper is a Java desktop application that uses **Swing** for the GUI and **JDBC (SQLite)** for storing data.  
It allows users to log and view crime records by **Location** and **Type of Crime**.

---

### ⚙️ Features
- Add crime records using a simple form  
- View all stored records in a table  
- SQLite database auto-created if not found  
- Easy-to-use interface built with Swing  

---

### 🧩 Technologies Used
- **Java (Swing GUI)**
- **JDBC (SQLite Database)**
- **SQLite JDBC Driver**

---

### 🗂️ Database Structure
**Database Name:** `crimeDB.db`  
**Table:** `Crimes`

| Column | Type |
|---------|------|
| Location | TEXT |
| TypeOfCrime | TEXT |

---

### ▶️ How to Run
1. Download the SQLite JDBC driver from  
   [https://github.com/xerial/sqlite-jdbc/releases](https://github.com/xerial/sqlite-jdbc/releases)
2. Place the `sqlite-jdbc.jar` file in your project folder.  
3. Compile and run:
   ```bash
   javac -cp ".;sqlite-jdbc.jar" CrimeApp.java
   java -cp ".;sqlite-jdbc.jar" CrimeApp
