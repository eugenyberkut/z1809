package logic;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yevhen on 09.10.2015.
 */
public class FullInfoTableModel extends AbstractTableModel {
    ResultSet rs;
    String[] columns = {"Название", "Автор", "стр", "Издательство"};

    public FullInfoTableModel(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public int getRowCount() {
        try {
            rs.last();
            int row = rs.getRow();
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            rs.absolute(rowIndex+1);
            switch (columnIndex) {
                case 0: return rs.getString("book.nazvanie");
                case 1: return rs.getString("name");
                case 2: return rs.getInt("pages");
                case 3: return rs.getString("izdatelstvo.nazvanie");
                default: return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }


}
