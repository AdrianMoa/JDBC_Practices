import java.sql.*;

public class JdbcInsertDemo{
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet myRs = null;
		
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		
		try {
			conn = DriverManager.getConnection(dbUrl, user, pass);
			stmt = conn.createStatement();
			System.out.println("Inserting a new employee to database\n");
			stmt.executeUpdate(
					"insert into employees " +
					"(last_name, first_name, email, department, salary) " +
					"values " +
					"('MOA', 'ADRIAN', 'adrian.moa@foo.com', 'TI', 55000.00)");
			myRs = stmt.executeQuery("select * from employees");
			while (myRs.next()) {
				System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				myRs.close();
			}
		}
	}
} 