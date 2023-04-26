package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IEquipmentDao;
import esprit.changemakers.sportshub.dao.IExerciceDao;
import esprit.changemakers.sportshub.dao.IMuscleDao;
import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Muscle;
import esprit.changemakers.sportshub.entities.Recipe;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jozef
 */
public class ExerciceDaoImpl implements IExerciceDao {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    IEquipmentDao equipmentDao;
    IMuscleDao muscleDao;

    public ExerciceDaoImpl() {
        con = DataSource.getInstance().getCnx();
        equipmentDao = new EquipmentDaoImpl();
        muscleDao = new MuscleDaoImpl();
    }

    public Exercice save(Exercice entity) {
        String req = "insert into exercice (name, image_url, description, difficulty_level, number_of_sets, number_of_repetition, rest_time) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getDescription());
            preparedStatement.setString(4,entity.getDifficultyLevel().name());
            preparedStatement.setInt(5,entity.getNumberOfSets());
            preparedStatement.setInt(6,entity.getNumberOfRepetition());
            preparedStatement.setString(7,entity.getRestTime());

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

    public void update(Exercice entity) {
        String req = "update exercice set name=?, image_url=?, description=?, difficulty_level=?, number_of_sets=?, number_of_repetition=?, rest_time=? where id=?";

        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getDescription());
            preparedStatement.setString(4,entity.getDifficultyLevel().name());
            preparedStatement.setInt(5,entity.getNumberOfSets());
            preparedStatement.setInt(6,entity.getNumberOfRepetition());
            preparedStatement.setString(7,entity.getRestTime());
            preparedStatement.setInt(8,entity.getId());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req = "delete from exercice where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public ObservableList<Exercice> getAll() {
        String req ="select * from exercice";
        ObservableList<Exercice> exercices = FXCollections.observableArrayList();
        Exercice exercice = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                exercice = new Exercice(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        Exercice.Difficulty.valueOf(rs.getString(5)),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8));
                exercices.add(exercice);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return exercices;
    }

    public Exercice addMuscle(int idMuscle, int idExercice) {
        String req = "insert into exercice_muscle (exercice_id, muscle_id) VALUES (?,?)";
        Exercice exercice = new Exercice();

        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,idExercice);
            preparedStatement.setInt(2,idMuscle);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        exercice = getById(idExercice);
        exercice.getMuscles().add(muscleDao.getById(idMuscle));

        return exercice;
    }

    public List<Muscle> getExerciceMuscles(Exercice exercice) {
        String req = "select * from exercice_muscle where exercice_id=?";
        List<Muscle> muscles = new ArrayList<Muscle>();
        Muscle muscle = new Muscle();
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,exercice.getId());

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                muscle = muscleDao.getById(rs.getInt(2));
                muscles.add(muscle);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return muscles;
    }

    public Exercice addEquipment(int idEquipment, int idExercice) {
        String req = "insert into equipment_exercice (equipment_id, exercice_id) VALUES (?,?)";
        Exercice exercice = new Exercice();
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,idEquipment);
            preparedStatement.setInt(2,idExercice);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        exercice = getById(idExercice);
        exercice.getEquipments().add(equipmentDao.getById(idEquipment));

        return exercice;
    }

    public List<Equipment> getExerciceEquipment(Exercice exercice) {
        String req = "select * from equipment_exercice where exercice_id=?";
        List<Equipment> equipments = new ArrayList<Equipment>();
        Equipment equipment = new Equipment();
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,exercice.getId());

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                equipment = equipmentDao.getById(rs.getInt(1));
                equipments.add(equipment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return equipments;
    }

    @Override
    public void deleteExerciceEquipment(Exercice exercice ,Equipment equipment) {
        String req = "delete from equipment_exercice where exercice_id=? AND equipment_id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,exercice.getId());
            preparedStatement.setInt(2,equipment.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean find(Exercice exercice) {
        if (exercice.getId() !=0 ){
            String req = "select * from exercice where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,exercice.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from exercice where name=? and image_url=? and description=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,exercice.getName());
                preparedStatement.setString(2,exercice.getImageUrl());
                preparedStatement.setString(3,exercice.getDescription());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public Exercice findOne(Exercice entity) {
        String req = "select * from exercice where name=? and image_url=? and description=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getDescription());
            rs = preparedStatement.executeQuery();
            if( rs.next()){
                entity = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }



    public Exercice getById(int id) {
        String req = "select * from exercice where id=?";
        Exercice exercice = null;
        Exercice exercice1 = new Exercice();
        exercice1.setId(id);
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                exercice = new Exercice(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        Exercice.Difficulty.valueOf(rs.getString(5)),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8));
        // HERE U NEED TO IMPLEMENT GETEXERCICESMUSCLE AND GETEXERCICEEQUIPMENT
                exercice.getEquipments().addAll(getExerciceEquipment(exercice1));
                exercice.getMuscles().addAll(getExerciceMuscles(exercice1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return exercice;
    }
}
