import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        while (true) {
            Bank bank = new Bank();
            bank.accountsNew();

            Thread t1 = new Thread(() -> {
                bank.transfer("1", "2", 50000);
            });
            Thread t2 = new Thread(() -> {
                bank.transfer("2", "1", 50000);
            });
            Thread t3 = new Thread(() -> {
                bank.transfer("1", "2", 20000);
            });
            Thread t4 = new Thread(() -> {
                bank.transfer("2", "1", 10000);
            });
            t1.start();
            t2.start();
            t3.start();
            t4.start();
        }
//
//        System.out.println(bank.getBalance("1"));
//        System.out.println(bank.getBalance("2"));
//        bank.transfer("1", "2", 5000);
//        bank.transfer("2", "1", 5000);

//        for (int i = 0; i < 10; i++){
//            NewAccount newAccount = new NewAccount();
//            new Thread(newAccount).start();
//        }
    }


}
