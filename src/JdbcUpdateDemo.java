import java.sql.*;

public class JdbcUpdateDemo {
	public static void main(String[] args) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet myRs = null;
		
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		String firstNameFilter = "John";
		String lastNameFilter = "Doe";
		String newEmail = firstNameFilter + "." + lastNameFilter + "@test.com";
		try {
			conn = DriverManager.getConnection(dbUrl, user, pass);
			stmt = conn.createStatement();
			System.out.println("Before the update...");
			displayEmployee(conn, firstNameFilter, lastNameFilter);
			System.out.println("\nEXECUTING THE UPDATE FOR: "+ firstNameFilter + " " + lastNameFilter + "\n");
			int rowsAffected = stmt.executeUpdate("update employees set email='" + 
						newEmail + "' where last_name='" + lastNameFilter +"' and first_name='" + firstNameFilter + "'");
			System.out.println("AFTER THE UPDATE...");
			displayEmployee(conn, firstNameFilter, lastNameFilter);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(conn, stmt, myRs);
		}
	}
	
	private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException{
		PreparedStatement pStmt = null;
		ResultSet myRs = null;
		try {
			pStmt = myConn.prepareStatement("select last_name,first_name,email from employees where last_name=? and first_name=?");
			pStmt.setString(1, lastName);
			pStmt.setString(2, firstName);
			myRs = pStmt.executeQuery();
			while (myRs.next()) {
				String theLastName = myRs.getString("last_name");
				String theFirstName = myRs.getString("first_name");
				String theEmail = myRs.getString("email");
				
				System.out.printf("%s %s, %s\n", theFirstName, theLastName, theEmail);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(pStmt, myRs);
		}
	}
	
	private static void close (Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
		if(myRs != null) {
			myRs.close();
		}
		
		if(myStmt != null) {
			myStmt.close();
		}
		
		if(myConn != null) {
			myConn.close();
		}
	}
	
	private static void close (Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}
}
