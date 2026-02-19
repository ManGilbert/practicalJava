package com.mycompany.q2;

import com.mycompany.model.Student;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/school",
                "root",
                "");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("welcome", "Welcome Student");

        if (request.getParameter("success") != null) {
            request.setAttribute("successfully", "Student inserted successfully!");
        }

        loadStudents(request);

        RequestDispatcher rd = request.getRequestDispatcher("students.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String course = request.getParameter("course");

        try (Connection con = getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student(name, course) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, course);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to avoid duplicate form submission
        response.sendRedirect("StudentServlet?success=1");
    }

    private void loadStudents(HttpServletRequest request) {

        try (Connection con = getConnection()) {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            List<Student> list = new ArrayList<>();

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course")));
            }

            request.setAttribute("students", list);

            List<String> subjects = Arrays.asList("Math", "Science", "English");
            request.setAttribute("subjects", subjects);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
