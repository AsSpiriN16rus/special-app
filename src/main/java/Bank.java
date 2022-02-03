import javax.naming.InsufficientResourcesException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Bank {

    private  Map<String, Account> accounts = new TreeMap<>();

    private final Random random = new Random();

    public  void accountsNew() {
        for (int i = 0; i < 100; i++){
            String a = String.valueOf(i);
            accounts.put(a, new Account());
            accounts.get(a).setMoney((long) ((Math.random() * 90000) + 0));
            accounts.get(a).setAccNumber(a);
        }
    }


    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     *
     */

    private void doTransfer(String fromAccountNum, String toAccountNum, long amount) {
        System.out.println("Количество денег на счете списания до операции - " + getBalance(fromAccountNum));
        System.out.println("Количество денег на счете пополнения до операции - " + getBalance(toAccountNum));

        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);


        if (fromAccount.getFraud() | toAccount.getFraud()) {
            System.out.println("Один из счетов заблокирован:( ");
        } else if (fromAccount.getMoney() - amount < 0) {
            System.out.println("Недостаточно средств на счете.");
        } else {
            System.out.println("Операция одобрена. Хорошего дня.");
            fromAccount.setMoney(fromAccount.getMoney() - amount);
            toAccount.setMoney(toAccount.getMoney() + amount);
        }
        System.out.println("Количество денег на счете списания после операции - " + getBalance(fromAccountNum));
        System.out.println("Количество денег на счете пополнения после операции - " + getBalance(toAccountNum));
        System.out.println();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

        if (amount > 50000 ) {
            try {
                if (isFraud(fromAccountNum, toAccountNum, amount)) {
                    accounts.get(fromAccountNum).setFraud(true);
                    accounts.get(toAccountNum).setFraud(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int fromId = Integer.parseInt(accounts.get(fromAccountNum).getAccNumber());
        int toId = Integer.parseInt(accounts.get(toAccountNum).getAccNumber());


        if (fromId < toId) {
            synchronized (accounts.get(fromAccountNum)) {
                synchronized (accounts.get(toAccountNum)) {
                    doTransfer(fromAccountNum,toAccountNum,amount);
                }
            }
        }else {
            synchronized (accounts.get(toAccountNum)) {
                synchronized (accounts.get(fromAccountNum)) {
                    doTransfer(fromAccountNum,toAccountNum,amount);
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        return 0;
    }


}
