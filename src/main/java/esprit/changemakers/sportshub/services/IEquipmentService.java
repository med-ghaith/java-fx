package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Equipment;

/**
 * @author Jozef
 */
public interface IEquipmentService extends IGenericService<Equipment> {
    public Equipment getEquipmentByName(String name);
}
