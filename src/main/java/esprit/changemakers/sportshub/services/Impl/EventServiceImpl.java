package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.dao.Impl.EventDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IEventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EventServiceImpl implements IEventService {
    IEventDao eventDao;
    public EventServiceImpl() { eventDao = new EventDaoImpl();}

    public Event create(Event entity) {
        if(!eventDao.find(entity)){
            return eventDao.save(entity);
        }else {
            System.out.println("event "+entity.getId_event()+" déjà existant");
            return eventDao.findOne(entity);
        }
    }

    public void update(Event entity) {
        eventDao.update(entity);
    }

    public void remove(int id) {
        eventDao.deleteById(id);
    }

    public Event getById(int id) {
        return eventDao.getById(id);
    }

    public ObservableList<Event> getAll() {
        return eventDao.getAll();
    }

    @Override
    public ObservableList<Event> getByStartDate(LocalDate startDate) {
        ObservableList <Event> x= FXCollections.observableArrayList();
        ObservableList <Event> ol= eventDao.getAll();
       ol.stream().filter(e->e.getStart_date().isEqual(startDate)).forEach(e->x.add(e));
      return x;
    }
    public ObservableList<Event> getByCategorie(String categ) {
        ObservableList <Event> x= FXCollections.observableArrayList();
        ObservableList <Event> ol= eventDao.getAll();
        ol.stream().filter(e-> e.getCategory().equals(categ)).forEach(e->x.add(e));
        return x;
    }
    public ObservableList<Event> getByPrice(Integer price) {
        ObservableList <Event> x= FXCollections.observableArrayList();
        ObservableList <Event> ol= eventDao.getAll();
        ol.stream().filter(e-> e.getFees()== price).forEach(e->x.add(e));
        return x;
    }

    public ObservableList<Event> getByPlanningId(int id_planning) {
        ObservableList <Event> x= FXCollections.observableArrayList();
        ObservableList <Event> ol= eventDao.getAll();
        ol.stream().filter(e-> e.getPlanning_id()==(id_planning)).forEach(e->x.add(e));
        return x;
    }




}
