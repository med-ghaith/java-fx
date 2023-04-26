package esprit.changemakers.sportshub.entities;


import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;

public class GymManger extends User{

    public GymManger() {
    }

    public GymManger(String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        super(nom, prenom, email, password, role, securityQuestion, securityAnswer);
    }

    public GymManger(int id, String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        super(id, nom, prenom, email, password, role, securityQuestion, securityAnswer);
    }

}
