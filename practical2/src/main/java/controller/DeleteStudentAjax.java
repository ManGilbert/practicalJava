
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

@WebServlet("/deleteStudentAjax")
public class DeleteStudentAjax extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "DELETE FROM students WHERE id=?"
             )) {

            ps.setInt(1, id);
            ps.executeUpdate();

            response.getWriter().print("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
