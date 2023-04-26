package esprit.changemakers.sportshub.services;

import javafx.collections.ObservableList;

public interface IReviewService <T> {
    public void addReviewCoach(T t);
    public void addReviewGym(T t);
    public void updateReviewCoach(T t);
    public void updateReviewGym(T t);
    public ObservableList<T> getAllReviewsByGym(int idGym);
    public ObservableList<T> getAllReviewsByCoach(int idCoach);
    public T getReviewByUserByCoach(int idUser, int idCoach);
    public T getReviewByUserByGym(int idUser, int idGym);
    public void deleteReviewCoach(T t);
    public void deleteReviewGym(T t);
}