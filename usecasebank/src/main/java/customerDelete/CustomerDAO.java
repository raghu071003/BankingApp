package customerDelete;

import java.sql.*;

public class CustomerDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Saireddy@123";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    public ResultSet getCustomerDetails(String accountNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE account_no = ?";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, accountNo);
        return stmt.executeQuery();
    }

    public boolean hasZeroBalance(String accountNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT initial_balance FROM customer WHERE account_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("initial_balance") == 0;
                }
            }
        }
        return false;
    }

    public boolean deleteCustomer(String accountNo) throws SQLException, ClassNotFoundException {
        String deleteCustomerSQL = "DELETE FROM customer WHERE account_no = ?";
        String deleteTransactionSQL = "DELETE FROM transactions WHERE account_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSQL);
             PreparedStatement deleteTransactionStmt = conn.prepareStatement(deleteTransactionSQL)) {
            conn.setAutoCommit(false);
            
            deleteCustomerStmt.setString(1, accountNo);
            deleteTransactionStmt.setString(1, accountNo);
            
            int rowsDeletedCustomer = deleteCustomerStmt.executeUpdate();
            int rowsDeletedTransactions = deleteTransactionStmt.executeUpdate();
            
            if (rowsDeletedCustomer > 0 && rowsDeletedTransactions > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        }
    }
}
