/* Read a .csv data file and load the data into an empty DataFrame.
The Column names are found as the first row in the .csv data file. It should
have a method that returns a filled DataFrame. */

package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {

    public static DataFrame loadData(String filePath) {
        DataFrame dataFrame = new DataFrame();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return dataFrame;
            }
            String[] columnNames=headerLine.split(",");
            for (String name: columnNames){
                dataFrame.addColumn(new Column(name.trim()));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                for (int i = 0; i < columnNames.length; i++) {
                    String value= i<values.length ? values[i].trim() : "";
                    dataFrame.addValue(columnNames[i].trim(), value);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " — " + e.getMessage());
        }

        return dataFrame;
    }
}
