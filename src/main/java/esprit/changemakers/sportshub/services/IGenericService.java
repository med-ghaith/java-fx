package esprit.changemakers.sportshub.services;

import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public interface IGenericService<E> {

    public E create(E entity);

    public void update(E entity);

    public void remove(int id);

    public E getById(int id);

    public ObservableList<E> getAll();

}
