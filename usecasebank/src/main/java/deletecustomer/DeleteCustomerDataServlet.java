package deletecustomer;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCustomerDataServlet")
public class DeleteCustomerDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Customerdao customerdao;

    public void init() {
        customerdao= new Customerdao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");

        try {
            boolean isDeleted = customerdao.deleteCustomer(accountNo);

            if (isDeleted) {
                response.sendRedirect("admin.jsp?message=Customer+deleted+successfully");
            } else {
                response.sendRedirect("admin.jsp?message=Error+deleting+customer");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("admin.jsp?message=Error+deleting+customer");
        }
    }
}
