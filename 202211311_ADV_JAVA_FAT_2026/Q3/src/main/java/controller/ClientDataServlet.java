package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import model.*;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/getClients")
public class ClientDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {
            ClientDAO dao = new ClientDAO();
            List<Client> list = dao.getAllClients();

            Gson gson = new Gson();
            String json = gson.toJson(list);

            response.getWriter().write(json);

        } catch (Exception e) {
            response.getWriter().write("[]");
        }
    }
}
