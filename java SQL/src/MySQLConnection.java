import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    public static void main(String[] args) {
        // Database URL, Username, and Password
        String url = "jdbc:mysql://localhost:3306/..........";  // Replace with your database name
        String user = "";  // Replace with your MySQL username
        String password = "";  // Replace with your MySQL password

        // SQL query to access data from the PROJECT table
        String query = "SELECT * FROM PROJECT";

        // Connection and Statement objects
        Connection conn = null;
        Statement stmt = null;


        try {
            // Establishing the connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

            // Creating the statement
            stmt = conn.createStatement();

            // Executing the query
            ResultSet rs = stmt.executeQuery(query);

            // Processing the result set
            while (rs.next()) {
                int id = rs.getInt("ID");
                int empId = rs.getInt("EMPID");
                String name = rs.getString("NAME");
                java.sql.Date startDate = rs.getDate("START_DATA");
                int clientId = rs.getInt("CLINT_ID");

                // Printing the retrieved data
                System.out.println("ID: " + id + ", EMPID: " + empId + ", Name: " + name
                        + ", Start Date: " + startDate + ", Client ID: " + clientId);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } finally {
            // Closing the connection
            if (conn != null) {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
