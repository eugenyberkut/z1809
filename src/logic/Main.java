package logic;

import gui.MyFrame;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.*;

/**
 * Created by Yevhen on 18.09.2015.
 */
public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    Connection connection;

    private void run() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/homelibrary", "eugeny", "123");
            MyFrame myFrame = new MyFrame(this);
            myFrame.setVisible(true);
//            addAvtor(connection, "Shevchenko", "Ukrainian");
//            addAvtor(connection, "Hemingway", "American");
//            viewAvtors(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewAvtors() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM avtor");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String comment = resultSet.getString("comment");
            System.out.printf("%4d %-46s %s%n", id, name, comment);
        }
    }

    public TableModel getAvtorsModel() throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM avtor");
        AvtorTableModel model = new AvtorTableModel(resultSet);
        return model;
    }

    private void addAvtor(String avtor, String comment) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO avtor (name, comment) VALUES (?,?)");
        insertStatement.setString(1, avtor);
        insertStatement.setString(2, comment);
        insertStatement.executeUpdate();
    }
}
