# COMP0004 Java Web Application — Patient Data App

## Overview

This project is a Java web application built with servlets that allows users to view, search, sort, edit, and visualise patient data stored in a CSV file (`data/patients100.csv`).

When the application starts, it loads the patient dataset into memory. From there, users can interact with the data through a web interface — browsing the table, filtering results, updating records, and exporting the data.

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
