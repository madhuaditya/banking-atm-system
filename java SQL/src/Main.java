import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
        System.out.println("======== Welcome Sir =========");
        System.out.println("If are in a Bank press 1 ");
        System.out.println("If u are in ATM press 2");
        Byte n = sc.nextByte();
        if(n==1){
            Banking.main(new String[]{});
        }
     else  if(n==2){
            ATM.main(new String[]{});
        }
        else {
            System.out.println("Please Enter right choice next time...........");
        }

    }
}
