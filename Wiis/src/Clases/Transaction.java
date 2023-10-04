package Clases;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Transaction {
    private Boolean type;
    private LocalDate date;

    private List<TransactionDetail> details;


    public Transaction(Boolean type) {
        this.type = type;
        this.date = LocalDate.now();
        this.details = new LinkedList<TransactionDetail>();
    }

    public void addTransactionDetail(Product product, int amount){
        this.details.add(new TransactionDetail(product, amount));
    }




}
