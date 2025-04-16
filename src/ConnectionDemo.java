import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileInputStream;

public class ConnectionDemo {
	public static void main(String[] args) throws SQLException{
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("demo.properties"));
			
			String theUser = props.getProperty("user");
			String thePassword = props.getProperty("password");
			String theDBUrl = props.getProperty("dburl");
			
			System.out.println("Connecting to database...");
			System.out.println("Database URL: " + theDBUrl);
			System.out.println("User: " + theUser);
			
			myConn = DriverManager.getConnection(theDBUrl, theUser, thePassword);
			System.out.println("\nConnection successful!\n");
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from employees");
			while(myRs.next()) {
				System.out.println(myRs.getString(2) + ", " + myRs.getString(3));
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(myRs!=null) {
				myRs.close();
			}
			if(myStmt!=null) {
				myStmt.close();
			}
			if(myConn!=null) {
				myConn.close();
			}
		}
	}
}
