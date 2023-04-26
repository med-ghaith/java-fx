/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.entities;

import java.util.Date;

/**
 *
 * @author med
 */
public class Reclamation {
    private int id;
    private int idUser;
    private Date dateReclamation;
    private String status,typeReclamation,object,description;

    public Reclamation(int id, int idUser, Date dateReclamation, String status, String typeReclamation, String object, String description) {
        this.id = id;
        this.idUser = idUser;
        this.dateReclamation = dateReclamation;
        this.status = status;
        this.typeReclamation = typeReclamation;
        this.object = object;
        this.description = description;
    }

    public Reclamation(int idUser, String status, String typeReclamation, String object, String description) {
        this.idUser = idUser;
        this.dateReclamation = dateReclamation;
        this.status = status;
        this.typeReclamation = typeReclamation;
        this.object = object;
        this.description = description;
    }
    
    public Reclamation(int idUser, String typeReclamation, String object, String description) {
        this.idUser = idUser;
        this.dateReclamation = dateReclamation;
        this.typeReclamation = typeReclamation;
        this.object = object;
        this.description = description;
    }


   
    public Reclamation(int idUser, String typeReclamation, String object, String description, int id) {
        this.idUser = idUser;
        this.dateReclamation = dateReclamation;
        this.status = status;
        this.typeReclamation = typeReclamation;
        this.object = object;
        this.description = description;
        this.id = id;
    }



    public Reclamation() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", idUser=" + idUser + ", dateReclamation=" + dateReclamation + ", status=" + status + ", typeReclamation=" + typeReclamation + ", object=" + object + ", description=" + description + '}';
    }
    
    
}
