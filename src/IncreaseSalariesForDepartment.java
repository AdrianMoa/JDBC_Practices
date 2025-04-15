import java.sql.*;

public class IncreaseSalariesForDepartment {
	public static void main(String[] args) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			String theDepartment = "Engineering";
			int theIncreaseAmount = 5000;
			System.out.println("Salaries BEFORE\n");
			showSalaries(myConn, theDepartment);
			myStmt = myConn.prepareCall("{call increase_salaries_for_department(?,?)}");
			myStmt.setString(1, theDepartment);
			myStmt.setInt(2, theIncreaseAmount);
			System.out.printf("\nCalling stored procedure. increse_salaries_for_department('%s','%s')", theDepartment, theIncreaseAmount);
			myStmt.execute();
			System.out.println("\nFinished calling stored procedure.");
			System.out.println("\n\nSalaries AFTER increase\n");
			showSalaries(myConn, theDepartment);
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	
	private static void showSalaries(Connection myConn, String department) throws Exception {
		PreparedStatement myStmt = myConn.prepareStatement("select * from employees where department=?");
		myStmt.setString(1, department);
		ResultSet myRs = null;
		try {
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				int salary = myRs.getInt("salary");
				System.out.printf("%s, %s. %s  - %,d%n", lastName, firstName, department, salary);
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(null, null, myRs);
		}
	}
	
	private static void close (Connection myConn, Statement myStmt, ResultSet myRs) throws Exception {
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
}
