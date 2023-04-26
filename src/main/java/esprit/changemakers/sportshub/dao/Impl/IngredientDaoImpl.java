package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IIngredientDao;
import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.PrivateMessage;
import esprit.changemakers.sportshub.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @author Jozef
 */
public class IngredientDaoImpl implements IIngredientDao {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public IngredientDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
    }



    public Ingredient save(Ingredient entity) {
        String req = "insert into ingredients ( name, calories, total_fat, protein) VALUES (?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setInt(2,entity.getCalorie());
            preparedStatement.setInt(3,entity.getTotalFat());
            preparedStatement.setInt(4,entity.getProtein());
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

    public void update(Ingredient entity) {
        String req = "update ingredients set name=?,calories=?,total_fat=?,protein=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setInt(2,entity.getCalorie());
            preparedStatement.setInt(3,entity.getTotalFat());
            preparedStatement.setInt(4,entity.getProtein());

            preparedStatement.setInt(5,entity.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req ="delete from ingredients where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Ingredient> getAll() {
        String req = "select * from ingredients";
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Ingredient p1 = new Ingredient(rs.getInt(1)
                        ,rs.getString(2)
                        ,rs.getInt(3)
                        ,rs.getInt(5)
                        ,rs.getInt(4));
                ingredients.add(p1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredients;
    }

    public Ingredient getById(int id) {
        String req = "select * from ingredients where id=?";
        Ingredient ingredient = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            if ( rs.next()){
                ingredient = new Ingredient(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),rs.getInt(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredient;
    }

    public boolean find(Ingredient ingredient) {
        if (ingredient.getId() !=0 ){
            String req = "select * from ingredients where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,ingredient.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from ingredients where name=? and calories=? and total_fat=? and protein=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,ingredient.getName());
                preparedStatement.setInt(2,ingredient.getCalorie());
                preparedStatement.setInt(3,ingredient.getTotalFat());
                preparedStatement.setInt(4,ingredient.getProtein());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public Ingredient findOne(Ingredient ingredient) {
        String req = "select * from ingredients where name=? and calories=? and total_fat=? and protein=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,ingredient.getName());
            preparedStatement.setInt(2,ingredient.getCalorie());
            preparedStatement.setInt(3,ingredient.getTotalFat());
            preparedStatement.setInt(4,ingredient.getProtein());
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                ingredient = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredient;
    }
}
