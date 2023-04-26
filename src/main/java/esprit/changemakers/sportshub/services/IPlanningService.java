package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.entities.Planning;
import javafx.collections.ObservableList;

public interface IPlanningService extends IGenericService <Planning>{
    public ObservableList<Planning> getByUserId (int id_user);

}
