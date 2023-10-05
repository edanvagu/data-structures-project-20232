package Clases;

import java.util.LinkedList;
import java.util.List;

public class TransactionHistory {
    List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new LinkedList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void printTransactions(){
        for (Transaction  t : transactions) {
            List<TransactionDetail> temp  =  t.getDetails();
            //System.out.println(t.getDate());
            for (TransactionDetail details : temp) {
                System.out.println(details.getProduct().getName());
                System.out.println(details.getProduct().getAmount());
            }
        }
    }

    public void printTransactions(String initial, String finalDate){

    }


}
