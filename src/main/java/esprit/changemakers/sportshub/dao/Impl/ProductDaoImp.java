package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IProductDao;
import esprit.changemakers.sportshub.entities.Cart;
import esprit.changemakers.sportshub.entities.Product;
import esprit.changemakers.sportshub.entities.Product.Category;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImp implements IProductDao {



    Connection cnx = DataSource.getInstance().getCnx();


    public void addProduit(Product P) {

        try {
            String req = "INSERT INTO product (name,image_url,price,description,category) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, P.getName());
            ps.setString(2, P.getImage_url());
            ps.setFloat(3, P.getPrice());
            ps.setString(4, P.getDescription());
            ps.setString(5, P.getCategory().name());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Product> listProduit() {
        List<Product> prod = new ArrayList<Product>();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Id"));
                p.setName(rs.getString("Name"));
                p.setImage_url(rs.getString("image_Url"));
                p.setPrice(rs.getFloat("price"));
                p.setDescription(rs.getString("Description"));

              //   Category.valueOf(rs.getString(20));

               p.setCategory(Category.valueOf(rs.getString("Category")));
                prod.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prod;
    }

    public List<Product> ProduitParNom(String MC) {
        List<Product> prod = new ArrayList<Product>();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product where name like ?");
            ps.setString(1, "%" + MC + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("Name"));
                p.setImage_url(rs.getString("image_Url"));
                p.setPrice(rs.getFloat("price"));
                p.setDescription(rs.getString("Description"));

                p.setCategory(Category.valueOf(rs.getString("Category")));

                prod.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prod;
    }


    public List<Product> ProduitParcategorie(String cat) {
        List<Product> prod = new ArrayList<Product>();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product where Category like ?");
            ps.setString(1, "%" + cat + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("Name"));
                p.setImage_url(rs.getString("image_Url"));
                p.setPrice(rs.getFloat("price"));
                p.setDescription(rs.getString("Description"));

                p.setCategory(Category.valueOf(rs.getString("Category")));

                prod.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prod;
    }

    public List<Product> ProduitParPrix(Float Price) {

        List<Product> prod = new ArrayList<Product>();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product where Price =?");
            ps.setFloat(1, Price);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setImage_url(rs.getString("image_Url"));
                p.setPrice(rs.getFloat("Price"));
                p.setDescription(rs.getString("Description"));
                p.setCategory(Category.valueOf(rs.getString("Category")));
                prod.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prod;

    }

    public Product getProduitById(int Id_product) {

        Product p = null;

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product where id =?");
            ps.setInt(1, Id_product);
            ResultSet rs = ps.executeQuery();
            if (rs.next());
            p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setImage_url(rs.getString("image_Url"));
            p.setPrice(rs.getFloat("Price"));
            p.setDescription(rs.getString("Description"));
            p.setCategory(Category.halters);

            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (p == null) {
           // throw new RuntimeException("Produit" +  + " introuvable");
        }
        return p;

    }

    public void UpdateProduit(Product P) {
        try {
            PreparedStatement ps = cnx.prepareStatement("update product set name=?,image_url=?,Price=?,Description=? where id=?");


            ps.setString(1, P.getName());
            ps.setString(2, P.getImage_url());
            ps.setFloat(3, P.getPrice());
            ps.setString(4, P.getDescription());
           // ps.setString(5, P.getCategory().name());
            ps.setInt(5, P.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void deleteProduit(int id) {
        try {
            PreparedStatement ps = cnx.prepareStatement("delete from product where id=?");

            ps.setInt(1,id);
           ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 public List<Product> listProduitall() {
    ObservableList<Product> listprd =FXCollections.observableArrayList();

        try {
            PreparedStatement ps = cnx.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("Id"));
                p.setName(rs.getString("Name"));
                p.setImage_url(rs.getString("image_Url"));
                p.setPrice(rs.getFloat("price"));
                p.setDescription(rs.getString("Description"));

              //   Category.valueOf(rs.getString(20));

               p.setCategory(Category.valueOf(rs.getString("Category")));
                listprd.add(p);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listprd;
    }
    public List<Cart> product_Cart(int user_id) {
        List<Cart> cart = new ArrayList<Cart>();

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
                cart.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cart;
    }
   

}
