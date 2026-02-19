package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import model.*;

@WebServlet("/addClient")
public class ClientInputServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            System.out.println("Received: " + name + " " + email + " " + phone);

            Client client = new Client();
            client.setName(name);
            client.setEmail(email);
            client.setPhone(phone);

            ClientDAO dao = new ClientDAO();
            dao.insertClient(client);

            out.write("{\"status\":\"success\"}");

        } catch (Exception e) {

            e.printStackTrace();  
            out.write("{\"status\":\"error\"}");
        }

        out.flush();
    }
}
