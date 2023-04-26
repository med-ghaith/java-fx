/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.ICartDao;
import esprit.changemakers.sportshub.entities.Cart;
import esprit.changemakers.sportshub.entities.Product;
import esprit.changemakers.sportshub.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bilel abbassi
 */
public class CartDaoImp implements ICartDao {
    Connection cnx = DataSource.getInstance().getCnx();

    public void addCart(Cart c) {
        try {
            System.out.println(c.getId());
            String req = "SELECT * FROM cart WHERE product_id=? and user_id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, c.getProduct_id());
            ps.setInt(2, c.getUser_id());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("quantity") > 0) {
                String req2 = "update cart set quantity=? ,price=? where user_id =?";

                PreparedStatement ps2 = cnx.prepareStatement(req2);
                ps2.setInt(1, rs.getInt("quantity") + 1);

              ps2.setFloat(2, rs.getFloat("price") * rs.getInt("quantity"));
                ps2.setInt(3, rs.getInt("user_id"));
                ps2.executeUpdate();
            } else {
                String req2 = "INSERT INTO cart (Product_id,user_id,Price,quantity) VALUES (?,?,?,?)";
                PreparedStatement ps2 = cnx.prepareStatement(req2);
                ps2.setInt(1, c.getProduct_id());
                ps2.setInt(2, c.getUser_id());
                ps2.setFloat(3, c.getPrice());
                ps2.setInt(4, c.getquantity());

                ps2.executeUpdate();
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Cart> listCart() {
        List<Cart> cart = new ArrayList<Cart>();

        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("select * from cart");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Cart c = new Cart();
                c.setId(rs.getInt("Id"));
                c.setProduct_id(rs.getInt("Product_id"));
                c.setUser_id(rs.getInt("User_id"));
                c.setPrice(rs.getFloat("price"));
                c.setquantity(rs.getInt("quantity"));


                cart.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cart;
    }


    public List<Cart> CartParUser(int user_id) {
        List<Cart> cart = new ArrayList<Cart>();

        try {

            PreparedStatement ps = cnx.prepareStatement
                    ("select * from cart where user_id =?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setId(rs.getInt("Id"));
                c.setProduct_id(rs.getInt("Product_id"));
                c.setUser_id(rs.getInt("User_id"));
                c.setPrice(rs.getFloat("price"));
                c.setquantity(rs.getInt("quantity"));


                cart.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cart;
    }

    public int cunt_quantity(int user_id) {
            int total = 0;
        try {
            String cunt =
                    ("select count(*) as total from cart where user_id =?");

            PreparedStatement ps = cnx.prepareStatement(cunt);

            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");

            }
            ps.close();
            return total ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }
    public float TOTAL_PRICE(int user_id) {
        float total = 0;
        try {
            String cunt =
                    ("SELECT SUM(price) as total FROM `cart` WHERE user_id = ?");

            PreparedStatement ps = cnx.prepareStatement(cunt);

            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");

            }
            ps.close();
            return total ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }
    public int quantity_product(int Product_id) {
        int total = 0 ;
        try {
            String cunt = ("SELECT quantity as total FROM cart c,product p WHERE c.product_id = p.id AND product_id=?");

            PreparedStatement ps = cnx.prepareStatement(cunt);

            ps.setInt(1, Product_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");

            }
            ps.close();
            return total ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }



    public Cart getCartById(int id) {
        Cart c = null;

        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("select * from cart where Id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) ;
            c = new Cart();
            c.setId(rs.getInt("Id"));
            c.setProduct_id(rs.getInt("Product_id"));
            c.setUser_id(rs.getInt("User_id"));
            c.setPrice(rs.getFloat("price"));
            c.setquantity(rs.getInt("quantity"));


            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c == null) throw new RuntimeException("cart" + id + " introuvable");
        return c;
    }

    public void UpdateCart(Cart c) {
        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("update cart set product_id=?,user_id=?,Price=?,quantity=? where id =?");

            ps.setInt(1, c.getProduct_id());

            ps.setInt(1, c.getUser_id());


            ps.setFloat(3, c.getPrice());
            ps.setInt(1, c.getquantity());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void deleteCart(int id) {
        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("delete from cart  where id=?");


            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void deleteCartuser (int user_id) {
        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("delete from cart  where user_id=?");


            ps.setInt(1, user_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void deleteCartProduct (int Product_id ) {
        try {
            PreparedStatement ps = cnx.prepareStatement
                    ("delete from cart  where Product_id=?");


            ps.setInt(1, Product_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public List<Product> product_Cart(int user_id) {
        List<Product> cart = new ArrayList<Product>();

        try {

            PreparedStatement ps = cnx.prepareStatement
                    ("select p.id as prodid,c.id,p.name,p.image_url,p.price,c.quantity from cart c JOIN product p on p.id = c.product_id and user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                Product p =new Product();
                p.setId(rs.getInt("prodid"));
                p.setName(rs.getString("Name"));
                p.setImage_url(rs.getString("Image_url"));
                p.setPrice(rs.getInt("price"));
                c.setId(rs.getInt("id"));
                c.setPrice(rs.getFloat("price"));
                c.setquantity(rs.getInt("quantity"));
                c.setProduct(p);
                cart.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cart;
    }

}
