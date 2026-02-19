package dao;

import java.sql.*;
import java.util.*;
import model.Student;
import util.DBConnection;

public class StudentDAO {

    public List<Student> getAllStudents() throws Exception {

        List<Student> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {

            while(rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course")
                ));
            }
        }

        return list;
    }

    public void addStudent(Student s) throws Exception {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO students(name,course) VALUES (?,?)")) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getCourse());
            ps.executeUpdate();
        }
    }

    public void updateStudent(Student s) throws Exception {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE students SET name=?, course=? WHERE id=?")) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getCourse());
            ps.setInt(3, s.getId());
            ps.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws Exception {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM students WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
