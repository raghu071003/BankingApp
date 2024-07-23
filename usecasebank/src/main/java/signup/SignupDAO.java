package signup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Saireddy@123";

    public boolean addCustomer(String fullName, String address, String mobileNo, String emailId, String accountType, String initialBalance, String dob, String idProof, long accountNumber, String tempPassword) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO customer (full_name, address, mobile_no, email_id, account_type, initial_balance, dob, id_proof, account_no, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, fullName);
                statement.setString(2, address);
                statement.setString(3, mobileNo);
                statement.setString(4, emailId);
                statement.setString(5, accountType);
                statement.setString(6, initialBalance);
                statement.setString(7, dob);
                statement.setString(8, idProof);
                statement.setLong(9, accountNumber);
                statement.setString(10, tempPassword);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        }
    }
}
