import java.sql.*;
import java.util.Scanner;

public class AccountManage {
    private static Scanner scanner = new Scanner(System.in);
    public static void createNewAccount() {
        System.out.println("\n=== Create New Account ===");

        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left after nextInt()

        System.out.print("Enter account type (savings/checking): ");
        String accountType = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        // Call the method to create a new account in the database
        if (insertNewAccount(userId, accountType, initialBalance)) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed. Please try again.");

        }
    }

    private static boolean insertNewAccount(int userId, String accountType, double initialBalance) {
        String insertAccountQuery = "INSERT INTO Accounts (user_id, account_type, balance) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(insertAccountQuery)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, accountType); // Accepts 'savings' or 'checking'
            preparedStatement.setDouble(3, initialBalance);

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the account was created successfully

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false; // Account creation failed
        }
    }
    public static void getUserAccounts(int accountID , int userID ) {
        String query = "SELECT * FROM Accounts WHERE account_Id = ? AND user_id =?"; // SQL query to get accounts for a specific user

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, accountID);
            preparedStatement.setInt(2, userID); // Setting the user_id in the query

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Displaying header
                System.out.println("\n=== Account Details for User ID: " + userID + " ===");
                System.out.println("AccountID | Account Type | Balance | Created At");
                System.out.println("----------------------------------------------");

                // Loop through the result set to print each account's details
                while (resultSet.next()) {
                    int accountId = resultSet.getInt("account_id");
                    String accountType = resultSet.getString("account_type");
                    double balance = resultSet.getDouble("balance");
                    String createdAt = resultSet.getString("created_at");

                    System.out.printf("%-9d | %-12s | %-10.2f | %s\n",
                            accountId, accountType, balance, createdAt);
                }

            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

}
