package uk.ac.ucl;
import java.util.ArrayList;

public class DataFrame {

    private final ArrayList<Column> dataFrame = new ArrayList<>();

    public void addColumn(Column column){
        dataFrame.add(column);
    }

    public ArrayList<String> getColumnNames(){ //(a list of names in the same order as they are stored in the frame)
        ArrayList<String> column_names= new ArrayList<>();
        
        for (int i=0; i<dataFrame.size(); i++){
            column_names.add(dataFrame.get(i).getName());
        }
        return column_names;
    }

    public int getRowCount(){
        if (dataFrame.isEmpty()) {
            return 0;
        }
        return dataFrame.get(0).getSize();
    }

    public String getValue(String columnName, int row){ // get a row value from a column.
        for (int i=0; i<dataFrame.size(); i++){
            if (dataFrame.get(i).getName().equals(columnName)){
                return dataFrame.get(i).getRowValue(row);
            }
        }
        return null;
    }

    public void putValue(String columnName, int row, String value){
        for (int i=0; i<dataFrame.size(); i++){
            if (dataFrame.get(i).getName().equals(columnName)){
                dataFrame.get(i).setRowValue(row, value);
                return;
            }
        }
    }

    public void addValue(String columnName, String value) {
        for (int i=0; i<dataFrame.size(); i++){
            if (dataFrame.get(i).getName().equals(columnName)){
                dataFrame.get(i).addRowValue(value);
            }
        }
    }
}