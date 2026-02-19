/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Student;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() throws Exception {
        List<Student> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM students");

        while (rs.next()) {
            Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("marks")
            );
            list.add(s);
        }
        con.close();
        return list;
    }

    public Student getStudentById(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("marks")
            );
        }
        con.close();
        return null;
    }

    public void addStudent(Student student) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(name,email,marks) VALUES(?,?,?)");
        ps.setString(1, student.getName());
        ps.setString(2, student.getEmail());
        ps.setInt(3, student.getMarks());
        ps.executeUpdate();
        con.close();
    }

    public Student updateStudent(int id, Student student) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE students SET email=?, marks=? WHERE id=?");
        ps.setString(1, student.getEmail());
        ps.setInt(2, student.getMarks());
        ps.setInt(3, id);
        ps.executeUpdate();
        con.close();
        return getStudentById(id);
    }

    public void deleteStudent(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM students WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }


}
