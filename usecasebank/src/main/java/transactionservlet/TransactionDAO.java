package transactionservlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDAO {

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

    public boolean processTransaction(String accountNo, String transactionAction, double amount) throws SQLException {
        String checkBalanceQuery = "SELECT initial_balance FROM customer WHERE account_no = ?";
        String updateBalanceQuery = "UPDATE customer SET initial_balance = ? WHERE account_no = ?";
        String insertTransactionQuery = "INSERT INTO transactions (account_no, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            // Check current balance
            double currentBalance = 0.0;
            try (PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceQuery)) {
                checkBalanceStmt.setString(1, accountNo);
                try (ResultSet rs = checkBalanceStmt.executeQuery()) {
                    if (rs.next()) {
                        currentBalance = rs.getDouble("initial_balance");
                    } else {
                        return false; // Account not found
                    }
                }
            }

            double newBalance;
            if (transactionAction.equals("deposit")) {
                newBalance = currentBalance + amount;
            } else if (transactionAction.equals("withdraw")) {
                newBalance = currentBalance - amount;
                if (newBalance < 0) {
                    return false; // Insufficient balance
                }
            } else {
                return false; // Invalid transaction action
            }

            // Update the balance
            try (PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery)) {
                updateBalanceStmt.setDouble(1, newBalance);
                updateBalanceStmt.setString(2, accountNo);
                updateBalanceStmt.executeUpdate();
            }

            // Insert transaction record
            try (PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery)) {
                insertTransactionStmt.setString(1, accountNo);
                insertTransactionStmt.setString(2, transactionAction);
                insertTransactionStmt.setDouble(3, amount);
                insertTransactionStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                insertTransactionStmt.executeUpdate();
            }

            return true;
        }
    }
}
