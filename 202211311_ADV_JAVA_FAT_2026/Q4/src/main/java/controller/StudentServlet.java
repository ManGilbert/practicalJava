/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import util.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

@WebServlet("/api/*")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            String id = request.getParameter("id");

            if (id == null) {

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM students");

                JSONArray array = new JSONArray();

                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", rs.getInt("id"));
                    obj.put("name", rs.getString("name"));
                    obj.put("email", rs.getString("email"));
                    obj.put("marks", rs.getInt("marks"));
                    array.put(obj);
                }

                response.getWriter().print(array);

            } else {

                PreparedStatement ps
                        = con.prepareStatement("SELECT * FROM students WHERE id=?");
                ps.setInt(1, Integer.parseInt(id));

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", rs.getInt("id"));
                    obj.put("name", rs.getString("name"));
                    obj.put("email", rs.getString("email"));
                    obj.put("marks", rs.getInt("marks"));
                    response.getWriter().print(obj);
                } else {
                    response.getWriter().print(
                            new JSONObject().put("message", "Student Not Found"));
                }
            }

        } catch (Exception e) {
            response.getWriter().print(
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students(name,email,marks) VALUES(?,?,?)");

            ps.setString(1, json.getString("name"));
            ps.setString(2, json.getString("email"));
            ps.setInt(3, json.getInt("marks"));

            ps.executeUpdate();

            response.getWriter().print(
                    new JSONObject().put("message", "Student Added Successfully"));

        } catch (Exception e) {
            response.getWriter().print(
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            int id = Integer.parseInt(request.getParameter("id"));

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET email=?, marks=? WHERE id=?");

            ps.setString(1, json.getString("email"));
            ps.setInt(2, json.getInt("marks"));
            ps.setInt(3, id);

            ps.executeUpdate();

            PreparedStatement ps2
                    = con.prepareStatement("SELECT * FROM students WHERE id=?");
            ps2.setInt(1, id);

            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("name", rs.getString("name"));
                obj.put("email", rs.getString("email"));
                obj.put("marks", rs.getInt("marks"));
                response.getWriter().print(obj);
            }

        } catch (Exception e) {
            response.getWriter().print(
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            int id = Integer.parseInt(request.getParameter("id"));

            PreparedStatement ps
                    = con.prepareStatement("DELETE FROM students WHERE id=?");

            ps.setInt(1, id);
            ps.executeUpdate();

            response.getWriter().print(
                    new JSONObject().put("message", "Student Deleted Successfully"));

        } catch (Exception e) {
            response.getWriter().print(
                    new JSONObject().put("error", e.getMessage()));
        }
    }
}
