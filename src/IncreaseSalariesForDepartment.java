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
			myStmt = myConn.prepareCall("{call increase_salary_for_department(?,?)}");
			myStmt.setString(1, theDepartment);
			myStmt.setInt(2, theIncreaseAmount);
			myStmt.execute();
			showSalaries(myConn, theDepartment);
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(myStmt != null) {
				myStmt.close();
			}
			
			if(myConn != null) {
				myConn.close();
			}
		}
	}
}
