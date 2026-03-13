package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editPatient")
public class EditPatientServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Model model = ModelFactory.getModel();
    request.setAttribute("columnNames", model.getColumnNames());
    String indexParam = request.getParameter("index");
    if (indexParam != null && !indexParam.isEmpty()) {
      int index = Integer.parseInt(indexParam);
      request.setAttribute("patient", model.getPatient(index));
      request.setAttribute("index", index);
    }
    RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/editPatient.jsp");
    dispatch.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Model model = ModelFactory.getModel();
    Map<String, String> patient = new HashMap<>();
    for (String col : model.getColumnNames()) {
      String value = request.getParameter(col);
      patient.put(col, value != null ? value : "");
    }
    String indexParam = request.getParameter("index");
    if (indexParam != null && !indexParam.isEmpty()) {
      model.updatePatient(Integer.parseInt(indexParam), patient);
    } else {
      model.addPatient(patient);
    }
    model.saveData();
    response.sendRedirect(request.getContextPath() + "/patientList");
  }
}
