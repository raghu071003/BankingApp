package passwordChange;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");
        String currentPassword = request.getParameter("current_password");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        PasswordChangeDAO dao = new PasswordChangeDAO();

        if (newPassword.equals(confirmPassword)) {
            try {
                boolean isUpdated = dao.changePassword(accountNo, currentPassword, newPassword);
                if (isUpdated) {
                    // Password updated successfully
                    response.sendRedirect("dashboard.jsp");
                } else {
                    // Incorrect current password
                    String errorMessage = "Current password is incorrect. Please try again.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher("passwordChange.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException("Error updating password: " + e.getMessage(), e);
            }
        } else {
            // Passwords do not match
            String errorMessage = "New passwords do not match. Please try again.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("passwordChange.jsp").forward(request, response);
        }
    }
}
