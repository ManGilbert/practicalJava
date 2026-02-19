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

@WebServlet("/updateStudentAjax")
public class UpdateStudentAjax extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String course = request.getParameter("course");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
               "UPDATE students SET name=?, course=? WHERE id=?")) {

            ps.setString(1, name);
            ps.setString(2, course);
            ps.setInt(3, id);
            ps.executeUpdate();

            response.setContentType("application/json");
            response.getWriter().print("{\"status\":\"updated\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
