
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;

public class DatabaseConnection {


		public static void main(String[] args) {
			
			Connection con = null;
			//System.out.println("Testing");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Saireddy@123");
				
				if(con!= null)
				{
					System.out.println("database is connected");
				}
			}catch (Exception e) {
				System.out.println("not connected");
				e.printStackTrace();
			}
		}
	}