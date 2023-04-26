package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.entities.Comment;
import esprit.changemakers.sportshub.services.ICommentService;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentServiceImpl implements ICommentService<Comment> {

    Connection con = null;
    public CommentServiceImpl() {
        this.con = DataSource.getInstance().getCnx();
    }

    public void addCommentCoach(Comment t) {
        try {
            String req = "INSERT INTO `comment` (`user_id`, `coach_id`, `text`) VALUES ( ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdCoach());
            ps.setString(3, t.getCommentText());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("error ajout comment to coach");
        }
    }

    public void addCommentGym(Comment t) {
        try {
            String req = "INSERT INTO `comment` (`user_id`, `gym_id`, `text`) VALUES ( ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdGym());
            ps.setString(3, t.getCommentText());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error ajout comment to gym");
        }
    }


    public void updateCommentCoach(Comment t) {
        try {
            String req = "update comment set user_id= ? , coach_id= ? , text= ? where id= ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdCoach());
            ps.setString(3, t.getCommentText());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updated succefully");
        }
    }


    public void updateCommentGym(Comment t) {
        try {
            String req = "update comment set user_id= ? , gym_id= ? , text= ? where id= ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setInt(2, t.getIdGym());
            ps.setString(3, t.getCommentText());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updated succefully");
        }
    }


    public ObservableList<Comment> getAllCommentsByGym(int idGym) {
        ObservableList<Comment> list = FXCollections.observableArrayList();
        try {
            String req = "select id, user_id, text, date from comment where gym_id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idGym);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Comment c = new Comment(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getDate(4));
                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return list;
    }


    public ObservableList<Comment> getAllCommentsByCoach(int idCoach) {
        ObservableList<Comment> list = FXCollections.observableArrayList();
        try {
            String req = "select id, user_id, text, date from comment where coach_id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idCoach);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Comment c = new Comment(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getDate(4));
                list.add(c);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return list;
    }


    public Comment getCommentByUserByCoach(int idUser, int idCoach) {
        Comment c = new Comment();
        try {
            String req = "select id, text, date from comment where user_id = ? and coach_id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idUser);
            ps.setInt(2, idCoach);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                c = new Comment(rs.getInt(1), rs.getString(2),rs.getDate(3));
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }


    public Comment getCommentByUserByGym(int idUser, int idGym) {
        Comment c = new Comment();
        try {
            String req = "select id, text, date from comment where user_id = ? and gym_id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idUser);
            ps.setInt(2, idGym);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                c = new Comment(rs.getInt(1), rs.getString(2),rs.getDate(3));
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }


    public void deleteCommentCoach(Comment t) {
        try {
            String req = "delete from comment where id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error");
        }
    }


    public void deleteCommentGym(Comment t) {
        try {
            String req = "delete from comment where id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
