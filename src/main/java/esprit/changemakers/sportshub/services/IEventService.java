package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Event;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public interface IEventService extends IGenericService <Event> {

    public ObservableList <Event> getByStartDate(LocalDate startDate);
    //public ObservableList <Event> getByDesc(String description);
    public ObservableList <Event> getByCategorie(String categ);
    public ObservableList <Event> getByPrice(Integer price);


}
