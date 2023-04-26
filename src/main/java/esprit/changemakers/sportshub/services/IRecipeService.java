package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Recipe;

import java.util.List;

/**
 * @author Jozef
 */
public interface IRecipeService extends IGenericService<Recipe>{

    public Recipe create(Recipe entity, List<Ingredient> ingredients);
}
