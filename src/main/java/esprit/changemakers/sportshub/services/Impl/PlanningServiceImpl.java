package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IPlanningDao;
import esprit.changemakers.sportshub.dao.Impl.PlanningDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.entities.Planning;
import esprit.changemakers.sportshub.services.IPlanningService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlanningServiceImpl implements IPlanningService {
    IPlanningDao planningDao;
    public PlanningServiceImpl() { planningDao = new PlanningDaoImpl();}

    public Planning create(Planning entity) {
        if(!planningDao.find(entity)){
            return planningDao.save(entity);
        }else {
            System.out.println("Planning "+entity.getId_planning()+" déjà existant");
            return planningDao.findOne(entity);
        }
    }

    public void update(Planning entity) {
        planningDao.update(entity);
    }

    public void remove(int id) {
        planningDao.deleteById(id);
    }

    public Planning getById(int id) {
        return planningDao.getById(id);
    }

    public ObservableList<Planning> getAll() {
        return planningDao.getAll();
    }

    public ObservableList<Planning> getByUserId(int id_user) {
        ObservableList <Planning> x= FXCollections.observableArrayList();
        ObservableList <Planning> ol= planningDao.getAll();
        ol.stream().filter(e-> e.getId_user()==(id_user)).forEach(e->x.add(e));
        return x;
    }
}
