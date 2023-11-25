package InventoryClases;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Transaction {
    
    private static final String COMPRA = "COMPRA";
    private static final String VENTA = "VENTA";    
    
    private String type;
    private LocalDateTime date;
    private String transactionCode;
    private List<TransactionDetail> details = new ArrayList<>();

    public Transaction(String transactionCode, String type, LocalDateTime date){
        this.transactionCode = transactionCode;
        this.type = type;
        this.date = date;
    }
    
    public TransactionDetail addTransactionDetail(String productCode, int quantity) {
        TransactionDetail detail = new TransactionDetail(productCode, quantity, this);
        detail.setTransactionId(transactionCode);
        details.add(detail);
        return detail;
    }

    public void execute(Inventory inventory) {
        for (TransactionDetail detail : details) {
            Product product = inventory.getProductByCode(detail.getProductCode());
            if (product != null) {
                if (type.equals(COMPRA)) {
                    product.increaseQuantity(detail.getQuantity());
                } else if (type.equals(VENTA)) {
                    if (product.getQuantity() >= detail.getQuantity()) {
                        product.decreaseQuantity(detail.getQuantity());
                    } else {
                        System.out.println("No hay suficientes productos para la venta de " + product.getName());
                    }
                }
            } else {
                System.out.println(
                        "El producto con código " + detail.getProductCode() + " no se encontró en el inventario.");
            }
        }
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
}
