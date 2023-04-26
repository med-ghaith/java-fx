/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.entities;

/**
 *
 * @author bilel abbassi
 */
public class Transaction {
     private Integer id  ; 
    private String id_transaction  ; 
    private Integer id_command ; 
     private String status ; 

    public Transaction() {
    }

    public Transaction(String id_transaction, Integer id_command, String status) {
        this.id_transaction = id_transaction;
        this.id_command = id_command;
        this.status = status;
    }

    public Transaction(Integer id, String id_transaction, Integer id_command, String status) {
        this.id = id;
        this.id_transaction = id_transaction;
        this.id_command = id_command;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public Integer getId_command() {
        return id_command;
    }

    public void setId_command(Integer id_command) {
        this.id_command = id_command;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", id_transaction=" + id_transaction + ", id_command=" + id_command + ", status=" + status + '}';
    }
    
     
     
     
}
