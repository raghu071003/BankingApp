package Customerlogin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Customerlogin.dao.CustomerDAO;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;     

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");
        String tempPassword = request.getParameter("temp_password");
        String keepSignedIn = request.getParameter("checkbox-1-1");

        if (customerDAO.validate(accountNo, tempPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute("account_no", accountNo);

            if ("on".equals(keepSignedIn)) {
                session.setMaxInactiveInterval(60 * 60 * 24 * 7); // 7 days
            } else {
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
            }

            request.setAttribute("account_no", accountNo);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } else {
            String errorMessage = "Invalid account no or password. Please try again.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Customerlogin.jsp").forward(request, response);
        }
    }
}
