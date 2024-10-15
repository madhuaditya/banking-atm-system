import java.sql.*;
import java.util.Collections;
import java.util.Scanner;

public class CreateNewAcc {
   public  Scanner scanner = new Scanner(System.in)    ;
    public boolean creteUserAccount(){
        System.out.println("\n=== User Registration ===");
        System.out.println("Please inter your details correctly... ");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        long phoneNo = scanner.nextLong();
        scanner.nextLine(); // Consume newline left by nextLong()

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter role (customer/admin): ");
        String role = scanner.nextLine();

        // Call the method to insert the user into the database
        if (insertUser(username, password, address, phoneNo, email, role)) {
            System.out.println("User registered successfully!");
             getUserDetails(username,password);
            return true;
        } else {
            System.out.println("User registration failed. Please try again.");
            return false;
        }
    }
    private static boolean insertUser(String username, String password, String address, long phoneNo, String email, String role) {

        String insertQuery = "INSERT INTO Users (username, password, address, phoneNo, email, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Consider hashing the password for security
            preparedStatement.setString(3, address);
            preparedStatement.setLong(4, phoneNo);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, role);

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the user was inserted successfully

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false; // Registration failed
        }
    }

    public    static void getAllUsers() {
        String query = "SELECT * FROM Users"; // SQL query to select all users

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Displaying header
            System.out.println("\n=== All Users ===");
            System.out.println("UserID | Username | Address | Phone No | Email | Role | Created At");
            System.out.println("---------------------------------------------------------------");

            // Loop through the result set to print each user's details
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String address = resultSet.getString("address");
                long phoneNo = resultSet.getLong("phoneNo");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String createdAt = resultSet.getString("created_at");

                System.out.printf("%-6d | %-10s | %-20s | %-10d | %-20s | %-6s | %s\n",
                        userId, username, address, phoneNo, email, role, createdAt);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
    public static void getUserDetails(int userId, String password) {
        String query = "SELECT * FROM Users WHERE user_id = ? AND password = ?"; // SQL query to verify user credentials

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for user_id and password
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, password);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Check if user exists with given credentials
                if (resultSet.next()) {
                    // If valid user, display user details
                    String username = resultSet.getString("username");
                    String address = resultSet.getString("address");
                    String role = resultSet.getString("role");
                    String createdAt = resultSet.getString("created_at");
                    long phone = resultSet.getLong("phoneNo");
                    String email = resultSet.getString("email");

                    System.out.println("\n=== User Details ===");
                    System.out.println("UserID: " + userId);
                    System.out.println("Username: " + username);
                    System.out.println("Address: " + address);
                    System.out.println("Phone No: " + phone);
                    System.out.println("Email: " + email);
                    System.out.println("Role: " + role);
                    System.out.println("Created At: " + createdAt);
                } else {
                    // If no match, show error message
                    System.out.println("Invalid user ID or password.");
                }

            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
    public static void getUserDetails(String userName, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?"; // SQL query to verify user credentials

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for user_id and password
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Check if user exists with given credentials
                if (resultSet.next()) {
                    // If valid user, display user details
                    int userID = resultSet.getInt("user_id");
                    String address = resultSet.getString("address");
                    String role = resultSet.getString("role");
                    String createdAt = resultSet.getString("created_at");
                    long phone = resultSet.getLong("phoneNo");
                    String email = resultSet.getString("email");

                    System.out.println("\n=== User Details ===");
                    System.out.println("UserID: " + userID);
                    System.out.println("Username: " + userName);
                    System.out.println("Address: " + address);
                    System.out.println("Phone No: " + phone);
                    System.out.println("Email: " + email);
                    System.out.println("Role: " + role);
                    System.out.println("Created At: " + createdAt);
                } else {
                    // If no match, show error message
                    System.out.println("Invalid user ID or password.");
                }

            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


    public static void changeEmail(int userId, String password, String newEmail) {
        String verifyQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        String updateQuery = "UPDATE Users SET email = ? WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Verify the user with password
            verifyStmt.setInt(1, userId);
            verifyStmt.setString(2, password);
            ResultSet resultSet = verifyStmt.executeQuery();

            if (resultSet.next()) {
                // User verified, update the email
                updateStmt.setString(1, newEmail);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("Invalid User ID or Password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void changePhoneNumber(int userId, String password, long newPhoneNumber) {
        String verifyQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        String updateQuery = "UPDATE Users SET phoneNo = ? WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Verify the user with password
            verifyStmt.setInt(1, userId);
            verifyStmt.setString(2, password);
            ResultSet resultSet = verifyStmt.executeQuery();

            if (resultSet.next()) {
                // User verified, update the phone number
                updateStmt.setLong(1, newPhoneNumber);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
                System.out.println("Phone number updated successfully.");
            } else {
                System.out.println("Invalid User ID or Password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void changePassword(int userId, String oldPassword, String newPassword) {
        String verifyQuery = "SELECT password FROM Users WHERE user_id = ? AND password = ?";
        String updateQuery = "UPDATE Users SET password = ? WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Verify the current password
            verifyStmt.setInt(1, userId);
            verifyStmt.setString(2, oldPassword);
            ResultSet resultSet = verifyStmt.executeQuery();

            if (resultSet.next()) {
                // Current password is correct, proceed with password change
                updateStmt.setString(1, newPassword);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("Incorrect current password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void submitFeedback(int userId, String feedbackMessage, int rating) {
        // SQL query to insert feedback into the Feedback table
        String insertFeedbackQuery = "INSERT INTO Feedback (user_id, message, rating) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(insertFeedbackQuery)) {

            // Set the parameters for the feedback
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, feedbackMessage);
            preparedStatement.setInt(3, rating);

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thank you! Your feedback has been submitted.");
            } else {
                System.out.println("Failed to submit feedback. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Error while submitting feedback: " + e.getMessage());
        }
    }
    public static boolean doesUserExist(int userId, String password) {
        // SQL query to check if the user exists with the given user_id and password
        String checkUserQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(checkUserQuery)) {

            // Set the parameters for user_id and password
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result is returned
            if (resultSet.next()) {
                System.out.println("User exists. Authentication successful.");
                return true; // User exists
            } else {
                System.out.println("Invalid user ID or password.");
                return false; // User does not exist
            }

        } catch (SQLException e) {
            System.out.println("Error while checking user existence: " + e.getMessage());
            return false;
        }
    }


}
