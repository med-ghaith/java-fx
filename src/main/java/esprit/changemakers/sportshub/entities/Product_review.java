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
public class Product_review {
     private int id ; 
    private int product_id ; 
     private int user_id ; 
     private int stars ; 
     private String comment ; 

    public Product_review() {
    }

    public Product_review(int product_id, int user_id, int stars, String comment) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.stars = stars;
        this.comment = comment;
    }

    public Product_review(int id, int product_id, int user_id, int stars, String comment) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.stars = stars;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Product_review{" + "id=" + id + ", product_id=" + product_id + ", user_id=" + user_id + ", stars=" + stars + ", comment=" + comment + '}';
    }

   
}
