package uk.ac.ucl;
import java.util.ArrayList;

public class Column{
    private final String columnName;
    private final ArrayList<String> row;

    public Column(String name, ArrayList<String> row){
        this.columnName = name;
        this.row=row;
    }

    public Column(String name) {
        this(name, new ArrayList<>());
    }

    public String getName(){
        return columnName;
    }

    public int getSize(){
        return row.size();
    }

    public String getRowValue(int rowIndex){
        if (rowIndex < 0 || rowIndex >= row.size()) {
            return null;
        }
        return row.get(rowIndex);
    }

    public void setRowValue(int rowIndex, String value) {
        if (rowIndex < 0 || rowIndex >= row.size()) {
            return;
        }
        row.set(rowIndex, value);
    }

    public void addRowValue(String row_value){
        row.add(row_value);
    }
}
