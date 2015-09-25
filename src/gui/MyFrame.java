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
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        getContentPane().add(sp);

        JButton avtButton = new JButton("Авторы");
        avtButton.addActionListener(e -> showAvtors());

        JButton izdButton = new JButton("Издательства");
        JButton bookButton = new JButton("Книги");
        JButton editButton = new JButton("Редактирование БД");
        editButton.addActionListener(e -> showEditDialog());

        JPanel panel = new JPanel();
        panel.add(avtButton);
        panel.add(izdButton);
        panel.add(bookButton);
        panel.add(editButton);

        getContentPane().add(panel, BorderLayout.SOUTH);
        setTitle("База данных");
        pack();
    }

    private void showEditDialog() {
        try {
            MyDialog dialog = new MyDialog(this, "Редактирование БД", false);
            dialog.setModel(main.getAvtorsModel(true));
            dialog.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAvtors() {
        try {
            //main.viewAvtors();
            TableModel avtorsModel = main.getAvtorsModel(false);
            table.setModel(avtorsModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
