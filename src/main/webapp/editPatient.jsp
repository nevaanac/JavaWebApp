<%@ page import="java.util.List, java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
  <style>
    table { border-collapse: collapse; font-family: sans-serif; font-size: 14px; }
    th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: left; }
    th { background-color: #f0f0f0; font-weight: bold; }
    input[type="text"] { width: 300px; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <%
    List<String> columnNames = (List<String>) request.getAttribute("columnNames");
    Map<String, String> patient = (Map<String, String>) request.getAttribute("patient");
    Integer index = (Integer) request.getAttribute("index");
    boolean isEdit = (index != null);
  %>
  <h2><%= isEdit ? "Edit Patient" : "Add Patient" %></h2>
  <form method="POST" action="editPatient">
    <% if (isEdit) { %>
      <input type="hidden" name="index" value="<%= index %>"/>
    <% } %>
    <table>
      <% for (String col : columnNames) { %>
        <tr>
          <th><%= col %></th>
          <td><input type="text" name="<%= col %>" value="<%= patient != null ? patient.getOrDefault(col, "") : "" %>"/></td>
        </tr>
      <% } %>
    </table>
    <br/>
    <input type="submit" value="<%= isEdit ? "Save" : "Add" %>"/>
    <a href="patientList"><button type="button">Cancel</button></a>
  </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
