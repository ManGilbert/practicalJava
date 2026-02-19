
package q1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Q1 extends JFrame {
    

    private JTextField txtStudentId, txtName, txtCourse;
    private JButton btnSave, btnClean;

    public Q1() {
        
        setTitle("Student Registration");

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        
        JLabel lblStudentId = new JLabel("Student ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblCourse = new JLabel("Course:");

        
        txtStudentId = new JTextField(15);
        txtName = new JTextField(15);
        txtCourse = new JTextField(15);

        
        btnSave = new JButton("Save");
        btnClean = new JButton("Clean");

        
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblStudentId, gbc);

        gbc.gridx = 1;
        add(txtStudentId, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblName, gbc);

        gbc.gridx = 1;
        add(txtName, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblCourse, gbc);

        gbc.gridx = 1;
        add(txtCourse, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        add(btnSave, gbc);

        gbc.gridx = 1;
        add(btnClean, gbc);

        
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentId = txtStudentId.getText();
                String name = txtName.getText();
                String course = txtCourse.getText();

                JOptionPane.showMessageDialog(null,
                        "Student Details:\n" +
                        "ID: " + studentId + "\n" +
                        "Name: " + name + "\n" +
                        "Course: " + course);
            }
        });

        
        btnClean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtStudentId.setText("");
                txtName.setText("");
                txtCourse.setText("");
            }
        });

        
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
        new Q1();
    }
}
