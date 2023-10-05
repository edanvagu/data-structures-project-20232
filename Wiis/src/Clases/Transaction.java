package Clases;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Transaction {
    private Boolean type;
    private String date;
    private List<TransactionDetail> details;

    public Transaction(Boolean type){
        this.type = type;
        //this.date = date;
        this.details = new LinkedList<>();
    }

    public void addTransactionDetail(Product product, int amount){
        this.details.add(new TransactionDetail(product, amount));
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
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

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
}
