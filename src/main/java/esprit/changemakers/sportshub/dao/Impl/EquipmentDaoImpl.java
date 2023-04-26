package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IEquipmentDao;
import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Jozef
 */
public class EquipmentDaoImpl implements IEquipmentDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public EquipmentDaoImpl() {
        con = DataSource.getInstance().getCnx();
    }

    public Equipment save(Equipment entity) {
        String req = "insert into equipment (name, image_url) VALUES (?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
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

    public void update(Equipment entity) {
        String req = "update equipment set name=?,image_url=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setInt(3,entity.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req ="delete from equipment where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Equipment> getAll() {
        String req = "select * from equipment";
        ObservableList<Equipment> equipments = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Equipment e1 = new Equipment(rs.getInt(1)
                        ,rs.getString(2)
                        ,rs.getString(3));

                equipments.add(e1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return equipments;
    }

    public Equipment getById(int id) {
        String req = "select * from equipment where id=?";
        Equipment equipment = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            if ( rs.next()){
                equipment = new Equipment(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return equipment;
    }

    public boolean find(Equipment equipment) {
        if (equipment.getId() !=0 ){
            String req = "select * from equipment where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,equipment.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from equipment where name=? and image_url=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,equipment.getName());
                preparedStatement.setString(2,equipment.getImageUrl());

                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public Equipment findOne(Equipment entity) {
        String req = "select * from equipment where name=? and image_url=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                entity = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }
}
