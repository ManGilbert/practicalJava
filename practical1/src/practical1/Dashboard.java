package practical1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Dashboard extends JFrame {

    DefaultTableModel model;
    JTable table;

    public Dashboard(String username) {

        setTitle("Dashboard");
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblWelcome = new JLabel("Welcome " + username);
        lblWelcome.setBounds(20, 10, 200, 25);
        add(lblWelcome);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 40, 80, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 40, 90, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(210, 40, 90, 30);
        add(btnDelete);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(470, 40, 90, 30);
        add(btnLogout);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Course");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 90, 540, 240);
        add(sp);

        loadStudents();

        // ADD
        btnAdd.addActionListener(e -> addStudent());

        // UPDATE
        btnUpdate.addActionListener(e -> updateStudent());

        // DELETE
        btnDelete.addActionListener(e -> deleteStudent());

        // LOGOUT
        btnLogout.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setVisible(true);
    }

    private void loadStudents() {
        try {
            model.setRowCount(0);
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course")
                });
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog("Enter Name:");
        String course = JOptionPane.showInputDialog("Enter Course:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students(name, course) VALUES (?,?)");

            ps.setString(1, name);
            ps.setString(2, course);
            ps.executeUpdate();

            con.close();
            loadStudents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStudent() {

        int selected = table.getSelectedRow();

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(selected, 0);
        String newName = JOptionPane.showInputDialog("New Name:");
        String newCourse = JOptionPane.showInputDialog("New Course:");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET name=?, course=? WHERE id=?");

            ps.setString(1, newName);
            ps.setString(2, newCourse);
            ps.setInt(3, id);
            ps.executeUpdate();

            con.close();
            loadStudents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {

        int selected = table.getSelectedRow();

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(selected, 0);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM students WHERE id=?");

            ps.setInt(1, id);
            ps.executeUpdate();

            con.close();
            loadStudents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
