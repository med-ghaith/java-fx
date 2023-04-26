package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Muscle;

import java.util.List;

/**
 * @author Jozef
 */
public interface IExerciceDao extends IGenericDao<Exercice> {

    public Exercice addMuscle(int idMuscle, int idExercice);

    public List<Muscle> getExerciceMuscles(Exercice exercice);

    public Exercice addEquipment(int idEquipment,int idExercice);

    public List<Equipment> getExerciceEquipment(Exercice exercice);

    public void deleteExerciceEquipment(Exercice exercice ,Equipment equipment);
}
