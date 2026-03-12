<%@ page import="java.util.List, java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
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
    List<Map<String, String>> patientData = (List<Map<String, String>>) request.getAttribute("patientData");
    if (columnNames != null && patientData != null)
    {
  %>
  <table border="1">
    <thead>
      <tr>
        <% for (String columnName : columnNames) { %>
          <th><%= columnName %></th>
        <% } %>
      </tr>
    </thead>
    <tbody>
      <% for (Map<String, String> patient : patientData) { %>
        <tr>
          <% for (String columnName : columnNames) { %>
            <td><%= patient.get(columnName) %></td>
          <% } %>
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
