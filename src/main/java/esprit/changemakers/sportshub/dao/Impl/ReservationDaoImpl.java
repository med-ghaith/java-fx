package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IReservationDao;
import esprit.changemakers.sportshub.entities.Reservation;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ReservationDaoImpl implements IReservationDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public ReservationDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
    }

    public Reservation save(Reservation entity) {
        String req = "INSERT INTO `reservation` (`event_id`, `user_id`,`status`) VALUES (?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,entity.getId_event());
            preparedStatement.setInt(2,entity.getId_user());
            preparedStatement.setString(3, entity.getStatus());

            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId_reservation(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(Reservation entity) {
        String req = "update reservation set event_id=?,user_id=?,status=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1, entity.getId_event());
            preparedStatement.setInt(2, entity.getId_user());
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setInt(4,entity.getId_reservation());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id_reservation) {
        String req ="delete from reservation where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_reservation);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Reservation> getAll() {
        String req = "select * from reservation";
        ObservableList<Reservation> reservation = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Reservation r1 = new Reservation(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4));
                reservation.add(r1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public Reservation getById(int id_reservation) {
        String req = "select * from reservation where id=?";
        Reservation e = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_reservation);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()) {
                e = new Reservation();
                e.setId_reservation(rs.getInt("id"));
                e.setId_event(rs.getInt("event_id"));
                e.setId_user(rs.getInt("user_id"));
                e.setStatus(rs.getString("status"));
            }
        }catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        //if (e==null) throw new RuntimeException("reservation"+id_reservation+ " introuvable");
        return e ;
    }

    @Override
    public boolean find(Reservation reservation) {
        if (reservation.getId_reservation() !=0 ){
            String req = "select * from reservation where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,reservation.getId_event());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from reservation where event_id=? and user_id=? and status=? ";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,reservation.getId_event());
                preparedStatement.setInt(2,reservation.getId_user());
                preparedStatement.setString(3,reservation.getStatus());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;


    }

    @Override
    public Reservation findOne(Reservation reservation) {
        String req = "select * from reservation where event_id=? and user_id=? and status=?  ";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,reservation.getId_event());
            preparedStatement.setInt(2,reservation.getId_user());
            preparedStatement.setString(3,reservation.getStatus());
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                reservation = getById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }
}
