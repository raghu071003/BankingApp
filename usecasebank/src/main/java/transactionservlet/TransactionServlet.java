package transactionservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TransactionDAO transactionDAO = new TransactionDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String transactionAction = request.getParameter("transactionAction");
        String accountNo = request.getParameter("account_no");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            boolean success = transactionDAO.processTransaction(accountNo, transactionAction, amount);

            if (success) {
                response.sendRedirect("dashboard.jsp");
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Transaction failed!');");
                response.getWriter().println("window.location.href = 'dashboard.jsp';");
                response.getWriter().println("</script>");
                response.getWriter().println("</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error processing transaction: " + e.getMessage());
        }
    }
}
