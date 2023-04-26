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
public class Cart {
    private int id ;
    private int product_id ;
    private int user_id ;
     private int quantity ;
     private Float price ;
    private Product product ;

    public Cart() {
    }

    public Cart(int product_id, int user_id, int quantity, Float price) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Cart(int id, int product_id, int user_id, int quantity, Float price) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Cart(int id, int product_id, int user_id, int quantity, Float price, Product product) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public int getquantity() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
