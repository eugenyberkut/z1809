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

    private JTable table;
    private JButton deleteButton;
    private JButton insertButton;

    public MyDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(500,500));
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        getContentPane().add(sp);
        deleteButton = new JButton("Удалить");
        insertButton = new JButton("Добавить");
        JPanel panel = new JPanel();
        panel.add(insertButton);
        insertButton.addActionListener(e -> insertRecord());
        deleteButton.addActionListener(e -> deleteRecord());
        panel.add(deleteButton);
        getContentPane().add(panel,BorderLayout.SOUTH);
        pack();
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,"Не выбрана запись", "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer id = (Integer) (table.getModel().getValueAt(selectedRow, 0));
            Object name = table.getModel().getValueAt(selectedRow, 1);
            Object comment = table.getModel().getValueAt(selectedRow, 2);
            int answer = JOptionPane.showConfirmDialog(this, "Удаляем запись: [" + name + " : " + comment + "] ?", "Подтвердите удаление", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    Main.getMain().deleteAvtor(id);
                    table.setModel(Main.getMain().getAvtorsModel(true));
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
            table.setModel(Main.getMain().getAvtorsModel(true));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setModel(TableModel model) {
        table.setModel(model);
    }
}
