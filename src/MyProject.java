import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class MyProject {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private final String[] columns = {"First Name", "Last Name", "Email", "Phone", "Location", "Hobby"};

    public MyProject() {

        frame = new JFrame("InfoGrid - CRUD Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);


        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Record");
        JButton editButton = new JButton("Edit Record");
        JButton deleteButton = new JButton("Delete Record");
        JButton exportButton = new JButton("Export CSV");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);


        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        addButton.addActionListener(e -> showAddDialog());
//        editButton.addActionListener(e -> showEditDialog());

        frame.setVisible(true);
    }


    //write code here
    private void showAddDialog() {
        JDialog dialog = new JDialog(frame, "Add Record", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(columns.length + 1, 2));

        JTextField[] fields = new JTextField[columns.length];
        for (int i = 0; i < columns.length; i++) {
            dialog.add(new JLabel(columns[i] + ":"));
            fields[i] = new JTextField();
            dialog.add(fields[i]);
        }

        JButton saveButton = new JButton("Save");
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            String[] rowData = new String[columns.length];
            for (int i = 0; i < fields.length; i++) {
                rowData[i] = fields[i].getText();
                if (rowData[i].isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            tableModel.addRow(rowData);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }



    // write code here
    private void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(file)) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Vector<?> row = tableModel.getDataVector().elementAt(i);
                    pw.println(String.join(",", row.toArray(new String[0])));
                }
                JOptionPane.showMessageDialog(frame, "CSV exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error exporting CSV!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyProject::new);
    }
}
