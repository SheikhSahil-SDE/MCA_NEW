import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionServlet extends HttpServlet {

    // Helper to ensure a session exists and optionally set a max inactive interval
    private HttpSession ensureSession(HttpServletRequest request, boolean create) {
        return request.getSession(create); // creates if true, else returns null when no session
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create or retrieve the session
        HttpSession session = request.getSession(true);

        // Read form parameters
        String username = request.getParameter("username");
        String remember = request.getParameter("remember");

        // Store user info in session
        session.setAttribute("username", username);

        // Optional: set a longer timeout if "Remember Me" is checked
        if ("yes".equalsIgnoreCase(remember)) {
            session.setMaxInactiveInterval(60 * 60 * 24 * 30); // 30 days
        } else {
            // Default timeout (e.g., 30 minutes)
            session.setMaxInactiveInterval(30 * 60);
        }

        // Demonstrate explicit cookie handling for session tracking
        // The container typically creates a JSESSIONID cookie automatically.
        Cookie[] cookies = request.getCookies();
        boolean hasSessionCookie = false;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("JSESSIONID".equalsIgnoreCase(c.getName())) {
                    hasSessionCookie = true;
                    break;
                }
            }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Session Created</h2>");
        out.println("<p>Username stored in session: " + username + "</p>");
        out.println("<p>Session ID: " + session.getId() + "</p>");
        out.println("<p>Session Timeout (s): " + session.getMaxInactiveInterval() + "</p>");
        out.println("<p>Session cookie present: " + (hasSessionCookie ? "Yes" : "No") + "</p>");
        out.println("<a href="login.html">Back to form</a>");
        out.println("</body></html>");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if ("status".equalsIgnoreCase(action)) {
            HttpSession session = ensureSession(request, false);
            out.println("<html><body>");
            if (session != null) {
                String username = (String) session.getAttribute("username");
                out.println("<h2>Session Status</h2>");
                out.println("<p>Session ID: " + session.getId() + "</p>");
                out.println("<p>Username in session: " + (username != null ? username : "not set") + "</p>");
                out.println("<p>Created time: " + new java.util.Date(session.getCreationTime()) + "</p>");
                out.println("<p>Last accessed: " + new java.util.Date(session.getLastAccessedTime()) + "</p>");
                out.println("<p>Session Timeout (s): " + session.getMaxInactiveInterval() + "</p>");
            } else {
                out.println("<h2>No active session</h2>");
            }
            out.println("<a href="login.html">Back to form</a>");
            out.println("</body></html>");
        } else {
            // Default: show a simple status page prompting login
            HttpSession session = ensureSession(request, false);
            out.println("<html><body>");
            if (session != null && session.getAttribute("username") != null) {
                out.println("<h2>Already Logged In</h2>");
                out.println("<p>Username: " + session.getAttribute("username") + "</p>");
            } else {
                out.println("<h2>No active session</h2>");
            }
            out.println("<a href="login.html">Go to login</a>");
            out.println("</body></html>");
        }

        out.close();
    }
}