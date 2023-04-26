package esprit.changemakers.sportshub.dao;

import javafx.collections.ObservableList;

/**
 * @author Jozef
 */
public interface IGenericDao<E> {

    public E save(E entity);

    public void update(E entity);

    public void deleteById(int id);

    public ObservableList<E> getAll();

    public E getById(int id);

    public boolean find(E e);

    public E findOne(E entity);
}
