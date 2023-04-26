package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IMealDao;
import esprit.changemakers.sportshub.dao.IRecipeDao;
import esprit.changemakers.sportshub.dao.Impl.MealDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.RecipeDaoImpl;
import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.entities.Meal;
import esprit.changemakers.sportshub.entities.Recipe;
import esprit.changemakers.sportshub.services.IMealService;
import esprit.changemakers.sportshub.services.IRecipeService;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Jozef
 */
public class MealServiceImpl implements IMealService {

    IMealDao mealDao;
    IRecipeDao recipeDao;
    IRecipeService recipeService;
    public MealServiceImpl() {
        mealDao = new MealDaoImpl();
        recipeDao = new RecipeDaoImpl();
        recipeService = new RecipeServiceImpl();
    }

    public Meal create(Meal entity, List<Recipe> recipes) {
        if(!mealDao.find(entity)){
            entity = mealDao.save(entity);
            for (Recipe recipe : recipes) {
                entity = mealDao.addRecipe(entity.getId(),recipe.getId());
            }
        }else {
            System.out.println("Meal "+ entity.getName() +" déjà existante");
            entity = mealDao.findOne(entity);
            entity.getRecipes().addAll(mealDao.getMealRecipes(entity));
        }
        return entity;
    }

    public Meal create(Meal entity) {
       return mealDao.save(entity);
    }

    public void update(Meal entity) {
        mealDao.update(entity);
    }

    public void remove(int id) {
        mealDao.deleteById(id);
    }

    public Meal getById(int id) {
        Meal meal = mealDao.getById(id);
        meal.getRecipes().addAll(mealDao.getMealRecipes(meal));
        return meal;
    }

    public ObservableList<Meal> getAll() {
        ObservableList<Meal> meals = mealDao.getAll();
        for (Meal meal : meals) {
            meal.getRecipes().addAll(mealDao.getMealRecipes(meal));
        }
        return meals;
    }


}
