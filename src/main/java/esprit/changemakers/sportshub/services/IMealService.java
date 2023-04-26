package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Meal;
import esprit.changemakers.sportshub.entities.Recipe;

import java.util.List;

/**
 * @author Jozef
 */
public interface IMealService extends IGenericService<Meal> {

    public Meal create(Meal entity, List<Recipe> recipes);
}
