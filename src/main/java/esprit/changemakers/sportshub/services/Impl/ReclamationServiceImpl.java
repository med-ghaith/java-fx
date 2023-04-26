package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.services.IReclamationService;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import esprit.changemakers.sportshub.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReclamationServiceImpl implements IReclamationService<Reclamation> {
    Connection con ;
    public ReclamationServiceImpl() {
        this.con = DataSource.getInstance().getCnx();
    }


    public void addReclamation(Reclamation t) {


        try {
            String req = "INSERT INTO `reclamation` (`user_id`, `type_reclamation`, `object`, `description`) VALUES ( ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setString(2, t.getTypeReclamation());
            ps.setString(3, t.getObject());
            ps.setString(4, t.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public boolean deleteReclamation(Reclamation t) {
        boolean del = false;
        try {
            String req = "delete from reclamation where id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getId());
            if( ps.executeUpdate()==1)
                del= true;

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return del;
    }


    public boolean updateReclamation(Reclamation t) {
        boolean upd = false;
        try {
            String req = "update reclamation set user_id = ?, type_reclamation = ?, object = ? , description = ? ,status = ? where id = ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setString(2, t.getTypeReclamation());
            ps.setString(3, t.getObject());
            ps.setString(4, t.getDescription());
            ps.setString(5, t.getStatus());
            ps.setInt(6, t.getId());

            if( ps.executeUpdate()==1)
                upd= true;

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return upd;
    }

    public boolean updateReclamationUser(Reclamation t) {
        boolean upd = false;
        try {
            String req = "update reclamation set user_id = ?, type_reclamation = ?, object = ? , description = ?  where id = ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, t.getIdUser());
            ps.setString(2, t.getTypeReclamation());
            ps.setString(3, t.getObject());
            ps.setString(4, t.getDescription());
            ps.setInt(5, t.getId());

            if( ps.executeUpdate()==1)
                upd= true;

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return upd;
    }


    public ObservableList<Reclamation> getAllReclamation() {
        ObservableList<Reclamation> list=FXCollections.observableArrayList();
        try {
            String req = "select * from reclamation";
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                int id=rs.getInt("id");
                String typeReclamation=rs.getString("type_reclamation");
                Date dateReclamation=rs.getDate("reclamation_date");
                String status=rs.getString("status");
                String objet=rs.getString("object");
                String description=rs.getString("description");
                int id_user=rs.getInt("user_id");
                Reclamation r=new Reclamation(id, id_user, dateReclamation ,status, typeReclamation,objet,description);
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public ObservableList<Reclamation> getReclamationByUser(int idUser) {
        ObservableList<Reclamation> list=FXCollections.observableArrayList();
        try {
            String req = "select * from reclamation where user_id="+idUser;
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                int id=rs.getInt("id");
                String typeReclamation=rs.getString("type_reclamation");
                Date dateReclamation=rs.getDate("reclamation_date");
                String status=rs.getString("status");
                String objet=rs.getString("object");
                String description=rs.getString("description");
                int id_user=rs.getInt("user_id");
                Reclamation r=new Reclamation(id, id_user, dateReclamation ,status, typeReclamation,objet,description);
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ObservableList<Reclamation> readAllByStatus(String s) {
        ObservableList<Reclamation> list=FXCollections.observableArrayList();
        try {
            String req = "select * from reclamation where status="+s;
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                int id=rs.getInt("id");
                String typeReclamation=rs.getString("type_reclamation");
                Date dateReclamation=rs.getDate("reclamation_date");
                String status=rs.getString("status");
                String objet=rs.getString("object");
                String description=rs.getString("description");
                int id_user=rs.getInt("user_id");
                Reclamation r=new Reclamation(id, id_user, dateReclamation ,status, typeReclamation,objet,description);
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ObservableList<Reclamation> readAllByStatusUser(String s,int userId) {
        ObservableList<Reclamation> list=FXCollections.observableArrayList();
        try {
            String req = "select * from reclamation where status='"+s+"' AND user_id='"+userId+"';";
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                int id=rs.getInt("id");
                String typeReclamation=rs.getString("type_reclamation");
                Date dateReclamation=rs.getDate("reclamation_date");
                String status=rs.getString("status");
                String objet=rs.getString("object");
                String description=rs.getString("description");
                int id_user=rs.getInt("user_id");
                Reclamation r=new Reclamation(id, id_user, dateReclamation ,status, typeReclamation,objet,description);
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int get_Number_Reclamation() {
        int Message_Number=0;
        try {
            String req ="SELECT COUNT(*) FROM `reclamation` ";
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs=ps.executeQuery(req);
            while (rs.next()) {
                Message_Number = rs.getInt(1);
            }
            return Message_Number;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }


        return Message_Number;
    }

    public void changeReclamationStatus(){
        try {

        } catch (Exception e) {
        }
    }

    public Reclamation getReclamation(int idRec){
        Reclamation r = new Reclamation();
        try {
            String req = "select * from reclamation where id='"+idRec+"';";
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                int id=rs.getInt("id");
                String typeReclamation=rs.getString("type_reclamation");
                Date dateReclamation=rs.getDate("reclamation_date");
                String status=rs.getString("status");
                String objet=rs.getString("object");
                String description=rs.getString("description");
                int id_user=rs.getInt("user_id");
                r=new Reclamation(id, id_user, dateReclamation ,status, typeReclamation,objet,description);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
}
