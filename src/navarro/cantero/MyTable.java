package navarro.cantero;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyTable {
    private static Object[] columns = {"ID", "Name", "Lessons"};

    private JFrame frame;
    private JTable jTable;
    private JTextField textId;
    private JTextField textName;
    private JTextField textLessons;
    private JButton buttonAdd;
    private JButton buttonDelete;
    private JButton buttonUpdate;
    private ArrayList<Alumn> alumns;

    public MyTable() {
        this.frame = new JFrame();
        this.jTable = new JTable();
        this.textId = new JTextField();
        this.textName = new JTextField();
        this.textLessons = new JTextField();
        this.buttonAdd = new JButton("Add");
        this.buttonDelete = new JButton("Delete");
        this.buttonUpdate = new JButton("Update");
        this.alumns = Alumn.fromFileToAlumns(Alumn.getPath());


    }

    public JFrame getFrame() {
        return frame;
    }

    public JTable getjTable() {
        return jTable;
    }

    public JTextField getTextId() {
        return textId;
    }

    public JTextField getTextName() {
        return textName;
    }

    public JTextField getTextLessons() {
        return textLessons;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public JButton getButtonUpdate() {
        return buttonUpdate;
    }

    public void go() {
        JFrame frame = this.getFrame();
        JTable table = this.getjTable();
        JButton btnAdd = this.getButtonAdd();
        JButton btnDelete = this.getButtonDelete();
        JButton btnUpdate = this.getButtonUpdate();
        JTextField textId = this.getTextId();
        JTextField textName = this.getTextName();
        JTextField textLessons = this.getTextLessons();
        JLabel labId = new JLabel("ID");
        JLabel labName = new JLabel("Name");
        JLabel labLessons = new JLabel("Lessons");
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table.setModel(model);

        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("", 2, 20);
        table.setFont(font);
        table.setRowHeight(50);

        labId.setBounds(10, 230, 100, 25);
        labName.setBounds(310, 230, 100, 25);
        labLessons.setBounds(610, 230, 100, 25);

        textId.setBounds(5, 250, 200, 25);
        textName.setBounds(305, 250, 200, 25);
        textLessons.setBounds(605, 250, 200, 25);
        textId.disable();

        btnAdd.setBounds(10, 310, 100, 25);
        btnUpdate.setBounds(310, 310, 100, 25);
        btnDelete.setBounds(610, 310, 100, 25);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 900, 200);

        frame.setLayout(null);

        frame.add(labName);
        frame.add(labId);
        frame.add(labLessons);
        frame.add(pane);

        frame.add(textId);
        frame.add(textName);
        frame.add(textLessons);

        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);

        Object[] row = new Object[3];
        for (Alumn alumn : alumns) {
            row[0] = alumn.getId();
            row[1] = alumn.getName();
            row[2] = alumn.getLessons();
            model.addRow(row);
        }


        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                row[0] = Alumn.max + 1;
                row[1] = textName.getText();
                row[2] = textLessons.getText();
                Alumn alumn = new Alumn(Alumn.max + 1, textName.getText(), textLessons.getText());
                Alumn.addAlumnToFile(Alumn.fromFileToAlumns(Alumn.getPath()), Alumn.getPath(), alumn);
                model.addRow(row);

            }
        });

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable
                    System.out.println(Integer.parseInt(model.getValueAt(i, 0).toString()));
                    Alumn.removeAlumnToFile(Alumn.fromFileToAlumns(Alumn.getPath()), Alumn.getPath(),
                            new Alumn(Integer.parseInt(model.getValueAt(i, 0).toString()), model.getValueAt(i, 1).toString(), model.getValueAt(i, 2).toString()));

                    model.removeRow(i);

                } else {
                    System.out.println("Delete Error");
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                textId.setText(model.getValueAt(i, 0).toString());
                textName.setText(model.getValueAt(i, 1).toString());
                textLessons.setText(model.getValueAt(i, 2).toString());
            }
        });

        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int maxValue;

                int i = table.getSelectedRow();

                if (i >= 0) {
                    int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                    String name = model.getValueAt(i, 1).toString();
                    String lessons = model.getValueAt(i, 2).toString();

                    Alumn toErase = new Alumn(id, name, lessons);
                    model.setValueAt(textId.getText(), i, 0);
                    model.setValueAt(textName.getText(), i, 1);
                    model.setValueAt(textLessons.getText(), i, 2);

                    Alumn alumn = new Alumn(Integer.parseInt(model.getValueAt(i, 0).toString()), model.getValueAt(i, 1).toString(),
                            model.getValueAt(i, 2).toString());
                    Alumn.updateAlumnToFile(Alumn.fromFileToAlumns(Alumn.getPath()), Alumn.getPath(), toErase, alumn);
                } else {
                    System.out.println("Update Error");
                }
            }
        });
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}