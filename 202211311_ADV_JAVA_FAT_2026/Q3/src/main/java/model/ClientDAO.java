package model;

import java.sql.*;
import java.util.*;

public class ClientDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/fat_db_2026";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void insertClient(Client client) throws Exception {
        String sql = "INSERT INTO clients(name,email,phone) VALUES(?,?,?)";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, client.getName());
        ps.setString(2, client.getEmail());
        ps.setString(3, client.getPhone());
        ps.executeUpdate();
        con.close();
    }

    public List<Client> getAllClients() throws Exception {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        Connection con = getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()) {
            Client c = new Client();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            list.add(c);
        }
        con.close();
        return list;
    }
}
