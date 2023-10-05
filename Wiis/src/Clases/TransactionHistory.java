package Clases;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TransactionHistory {

    private List<Transaction> transactions;
    //CARPETA EN DONDE SE GUARDARA EL ARCHIVO DEL INVENTARIO (CAMBIAR PARA CADA PERSONA)

    private String folderPath="./data-structures-project-20232/Wiis/src/Files";
    //NOMBRE DEL ARCHIVO PREDETERMINADO
    private String fileName="transactionHistory.txt";

    public TransactionHistory() {
        this.transactions = new LinkedList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void printTransactions(){
        for (Transaction  t : transactions) {
            List<TransactionDetail> temp  =  t.getDetails();
            System.out.println(t.getDate());
            for (TransactionDetail details : temp) {
                System.out.println(details.getProduct().getName());
                System.out.println(details.getProduct().getAmount());
            }
        }
    }

    public void printTransactions(String initial, String finalDate){

    }

    public void saveFile(){
        try{
            File carpeta = new File(folderPath);
            if (!carpeta.exists()) {
                carpeta.mkdirs();  // Crear la carpeta si no existe
            }
            File archivo = new File(carpeta, this.fileName);
            FileWriter newFile = new FileWriter(archivo);

            for (Transaction t : this.transactions) {
                newFile.write(t.getType()+"," +t.getDate()+ "\n");
                for(int i =0; i<t.getDetails().size(); i++){
                    TransactionDetail details = t.getDetails().get(i);
                    newFile.write(details.getProduct().getCode()+","+details.getProduct().getName()+","+details.getProduct().getPrice()+","+details.getProduct().getAmount()+","+details.getAmount()+","+ "\n");
                }
                newFile.write("a"+"\n");
            }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }

    }
    public void loadFile(){

    }
    public String getFileName(){
        return this.fileName;
    }
    public String getFolderPath(){
        return this.folderPath;
    }
    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

}
