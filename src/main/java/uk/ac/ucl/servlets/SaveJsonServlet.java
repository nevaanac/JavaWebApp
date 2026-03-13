package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.JSONWriter;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/saveJson")
public class SaveJsonServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "attachment; filename=\"patients.json\"");
    JSONWriter.write(ModelFactory.getModel().toDataFrame(), response.getWriter());
  }
}
