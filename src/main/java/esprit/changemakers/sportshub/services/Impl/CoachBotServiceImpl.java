package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Muscle;
import esprit.changemakers.sportshub.services.APIs.ExcelApiImpl;
import esprit.changemakers.sportshub.services.ICoachBotService;
import esprit.changemakers.sportshub.services.IEquipmentService;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.IMuscleService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jozef
 */
public class CoachBotServiceImpl implements ICoachBotService {

    /**
     * return:
     * * -1 if we can't extract informations from the message
     * * -2 if the weight exceeded the norm
     * * -3 if the weight is less than the norm
     * * the weight if everything went well
     * */
    @Override
    public int extractWeight(String msg) {
        int wazn=-1;
        List<String> weight = ExcelApiImpl.readColumnByName("WAZN");
        weight = weight.stream().skip(1).collect(Collectors.toList());
        msg = msg.replaceAll("[^0-9]", "");
        if(msg.equals("")){
            return wazn;
        }
        String finalMsg = msg;
        if (weight.stream().anyMatch(e->e.equals(finalMsg))){
            System.out.println("Youzen "+msg+" kg");
            return Integer.parseInt(finalMsg);
        }else if(Integer.parseInt(finalMsg) > weight.stream().map(Integer::new).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).max().getAsInt()){
            return -2;
        }else if(Integer.parseInt(finalMsg) < weight.stream().map(Integer::new).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).min().getAsInt()){
            return -3;
        }
        return wazn;
    }


    /**
     * To upgrade later
     * for now it returns the equipment if it finds it
     * null if not
     * */
    @Override
    public Equipment extractEquipment(String msg) {
        List<String> equipments = ExcelApiImpl.readColumnByName("EQUIPMENT");
        IEquipmentService equipmentService = new EquipmentServiceImpl();
        String finalMsg1 = msg.toLowerCase();
        if(equipmentService.getAll().stream().anyMatch(e -> e.getName().toLowerCase().contains(finalMsg1))){
            return equipmentService.getAll().stream().filter(e -> e.getName().toLowerCase().contains(finalMsg1)).findFirst().get();
        }/*else {

        }
        equipments = equipments.stream().skip(1).collect(Collectors.toList());
        System.out.println("Hedhol el equipments \n"+equipments);
        msg = msg.replaceAll(" ", "");
        msg = msg.toLowerCase();
        char[] chars = new char[msg.length()];

        // Copy character by character into array
        for (int i = 0; i < msg.length(); i++) {
            chars[i] = msg.charAt(i);
        }
        String testRegex = "^";
        for (int i = 0; i < chars.length; i++) {
            if(i==chars.length-1){
                testRegex += chars[i];
            }else {
                testRegex += chars[i]+"*";
            }
        }
        testRegex+= "$";

        System.out.println("Ayaaaa  :  "+testRegex);

        String finalMsg = msg;
        String esm = equipments.stream().filter(e->e.contains(finalMsg)).findFirst().get();
        System.out.println("hedha esm el equip: "+esm);

        String regex = ".*["+esm+"].*";
        String finalTestRegex = testRegex;
        return equipmentService.getAll().stream().filter(e->e.getName().matches(finalTestRegex)).findFirst().get();
        /*



        //equipments.stream().forEach(e->e.con);

        if(msg.equals("")){
            return null;
        }*/
        return null;
    }

    /**
     * return:
     * * -1 if we can't extract informations from the message
     * * -2 if the height exceeded the norm
     * * -3 if the height is less than the norm
     * * the height in cm if everything went well
     * */
    @Override
    public int extractHeight(String msg) {
        int toul=-1;
        List<String> height = ExcelApiImpl.readColumnByName("TOUL");
        height = height.stream().skip(1).collect(Collectors.toList());
        msg = msg.replaceAll("[^0-9]", "");
        if(msg.equals("")){
            return toul;
        }
        String finalMsg = msg;
        if (height.stream().anyMatch(e->e.equals(finalMsg))){
            System.out.println("Toulou "+msg+" cm");
            return Integer.parseInt(finalMsg);
        }else if(Integer.parseInt(finalMsg) > height.stream().map(Integer::new).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).max().getAsInt()){
            return -2;
        }else if(Integer.parseInt(finalMsg) < height.stream().map(Integer::new).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).min().getAsInt()){
            return -3;
        }
        return toul;
    }

    @Override
    public List<String> getAllInformations() {
        extractHeight("120kg");
        return null;
    }

    @Override
    public Muscle extractMuscle(String msg) {
        IMuscleService muscleService = new MuscleServiceImpl();
        String finalMsg1 = msg.toLowerCase();
        if(muscleService.getAll().stream().anyMatch(e -> e.getName().toLowerCase().contains(finalMsg1))){
            return muscleService.getAll().stream().filter(e -> e.getName().toLowerCase().contains(finalMsg1)).findFirst().get();
        }

        return null;
    }

    /**
     * return:
     * 0 if the answer is no
     * 1 if the answer is yes
     * -1 else
     * */
    @Override
    public int extractPositiveOrNegativeResp(String msg) {
        List<String> yes = ExcelApiImpl.readColumnByName("POSITIVE-ANSWER");
        List<String> no = ExcelApiImpl.readColumnByName("NEGATIVE-ANSWER");
        if(yes.stream().anyMatch(e->e.contains(msg))){
            return 1;
        }
        if(no.stream().anyMatch(e->e.contains(msg))){
            return 0;
        }
        return -1;
    }

    @Override
    public List<Exercice> generateExercice(List<Equipment> equipment, List<Muscle> muscles) {
        IExerciceService exerciceService = new ExerciceServiceImpl();
        List<Exercice> exercices = exerciceService.getAll();
/*
        Set<String> acceptableNames =
                        equipment.stream()
                        .map(Equipment::getName)
                        .collect(Collectors.toSet());

        for (int i = 0; i < exercices.size(); i++) {
            exercices.get(i).getEquipments().stream()
        }
        exercices.forEach(e->e.getEquipments().stream().filter(c->acceptableNames.contains(c.getName())).collect(Collectors.toList()));
        /*clients.stream()
                .filter(c -> acceptableNames.contains(c.getName()))
                .collect(Collectors.toList());


        List<Exercice> uselessExer = null;
        List<Exercice> endExercice = null;
        for (int i = 0; i < exercices.size(); i++) {
            for (int j = 0; j < exercices.get(i).getEquipments().size(); j++) {
                for (int k = 0; k < equipment.size(); k++) {
                    if(exercices.get(i).getEquipments().get(j).getName().equals(equipment.get(k).getName())){
                        System.out.println("houni li fih");
                        System.out.println(exercices.get(i));
                    }else {
                        System.out.println("houni li mefihech");
                        System.out.println(exercices.get(i));
                    }
                }
            }
        }
        System.out.println(endExercice);
        System.out.println("----------------------------");
        System.out.println(uselessExer);

        //List<Exercice> exEqup = exerciceService.getAll();
        /*for (int i = 0; i < exEqup.size(); i++) {
            for (int j = 0; j < equipment.size(); j++) {
                exEqup.get(i).getEquipments().contains(equipment.get(j));
            }
            int finalI = i;
            exerciceService.getAll().forEach(e-> e.getEquipments().contains(equipment.get(finalI)));
        }
        System.out.println(equipment);
        System.out.println(exerciceService.getAll().get(0).getEquipments().retainAll(equipment));
        List<Exercice> ex = exerciceService.getAll().stream().filter(e->e.getEquipments().contains(equipment)).collect(Collectors.toList());
        System.out.println(ex);
        //exerciceService.getAll().stream().filter(e->equipment.contains(e)).collect(Collectors.toList());
*/
        return exercices;
    }

    @Override
    public int extractSalutationResp(String msg) {
        ExcelApiImpl.readColumnByName("SALUTATION");
        return 0;
    }
}
