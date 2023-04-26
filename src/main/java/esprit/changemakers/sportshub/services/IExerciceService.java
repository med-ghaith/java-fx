package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.*;

import java.util.List;

/**
 * @author Jozef
 */
public interface IExerciceService extends IGenericService<Exercice> {

    public Exercice create(Exercice entity, List<Muscle> muscles, List<Equipment> equipments);

    public void deleteEquipmentFromExercice(Exercice exercice, Equipment equipment);
}
