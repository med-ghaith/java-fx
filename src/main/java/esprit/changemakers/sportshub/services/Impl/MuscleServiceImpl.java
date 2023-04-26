package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IMuscleDao;
import esprit.changemakers.sportshub.dao.Impl.MuscleDaoImpl;
import esprit.changemakers.sportshub.entities.Muscle;
import esprit.changemakers.sportshub.services.IMuscleService;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class MuscleServiceImpl implements IMuscleService {
    IMuscleDao muscleDao;

    public MuscleServiceImpl() {
        muscleDao = new MuscleDaoImpl();
    }

    public Muscle create(Muscle entity) {
        if(!muscleDao.find(entity)){
            return muscleDao.save(entity);
        }else {
            System.out.println("Muscle "+entity.getName()+" déjà existant");
            return muscleDao.findOne(entity);
        }
    }

    public void update(Muscle entity) {
        muscleDao.update(entity);
    }

    public void remove(int id) {
        muscleDao.deleteById(id);
    }

    public Muscle getById(int id) {
        return muscleDao.getById(id);
    }

    public ObservableList<Muscle> getAll() {
        return muscleDao.getAll();

    }
}
