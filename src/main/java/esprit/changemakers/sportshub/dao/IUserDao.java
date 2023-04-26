package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.User;

/**
 * @author Ahmed
 */
public interface IUserDao extends IGenericDao<User> {
    public User getByEmail(String email);
}
