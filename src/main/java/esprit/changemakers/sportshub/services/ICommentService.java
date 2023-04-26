package esprit.changemakers.sportshub.services;

import javafx.collections.ObservableList;

public interface ICommentService <T>{
    public void addCommentCoach(T t);
    public void addCommentGym(T t);
    public void updateCommentCoach(T t);
    public void updateCommentGym(T t);
    public ObservableList<T> getAllCommentsByGym(int idGym);
    public ObservableList<T> getAllCommentsByCoach(int idCoach);
    public T getCommentByUserByCoach(int idUser, int idCoach);
    public T getCommentByUserByGym(int idUser, int idGym);
    public void deleteCommentCoach(T t);
    public void deleteCommentGym(T t);
}