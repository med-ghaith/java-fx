package esprit.changemakers.sportshub.services;

import javafx.collections.ObservableList;

public interface IReclamationService <T> {
    public void addReclamation(T t);
    public boolean deleteReclamation(T t);
    public boolean updateReclamation(T t);
    public ObservableList<T> getAllReclamation();
    public ObservableList<T> getReclamationByUser(int idUser);
}
