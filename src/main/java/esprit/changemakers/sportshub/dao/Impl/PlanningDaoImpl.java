package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IPlanningDao;
import esprit.changemakers.sportshub.entities.Planning;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PlanningDaoImpl implements IPlanningDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public PlanningDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
    }
    @Override
    public Planning save(Planning entity) {
        String req = "INSERT INTO `planning` (`id`,`user_id`,`date`) VALUES (?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, entity.getId_planning());

            preparedStatement.setDate(3,Date.valueOf(entity.getPlanning_date()));
            preparedStatement.setInt(2, entity.getId_user());
            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId_planning(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(Planning entity) {
        String req ="update planning set user_id=?,date=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);

            preparedStatement.setDate(2,Date.valueOf(entity.getPlanning_date()));
            preparedStatement.setInt(1,entity.getId_user());
            preparedStatement.setInt(3,entity.getId_planning());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id_planning) {
        String req ="delete from planning where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_planning);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Planning> getAll() {
        String req = "select * from planning";
        ObservableList<Planning> planning = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Planning p1 = new Planning(rs.getInt(1),rs.getInt(2),rs.getDate(3).toLocalDate());
                planning.add(p1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planning;
    }

    @Override
    public Planning getById(int id_planning) {
        String req = "select * from planning where id=?";

       Planning e=null;

        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id_planning);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()) {
                e = new Planning();
                e.setId_planning(rs.getInt("id"));
                e.setId_user(rs.getInt("user_id"));
                e.setPlanning_date(rs.getDate("date").toLocalDate());

            }


        }catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
       // if (e==null) throw new RuntimeException("Planning"+id_planning+ " introuvable");
        return e ;

    }

    @Override
    public boolean find(Planning planning) {
        if (planning.getId_planning() !=0 ){
            String req = "select * from planning where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,planning.getId_planning());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            String req = "select * from planning where user_id=?  and date=? ";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setDate(2,Date.valueOf(planning.getPlanning_date()));
                preparedStatement.setInt(1,planning.getId_user());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Planning findOne(Planning planning) {
        String req = "select * from planning where user_id=? and date=?  ";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,planning.getId_user());
            preparedStatement.setDate(2,Date.valueOf(planning.getPlanning_date()));

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                planning = getById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planning;    }
}
