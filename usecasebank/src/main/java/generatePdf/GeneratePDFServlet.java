package generatePdf;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/GeneratePDFServlet")
public class GeneratePDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");

        GenerateDAO dao = new GenerateDAO();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            document.add(new Paragraph("Last 10 Transactions for Account No: " + accountNo));
            document.add(new Paragraph(" ")); // Empty line

            PdfPTable table = new PdfPTable(3);
            table.addCell("Transaction Type");
            table.addCell("Amount");
            table.addCell("Transaction Date");

            dao.getLastTransactions(accountNo, rs -> {
                try {
                    while (rs.next()) {
                        table.addCell(rs.getString("transaction_type"));
                        table.addCell(String.valueOf(rs.getDouble("amount")));
                        table.addCell(rs.getString("transaction_date"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error generating PDF: " + e.getMessage());
        }
    }
}
