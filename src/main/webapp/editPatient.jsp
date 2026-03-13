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
  <%
    List<String> columnNames = (List<String>) request.getAttribute("columnNames");
    Map<String, String> patient = (Map<String, String>) request.getAttribute("patient");
    Integer index = (Integer) request.getAttribute("index");
    boolean isEdit = (index != null);
  %>
  <div class="form-card">
    <h2><%= isEdit ? "Edit Patient" : "Add Patient" %></h2>
    <form method="POST" action="editPatient">
      <% if (isEdit) { %>
        <input type="hidden" name="index" value="<%= index %>"/>
      <% } %>
      <% for (String col : columnNames) { %>
        <div class="form-row">
          <label><%= col %></label>
          <input type="text" name="<%= col %>"
            value="<%= patient != null ? patient.getOrDefault(col, "") : "" %>"/>
        </div>
      <% } %>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary"><%= isEdit ? "Save Changes" : "Add Patient" %></button>
        <a href="patientList" class="btn btn-secondary">Cancel</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
