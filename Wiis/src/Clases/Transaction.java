package Clases;

import java.util.LinkedList;
import java.util.List;

public class Transaction {
    private String type;
    private String date;
    private List<TransactionDetail> details;

    public Transaction(String type, String date){
        this.type = type;
        this.date = date;
        this.details = new LinkedList<>();
    }

    public void addTransactionDetail(String productCode, int amount){
        TransactionDetail detail = new TransactionDetail(productCode, amount);
        this.details.add(detail);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }
}
