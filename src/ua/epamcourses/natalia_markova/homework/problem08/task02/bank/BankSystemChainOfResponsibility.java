package ua.epamcourses.natalia_markova.homework.problem08.task02.bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 12.06.2016.
 */

class Bank {

    private String mfo;
    private String name;

    public Bank(String mfo, String name) {
        this.mfo = mfo;
        this.name = name;
    }

    public double getCommission(Payment payment) {
        return 3.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bank bank = (Bank) o;

        return mfo.equals(bank.mfo);

    }

    @Override
    public int hashCode() {
        return mfo.hashCode();
    }
}

abstract class PaymentHandler {
    PaymentHandler handler;

    public PaymentHandler(PaymentHandler handler) {
        this.handler = handler;
    }

    public abstract void proceed(Payment payment);
}

class InternalPaymentHandler extends PaymentHandler {
    public InternalPaymentHandler(PaymentHandler handler) {
        super(handler);
    }

    @Override
    public void proceed(Payment payment) {
        if (payment.getType() == PaymentType.INTERNAL) {
            System.out.println("Internal payment");
            Account account = payment.getAccount();
            account.withdraw(payment.getAmount());
            account = payment.getCorrAccount();
            account.receipt(payment.getAmount());
        } else if (handler != null) {
            handler.proceed(payment);
        }
    }
}

class CommissionFreePaymentHandler extends PaymentHandler {

    public CommissionFreePaymentHandler(PaymentHandler handler) {
        super(handler);
    }

    @Override
    public void proceed(Payment payment) {
        if (payment.getType() == PaymentType.COMMISSION_FREE) {
            System.out.println("Commission-free payment");
            Account account = payment.getAccount();
            account.withdraw(payment.getAmount());
        } else if (handler != null) {
            handler.proceed(payment);
        }
    }
}

class OrdinaryPaymentHandler extends PaymentHandler {
    public OrdinaryPaymentHandler(PaymentHandler handler) {
        super(handler);
    }

    @Override
    public void proceed(Payment payment) {
        if (payment.getType() == PaymentType.ORDINARY) {
            System.out.println("Ordinary payment");
            Account account = payment.getAccount();
            account.withdraw(payment.getAmount());
            double commission = payment.getCommission();
            account.withdraw(commission);

        } else if (handler != null) {
            handler.proceed(payment);
        }
    }
}

class Payment {
    private PaymentType type;
    private double amount;
    private Account account;
    private Account corrAccount;
    private String comment;

    public Payment(double amount, Account account, Account corrAccount, String comment) {
        this.amount = amount;
        this.account = account;
        this.corrAccount = corrAccount;
        this.comment = comment;
        setPaymentType();
    }

    private void setPaymentType() {
        if (account.getBank().equals(corrAccount.getBank())) {
            type = PaymentType.INTERNAL;
        } else if (corrAccount.isCommissionFree()) {
            type = PaymentType.COMMISSION_FREE;
        } else {
            type = PaymentType.ORDINARY;
        }
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public Account getCorrAccount() {
        return corrAccount;
    }

    public String getComment() {
        return comment;
    }

    public PaymentType getType() {
        return type;
    }

    public double getCommission() {
        return account.getBank().getCommission(this);
    }
}

class Account {
    private double balance;
    private String no;
    private AccountHolder holder;
    private boolean commissionFree;
    private Bank bank;

    public Account(String no, AccountHolder holder, double balance) {
        this.balance = balance;
        this.no = no;
        this.holder = holder;
    }

    public Account(double balance, String no, Bank bank, boolean commissionFree) {
        this.balance = balance;
        this.no = no;
        this.bank = bank;
        this.commissionFree = commissionFree;
    }

    public double getBalance() {
        return balance;
    }

    public String getNo() {
        return no;
    }

    public AccountHolder getHolder() {
        return holder;
    }

    public boolean isCommissionFree() {
        return commissionFree;
    }

    public Bank getBank() {
        return bank;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void receipt(double amount) {
        balance += amount;
    }
}

class AccountHolder {
    private String codeEDRPOU;
    private String name;

    public AccountHolder(String codeEDRPOU, String name) {
        this.codeEDRPOU = codeEDRPOU;
        this.name = name;
    }

    public String getCodeEDRPOU() {
        return codeEDRPOU;
    }

    public String getName() {
        return name;
    }
}

enum PaymentType {
    INTERNAL, COMMISSION_FREE, ORDINARY
}

public class BankSystemChainOfResponsibility {

    public static void main(String[] args) {
        PaymentHandler handler1 = new OrdinaryPaymentHandler(null);
        PaymentHandler handler2 = new CommissionFreePaymentHandler(handler1);
        PaymentHandler handler = new InternalPaymentHandler(handler2);

        Bank bank1 = new Bank("300335", "–‡ÈÙÙ‡ÈÁÂÌ ¡‡ÌÍ ¿‚‡Î¸ Ï.  Ëø‚");
        Bank bank2 = new Bank("300175", "œ¿“ \"‘≤ƒŒ¡¿Õ \" Ï. Ëø‚");
        Bank bank3 = new Bank("320917", "‘-ˇ ¿¡ \"œ≥‚‰ÂÌÌËÈ\" Ï. Ëø‚.");

        Account account1 = new Account(2000, "26004443694", bank1, false);
        Account account2 = new Account(2000, "26004443692", bank1, false);
        Account account3 = new Account(1000, "26004443694", bank2, false);
        Account account4 = new Account(200, "26007310842101", bank3, true);

        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(400, account1, account2, "payment 1"));
        payments.add(new Payment(300, account1, account3, "payment 2"));
        payments.add(new Payment(100, account1, account4, "payment 3"));

        for (Payment payment: payments) {
            handler.proceed(payment);
        }

    }


}
