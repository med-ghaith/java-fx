package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.EmailValidator;
import esprit.changemakers.sportshub.utils.UploadFileToServer;
import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXComboBox<String> roleChoice;
    private final String[] roles = {"Adherent","Coach","GymManger"};

    @FXML
    private JFXComboBox<String> securityQuestions;
    private final String[] securityQuestionsChoices = {"What is your favorite color ?"};

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField securityAnswer;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label fieldError;

    @FXML
    private Button imgProfile;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXPasswordField confirmPassword;

    private File selectedFile;

    private static SignUpController instance;

    private UserServiceImpl userService;

    private User user;

    public SignUpController(){
        instance = this;
    }

    public static SignUpController getInstance(){
        return instance;
    }

    public BorderPane getBorderPane(){
        return this.borderPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.roleChoice.getItems().addAll(roles);
        this.securityQuestions.getItems().addAll(securityQuestionsChoices);
        userService = new UserServiceImpl();
    }

    @FXML
    public void onSignUp(){
        if(this.roleChoice.getValue() == null || this.securityQuestions.getValue() == null||
                this.securityAnswer.getText().equals("") || this.email.getText().equals("")||
                this.password.getText().equals("") || this.firstName.getText().equals("")|| this.lastName.getText().equals("")
        ){
            this.fieldError.setText("Missing Fields !");
            this.fieldError.setVisible(true);
            return;
        }else if(!new EmailValidator().test(this.email.getText())){
            this.fieldError.setText("Invalid Email");
            this.fieldError.setVisible(true);
            return;
        }else if (this.password.getText().length()<6){
            this.fieldError.setText("Password length must be atleast 6");
            this.fieldError.setVisible(true);
            return;
        }else if (this.selectedFile == null){
            this.fieldError.setText("Choose a Profile photo");
            this.fieldError.setVisible(true);
            return;
        }else if(!this.password.getText().equals(this.confirmPassword.getText())){
            this.fieldError.setText("The 2 passwords are different");
            this.fieldError.setVisible(true);
            return;
        }
        User user = userService.getByEmail(this.email.getText());
        if(user != null){
            this.fieldError.setText("Email Already Exist");
            this.fieldError.setVisible(true);
            return;
        }
        this.fieldError.setVisible(false);
        if(this.roleChoice.getValue().equals("GymManger")){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/CheckEmail.fxml"));
                TextField checkEmail = (TextField) root.lookup("#emailField");
                checkEmail.setText(this.email.getText());
                checkEmail.setDisable(true);
                Scene scene = new Scene(root, 700, 400);
                Stage stage = new Stage();
                stage.setTitle("Sports Hub");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/AdditionalUserData.fxml"));
                Label label = (Label) root.lookup("#userData");
                if(roleChoice.getValue().equals("Adherent")){
                    TextField certifName = (TextField) root.lookup("#certifName");
                    JFXComboBox<String> certifSpec = (JFXComboBox<String>) root.lookup("#certifSpec");
                    if(certifName != null && certifSpec != null){
                        certifName.setVisible(false);
                        certifSpec.setVisible(false);
                    }
                }
                String newFileName = ""+gen();
                UploadFileToServer.uploadFile(selectedFile.getAbsolutePath(),newFileName);
                if(label != null) {label.setText(
                        firstName.getText()+" "+lastName.getText()+" "+email.getText()+" "+password.getText()+" "+
                                roleChoice.getValue()+" "+securityAnswer.getText()+" "+newFileName
                );}
                HomeController.getInstance().getBorderPane().setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onChoseFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Hey");
        }
    }


    public void register() throws IOException {
        User user = new User(firstName.getText(),lastName.getText(),email.getText(),password.getText(), RoleEnum.GYMMANGER, SecurityQuestionEnum.FavoriteColor,securityAnswer.getText());
        if(securityQuestions.getValue().contains("color")) user.setSecurityQuestion(SecurityQuestionEnum.FavoriteColor);
        else if (securityQuestions.getValue().contains("pet")) user.setSecurityQuestion(SecurityQuestionEnum.FirstPetName);
        else user.setSecurityQuestion(SecurityQuestionEnum.HighSchoolName);
        System.out.println(userService.create(user));
        Parent root = FXMLLoader.load(getClass().getResource("/views/Modulegestionsalle/Formulairesalle.fxml"));
        HomeController.getInstance().getBorderPane().setCenter(root);
    }

    @FXML
    public void onResetAll(){
        this.firstName.setText("");
        this.lastName.setText("");
        this.email.setText("");
        this.password.setText("");
        this.securityAnswer.setText("");
        this.securityQuestions.setValue("");
        this.roleChoice.setValue("");
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000000 + r.nextInt(100000000));
    }


}
