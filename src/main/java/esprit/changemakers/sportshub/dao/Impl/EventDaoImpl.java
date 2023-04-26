package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EventDaoImpl implements IEventDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public EventDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
    }

    public Event save(Event entity) {
        String req = "insert into event ( start_date, end_date, description,planning_id,nombre_reservation,fees,category,image_url) VALUES (?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1,Date.valueOf(entity.getStart_date()));
            preparedStatement.setDate(2,Date.valueOf(entity.getEnd_date()));
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setInt(4, entity.getPlanning_id());
            preparedStatement.setInt(5, entity.getNombreReservation());
            preparedStatement.setInt(6, entity.getFees());
            preparedStatement.setString(7, entity.getCategory());
            preparedStatement.setString(8, entity.getImageUrl());
            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId_event(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }


    @Override
    public void update(Event entity) {
        String req = "update event set start_date=?,end_date=?,description=?,planning_id=?,nombre_reservation=?,fees=?,category=?,image_url=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setDate(1,Date.valueOf(entity.getStart_date()));
            preparedStatement.setDate(2,Date.valueOf(entity.getEnd_date()));
            preparedStatement.setString(3,entity.getDescription());
            preparedStatement.setInt(4,entity.getPlanning_id());
            preparedStatement.setInt(5, entity.getNombreReservation());
            preparedStatement.setInt(6, entity.getFees());
            preparedStatement.setString(7, entity.getCategory());
            preparedStatement.setString(8, entity.getImageUrl());
            preparedStatement.setInt(9,entity.getId_event());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(int id_event) {
        String req ="delete from event where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_event);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<Event> getAll() {
        String req = "select * from event";
        ObservableList<Event> event = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Event e1 = new Event (rs.getInt(1),rs.getDate(2).toLocalDate(),rs.getDate(3).toLocalDate(),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString(8),rs.getString(9));
                event.add(e1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public Event getById(int id_event) {
        String req = "select * from event where id=?";
        Event e = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_event);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()) {
                e = new Event();
                e.setId_event(rs.getInt("id"));
                e.setStart_date(rs.getDate("start_date").toLocalDate());
                e.setEnd_date(rs.getDate("end_date").toLocalDate());
                e.setDescription(rs.getString("description"));
                e.setPlanning_id(rs.getInt("planning_id"));
                e.setNombreReservation(rs.getInt("nombre_reservation"));
                e.setFees(rs.getInt("fees"));
                e.setCategory(rs.getString("category"));
                e.setImageUrl(rs.getString("image_url"));

            }

        }catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
       // if (e==null) throw new RuntimeException("Event"+id_event+ " introuvable");
        return e ;

    }

    @Override
    public boolean find(Event event) {
        if (event.getId_event() !=0 ){
            String req = "select * from event where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,event.getId_event());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from event where start_date=? and end_date=? and description=? and planning_id=? and nombre_reservation=? and fees=? and category=? and image_url=? ";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setDate(1,Date.valueOf(event.getStart_date()));
                preparedStatement.setDate(2,Date.valueOf(event.getEnd_date()));
                preparedStatement.setString(3,event.getDescription());
                preparedStatement.setInt(4,event.getPlanning_id());
                preparedStatement.setInt(5, event.getNombreReservation());
                preparedStatement.setInt(6, event.getFees());
                preparedStatement.setString(7, event.getCategory());
                preparedStatement.setString(8, event.getImageUrl());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Event findOne(Event event) {
        String req = "select * from event where start_date=? and end_date=? and description=? and planning_id=? and nombre_reservation=? and fees=? and category=? and image_url=? ";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setDate(1,Date.valueOf(event.getStart_date()));
            preparedStatement.setDate(2,Date.valueOf(event.getEnd_date()));
            preparedStatement.setString(3,event.getDescription());
            preparedStatement.setInt(4,event.getPlanning_id());
            preparedStatement.setInt(5, event.getNombreReservation());
            preparedStatement.setInt(6, event.getFees());
            preparedStatement.setString(7, event.getCategory());
            preparedStatement.setString(8, event.getImageUrl());
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                event = getById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
    }

