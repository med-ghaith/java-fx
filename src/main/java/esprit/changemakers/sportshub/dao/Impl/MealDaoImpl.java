package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IMealDao;
import esprit.changemakers.sportshub.dao.IRecipeDao;
import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Meal;
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
public class MealDaoImpl implements IMealDao {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    IRecipeDao recipeDao;

    public MealDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
        recipeDao = new RecipeDaoImpl();
    }

    public Meal save(Meal entity) {
        String req = "insert into meal (name, image_url, description) VALUES (?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getDescription());

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

    public void update(Meal entity) {
        String req = "update meal set name=?,image_url=?,description=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);

            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getDescription());
            preparedStatement.setInt(4,entity.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req = "delete from meal where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Meal> getAll() {
        String req ="select * from meal";
        ObservableList<Meal> meals = FXCollections.observableArrayList();
        Meal meal = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                meal = new Meal(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                meals.add(meal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meals;
    }

    public Meal getById(int id) {
        String req = "select * from meal where id=?";
        Meal meal = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                meal = new Meal(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meal;
    }

    public boolean find(Meal meal) {
        if (meal.getId() !=0 ){
            String req = "select * from meal where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,meal.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from meal where name=? and description=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,meal.getName());
                preparedStatement.setString(2,meal.getDescription());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public Meal findOne(Meal meal) {
        String req = "select * from meal where name=? and description=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,meal.getName());
            preparedStatement.setString(2,meal.getDescription());

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                meal = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meal;
    }

    public Meal addRecipe(int idMeal, int idRecipe) {
        String req = "insert into recipe_meal (recipe_id, meal_id) VALUES (?,?)";
        Meal meal = new Meal();
        try {
            preparedStatement =con.prepareStatement(req);
            preparedStatement.setInt(1,idRecipe);
            preparedStatement.setInt(2,idMeal);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        meal = getById(idMeal);
        meal.getRecipes().add(recipeDao.getById(idRecipe));
        return meal;
    }

    public Meal addRecipes(int idMeal, List<Recipe> recipes) {
        Meal meal = getById(idMeal);
        meal.getRecipes().addAll(recipes);
        return meal;
    }

    public List<Recipe> getMealRecipes(Meal meal) {
        String req = "select * from recipe_meal where meal_id=?";
        List<Recipe> recipes = new ArrayList<Recipe>();
        Recipe recipe = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,meal.getId());

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                recipe = recipeDao.getById(rs.getInt(1));

                recipes.add(recipe);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recipes;
    }
}
