package generatePdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Saireddy@123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public void getLastTransactions(String accountNo, ResultSetHandler handler) throws SQLException {
        String transactionQuery = "SELECT transaction_type, amount, transaction_date FROM transactions WHERE account_no = ? ORDER BY transaction_date DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement transactionStmt = conn.prepareStatement(transactionQuery)) {

            transactionStmt.setString(1, accountNo);
            try (ResultSet rs = transactionStmt.executeQuery()) {
                handler.handle(rs);
            }
        }
    }

    @FunctionalInterface
    public interface ResultSetHandler {
        void handle(ResultSet rs) throws SQLException;
    }
}
