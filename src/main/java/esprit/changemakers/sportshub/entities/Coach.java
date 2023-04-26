package esprit.changemakers.sportshub.entities;

import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;

import java.time.LocalDate;


public class Coach extends User {
    private String description;
    private String phoneNumber;
    private LocalDate birthDate;
    private String city;

    public Coach() {
    }

    public Coach(int id, String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer, String imgURL, String description, String phoneNumber, LocalDate birthDate, String city) {
        super(id, nom, prenom, email, password, role, securityQuestion, securityAnswer, imgURL);
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.city = city;
    }

    public Coach(String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer, String imgURL, String description, String phoneNumber, LocalDate birthDate, String city) {
        super(nom, prenom, email, password, role, securityQuestion, securityAnswer, imgURL);
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + this.getPrenom() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                ", securityQuestion=" + this.getSecurityQuestion() + '\'' +
                ", securityAnswer='" + this.getSecurityAnswer() + '\'' +
                "description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", city='" + city + '\'' +
                ", ImgUrl='" + this.getImgURL() + '\'' +
                '}';
    }
}
