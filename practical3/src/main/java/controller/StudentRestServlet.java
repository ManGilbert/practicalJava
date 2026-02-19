package controller;

import com.google.gson.Gson;
import dao.StudentDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.util.List;
import model.Student;

@WebServlet("/api/students")
public class StudentRestServlet extends HttpServlet {

    private StudentDAO dao = new StudentDAO();
    private Gson gson = new Gson();

    // ================= GET =================
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        response.setContentType("application/json");

        try {
            List<Student> list = dao.getAllStudents();
            response.getWriter().print(gson.toJson(list));
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    // ================= POST =================
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {

        response.setContentType("application/json");

        try {
            Student student =
                gson.fromJson(request.getReader(), Student.class);

            dao.addStudent(student);

            response.getWriter().print("{\"message\":\"Created\"}");
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    // ================= PUT =================
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        response.setContentType("application/json");

        try {
            Student student =
                gson.fromJson(request.getReader(), Student.class);

            dao.updateStudent(student);

            response.getWriter().print("{\"message\":\"Updated\"}");
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    // ================= DELETE =================
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
                            throws IOException {

        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"Invalid ID\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            dao.deleteStudent(id);
            response.getWriter().print("{\"message\":\"Deleted\"}");
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
