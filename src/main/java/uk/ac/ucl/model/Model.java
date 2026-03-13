package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import uk.ac.ucl.Column;
import uk.ac.ucl.DataFrame;
import uk.ac.ucl.DataLoader;
import uk.ac.ucl.JSONWriter;

public class Model {
  private final ArrayList<String> columnNames;
  private final List<Map<String, String>> allPatientData;
  private final String filePath;

  public Model(String filePath) {
    this.filePath = filePath;
    DataFrame dataFrame = DataLoader.loadData(filePath);
    this.columnNames = dataFrame.getColumnNames();
    this.allPatientData = new ArrayList<>();
    for (int i = 0; i < dataFrame.getRowCount(); i++) {
      Map<String, String> row = new HashMap<>();
      for (String col : columnNames) {
        row.put(col, dataFrame.getValue(col, i));
      }
      allPatientData.add(row);
    }
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  // Returns copies of all rows, each with "__idx__" set to its original index.
  public List<Map<String, String>> getPatientData() {
    List<Map<String, String>> result = new ArrayList<>();
    for (int i = 0; i < allPatientData.size(); i++) {
      Map<String, String> copy = new HashMap<>(allPatientData.get(i));
      copy.put("__idx__", String.valueOf(i));
      result.add(copy);
    }
    return result;
  }

  public Map<String, String> getPatient(int index) {
    return allPatientData.get(index);
  }

  public void addPatient(Map<String, String> patient) {
    allPatientData.add(new HashMap<>(patient));
  }

  public void updatePatient(int index, Map<String, String> patient) {
    allPatientData.set(index, new HashMap<>(patient));
  }

  public void deletePatient(int index) {
    allPatientData.remove(index);
  }

  public DataFrame toDataFrame() {
    DataFrame df = new DataFrame();
    for (String col : columnNames) df.addColumn(new Column(col));
    for (Map<String, String> row : allPatientData) {
      for (String col : columnNames) df.addValue(col, row.getOrDefault(col, ""));
    }
    return df;
  }

  public void saveAsJson(String filePath) throws IOException {
    JSONWriter.write(toDataFrame(), filePath);
  }

  public void saveData() throws IOException {
    try (Writer writer = new FileWriter(filePath);
         CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(columnNames.toArray(new String[0])))) {
      for (Map<String, String> row : allPatientData) {
        List<String> values = new ArrayList<>();
        for (String col : columnNames) {
          values.add(row.getOrDefault(col, ""));
        }
        printer.printRecord(values);
      }
    }
  }

  public List<Map<String, String>> searchFor(String keyword) {
    List<Map<String, String>> results = new ArrayList<>();
    for (Map<String, String> patient : getPatientData()) {
      for (Map.Entry<String, String> entry : patient.entrySet()) {
        if (!"__idx__".equals(entry.getKey()) && entry.getValue() != null
            && entry.getValue().toLowerCase().contains(keyword.toLowerCase())) {
          results.add(patient);
          break;
        }
      }
    }
    return results;
  }

  public List<Map<String, String>> sortColumn(String columnName) {
    List<Map<String, String>> rows = getPatientData();
    for (int i = 0; i < rows.size(); i++) {
      int minIndex = i;
      for (int j = i + 1; j < rows.size(); j++) {
        String a = rows.get(j).getOrDefault(columnName, "");
        String b = rows.get(minIndex).getOrDefault(columnName, "");
        if (a == null) a = "";
        if (b == null) b = "";
        if (a.compareTo(b) < 0) {
          minIndex = j;
        }
      }
      Map<String, String> temp = rows.get(i);
      rows.set(i, rows.get(minIndex));
      rows.set(minIndex, temp);
    }
    return rows;
  }

  public List<Map<String, String>> reversedSortColumn(String columnName) {
    return sortColumn(columnName).reversed();
  }
}
