package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import esprit.changemakers.sportshub.entities.Adherent;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private Pane certificationPane;
    @FXML
    private VBox editProfileContainer;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField phoneNumber;
    @FXML
    private JFXComboBox<String> city;
    @FXML
    private JFXDatePicker birthDate;
    @FXML
    private JFXTextArea description;

    private String[] cityChoice = {"Tunis","Beja","New York"};

    private static EditProfileController instance;

    private UserServiceImpl userService;

    public EditProfileController(){
        instance = this;
    }

    public static EditProfileController getInstance(){
        return instance;
    }

    @FXML
    public void onEdit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please Correct invalid fields");
        if(firstName.getText().equals("") || lastName.getText().equals("") || phoneNumber.getText().equals("")
                || birthDate.getValue() == null || city.getValue() == null || description.getText().equals("")){
            alert.setContentText("Missing Fields");
            alert.showAndWait();
            return;
        }
        if(!firstName.getText().matches("[A-Za-z]*")){
            alert.setContentText("FirstName contains only characters");
            alert.showAndWait();
            return;
        }
        if(!lastName.getText().matches("[A-Za-z]*")){
            alert.setContentText("LastName contains only characters");
            alert.showAndWait();
            return;
        }
        if(!phoneNumber.getText().matches("[0-9]*")){
            alert.setContentText("PhoneNumber contains only digits");
            alert.showAndWait();
            return;
        }
        if(phoneNumber.getText().length() != 8 ){
            alert.setContentText("PhoneNumber contains only 8 digits");
            alert.showAndWait();
            return;
        }
        if(birthDate.getValue().compareTo(LocalDate.of(2014,01,01))>0){
            alert.setContentText("Age must be superior to 8 years old");
            alert.showAndWait();
            return;
        }
        User user = userService.getById(HomeController.getInstance().getIdUser());
        if(user.getRole().toString().equals("ADHERENT")){
            Adherent adherent = (Adherent) user;
            adherent.setNom(firstName.getText());
            adherent.setPrenom(lastName.getText());
            adherent.setBirthDate(birthDate.getValue());
            adherent.setDescription(description.getText());
            adherent.setPhoneNumber(phoneNumber.getText());
            adherent.setCity(city.getValue());
            userService.update(adherent);
            MyProfileController.getInstance().loadProfile();
        }else if (user.getRole().toString().equals("COACH")){
            Coach coach = (Coach) user;
            coach.setNom(firstName.getText());
            coach.setPrenom(lastName.getText());
            coach.setBirthDate(birthDate.getValue());
            coach.setDescription(description.getText());
            coach.setPhoneNumber(phoneNumber.getText());
            coach.setCity(city.getValue());
            userService.update(coach);
            MyProfileController.getInstance().loadProfile();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
        city.getItems().addAll(cityChoice);
        if(HomeController.getInstance().getIdUser() != 0){
            User user =  userService.getById(HomeController.getInstance().getIdUser());

            if (user != null && user.getRole().toString().equals("ADHERENT")){
                Adherent adherent = (Adherent) user;
                firstName.setText(adherent.getNom());
                lastName.setText(adherent.getPrenom());
                phoneNumber.setText(adherent.getPhoneNumber());
                birthDate.setValue(adherent.getBirthDate());
                city.setValue(adherent.getCity());
                description.setText(adherent.getDescription());
                editProfileContainer.getChildren().remove(certificationPane);
            }else if(user.getRole().toString().equals("COACH")){
                Coach coach = (Coach) user;
                firstName.setText(coach.getNom());
                lastName.setText(coach.getPrenom());
                phoneNumber.setText(coach.getPhoneNumber());
                birthDate.setValue(coach.getBirthDate());
                city.setValue(coach.getCity());
                description.setText(coach.getDescription());
                try {
                    VBox editCertif = FXMLLoader.load(getClass().getResource("/views/editCertification.fxml"));
                    certificationPane.getChildren().add(editCertif);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
