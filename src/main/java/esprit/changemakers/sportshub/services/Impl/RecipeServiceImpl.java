package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IIngredientDao;
import esprit.changemakers.sportshub.dao.IRecipeDao;
import esprit.changemakers.sportshub.dao.Impl.IngredientDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.RecipeDaoImpl;
import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Recipe;
import esprit.changemakers.sportshub.services.IRecipeService;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Jozef
 */
public class RecipeServiceImpl implements IRecipeService {
    IRecipeDao recipeDao = null;
    IIngredientDao ingredientDao =null;
    public RecipeServiceImpl() {
        recipeDao = new RecipeDaoImpl();
        ingredientDao = new IngredientDaoImpl();
    }

    public Recipe create(Recipe entity, List<Ingredient> ingredients) {
        if (!recipeDao.find(entity)){
            entity = recipeDao.save(entity);
            for (Ingredient ingredient : ingredients) {
                entity = recipeDao.addIngredient(ingredient.getId(), entity.getId());
            }
        }else {
            System.out.println("Recette "+ entity.getName() +" déjà existante");
            entity = recipeDao.findOne(entity);
            entity.getIngredients().addAll(recipeDao.getRecipeIngredients(entity));
        }


        return entity;
    }

    public Recipe create(Recipe entity) {
        return recipeDao.save(entity);
    }

    public void update(Recipe entity) {
        recipeDao.update(entity);
    }

    public void remove(int id) {
        recipeDao.deleteById(id);
    }

    public Recipe getById(int id) {
        Recipe recipe =recipeDao.getById(id);
        recipe.getIngredients().addAll(recipeDao.getRecipeIngredients(recipe));
        return recipe;
    }

    public ObservableList<Recipe> getAll() {
        ObservableList<Recipe> recipes = recipeDao.getAll();
        for (Recipe recipe : recipes) {
            recipe.getIngredients().addAll(recipeDao.getRecipeIngredients(recipe));
        }
        return recipes;
    }


}
