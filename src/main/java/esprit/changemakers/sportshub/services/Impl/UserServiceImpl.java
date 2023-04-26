package esprit.changemakers.sportshub.services.Impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import esprit.changemakers.sportshub.dao.IUserDao;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.IUserService;
import javafx.collections.ObservableList;

public class UserServiceImpl implements IUserService {
    IUserDao userService;

    public UserServiceImpl() {
        userService = new UserDaoImpl();
    }

    @Override
    public User create(User entity) {
        System.out.println(entity);
        if(userService.find(entity)) return entity;
        String hashedPassword = BCrypt.withDefaults().hashToString(12,entity.getPassword().toCharArray());
        entity.setPassword(hashedPassword);
    return userService.save(entity);
    }

    @Override
    public void update(User entity) {
        User user = userService.getById(entity.getId());
        if(!entity.getPassword().equals(user.getPassword())){
        String hashedPassword = BCrypt.withDefaults().hashToString(12,entity.getPassword().toCharArray());
            entity.setPassword(hashedPassword);
        }
        userService.update(entity);
    }

    @Override
    public void remove(int id) {
        userService.deleteById(id);
    }

    @Override
    public User getById(int id) {
        return userService.getById(id);
    }

    @Override
    public ObservableList<User> getAll() {
        return userService.getAll();
    }

    @Override
    public User getByEmail(String email) {
        return userService.getByEmail(email);
    }
}
