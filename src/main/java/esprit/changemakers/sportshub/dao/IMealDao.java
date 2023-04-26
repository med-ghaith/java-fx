package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Meal;
import esprit.changemakers.sportshub.entities.Recipe;

import java.util.List;

/**
 * @author Jozef
 */
public interface IMealDao extends IGenericDao<Meal> {

    public Meal addRecipe(int idMeal,int idRecipe);

    public Meal addRecipes(int idMeal, List<Recipe> recipes);

    public List<Recipe> getMealRecipes(Meal meal);
}
