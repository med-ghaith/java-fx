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
public class Review {
    private int id;
    private float rate;
    private int idUser,idCoach,idGym;
    private Date date;

    public Review(int id,  int idUser, int idGym, float rate) {
        this.id = id;
        this.rate = rate;
        this.idUser = idUser;
        this.idGym = idGym;
    }

    public Review(  int idUser, int idCoach, float rate, int id) {
        this.id = id;
        this.rate = rate;
        this.idUser = idUser;
        this.idCoach = idCoach;
    }


    public Review(int id, float rate, int idUser, int idCoach, int idGym) {
        this.id = id;
        this.rate = rate;
        this.idUser = idUser;
        this.idCoach = idCoach;
        this.idGym = idGym;
    }

    

    public Review(int id, float rate) {
        this.id = id;
        this.rate = rate;
    }

    public Review( int idUser, int idCoach ,float rate) {
        this.idUser = idUser;
        this.idCoach = idCoach;
        this.rate = rate;
    }

    public Review( int idUser ,float rate, int idGym) {
        this.idUser = idUser;
        this.idGym = idGym;
        this.rate = rate;
    }
    
    

    public Review() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(int idCoach) {
        this.idCoach = idCoach;
    }

    public int getIdGym() {
        return idGym;
    }

    public void setIdGym(int idGym) {
        this.idGym = idGym;
    }

    @Override
    public String toString() {
        return "Review{" + "id=" + id + ", rate=" + rate + ", idUser=" + idUser + ", idCoach=" + idCoach + ", idGym=" + idGym + '}';
    }

    
    
    
}
