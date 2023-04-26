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
public class Command {
    private Integer id; 
    private String ref ; 
    private Integer user_id ; 

    private Float total_price ; 
     private String status ; 

    public Command() {
    }

    public Command(String ref, Integer user_id, Float total_price, String status) {
        this.ref = ref;
        this.user_id = user_id;
        this.total_price = total_price;
        this.status = status;
    }

    public Command(Integer id, String ref, Integer user_id, Float total_price, String status) {
        this.id = id;
        this.ref = ref;
        this.user_id = user_id;
        this.total_price = total_price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", user_id=" + user_id +
                ", total_price=" + total_price +
                ", status='" + status + '\'' +
                '}';
    }
}
