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
    JTextField pagesField;

    public MyFrame(Main main) throws HeadlessException {
        this.main = main;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));


//        JButton avtButton = new JButton("Авторы");
//        avtButton.addActionListener(e -> showAvtors());

        JButton izdButton = new JButton("Издательства");
        JButton bookButton = new JButton("Книги");
        JButton editButton = new JButton("Авторы");
        editButton.addActionListener(e -> showAvtorsDialog());

        JPanel panel = new JPanel();
        //panel.add(avtButton);
        panel.add(izdButton);
        panel.add(bookButton);
        panel.add(editButton);

        getContentPane().add(panel, BorderLayout.SOUTH);
        JPanel topPanel = new JPanel();
        pagesField = new JTextField(10);
        topPanel.add(new Label("К-во страниц больше "));
        topPanel.add(pagesField);
        JToggleButton tb = new JToggleButton("Фильтр");
        tb.addActionListener(e -> toggleFilter(tb.isSelected()));
        topPanel.add(tb);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        createFullTable(false);
        setTitle("База данных");
        pack();
    }

    private void toggleFilter(boolean selected) {
        System.out.println(selected);
//        createFullTable(selected);

    }

    private void createFullTable(boolean selected) {
        TableModel ftm = main.createFullTableModel(pagesField.getText());
        if (selected) {
            table = new JTable(main.createFullTableModel(pagesField.getText()));
        } else {
            table = new JTable(main.createFullTableModel(""));
        }

        JScrollPane sp = new JScrollPane(table);
        getContentPane().add(sp);
    }

    private void showAvtorsDialog() {
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
