package practical1;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public LoginForm() {

        setTitle("Login Form");
        setSize(350, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 30, 80, 25);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(120, 30, 150, 25);
        add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 70, 80, 25);
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(120, 70, 150, 25);
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 110, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> loginUser());

        setVisible(true);
    }

    private void loginUser() {

        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new Dashboard(username);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
