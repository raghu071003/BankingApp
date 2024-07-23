package signup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SignupDAO signupDAO;

    public void init() {
        signupDAO = new SignupDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String emailId = request.getParameter("email_id");
        String accountType = request.getParameter("account_type");
        String initialBalance = request.getParameter("initial_balance");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("id_proof");

        try {
            long accountNumber = generateAccountNumber();
            String tempPassword = generateTempPassword();

            boolean isSuccess = signupDAO.addCustomer(fullName, address, mobileNo, emailId, accountType, initialBalance, dob, idProof, accountNumber, tempPassword);

            if (isSuccess) {
                response.sendRedirect("admin.jsp");
            } else {
                out.println("<html><body>");
                out.println("<script type='text/javascript'>");
                out.println("alert('Error: Unable to register the customer.');");
                out.println("window.location.href = 'signup.jsp';");
                out.println("</script>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Error: " + e.getMessage() + "');");
            out.println("window.location.href = 'signup.jsp';");
            out.println("</script>");
            out.println("</body></html>");
        }
    }

    private long generateAccountNumber() {
        return (long) (Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L;
    }

    private String generateTempPassword() {
        return Long.toHexString((long) (Math.random() * 1000000000));
    }
}
