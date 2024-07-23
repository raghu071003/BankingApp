package login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String keepSignedIn = request.getParameter("checkbox-1-1");

        LoginDAO loginDAO = new LoginDAO();
        boolean isValidUser = false;

        try {
            isValidUser = loginDAO.validateUser(username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database access error", e);
        }

        if (isValidUser) {
            // Valid credentials, create or retrieve session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            if ("on".equals(keepSignedIn)) {
                // Keep signed in for 7 days
                session.setMaxInactiveInterval(60 * 60 * 24 * 7); // 7 days
            } else {
                // Default session timeout
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
            }

            // Redirect to admin page
            response.sendRedirect("admin.jsp");
        } else {
            // Invalid credentials
            String errorMessage = "Invalid username or password. Please try again.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
