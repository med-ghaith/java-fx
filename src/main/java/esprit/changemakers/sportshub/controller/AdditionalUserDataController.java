package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import esprit.changemakers.sportshub.entities.Adherent;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdditionalUserDataController implements Initializable {

    private UserServiceImpl userService;
    private CertificationServiceImpl certificationService;

    @FXML
    private Label userData;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXTextField phoneNumber;
    @FXML
    private JFXTextField certifName;
    @FXML
    private JFXDatePicker birthDate;
    @FXML
    private JFXComboBox<String> city;
    @FXML
    private JFXComboBox<String> certifSpec;
    @FXML
    private TextArea description;
    @FXML
    private Label fieldError;

    private final String[] cityExp = {"Tunis","Beja","Sousse",
            "Bizerte","Ariana","Jendouba"
            ,"Kef","Nabeul","Hammamet"
            ,"Monastir","Sfax","Gabes"
            ,"Medenine","Djerba","Tataouine"
            ,"Kbeli","Tozeur","Gafsa"
            ,"Kasserine","SidiBouZid","Siliana"
    };

    private final String[] specialities = {SpecialityEnum.YOGA.name(),
            SpecialityEnum.KARATE.name(),
            SpecialityEnum.MMA.name(),
            SpecialityEnum.BOXING.name(),
            SpecialityEnum.BODYBUILDING.name(),
            SpecialityEnum.JUDO.name(),
            SpecialityEnum.KICKBOXING.name(),
            SpecialityEnum.DANCE.name()
    };

    private static AdditionalUserDataController instance;

    public AdditionalUserDataController(){
        instance = this;
    }

    public static AdditionalUserDataController getInstance(){
        return instance;
    }

    public BorderPane getBorderPane(){
        return this.borderPane;
    }

    @FXML
    public void onSubmit(){
        if(userData.getText().split(" ")[4].equals("Adherent")){
            if(!phoneNumber.getText().equals("") && !description.getText().equals("")
                    && city.getValue()!=null && birthDate.getValue() != null){
                if(birthDate.getValue().compareTo(LocalDate.of(2014,01,01))>0){
                    fieldError.setText("Sorry Your to young to use this application!");
                    fieldError.setVisible(true);
                }else if(phoneNumber.getText().length()>8){
                    fieldError.setText("Phone Number must be 8 characters");
                    fieldError.setVisible(true);
                }else if(phoneNumber.getText().length() < 8){
                    fieldError.setText("Phone Number must be 8 digits");
                    fieldError.setVisible(true);
                }else if(!phoneNumber.getText().matches("[0-9]*")){
                    fieldError.setText("Phone Number must be only digits");
                    fieldError.setVisible(true);
                }else{
                fieldError.setVisible(false);
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/CheckEmail.fxml"));
                        TextField checkEmail = (TextField) root.lookup("#emailField");
                        checkEmail.setText(this.userData.getText().split(" ")[2]);
                        checkEmail.setDisable(true);
                        Label label = (Label) root.lookup("#roleData");
                        if(label !=null ) label.setText(userData.getText().split(" ")[4]);
                        Scene scene = new Scene(root, 700, 400);
                        Stage stage = new Stage();
                        stage.setTitle("Sports Hub");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                fieldError.setText("Missing Fields !");
                fieldError.setVisible(true);
            }
        }else{

            if(!phoneNumber.getText().equals("") && !description.getText().equals("")
                    && city.getValue()!=null && birthDate.getValue() != null
                    && certifSpec.getValue()!=null && !certifName.getText().equals("")
            ){
                if(birthDate.getValue().compareTo(LocalDate.of(2014,01,01))>0){
                    fieldError.setText("Sorry Your to young to use this application!");
                    fieldError.setVisible(true);
                }else if(phoneNumber.getText().length() != 8){
                    fieldError.setText("Phone Number must be 8 digits");
                    fieldError.setVisible(true);
                }else if(!phoneNumber.getText().matches("[0-9]*")){
                    fieldError.setText("Phone Number must be only digits");
                    fieldError.setVisible(true);
                }else{
                    fieldError.setVisible(false);
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/CheckEmail.fxml"));
                        TextField checkEmail = (TextField) root.lookup("#emailField");
                        checkEmail.setText(this.userData.getText().split(" ")[2]);
                        checkEmail.setDisable(true);
                        Label label = (Label) root.lookup("#roleData");
                        if(label !=null ) label.setText(userData.getText().split(" ")[4]);
                        Scene scene = new Scene(root, 700, 400);
                        Stage stage = new Stage();
                        stage.setTitle("Sports Hub");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                fieldError.setText("Missing Fields !");
                fieldError.setVisible(true);
            }
        }
    }

    public void register(){
        String[] userDataArr = userData.getText().split(" ");
        if(userDataArr[4].equals("Adherent")){
            Adherent adherent = new Adherent(
              userDataArr[0],
              userDataArr[1],
              userDataArr[2],
              userDataArr[3],
              RoleEnum.valueOf(userDataArr[4].toUpperCase()),
              SecurityQuestionEnum.FavoriteColor,
              userDataArr[5],
              "http://localhost/sports-hub-images/"+userDataArr[6],
              this.description.getText(),
              this.phoneNumber.getText(),
              this.birthDate.getValue(),
              this.city.getValue()
            );
            userService.create(adherent);
            try {
                HomeController.getInstance().loadLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Coach coach = new Coach(
                    userDataArr[0],
                    userDataArr[1],
                    userDataArr[2],
                    userDataArr[3],
                    RoleEnum.valueOf(userDataArr[4].toUpperCase()),
                    SecurityQuestionEnum.FavoriteColor,
                    userDataArr[5],
                    "http://localhost/sports-hub-images/"+userDataArr[6],
                    this.description.getText(),
                    this.phoneNumber.getText(),
                    this.birthDate.getValue(),
                    this.city.getValue()
            );
            coach = (Coach) userService.create(coach);
            Certification certification = new Certification(this.certifName.getText(), SpecialityEnum.valueOf(this.certifSpec.getValue().toUpperCase()),coach.getId());
            certificationService.create(certification);
            try {
                HomeController.getInstance().loadLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
        certificationService = new CertificationServiceImpl();
        this.certifSpec.getItems().addAll(this.specialities);
        this.city.getItems().addAll(this.cityExp);
    }
}
