package gui;

import logic.Main;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Yevhen on 25.09.2015.
 */
public class MyDialog extends JDialog{

    private JTable avtorTable;
    private JTable bookTable = new JTable();
    private JButton deleteButton;
    private JButton insertButton;

    public MyDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(500,500));

        avtorTable = new JTable();
        JScrollPane sp = new JScrollPane(avtorTable);
        deleteButton = new JButton("Удалить");
        insertButton = new JButton("Добавить");
        JScrollPane scrollPane2 = new JScrollPane(bookTable);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sp,scrollPane2);
        splitPane.setDividerLocation(100);
        getContentPane().add(splitPane);

        JPanel panel = new JPanel();
        panel.add(insertButton);
        insertButton.addActionListener(e -> insertRecord());
        deleteButton.addActionListener(e -> deleteRecord());
        panel.add(deleteButton);
        getContentPane().add(panel,BorderLayout.SOUTH);

        JButton showBooksButton = new JButton("Книги");
        panel.add(showBooksButton);
        showBooksButton.addActionListener(e -> showBooksByAvtor());
        pack();
    }

    private void showBooksByAvtor() {
        int selectedRow = avtorTable.getSelectedRow();
        if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this, "Сначала выберите автора");
        } else {
            TableModel model = avtorTable.getModel();
            Object idObj = model.getValueAt(selectedRow, 0);
            Integer id = (Integer) idObj;
            TableModel model2 = Main.getMain().getBooksByAvtorModel(id);
            bookTable.setModel(model2);
        }
    }

    private void deleteRecord() {
        int selectedRow = avtorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,"Не выбрана запись", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer id = (Integer) (avtorTable.getModel().getValueAt(selectedRow, 0));
            Object name = avtorTable.getModel().getValueAt(selectedRow, 1);
            Object comment = avtorTable.getModel().getValueAt(selectedRow, 2);
            int answer = JOptionPane.showConfirmDialog(this, "Удаляем запись: [" + name + " : " + comment + "] ?", "Подтвердите удаление", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    Main.getMain().deleteAvtor(id);
                    avtorTable.setModel(Main.getMain().getAvtorsModel(true));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void insertRecord() {
        String name = "Ivanov";
        String comment = "no comment";
        try {
            Main.getMain().addAvtor(name, comment);
            avtorTable.setModel(Main.getMain().getAvtorsModel(true));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setModel(TableModel model) {
        avtorTable.setModel(model);
    }
}
