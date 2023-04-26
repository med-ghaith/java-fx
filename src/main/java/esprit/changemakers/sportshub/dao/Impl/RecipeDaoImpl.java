package esprit.changemakers.sportshub.dao.Impl;

import esprit.changemakers.sportshub.dao.IIngredientDao;
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
public class RecipeDaoImpl implements IRecipeDao {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    IIngredientDao ingredientDao = null;

    public RecipeDaoImpl() {
        this.con = DataSource.getInstance().getCnx();
        ingredientDao = new IngredientDaoImpl();
    }

    public Recipe save(Recipe entity) {
        String req = "insert into recipe (name, description, caloric_value) VALUES (?,?,?)";

        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getDescription());
            preparedStatement.setString(3,entity.getCaloricValue());

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

    public void update(Recipe entity) {
        String req = "update recipe set name=?,description=?,caloric_value=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);

            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getDescription());
            preparedStatement.setString(3,entity.getCaloricValue());
            preparedStatement.setInt(4,entity.getId());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req = "delete from recipe where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Recipe> getAll() {
        String req ="select * from recipe";
        ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        Recipe recipe = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                recipe = new Recipe(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                recipes.add(recipe);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recipes;
    }

    public Recipe getById(int id) {
        String req = "select * from recipe where id=?";
        Recipe recipe = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                recipe = new Recipe(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                recipe.getIngredients().addAll(getRecipeIngredients(recipe));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recipe;
    }

    public boolean find(Recipe recipe) {
        if (recipe.getId() !=0 ){
            String req = "select * from recipe where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,recipe.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from recipe where name=? and description=? and caloric_value=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,recipe.getName());
                preparedStatement.setString(2,recipe.getDescription());
                preparedStatement.setString(3,recipe.getCaloricValue());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public Recipe findOne(Recipe recipe) {
        String req = "select * from recipe where name=? and description=? and caloric_value=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,recipe.getName());
            preparedStatement.setString(2,recipe.getDescription());
            preparedStatement.setString(3,recipe.getCaloricValue());

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                recipe = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recipe;
    }

    public Recipe addIngredient(int igId,int recipeId) {
        String req = "insert into ingredients_recipe (ingredients_id, recipe_id) VALUES (?,?)";

        Recipe recipe = new Recipe();
        try {

            preparedStatement =con.prepareStatement(req);
            preparedStatement.setInt(1,igId);
            preparedStatement.setInt(2,recipeId);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        recipe = getById(recipeId);
        recipe.getIngredients().add(ingredientDao.getById(igId));
        return recipe;
    }

    public List<Ingredient> getRecipeIngredients(Recipe recipe) {
        String req = "select * from ingredients_recipe where recipe_id=?";
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ingredient = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,recipe.getId());

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                ingredient = ingredientDao.getById(rs.getInt(1));
                ingredients.add(ingredient);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredients;
    }
}
