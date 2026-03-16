package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The ViewPatientListServlet handles HTTP requests for displaying the full list of patients.
 * It is mapped to the URL "/patientList".
 *
 * This servlet demonstrates:
 * 1. Handling GET requests to retrieve and display data.
 * 2. Interacting with a Model via a Factory pattern.
 * 3. Error handling and forwarding to error pages.
 * 4. Request-scoped attribute passing to JSPs for rendering lists.
 */
@WebServlet("/patientList")
public class ViewPatientListServlet extends HttpServlet
{

  /**
   * Handles HTTP GET requests.
   * This is the primary method for retrieving the patient list.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      // get the singleton instance of the Model
      Model model = ModelFactory.getModel();
      
      List<String> columnNames = model.getColumnNames();
      String searchString = request.getParameter("searchstring");
      String sortBy = request.getParameter("sort");
      String sortDesc = request.getParameter("sortDesc");
      List<Map<String, String>> patientData;
      if (searchString != null && !searchString.trim().isEmpty())
        patientData = model.searchFor(searchString);
      else if (sortDesc != null && !sortDesc.isEmpty())
        patientData = model.reversedSortColumn(sortDesc);
      else if (sortBy != null && !sortBy.isEmpty())
        patientData = model.sortColumn(sortBy);
      else
        patientData = model.getPatientData();

      String[] selectedCols = request.getParameterValues("col");
      List<String> selectedColumns;
      if (selectedCols != null && selectedCols.length > 0) {
        selectedColumns = Arrays.asList(selectedCols);
      } else {
        // keep these identifiers hidden by default until the user opts in
        Set<String> defaultHiddenColumns = new HashSet<>(
            Arrays.asList("DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "SUFFIX", "MAIDEN"));
        selectedColumns = new ArrayList<>();
        for (String columnName : columnNames) {
          if (!defaultHiddenColumns.contains(columnName)) {
            selectedColumns.add(columnName);
          }
        }
      }

      Map<String, Integer> genderCounts = new LinkedHashMap<>();
      Map<String, Integer> raceCounts   = new LinkedHashMap<>();
      for (Map<String, String> p : patientData) {
        String g = p.getOrDefault("GENDER", ""); if (g.isEmpty()) g = "Unknown";
        String r = p.getOrDefault("RACE",   ""); if (r.isEmpty()) r = "Unknown";
        genderCounts.merge(g, 1, Integer::sum);
        raceCounts.merge(r, 1, Integer::sum);
      }
      request.setAttribute("genderCounts", genderCounts);
      request.setAttribute("raceCounts",   raceCounts);

      request.setAttribute("columnNames", columnNames);
      request.setAttribute("selectedColumns", selectedColumns);
      request.setAttribute("patientData", patientData);

      // Invoke the JSP for display.
      // RequestDispatcher.forward() is used to send the request/response objects to another resource (JSP).
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
      dispatch.forward(request, response);
    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
      dispatch.forward(request, response);
    }
  }

  /**
   * Handles HTTP POST requests.
   * Redirects to doGet as viewing a list is typically an idempotent operation.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the POST could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the POST request
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }


}
