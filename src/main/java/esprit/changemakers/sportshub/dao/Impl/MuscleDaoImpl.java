package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IMuscleDao;
import esprit.changemakers.sportshub.entities.Muscle;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Jozef
 */
public class MuscleDaoImpl implements IMuscleDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public MuscleDaoImpl() {
        con = DataSource.getInstance().getCnx();
    }

    public Muscle save(Muscle entity) {
        String req = "insert into muscle ( name) VALUES (?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());

            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId(rs.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    public void update(Muscle entity) {
        String req = "update muscle set name=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());

            preparedStatement.setInt(2,entity.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req ="delete from muscle where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Muscle> getAll() {
        String req = "select * from muscle";
        ObservableList<Muscle> muscles = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Muscle p1 = new Muscle(rs.getInt(1)
                        ,rs.getString(2));
                muscles.add(p1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return muscles;
    }

    public Muscle getById(int id) {
        String req = "select * from muscle where id=?";
        Muscle muscle = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            if ( rs.next()){
                muscle = new Muscle(rs.getInt(1),
                        rs.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return muscle;
    }

    public boolean find(Muscle muscle) {
        if (muscle.getId() !=0 ){
            String req = "select * from muscle where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,muscle.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from muscle where name=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,muscle.getName());

                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public Muscle findOne(Muscle muscle) {
        String req = "select * from muscle where name=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,muscle.getName());

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                muscle = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return muscle;
    }
}
