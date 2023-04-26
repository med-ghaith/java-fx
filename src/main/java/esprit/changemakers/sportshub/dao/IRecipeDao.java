package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Meal;
import esprit.changemakers.sportshub.entities.Recipe;

import java.util.List;

/**
 * @author Jozef
 */
public interface IRecipeDao extends IGenericDao<Recipe> {

    public Recipe addIngredient(int igId,int recipeId);

    public List<Ingredient> getRecipeIngredients(Recipe recipe);
}
