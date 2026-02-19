package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        if ("admin".equals(username) && "123".equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("user", username);


            session.setMaxInactiveInterval(30);

            response.sendRedirect("dashboard.jsp");

        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);
        }
    }
}
