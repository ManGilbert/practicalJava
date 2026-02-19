
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;

@WebServlet("/addStudentAjax")
public class AddStudentAjax extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {

        String name = request.getParameter("name");
        String course = request.getParameter("course");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
               "INSERT INTO students(name,course) VALUES (?,?)")) {

            ps.setString(1, name);
            ps.setString(2, course);
            ps.executeUpdate();

            response.setContentType("application/json");
            response.getWriter().print("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
