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
public class Comment {
    
    private int id;
    private int idUser;
    private int idCoach;
    private int idGym;
    private Date date;
    private String commentText;
    
    public Comment(int idUser, int idCoach, String commentText) {
        this.idUser = idUser;
        this.idCoach = idCoach;
        this.commentText = commentText;
    }

    public Comment(int idUser, String commentText, int idGym) {
        this.idUser = idUser;
        this.idGym = idGym;
        this.commentText = commentText;
    }


    
    public Comment(int idUser, int idGym, String commentText, int id) {
        this.idUser = idUser;
        this.idGym = idGym;
        this.commentText = commentText;
        this.id=id;
    }

    public Comment(int id, int idUser, int idCoach, String commentText) {
        this.idUser = idUser;
        this.idCoach = idCoach;
        this.commentText = commentText;
        this.id=id;
    }

    public Comment(int id, int idUser, int idCoach, int idGym, Date date, String commentText) {
        this.id = id;
        this.idUser = idUser;
        this.idCoach = idCoach;
        this.idGym = idGym;
        this.date = date;
        this.commentText = commentText;
    }

    public Comment(int id, int idUser, String commentText, Date date ) {
        this.id = id;
        this.idUser = idUser;
        this.date = date;
        this.commentText = commentText;
    }

    public Comment() {
    }
    

    public Comment(int id, String commentText, Date date) {
        this.id = id;
        this.date = date;
        this.commentText = commentText;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idCoach=" + idCoach +
                ", idGym=" + idGym +
                ", date=" + date +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
