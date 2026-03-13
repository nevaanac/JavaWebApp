package uk.ac.ucl.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uk.ac.ucl.DataFrame;
import uk.ac.ucl.DataLoader;

public class Model {
  private DataFrame dataFrame;
  private ArrayList<String> columnNames;

  public Model(String filePath) {
    this.dataFrame = DataLoader.loadData(filePath);
    this.columnNames = dataFrame.getColumnNames();
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public List<Map<String, String>> getPatientData() {
    List<Map<String, String>> patientDataList = new ArrayList<>();

    for (int i = 0; i < dataFrame.getRowCount(); i++) {
      Map<String, String> patientData = new java.util.HashMap<>();
      for (String columnName : columnNames) {
        patientData.put(columnName, dataFrame.getValue(columnName, i));
      }
      patientDataList.add(patientData);
    }
    return patientDataList;
  }
  // A search servlet should pass the search words as parameters in method calls to the Model
  
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
