package InventoryClases;

import java.util.*;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionHistory {

    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName = "transactionHistory.txt";
    private List<Transaction> transactions = new ArrayList<>();
    private Inventory inventory;
    private static int totalTransactions = 0;
    private PriorityQueue<String[]> mostSoldProducts = new PriorityQueue<>(
            (p1, p2) -> Integer.parseInt(p2[1]) - Integer.parseInt(p1[1]));

    public TransactionHistory(Inventory inventory) {
        this.transactions = new LinkedList<>();
        this.inventory = inventory;
    }

    public void addTransaction(Transaction transaction) {
        String transactionCode = "T" + totalTransactions;
        transaction.setTransactionCode(transactionCode);
        transactions.add(transaction);

        for (TransactionDetail detail : transaction.getDetails()) {
            detail.setTransactionId(transactionCode);
        }

        transaction.execute(inventory);
        totalTransactions++;
        inventory.saveFile();

        // Actualiza mostSoldProducts solo si la transacción es de tipo "VENTA"
        if (transaction.getType().equals("VENTA")) {
            for (TransactionDetail detail : transaction.getDetails()) {
                String productCode = detail.getProductCode();
                int quantity = detail.getQuantity();
                boolean found = false;

                // Itera sobre una lista temporal para evitar la modificación concurrente
                List<String[]> updatedMostSoldProducts = new ArrayList<>();
                while (!mostSoldProducts.isEmpty()) {
                    String[] product = mostSoldProducts.poll(); // Esto extrae elementos desde la PriorityQueue
                    if (product[0].equals(productCode)) {
                        // Si encontramos el producto, actualizamos su cantidad
                        int newQuantity = Integer.parseInt(product[1]) + quantity;
                        product[1] = String.valueOf(newQuantity);
                        found = true;
                    }
                    updatedMostSoldProducts.add(product);
                }

                // Si el producto no estaba en la PriorityQueue, lo agregamos
                if (!found) {
                    updatedMostSoldProducts.add(new String[] { productCode, String.valueOf(quantity) });
                }

                // Reconstruimos la PriorityQueue con los elementos actualizados
                mostSoldProducts.addAll(updatedMostSoldProducts);
                detail.setTransactionId(transactionCode);
            }
        }
    }

    public void sortTransactionHistory() {
        this.transactions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
    }

    public List<String[]> getTopSoldProducts(int topN) {
        List<String[]> topSoldProducts = new ArrayList<>();
        Iterator<String[]> iterator = mostSoldProducts.iterator();

        while (iterator.hasNext() && topN > 0) {
            topSoldProducts.add(iterator.next());
            topN--;
        }
        return topSoldProducts;
    }

    public void saveFile() {
        try {
            File folder = new File(this.folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter newFile = new FileWriter(this.folderPath + File.separator + this.fileName);

            for (Transaction t : this.transactions) {
                for (TransactionDetail details : t.getDetails()) {
                    newFile.write(t.getTransactionCode() + "," + t.getDate() + "," + t.getType() + ","
                            + details.getProductCode() + "," + details.getQuantity() + "\n");
                }
            }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }

    }

    public void loadFile() throws IOException {
        try {
            File file = new File(this.folderPath + File.separator + this.fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner sc = new Scanner(file);

            PriorityQueue<String[]> newMostSoldProducts = new PriorityQueue<>(
                    (p1, p2) -> Integer.parseInt(p2[1]) - Integer.parseInt(p1[1]));

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                String transactionCode = line[0];
                LocalDateTime transactionDate = LocalDateTime.parse(line[1]);
                String transactionType = line[2];
                String productCode = line[3];
                int productAmount = Integer.parseInt(line[4]);

                // Crea una nueva transacción si es diferente del código de transacción anterior
                if (transactions.isEmpty()
                        || !transactionCode.equals(transactions.get(transactions.size() - 1).getTransactionCode())) {
                    Transaction transaction = new Transaction(transactionCode, transactionType, transactionDate);
                    transactions.add(transaction);
                }
                // Agrega un detalle de transacción a la última transacción
                transactions.get(transactions.size() - 1).addTransactionDetail(productCode, productAmount);

                // Encontrar el índice del primer dígito en el código de la transacción
                int firstDigitIndex = 0;
                for (int i = 0; i < transactionCode.length(); i++) {
                    if (Character.isDigit(transactionCode.charAt(i))) {
                        firstDigitIndex = i;
                        break;
                    }
                }

                // Tomar el substring desde el primer dígito hasta el final del código de la
                // transacción para obtener el número
                int transactionNumber = Integer.parseInt(transactionCode.substring(firstDigitIndex));

                // Usar el número más grande para establecer totalTransactions
                totalTransactions = Math.max(totalTransactions, transactionNumber + 1);

                // Si la transacción es de tipo "VENTA", actualiza la PriorityQueue
                if (transactionType.equals("VENTA")) {
                    boolean found = false;

                    List<String[]> updatedMostSoldProducts = new ArrayList<>();
                    while (!newMostSoldProducts.isEmpty()) {
                        String[] product = newMostSoldProducts.poll();
                        if (product[0].equals(productCode)) {
                            int newQuantity = Integer.parseInt(product[1]) + productAmount;
                            product[1] = String.valueOf(newQuantity);
                            found = true;
                        }
                        updatedMostSoldProducts.add(product);
                    }

                    if (!found) {
                        updatedMostSoldProducts.add(new String[] { productCode, String.valueOf(productAmount) });
                    }

                    newMostSoldProducts.addAll(updatedMostSoldProducts);
                }
            }

            this.mostSoldProducts = newMostSoldProducts;

            sc.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public PriorityQueue<String[]> getMostSoldProducts() {
        return mostSoldProducts;
    }

    public void setMostSoldProducts(PriorityQueue<String[]> mostSoldProducts) {
        this.mostSoldProducts = mostSoldProducts;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
