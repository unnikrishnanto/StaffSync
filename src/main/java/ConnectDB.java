import java.sql.Connection;
import java.sql.DriverManager;


// This class will use singleton design pattern to 
// send a single connection throughout the application

public class ConnectDB {
	private static Connection conn  =null;
	private static final String url = "jdbc:mysql://localhost:3306/jdbckodnest";
	private static final String userName = "root";
	private static final String password = "11@22y@M0!.";
	
	// Private constructor
	private ConnectDB() {}
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("Database Connection Successfull..");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Database Connection Failed..");
			}
		}
		System.out.println("Sharing Connection..");
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
				System.out.println("Connection closed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
