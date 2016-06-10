package ua.epamcourses.natalia_markova.homework.problem03_04.task06.subtask03;

import java.util.*;

/**
 * Created by natalia_markova on 12.05.2016.
 */
public class Account {

    private String account;
    private double balance;
    private List<Operation> operations;

    private static class Operation {
        Date date;
        int type; // 0 - income, 1 - payment, 2 - withdrawing
        double amount;
        String details;
        String corName;
        String corAccount;
        String corBankCode;
        String corBankName;

        public Operation() {
            date = Calendar.getInstance().getTime();
        }

        public Operation(Date date, int type, double amount, String details, String corName, String corAccount, String corBankCode, String corBankName) {
            this.date = date;
            this.type = type;
            this.amount = amount;
            this.details = details;
            this.corName = corName;
            this.corAccount = corAccount;
            this.corBankCode = corBankCode;
            this.corBankName = corBankName;
        }

        static class OrderByDate implements Comparator<Operation> {
            @Override
            public int compare(Operation o1, Operation o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                return o1.date.compareTo(o2.date);
            }
        }

    }

    public Account(String account) {
        this(account, 0);
    }

    public Account(String account, double balance) {
        this.account = account;
        this.balance = balance;
        this.operations = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void orderOperationsByDate() {
        operations.sort(new Operation.OrderByDate());
    }

    public boolean postOperation(Date date, int type, double amount, String details, String corName, String corAccount, String corBankCode, String corBankName) {
        if (!hasEnoughMoney(type, amount)) {
            return false;
        }
        operations.add(new Operation(date, type, amount, details, corName, corAccount, corBankCode, corBankName));
        balance = balance + amount * (type == 0 ? 1 : -1);
        return true;
    }

    public boolean postOperation(int type, double amount, String details) {
        return postOperation(Calendar.getInstance().getTime(), type, amount, details, "", "", "", "");
    }

    private boolean hasEnoughMoney(int type, double amount) {
        return (type != 0 && balance < amount ? false : true);
    }
}
