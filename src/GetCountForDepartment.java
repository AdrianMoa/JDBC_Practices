import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class GetCountForDepartment {
	public static void main (String[] args) throws Exception{
		Connection myConn = null;
		CallableStatement myStmt = null;
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			myStmt = myConn.prepareCall("{call get_count_for_department(?,?)}");
			//set SP parameters
			myStmt.setString(1, "Engineering");
			myStmt.registerOutParameter(2, Types.SMALLINT);
			//call stored procedure.
			System.out.printf("Calling stored procedure. get_count_for_department('',?)\n", "Engineering");
			myStmt.execute();
			System.out.println("Finished calling stored procedure.");
			
			int result  = myStmt.getInt(2);
			System.out.println("\nThe count = " + result);
		}
		catch (Exception exc) {
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
