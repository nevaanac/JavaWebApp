COMP0004 Java Web Application — Patient Data App
=================================================

OVERVIEW
--------
A Java servlet-based web application for viewing, searching, sorting, editing,
and visualising patient data loaded from a CSV file (data/patients100.csv).

FEATURES
--------
1. CSV Data Loading
   Patient records are loaded from a CSV file at startup using a custom
   DataLoader class into a DataFrame/Column structure.

2. View Patient Table
   All patient records are displayed in a scrollable, styled HTML table.
   Columns are auto-generated from the CSV headers.

3. Search
   A search bar filters all visible rows across every column in real time
   (case-insensitive substring match).

4. Sort (Ascending & Descending)
   Each column header has ▲ and ▼ buttons to sort the table using a custom
   selection sort algorithm implemented in the Model class.

5. Column Visibility
   Checkboxes above the table let users show or hide individual columns.
   Column selection is preserved when sorting.

6. Add / Edit / Delete Patients
   Users can add new rows, edit existing ones via a form, or delete rows.
   All changes are persisted immediately by rewriting the CSV file.

7. Save as JSON
   The current dataset (including any edits) can be downloaded as a
   well-formed JSON file via the JSONWriter class.

8. Charts
   A Gender Distribution pie chart and a Race Distribution bar chart are
   rendered below the table using Chart.js, reflecting the current view.

HIGHLIGHTS
----------
- MVC architecture: Model (data + logic), Servlets (controllers), JSPs (views).
- The Model is a singleton (ModelFactory) so in-memory edits persist across
  requests without re-reading the CSV on every call.
- JSONWriter accepts a DataFrame, keeping it reusable outside the web context.
- Charts update dynamically based on the currently displayed data (filtered
  or sorted), not just the full dataset.
