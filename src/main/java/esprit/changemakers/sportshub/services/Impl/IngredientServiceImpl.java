package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IIngredientDao;
import esprit.changemakers.sportshub.dao.Impl.IngredientDaoImpl;
import esprit.changemakers.sportshub.entities.Ingredient;
import esprit.changemakers.sportshub.services.IIngredientService;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class IngredientServiceImpl implements IIngredientService {
    IIngredientDao ingredientDao;
    public IngredientServiceImpl() {
        ingredientDao = new IngredientDaoImpl();
    }

    public Ingredient create(Ingredient entity) {
        if(!ingredientDao.find(entity)){
            return ingredientDao.save(entity);
        }else {
            System.out.println("Ingrédient "+entity.getName()+" déjà existant");
            return ingredientDao.findOne(entity);
        }
    }

    public void update(Ingredient entity) {
        ingredientDao.update(entity);
    }

    public void remove(int id) {
        ingredientDao.deleteById(id);
    }

    public Ingredient getById(int id) {
        return ingredientDao.getById(id);
    }

    public ObservableList<Ingredient> getAll() {
        return ingredientDao.getAll();
    }
}
