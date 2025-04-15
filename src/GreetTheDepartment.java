import java.sql.*;

public class GreetTheDepartment {
	public static void main (String[] args) throws Exception{
		Connection myConn = null;
		CallableStatement myStmt = null;
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);
			String theDepartment = "Engineering";
			myStmt = myConn.prepareCall("{call greet_the_department(?)}");
			//set the parameters
			myStmt.registerOutParameter(1, Types.VARCHAR);
			myStmt.setString(1, theDepartment);
			//call SP
			System.out.printf("Calling stored procedure. greet_the_department('%s')\n", theDepartment);
			myStmt.execute();
			System.out.println("Finished calling stored procedure.");
			
			//Get value from INOUT parameter
			String SPResult = myStmt.getString(1);
			System.out.println("\nThe result = " + SPResult);
		}
		catch(Exception exc){
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
