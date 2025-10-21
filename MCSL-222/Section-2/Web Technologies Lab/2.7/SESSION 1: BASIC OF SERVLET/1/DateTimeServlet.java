import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

public class DateTimeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Fetch current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Format date and time for readability
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Get timestamp
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);

        // Generate HTML response
        out.println("<html>");
        out.println("<head><title>Current Date and Time</title></head>");
        out.println("<body>");
        out.println("<h2>Current Date and Time: " + formattedDateTime + "</h2>");
        out.println("<h3>Timestamp: " + timestamp.getTime() + "</h3>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
}