import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CaptureIpServlet extends HttpServlet {

    // Utility to get the real client IP, accounting for proxies
    private String getClientIp(HttpServletRequest request) {
        String[] headers = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // In case of multiple IPs, the first is the originating client
                if (ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }
                return ip;
            }
        }
        // Fallback
        return request.getRemoteAddr();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        displayIp(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        displayIp(request, response);
    }

    private void displayIp(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String clientIp = getClientIp(request);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Client IP</title></head>");
        out.println("<body>");
        out.println("<h2>Client IP Address</h2>");
        out.println("<p>The detected IP is: <strong>" + clientIp + "</strong></p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}