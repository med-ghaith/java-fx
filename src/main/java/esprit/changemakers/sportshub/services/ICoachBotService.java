package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Muscle;

import java.util.List;

/**
 * @author Jozef
 */
public interface ICoachBotService {

    public int extractHeight(String msg);

    public int extractWeight(String msg);

    public Equipment extractEquipment(String msg);

    public List<String> getAllInformations();

    public Muscle extractMuscle(String msg);

    public int extractPositiveOrNegativeResp(String msg);

    public List<Exercice> generateExercice(List<Equipment> equipment,List<Muscle> muscles);

    public int extractSalutationResp(String msg);
}
