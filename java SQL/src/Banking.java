import java.util.*;

public class Banking {
    private static Scanner scanner = new Scanner(System.in);
    // Sample user data for demonstration
    private static Map<Integer, Double> userBalances = new HashMap<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n======= Welcome to Our Bank =======");
            System.out.println("0. Register in this bank");
            System.out.println("1. Create a New Account");
            System.out.println("2. Perform a Transaction");
            System.out.println("3. Change Your Password");
            System.out.println("4. Provide Feedback");
            System.out.println("5. To use different modification feature Account User details");
            System.out.println("6. Exit");
            System.out.println("Enter your choice (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left by nextInt()

            switch (choice) {
                case 0:
                      registration();
                      break;
                case 1:
                    AccountManage.createNewAccount();
                    break;
                case 2:
                    Transactions.performTransaction();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    provideFeedback();
                    break;
                case 5:
                    diffFeature();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
            return;
        }
    }

    // Restratiation
    public static void registration() {
        CreateNewAcc cr = new CreateNewAcc();
       boolean bl = cr.creteUserAccount();
       if(bl){
           System.out.println("\n If you want to open a new Account please Inter Y/N\n");
           System.out.flush();
           char ch = scanner.next().charAt(0);
           if(ch=='Y' || ch=='y'){
               AccountManage.createNewAccount();
           }
           else {
               System.out.println("\n Thank you for giving us time ");
           }
       }
       else {
           System.out.println("\n Thank you for giving us time ");
       }
    }

    // Function to perform a transaction
    public static void performTransaction() {
        System.out.println("\n=== Perform a Transaction ===");
        System.out.print("Enter your account ID: ");
        int accountId = scanner.nextInt();

        // Check if the account ID exists
        if (!userBalances.containsKey(accountId)) {
            System.out.println("Account ID not found.");
            return;
        }

        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.print("Enter your choice (1 for Deposit, 2 for Withdraw): ");
        int transactionType = scanner.nextInt();
        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Perform transaction based on type
        if (transactionType == 1) {
            deposit(accountId, amount);
        } else if (transactionType == 2) {
            withdraw(accountId, amount);
        } else {
            System.out.println("Invalid transaction type.");
        }
    }

    // Function to deposit money
    private static void deposit(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        double currentBalance = userBalances.get(accountId);
        double newBalance = currentBalance + amount;
        userBalances.put(accountId, newBalance);
        System.out.println("Deposited " + amount + " to account ID " + accountId);
        System.out.println("New balance: " + newBalance);
    }

    // Function to withdraw money
    private static void withdraw(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        double currentBalance = userBalances.get(accountId);
        if (currentBalance < amount) {
            System.out.println("Insufficient funds. Current balance: " + currentBalance);
            return;
        }

        double newBalance = currentBalance - amount;
        userBalances.put(accountId, newBalance);
        System.out.println("Withdrew " + amount + " from account ID " + accountId);
        System.out.println("New balance: " + newBalance);
    }

    // Function to change the password
    public static void changePassword() {
        System.out.println("\n=== Change Your Password ===");
        System.out.print("Enter your User ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        CreateNewAcc.changePassword(accountId,currentPassword,newPassword);
    }

    // Function to provide feedback
    public static void provideFeedback() {
        System.out.println("\n=== Provide Feedback ===");
        System.out.print("Enter your User ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter your feedback: ");
        String feedback = scanner.nextLine();
        System.out.print("Rate us Between 1 - 5 : ");
        int rat = scanner.nextInt();
       CreateNewAcc.submitFeedback(accountId, feedback, rat);
        // Logic to store feedback (replace with SQL logic)
        System.out.println("Thank you for your feedback!");
    }

    // Function to query data
    public static void queryData() {
        System.out.println("\n=== Query Data ===");
        System.out.print("Enter your account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Logic to query and display account info (replace with SQL logic)
        if (userBalances.containsKey(accountId)) {
            System.out.println("Displaying data for account ID " + accountId);
            System.out.println("Account Balance: " + userBalances.get(accountId));
        } else {
            System.out.println("Account ID not found.");
        }
    }
    // change password change phone number , change Email
    public static void diffFeature(){
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.println("1 to get account details");
        System.out.println("2 to get User details");
        System.out.println("3 to get last Transaction Details");
        System.out.println("4 to get All Transaction Details");
        System.out.println("5 to change phone number");
        System.out.println("6 to change Email");
        System.out.println("7. Exit");
        int n = scanner.nextInt();
        switch (n){
            case 1:
                    if(CreateNewAcc.doesUserExist(userId,password)){
                        System.out.println("Please Enter AccountId ");
                        int accId = scanner.nextInt();
                        AccountManage.getUserAccounts(accId, userId);
                    }
                    else {
                        System.out.println("Please Enter correct Details..");
                    }
                break;
            case 2:
                    CreateNewAcc.getUserDetails(userId,password);
                break;
            case 3:
                 Transactions.getLatestTransactions(userId, 1);
                break;
            case 4:
                Transactions.getAllTransactionsForUser(userId);
                break;
            case 5:
                System.out.println("Enter your new Phone number: ");
                long newPhone = scanner.nextLong();
                CreateNewAcc.changePhoneNumber(userId,password,newPhone);
                break;
            case 6:
                 String newEmail = scanner.next();
                 CreateNewAcc.changeEmail(userId,password,newEmail);
                 break;
            case 7:
                System.out.println("Exiting..........");
                break;
            default:
                System.out.println("Error : Wrong Choice...!");
                System.out.println("Exiting...........");
                break;
        }
    }
}

