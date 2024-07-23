package customerDelete;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");

        try {
            if (customerDAO.hasZeroBalance(accountNo)) {
                if (customerDAO.deleteCustomer(accountNo)) {
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    response.sendRedirect("Home.jsp?message=Customer+deleted+successfully");
                } else {
                    response.sendRedirect("error.jsp?message=Error+deleting+customer");
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("<script>alert('Cannot delete account. Balance must be zero.');</script>");
                response.getWriter().println("<script>window.location='dashboard.jsp';</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Error+deleting+customer");
        }
    }
}
