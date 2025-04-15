import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class GetEmployeesForDepartment {
	public static void main (String[] args) throws Exception{
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		String department = "Engineering";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");
			myStmt.setString(1, department);
			
			System.out.printf("Calling stored procedure. get_employees_for_department('%s')\n", department);
			myStmt.execute();
			System.out.println("Finished calling stored procedure.\n");
			
			myRs = myStmt.getResultSet();
			display(myRs);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
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
	
	private static void display(ResultSet myRs) throws Exception {
		boolean existsData = false;
		while(myRs.next()) {
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			String mail = myRs.getString("email");
			int salary = myRs.getInt("salary");
			System.out.printf("%s %s, %s - $%,d%n",firstName, lastName, mail, salary);
			existsData = true;
		}
		
		if(!existsData) {
			System.out.println("Department without employees registed.");
		}
	}
}
