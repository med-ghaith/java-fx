package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IEquipmentDao;
import esprit.changemakers.sportshub.dao.Impl.EquipmentDaoImpl;
import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.services.IEquipmentService;
import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public class EquipmentServiceImpl implements IEquipmentService {
    IEquipmentDao equipmentDao;

    public EquipmentServiceImpl() {
        equipmentDao = new EquipmentDaoImpl();
    }

    public Equipment create(Equipment entity) {
        if(!equipmentDao.find(entity)){
            return equipmentDao.save(entity);
        }else {
            System.out.println("Equipment "+entity.getName()+" déjà existant");
            return equipmentDao.findOne(entity);
        }
    }

    public void update(Equipment entity) {
        equipmentDao.update(entity);
    }

    public void remove(int id) {
        equipmentDao.deleteById(id);
    }

    public Equipment getById(int id) {
        return equipmentDao.getById(id);
    }

    public ObservableList<Equipment> getAll() {
        return equipmentDao.getAll();
    }

    @Override
    public Equipment getEquipmentByName(String name) {
            return equipmentDao.getAll().stream().filter(e->e.getName().equals(name)).findFirst().get();
    }
}
