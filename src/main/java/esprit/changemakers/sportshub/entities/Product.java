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
public class Product {
    private Integer id  ;
    private String name ;
    private String image_url	 ;
    private float price ;
    private String description ;
    public enum Category {
        proteins,
        halters
    }
    private Category category ;

    public Product() {
    }

    public Product(String name, String image_url, float price, String description, Category category) {
        this.name = name;
        this.image_url = image_url;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public Product(Integer id, String name, String image_url, float price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", image_url=" + image_url + ", price=" + price + ", description=" + description + ", category=" + category + '}';
    }

   




}
