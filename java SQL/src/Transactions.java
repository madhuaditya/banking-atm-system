import java.util.Scanner;
import java.sql.*;
public class Transactions {
    private  static Scanner scanner = new Scanner(System.in);
    public static void performTransaction() {


        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        System.out.print("Enter Password: ");
        String password = scanner.next();

        // Verify user credentials
        String verifyUserQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";


        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement verifyUserStmt = connection.prepareStatement(verifyUserQuery)) {

            verifyUserStmt.setInt(1, userId);
            verifyUserStmt.setString(2, password);
            ResultSet userResultSet = verifyUserStmt.executeQuery();

            if (userResultSet.next()) {
                // User verified, proceed with transaction
                System.out.println("User authenticated successfully.");

                // Get account information
                System.out.print("Enter Account ID: ");
                int accountId = scanner.nextInt();

                String accountQuery = "SELECT balance FROM Accounts WHERE account_id = ? AND user_id = ?";
                PreparedStatement accountStmt = connection.prepareStatement(accountQuery);
                accountStmt.setInt(1, accountId);
                accountStmt.setInt(2, userId);
                ResultSet accountResultSet = accountStmt.executeQuery();

                if (accountResultSet.next()) {
                    double currentBalance = accountResultSet.getDouble("balance");

                    // Ask for withdrawal amount
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawalAmount = scanner.nextDouble();

                    if (withdrawalAmount <= currentBalance) {
                        // Withdraw the amount and update balance
                        double newBalance = currentBalance - withdrawalAmount;

                        String updateBalanceQuery = "UPDATE Accounts SET balance = ? WHERE account_id = ?";
                        PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery);
                        updateBalanceStmt.setDouble(1, newBalance);
                        updateBalanceStmt.setInt(2, accountId);
                        updateBalanceStmt.executeUpdate();

                        // Record transaction details in Transactions table
                        String insertTransactionQuery = "INSERT INTO Transactions (account_id, user_id, transaction_type, amount) VALUES (?, ?, 'withdrawal', ?)";
                        PreparedStatement insertTransactionStmt = connection.prepareStatement(insertTransactionQuery);
                        insertTransactionStmt.setInt(1, accountId);
                        insertTransactionStmt.setInt(2, userId);
                        insertTransactionStmt.setDouble(3, withdrawalAmount);
                        insertTransactionStmt.executeUpdate();

                        System.out.println("Transaction successful! Withdrawn: " + withdrawalAmount);
                        System.out.println("New Balance: " + newBalance);
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                } else {
                    System.out.println("Account not found or does not belong to this user.");
                }

            } else {
                System.out.println("Invalid User ID or Password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }



    public static void depositMoney(int userId, String password, int accountId, double depositAmount) {
        String verifyUserQuery = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        String checkAccountQuery = "SELECT balance FROM Accounts WHERE account_id = ? AND user_id = ?";
        String updateBalanceQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        String insertTransactionQuery = "INSERT INTO Transactions (account_id, user_id, transaction_type, amount) VALUES (?, ?, 'deposit', ?)";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement verifyUserStmt = connection.prepareStatement(verifyUserQuery);
             PreparedStatement checkAccountStmt = connection.prepareStatement(checkAccountQuery);
             PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery);
             PreparedStatement insertTransactionStmt = connection.prepareStatement(insertTransactionQuery)) {

            // Verify the user with user_id and password
            verifyUserStmt.setInt(1, userId);
            verifyUserStmt.setString(2, password);
            ResultSet userResult = verifyUserStmt.executeQuery();

            if (userResult.next()) {
                // User verified, check if the account belongs to the user
                checkAccountStmt.setInt(1, accountId);
                checkAccountStmt.setInt(2, userId);
                ResultSet accountResult = checkAccountStmt.executeQuery();

                if (accountResult.next()) {
                    // Account exists, deposit the money
                    double currentBalance = accountResult.getDouble("balance");

                    // Update account balance
                    updateBalanceStmt.setDouble(1, depositAmount);
                    updateBalanceStmt.setInt(2, accountId);
                    updateBalanceStmt.executeUpdate();

                    // Log the transaction
                    insertTransactionStmt.setInt(1, accountId);
                    insertTransactionStmt.setInt(2, userId);
                    insertTransactionStmt.setDouble(3, depositAmount);
                    insertTransactionStmt.executeUpdate();

                    System.out.println("Deposit successful. Amount deposited: " + depositAmount);
                    System.out.println("New balance: " + (currentBalance + depositAmount));
                } else {
                    System.out.println("Account not found or does not belong to the user.");
                }
            } else {
                System.out.println("Invalid user ID or password.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void getAllTransactionsForUser(int userId) {
        String query = "SELECT * FROM Transactions WHERE user_id = ? ORDER BY transaction_date DESC";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();

            System.out.println("Transaction history for User ID: " + userId);
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                int accountId = resultSet.getInt("account_id");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");

                System.out.println("Transaction ID: " + transactionId +
                        ", Account ID: " + accountId +
                        ", Type: " + transactionType +
                        ", Amount: " + amount +
                        ", Date: " + transactionDate);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


    public static void getLatestTransactions(int userId, int limit) {
        String query = "SELECT * FROM Transactions WHERE user_id = ? ORDER BY transaction_date DESC LIMIT ?";

        try (Connection connection = DriverManager.getConnection(Connections.getUrl(), Connections.getUser(), Connections.getPassword());
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet resultSet = stmt.executeQuery();

            System.out.println("Latest " + limit + " transactions for User ID: " + userId);
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                int accountId = resultSet.getInt("account_id");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");

                System.out.println("Transaction ID: " + transactionId +
                        ", Account ID: " + accountId +
                        ", Type: " + transactionType +
                        ", Amount: " + amount +
                        ", Date: " + transactionDate);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


}
