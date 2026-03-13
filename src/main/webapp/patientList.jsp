<%@ page import="java.util.List, java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
  <style>
    table { border-collapse: collapse; width: 100%; font-family: sans-serif; font-size: 14px; }
    th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: left; }
    th { background-color: #f0f0f0; font-weight: bold; }
    tr:nth-child(even) { background-color: #fafafa; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>

  <%
    List<String> columnNames = (List<String>) request.getAttribute("columnNames");
    List<String> selectedColumns = (List<String>) request.getAttribute("selectedColumns");
    List<Map<String, String>> patientData = (List<Map<String, String>>) request.getAttribute("patientData");
  %>

  <a href="editPatient"><button>Add Patient</button></a>

  <form method="GET" action="patientList">
    <div style="display: flex; justify-content: space-between; align-items: flex-start;">
      <div>
        <input type="text" name="searchstring" placeholder="Search all columns..."/>
        <input type="submit" value="Search"/>
        <a href="patientList"><button type="button">Clear</button></a>
      </div>
      <div style="border: 1px solid #ccc; padding: 8px;">
        <strong>Columns:</strong>
        <div style="display: flex; flex-wrap: wrap; gap: 6px 16px; margin: 6px 0;">
          <% if (columnNames != null) { for (String col : columnNames) { %>
            <label style="white-space: nowrap;">
              <input type="checkbox" name="col" value="<%= col %>"
                <%= (selectedColumns != null && selectedColumns.contains(col)) ? "checked" : "" %>/>
              <%= col %>
            </label>
          <% } } %>
        </div>
        <input type="submit" value="Apply"/>
      </div>
    </div>
  </form>

  <%
    if (selectedColumns != null && patientData != null)
    {
      StringBuilder colParams = new StringBuilder();
      for (String col : selectedColumns) colParams.append("&col=").append(col);
  %>
  <table>
    <thead>
      <tr>
        <% for (String columnName : selectedColumns) { %>
          <th><%= columnName %>
            <a href="patientList?sort=<%= columnName %><%= colParams %>">▲</a>
            <a href="patientList?sortDesc=<%= columnName %><%= colParams %>">▼</a>
          </th>
        <% } %>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <% for (Map<String, String> patient : patientData) { %>
        <tr>
          <% for (String columnName : selectedColumns) { %>
            <td><%= patient.get(columnName) %></td>
          <% } %>
          <td>
            <a href="editPatient?index=<%= patient.get("__idx__") %>">Edit</a>
            <form method="POST" action="deletePatient" style="display:inline">
              <input type="hidden" name="index" value="<%= patient.get("__idx__") %>"/>
              <button type="submit">Delete</button>
            </form>
          </td>
        </tr>
      <% } %>
    </tbody>
  </table>
  <%
    }
  %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
