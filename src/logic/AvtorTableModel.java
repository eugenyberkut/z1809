package logic;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yevhen on 18.09.2015.
 */
public class AvtorTableModel extends AbstractTableModel{
    ResultSet rs;
    private String[] columns = {"id", "Имя", "Коммент"};

    public AvtorTableModel(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public int getRowCount() {
        try {
            rs.last();
            return rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            rs.absolute(rowIndex+1);
            if (columnIndex==0) {
                return rs.getInt(1);
            } else {
                return rs.getString(columnIndex+1);
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
