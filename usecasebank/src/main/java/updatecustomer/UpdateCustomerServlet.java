package updatecustomer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UpdateCustomerDAO updateCustomerDAO = new UpdateCustomerDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String emailId = request.getParameter("email_id");
        String accountType = request.getParameter("account_type");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("id_proof");
        String accountNo = request.getParameter("account_no");

        try {
            boolean success = updateCustomerDAO.updateCustomerDetails(fullName, address, mobileNo, emailId, accountType, dob, idProof, accountNo);

            if (success) {
                response.sendRedirect("admin.jsp"); // Redirect to admin dashboard or appropriate page
            } else {
                response.getWriter().println("Failed to update customer details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
