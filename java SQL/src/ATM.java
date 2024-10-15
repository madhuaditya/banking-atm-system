import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======== Hi ===========");
        System.out.println("Welcome to our ATM");
        int n ;
        System.out.println("Enter your choice sir/ma'am");
        System.out.println("1. Cash withdraw");
        System.out.println("2. Deposit Money");
        n = sc.nextInt();
        if(n == 1){
            Transactions.performTransaction();
        }
        if(n==2){
            System.out.println("please Enter userID : ");
            int userId = sc.nextInt();
            System.out.println("please Enter password : ");
            System.out.flush();
            String password = sc.next();
            System.out.println("please Enter accountId : ");
            System.out.flush();
            int accountId = sc.nextInt();
            System.out.println("please Enter deposit Amount: ");
            double depositAmount = sc.nextInt();
            Transactions.depositMoney(userId, password,accountId,depositAmount);
        }
        else {
            System.out.println("Please choose right option...!!");
        }
        return  ;
    }
}
