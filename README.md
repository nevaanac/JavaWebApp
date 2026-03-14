# COMP0004 Java Web Application — Patient Data App

## Overview

This project is a Java web application built with servlets that allows users to view, search, sort, edit, and visualise patient data stored in a CSV file (`data/patients100.csv`).

When the application starts, it loads the patient dataset into memory. From there, users can interact with the data through a web interface — browsing the table, filtering results, updating records, and exporting the data.

---

## Features

### 1. CSV Data Loading

Patient records are loaded from a CSV file when the application starts. A custom `DataLoader` reads the file and stores the information in a `DataFrame` and `Column` structure so it can be accessed efficiently by the rest of the application.

### 2. View Patient Table

All patient records are displayed in a scrollable HTML table on the webpage. The table structure is generated automatically from the column headers in the CSV file.

### 3. Search

A search bar allows users to search a keyword. The keyword is searched across all columns and updates the visible rows in real time. Matching is case-insensitive and supports partial text matches.

### 4. Sorting

Each column header contains ▲ and ▼ buttons that allow the table to be sorted in ascending or descending order. Sorting is handled using a custom selection sort algorithm implemented in the application's model layer.

### 5. Column Visibility

Users can choose which columns to display using checkboxes above the table. Columns can be hidden or shown as needed, and these selections remain consistent even after sorting the table.

### 6. Add, Edit, and Delete Patients

The application supports basic data management operations. Users can:

* add new patient records
* edit existing records
* delete rows from the table

Any changes made are immediately saved by rewriting the CSV file.

### 7. Export as JSON

The dataset can be downloaded as a JSON file. The `JSONWriter` class converts the current data (including any edits made in the interface) into a structured JSON format.

### 8. Data Visualisation

Two graphs are displayed below the table:

* a **Gender Distribution** bar graph
* a **Race Distribution** bar graph

These graphs update based on the data currently visible in the table.

---

## Prerequisites

* Java 25
* Apache Maven 3.x

---

## Project Structure

```
src/main/java/uk/ac/ucl/
├── Column.java, DataFrame.java, DataLoader.java   # Data layer
├── JSONWriter.java                                 # JSON export
├── main/Main.java                                  # Embedded Tomcat entry point
├── model/Model.java, ModelFactory.java             # Model (singleton)
└── servlets/                                       # Controllers
    ├── ViewPatientListServlet.java
    ├── EditPatientServlet.java
    ├── DeletePatientServlet.java
    └── SaveJsonServlet.java

src/main/webapp/
├── patientList.jsp, editPatient.jsp                # Views
├── styles.css                                      # Stylesheet
└── WEB-INF/web.xml                                 # Servlet config

data/patients100.csv                                # Patient dataset
```

---

## Running the Application

Compile and start the server:

```
mvn compile exec:exec
```

Then open http://localhost:8080 in a browser.

To force a full recompile:

```
mvn clean compile exec:exec
```

---

## Implementation Highlights

* The application follows the **Model–View–Controller (MVC)** design pattern:

  * **Model** handles the data and logic.
  * **Servlets** act as controllers that process user requests.
  * **JSP pages** provide the user interface.

* The model is implemented as a **singleton** using `ModelFactory`, ensuring that data loaded into memory remains available across requests without repeatedly reading the CSV file.

* The `JSONWriter` operates directly on the `DataFrame` structure, making it reusable outside the web application if needed.

* The charts dynamically reflect the **currently displayed data** (including search filters and sorting), rather than always showing statistics from the full dataset.
