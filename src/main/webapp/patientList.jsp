<%@ page import="java.util.List, java.util.Map, java.util.LinkedHashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4/dist/chart.umd.min.js"></script>
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

  <%
    if (patientData != null) {
      Map<String, Integer> genderCounts = new LinkedHashMap<>();
      Map<String, Integer> raceCounts   = new LinkedHashMap<>();
      for (Map<String, String> p : patientData) {
        String g = p.getOrDefault("GENDER", ""); if (g.isEmpty()) g = "Unknown";
        String r = p.getOrDefault("RACE",   ""); if (r.isEmpty()) r = "Unknown";
        genderCounts.merge(g, 1, Integer::sum);
        raceCounts.merge(r, 1, Integer::sum);
      }
      // Build JSON for chart data — passed via hidden DOM elements, no JSP in <script>
      StringBuilder gJson = new StringBuilder("[");
      for (Map.Entry<String, Integer> e : genderCounts.entrySet())
        gJson.append("{\"label\":\"").append(e.getKey()).append("\",\"value\":").append(e.getValue()).append("},");
      if (gJson.length() > 1) gJson.setLength(gJson.length() - 1);
      gJson.append("]");

      StringBuilder rJson = new StringBuilder("[");
      for (Map.Entry<String, Integer> e : raceCounts.entrySet())
        rJson.append("{\"label\":\"").append(e.getKey()).append("\",\"value\":").append(e.getValue()).append("},");
      if (rJson.length() > 1) rJson.setLength(rJson.length() - 1);
      rJson.append("]");
      out.println("<div id=\"genderJson\" hidden>" + gJson + "</div>");
      out.println("<div id=\"raceJson\" hidden>" + rJson + "</div>");
  %>
  <div style="margin-top:28px; display:flex; gap:24px; flex-wrap:wrap;">
    <div style="background:white;border:1px solid #e2e8f0;border-radius:8px;padding:20px;flex:1;min-width:280px;max-width:380px;">
      <h3 style="font-size:13px;font-weight:600;color:#475569;text-transform:uppercase;letter-spacing:.5px;margin-bottom:12px;">Gender Distribution</h3>
      <canvas id="genderChart"></canvas>
    </div>
    <div style="background:white;border:1px solid #e2e8f0;border-radius:8px;padding:20px;flex:1;min-width:280px;max-width:480px;">
      <h3 style="font-size:13px;font-weight:600;color:#475569;text-transform:uppercase;letter-spacing:.5px;margin-bottom:12px;">Race Distribution</h3>
      <canvas id="raceChart"></canvas>
    </div>
  </div>
  <script src="charts.js"></script>
  <% } %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
