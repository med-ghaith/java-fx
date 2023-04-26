package esprit.changemakers.sportshub.entities;

import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private RoleEnum role;
    private SecurityQuestionEnum securityQuestion;
    private String securityAnswer;
    private String phoneNumber;
    private String imgURL;

    public User() {
    }

    public User(String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public User(int id, String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public User(int id, String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer, String imgURL) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.imgURL = imgURL;
    }

    public User(String nom, String prenom, String email, String password, RoleEnum role, SecurityQuestionEnum securityQuestion, String securityAnswer, String imgURL) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public SecurityQuestionEnum getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestionEnum securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj ) return true;
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;

        User other = (User)obj;
        return this.email.equals(other.getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", securityQuestion=" + securityQuestion +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
