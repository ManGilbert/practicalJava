
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/realestate")
public class RealEstateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String json = "["
                + "{ \"houseNo\": \"H-101\", \"location\": \"Kigali\", \"price\": 85000000 },"
                + "{ \"houseNo\": \"H-102\", \"location\": \"Musanze\", \"price\": 65000000 },"
                + "{ \"houseNo\": \"H-103\", \"location\": \"Huye\", \"price\": 55000000 }"
                + "]";

        out.print(json);
        out.flush();
    }
}
