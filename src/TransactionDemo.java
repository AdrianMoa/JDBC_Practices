import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class TransactionDemo {
	public static void main(String[] args) throws Exception{
		Connection myConn = null;
		Statement myStmt = null;
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			myConn.setAutoCommit(false);
			
			System.out.println("SALARIES BEFORE\n");
			showSalaries(myConn, "HR");
			showSalaries(myConn, "Engineering");
			
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("delete from employees where department='HR'");
			myStmt.executeUpdate("update employees set salary=200000 where department='Engineering'");
			
			System.out.println("\n>> Transaction steps are ready.\n");
			boolean ok = askUserIfOkToSave();
			if(ok) {
				myConn.commit();
				System.out.println("\n>> Transaction COMMITTED");
			}
			else {
				myConn.rollback();
				System.out.println("\n>> Transaction ROLLED BACK");
			}
			
			System.out.println("Salaries AFTER\n");
			showSalaries(myConn, "HR");
			showSalaries(myConn, "Engineering");
			
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	
	private static void showSalaries(Connection myConn, String department) throws Exception{
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			pStmt = myConn.prepareStatement("select * from employees where department=?");
			pStmt.setString(1, department);
			rs = pStmt.executeQuery();
			System.out.printf("Show salaries for Department: %s.\n", department);
			boolean existsEmployees = false;
			while(rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int salary = rs.getInt("salary");
				System.out.printf("%s %s | %s | > $%,d%n", firstName, lastName, department, salary);
				existsEmployees = true;
			}
			
			if(!existsEmployees) {
				System.out.printf("Employees not found for department '%s'\n", department);
			}
			else {
				System.out.println();
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			close(null, pStmt, rs);
		}
	}
	
	private static boolean askUserIfOkToSave() throws Exception{
		Scanner scan = new Scanner(System.in);
		System.out.println("Is it okay to save? yes/no: ");
		String input = scan.nextLine();
		scan.close();
		
		return input.equalsIgnoreCase("yes");
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws Exception{
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
