package esprit.changemakers.sportshub.entities;

import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;

public class Admin extends User{

    public Admin() {

    }

    public Admin(String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        super(nom, prenom, email, password, role, securityQuestion, securityAnswer);
    }

    public Admin(int id, String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        super(id, nom, prenom, email, password, role, securityQuestion, securityAnswer);
    }

}
