package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.User;

public interface IUserService extends IGenericService<User>{
    public User getByEmail(String email);
}
