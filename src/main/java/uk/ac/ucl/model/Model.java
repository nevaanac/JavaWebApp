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

  // This also returns dummy data. The real version should use the keyword
  // parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword) {
    return List.of("Search keyword is: " + keyword, "result1", "result2", "result3");
  }
}
