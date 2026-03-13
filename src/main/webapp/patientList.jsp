<%@ page import="java.util.List, java.util.Map, java.util.Map.Entry" %>
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
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
    <div class="error-msg"><%= errorMessage %></div>
  <% } %>

  <%
    List<String> columnNames    = (List<String>) request.getAttribute("columnNames");
    List<String> selectedColumns = (List<String>) request.getAttribute("selectedColumns");
    List<Map<String, String>> patientData = (List<Map<String, String>>) request.getAttribute("patientData");
  %>

  <div class="toolbar">
    <a href="editPatient" class="btn btn-success">+ Add Patient</a>
    <a href="saveJson"    class="btn btn-secondary">&#8595; Save as JSON</a>
  </div>

  <form method="GET" action="patientList">
    <div class="controls">
      <div class="search-group">
        <input type="text" name="searchstring" placeholder="Search all columns..."/>
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="patientList" class="btn btn-secondary">Clear</a>
      </div>
      <div class="col-selector">
        <strong>Visible Columns</strong>
        <div class="col-checkboxes">
          <% if (columnNames != null) { for (String col : columnNames) { %>
            <label>
              <input type="checkbox" name="col" value="<%= col %>"
                <%= (selectedColumns != null && selectedColumns.contains(col)) ? "checked" : "" %>/>
              <%= col %>
            </label>
          <% } } %>
        </div>
        <button type="submit" class="btn btn-secondary">Apply</button>
      </div>
    </div>
  </form>

  <%
    Map<String, Integer> genderCounts = (Map<String, Integer>) request.getAttribute("genderCounts");
    Map<String, Integer> raceCounts   = (Map<String, Integer>) request.getAttribute("raceCounts");
    if (genderCounts != null) {
      int gTotal = 0; for (int v : genderCounts.values()) gTotal += v;
      int rTotal = 0; for (int v : raceCounts.values())   rTotal += v;
  %>
  <div style="margin-top:20px;">
    <table style="border:none; width:auto;">
      <tr>
        <td style="border:none; vertical-align:top; padding-right:40px;">
          <h3>Gender Distribution</h3>
          <table class="bar-chart">
            <% for (Entry<String, Integer> e : genderCounts.entrySet()) {
                 int pct = gTotal > 0 ? e.getValue() * 100 / gTotal : 0; %>
              <tr>
                <td><%= e.getKey() %></td>
                <td><div class="bar" style="width:<%= pct %>%;"></div></td>
                <td><%= e.getValue() %></td>
              </tr>
            <% } %>
          </table>
        </td>
        <td style="border:none; vertical-align:top;">
          <h3>Race Distribution</h3>
          <table class="bar-chart">
            <% for (Entry<String, Integer> e : raceCounts.entrySet()) {
                 int pct = rTotal > 0 ? e.getValue() * 100 / rTotal : 0; %>
              <tr>
                <td><%= e.getKey() %></td>
                <td><div class="bar" style="width:<%= pct %>%;"></div></td>
                <td><%= e.getValue() %></td>
              </tr>
            <% } %>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <% } %>

  <%
    if (selectedColumns != null && patientData != null) {
      StringBuilder colParams = new StringBuilder();
      for (String col : selectedColumns) colParams.append("&col=").append(col);
  %>
  <div class="table-wrapper">
    <table>
      <thead>
        <tr>
          <% for (String columnName : selectedColumns) { %>
            <th>
              <%= columnName %>
              <span class="sort-links">
                <a href="patientList?sort=<%= columnName %><%= colParams %>">▲</a>
                <a href="patientList?sortDesc=<%= columnName %><%= colParams %>">▼</a>
              </span>
            </th>
          <% } %>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <% for (Map<String, String> patient : patientData) { %>
          <tr>
            <% for (String columnName : selectedColumns) { %>
              <td><%= patient.get(columnName) != null ? patient.get(columnName) : "" %></td>
            <% } %>
            <td>
              <div class="actions-cell">
                <a href="editPatient?index=<%= patient.get("__idx__") %>" class="btn btn-primary">Edit</a>
                <form method="POST" action="deletePatient">
                  <input type="hidden" name="index" value="<%= patient.get("__idx__") %>"/>
                  <button type="submit" class="btn btn-danger">Delete</button>
                </form>
              </div>
            </td>
          </tr>
        <% } %>
      </tbody>
    </table>
  </div>
  <% } %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>