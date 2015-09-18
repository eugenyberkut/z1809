package gui;

import logic.Main;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Yevhen on 18.09.2015.
 */
public class MyFrame extends JFrame {
    Main main;
    JTable table;

    public MyFrame(Main main) throws HeadlessException {
        this.main = main;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        getContentPane().add(sp);
        JButton button = new JButton("Авторы");
        button.addActionListener(e -> showAvtors());
        JPanel panel = new JPanel();
        panel.add(button);
        getContentPane().add(panel, BorderLayout.SOUTH);
        setTitle("База данных");
        pack();
    }

    private void showAvtors() {
        try {
            main.viewAvtors();
            TableModel avtorsModel = main.getAvtorsModel();
            table.setModel(avtorsModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
