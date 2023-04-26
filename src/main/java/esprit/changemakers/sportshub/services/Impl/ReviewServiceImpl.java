package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.entities.Review;
import esprit.changemakers.sportshub.services.IReviewService;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewServiceImpl implements IReviewService<Review> {
    Connection con = null;

    public ReviewServiceImpl() {
        this.con = DataSource.getInstance().getCnx();
    }


    public void addReviewCoach(Review t) {
        try {
            // try {
            String req = "INSERT INTO `review` (`user_id`, `coach_id`, `rate`) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdCoach());
            ps.setFloat(3, t.getRate());
            ps.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("error ajout rate to coach");
//        }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void addReviewGym(Review t) {
        try {
            String req = "INSERT INTO `review` (`user_id`, `gym_id`, `rate`) VALUES ( ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdGym());
            ps.setFloat(3, t.getRate());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error ajout rate to gym");
        }
    }


    public void updateReviewCoach(Review t) {
        try {
            String req = "update review set user_id= ? , coach_id= ? , rate= ? where id= ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdCoach());
            ps.setFloat(3, t.getRate());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updated succefully");
        }
    }


    public void updateReviewGym(Review t) {
        try {
            String req = "update review set user_id= ? , gym_id= ? , rate= ? where id= ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdGym());
            ps.setFloat(3, t.getRate());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updated succefully");
        }
    }


    public ObservableList<Review> getAllReviewsByGym(int idGym) {
        ObservableList<Review> list = FXCollections.observableArrayList();
        try {
            String req = "select id, user_id, rate from review where gym_id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idGym);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()){
                Review c = new Review(rs.getInt(1), rs.getInt(2),rs.getFloat(3));
                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return list;
    }


    public ObservableList<Review> getAllReviewsByCoach(int idCoach) {
        ObservableList<Review> list =FXCollections.observableArrayList();
        try {
            String req = "select * from review where coach_id ="+idCoach;
            Statement s = con.createStatement();
            //PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setInt(1, idCoach);
            ResultSet rs = s.executeQuery(req);
            while (rs.next()){
                Review c = new Review(rs.getInt("id"), rs.getInt("user_id"),rs.getFloat("rate"));
                list.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }


    public Review getReviewByUserByCoach(int idUser, int idCoach) {
        Review c = new Review();
        try {
            String req = "select id_review, user_id, coach_id, rate from review where user_id = '"+idUser+"' and coach_id = '"+idCoach+"' ";
            Statement s = con.createStatement();
            //PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setInt(1, idUser);
            //ps.setInt(2, idCoach);
            ResultSet rs = s.executeQuery(req);
            while (rs.next()){
                c = new Review(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4));
            }
        } catch (Exception e) {
            Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return c;
    }


    public Review getReviewByUserByGym(int idUser, int idGym) {
        Review c = new Review();
        try {
            String req = "select id, user_id, gym_id, rate from review where user_id = '"+idUser+"' and gym_id = '"+idGym+"' ";
            Statement s = con.createStatement();
            //PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setInt(1, idUser);
            //ps.setInt(2, idCoach);
            ResultSet rs = s.executeQuery(req);
            while (rs.next()){
                c = new Review(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4));
            }
        } catch (Exception e) {
            Logger.getLogger(ReviewServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return c;

    }


    public void deleteReviewCoach(Review t) {
        try {
            String req = "delete from comment where id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error");
        }
    }


    public void deleteReviewGym(Review t) {
        try {
            String req = "delete from comment where id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public float getGymReview (int idGym) {
        float count =0.f;
        int n =0;
        try {
            String req ="SELECT AVG(rate), COUNT(id) FROM review where gym_id="+idGym;
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs=ps.executeQuery(req);
            while (rs.next()) {
                count = rs.getFloat(1);
                n = rs.getInt(2);
            }
            return count;
        } catch (Exception e) {
            System.out.println("error");
        }
        return count/n;
    }

    public float getCoachReview (int idCoach) {
        float count =0.f;
        int n =0;
        try {
            String req ="SELECT ROUND(SUM(rate), 2), COUNT(id) FROM review where coach_id="+idCoach;
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs=ps.executeQuery(req);
            while (rs.next()) {
                count = rs.getFloat(1);
                n = rs.getInt(2);

            }
            return count/n;
        } catch (Exception e) {
            System.out.println("error");
        }
        return count/n;
    }
}
