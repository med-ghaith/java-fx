package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IGenericDao;
import esprit.changemakers.sportshub.dao.IProduct_reviewDao;
import esprit.changemakers.sportshub.entities.Product_review;
import esprit.changemakers.sportshub.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;

import java.util.List;

public class Product_reviewDaoIml implements IProduct_reviewDao {


    Connection cnx = DataSource.getInstance().getCnx();


    public void addReview(Product_review R) {
        try {
            String req = "INSERT INTO product_review (product_id,user_id,stars,Comment) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1,R.getProduct_id());
            ps.setInt(2,R.getUser_id());
            ps.setInt(3,R.getStars());
            ps.setString(4,R.getComment());


            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    public List<Product_review> listReviewProduit() {
        List<Product_review> Revprod=new ArrayList<Product_review>();

        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("select * from product_review");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Product_review rp=new Product_review();
                rp.setId(rs.getInt("id"));
                rp.setProduct_id(rs.getInt("Product_id"));
                rp.setUser_id(rs.getInt("User_id"));
                rp.setStars(rs.getInt("Stars"));
                rp.setComment(rs.getString("Comment"));


                Revprod.add(rp);
            }
            ps.close();

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Revprod ;
    }


    public List<Product_review> ReviewParUser(int id_user) {
        List<Product_review> REV=new ArrayList<Product_review>();

        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("select * from product_review where user_id =?");
            ps.setInt(1,id_user );
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Product_review rp=new Product_review();
                rp.setId(rs.getInt("id"));
                rp.setProduct_id(rs.getInt("Product_id"));
                rp.setUser_id(rs.getInt("User_id"));
                rp.setStars(rs.getInt("Stars"));
                rp.setComment(rs.getString("Comment"));

                REV.add(rp);
            }
            ps.close();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return REV;

    }


    public List<Product_review> ReviewParProduit(int Id_product) {
        List<Product_review> REV=new ArrayList<Product_review>();

        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("select * from product_review where product_id =?");
            ps.setInt(1,Id_product );
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Product_review rp=new Product_review();
                rp.setId(rs.getInt("id"));
                rp.setProduct_id(rs.getInt("Product_id"));
                rp.setUser_id(rs.getInt("User_id"));
                rp.setStars(rs.getInt("Stars"));
                rp.setComment(rs.getString("Comment"));

                REV.add(rp);
            }
            ps.close();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return REV;

    }

    public Product_review getReviewProduitById(int Id) {
        Product_review rp=null;

        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("select * from product_review where id=?");
            ps.setInt(1,Id);
            ResultSet rs=ps.executeQuery();
            if(rs.next());
            rp=new Product_review();
           rp.setId(rs.getInt("id"));
                rp.setProduct_id(rs.getInt("Product_id"));
                rp.setUser_id(rs.getInt("User_id"));
            rp.setStars(rs.getInt("Stars"));
            rp.setComment(rs.getString("Comment"));

            ps.close();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (rp==null) throw new RuntimeException("REVIEW"+Id+ " introuvable");
        return rp ;

    }


    public void UpdateReview(Product_review R){




        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("update product_review set Product_id=?,User_id=?,Stars=?,Comment=? where id =?");



            ps.setInt(1,R.getProduct_id());
            ps.setInt(2,R.getUser_id());
            ps.setInt(3,R.getStars());
            ps.setString(4,R.getComment());
            ps.setInt(5,R.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public void deleteReview(int id) {
        try {
            PreparedStatement ps=cnx.prepareStatement
                    ("delete from product_review  where ID=?");


            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
