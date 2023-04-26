package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IEquipmentDao;
import esprit.changemakers.sportshub.dao.IExerciceDao;
import esprit.changemakers.sportshub.dao.Impl.EquipmentDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.ExerciceDaoImpl;
import esprit.changemakers.sportshub.entities.*;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.IMuscleService;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Jozef
 */
public class ExerciceServiceImpl implements IExerciceService {
    IExerciceDao exerciceDao;
    IMuscleService muscleDao;
    IEquipmentDao equipmentDao;

    public ExerciceServiceImpl() {
        exerciceDao = new ExerciceDaoImpl();
        muscleDao = new MuscleServiceImpl();
        equipmentDao = new EquipmentDaoImpl();
    }

    public Exercice create(Exercice entity, List<Muscle> muscles, List<Equipment> equipments) {
        if(!exerciceDao.find(entity)){
            entity = exerciceDao.save(entity);
            for (Muscle muscle : muscles) {
                entity = exerciceDao.addMuscle(muscle.getId(),entity.getId());
            }
            for (Equipment equipment : equipments) {
                entity = exerciceDao.addEquipment(equipment.getId(),entity.getId());
            }
        }else {
            System.out.println("Exercice "+ entity.getName() +" déjà existant");
            entity = exerciceDao.findOne(entity);
            entity.getMuscles().addAll(exerciceDao.getExerciceMuscles(entity));
            entity.getEquipments().addAll(exerciceDao.getExerciceEquipment(entity));
        }
        return entity;
    }

    @Override
    public void deleteEquipmentFromExercice(Exercice exercice,Equipment equipment) {
        exerciceDao.deleteExerciceEquipment(exercice,equipment);
    }

    public Exercice create(Exercice entity) {
        return null;
    }

    public void update(Exercice entity) {
        exerciceDao.update(entity);
    }

    public void remove(int id) {
        exerciceDao.deleteById(id);
    }

    public Exercice getById(int id) {
        return exerciceDao.getById(id);
    }

    public ObservableList<Exercice> getAll() {
        ObservableList<Exercice> exercices = exerciceDao.getAll();
        for (Exercice exercice : exercices) {
            exercice.getMuscles().addAll(exerciceDao.getExerciceMuscles(exercice));
            exercice.getEquipments().addAll(exerciceDao.getExerciceEquipment(exercice));
        }
        return exercices;
    }
}
