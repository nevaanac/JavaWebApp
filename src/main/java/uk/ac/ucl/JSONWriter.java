package uk.ac.ucl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class JSONWriter {

  // Writes DataFrame to a file in JSON format.
  public static void write(DataFrame dataFrame, String filePath) throws IOException {
    try (Writer writer = new FileWriter(filePath)) {
      write(dataFrame, writer);
    }
  }

  // Writes DataFrame to any Writer (e.g. HTTP response stream).
  public static void write(DataFrame dataFrame, Writer writer) throws IOException {
    ArrayList<String> columnNames = dataFrame.getColumnNames();
    int rowCount = dataFrame.getRowCount();

    writer.write("[\n");
    for (int i = 0; i < rowCount; i++) {
      writer.write("  {\n");
      for (int j = 0; j < columnNames.size(); j++) {
        String col = columnNames.get(j);
        String value = dataFrame.getValue(col, i);
        if (value == null) value = "";
        writer.write("    \"" + escape(col) + "\": \"" + escape(value) + "\"");
        if (j < columnNames.size() - 1) writer.write(",");
        writer.write("\n");
      }
      writer.write("  }");
      if (i < rowCount - 1) writer.write(",");
      writer.write("\n");
    }
    writer.write("]\n");
  }

  private static String escape(String s) {
    return s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
  }
}
