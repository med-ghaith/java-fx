package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import esprit.changemakers.sportshub.entities.Adherent;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.EmailValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ProfileSecurityController implements Initializable {

    @FXML
    JFXComboBox<String> securityQuestion;
    @FXML
    JFXTextField securityAnswer;
    @FXML
    JFXTextField email;
    @FXML
    JFXTextField password;
    @FXML
    JFXTextField confirmPassword;
    @FXML
    VBox verifContainer;
    @FXML
    Label phoneNumber;
    @FXML
    HBox msg;
    @FXML
    VBox container;
    @FXML
    VBox changeContainer;


    private UserServiceImpl userService;

    private static ProfileSecurityController instance;

    public ProfileSecurityController() {
        instance = this;
    }

    public static ProfileSecurityController getInstance(){
        return instance;
    }

    @FXML
    public void onVerif() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please Correct invalid fields");
        if(securityQuestion.getValue()== null){
            alert.setContentText("You have to chose a security question");
            alert.showAndWait();
            return;
        }
        User user = userService.getById(HomeController.getIdUser());
        if(user.getRole().toString().equals("COACH")){
            Coach coach = (Coach) user;
            if (!coach.getSecurityAnswer().equals(securityAnswer.getText())){
                alert.setContentText("Wrong Security answer");
                alert.showAndWait();
                return;
            }
            Parent root = FXMLLoader.load(getClass().getResource("/views/codeVerif.fxml"));
            Label label = (Label) root.lookup("#labelData");
            int genCode = gen();
            if(label != null) label.setText(genCode+" "+coach.getEmail()+" sec");
            msg.setVisible(true);
            phoneNumber.setText(coach.getPhoneNumber());
            Scene scene = new Scene(root,600 ,332);
            Stage stage = new Stage();
            stage.setTitle("Sports Hub");
            stage.setScene(scene);
            stage.show();
            send("58411494",genCode);
            email.setText(coach.getEmail());
        }else if(user.getRole().equals("ADHERENT")){
            Adherent adherent = (Adherent) user;
            if (!adherent.getSecurityAnswer().equals(securityAnswer.getText())){
                alert.setContentText("Wrong Security answer");
                alert.showAndWait();
                return;
            }
            Parent root = FXMLLoader.load(getClass().getResource("/views/codeVerif.fxml"));
            Label label = (Label) root.lookup("#labelData");
            int genCode = gen();
            if(label != null) label.setText(genCode+" "+adherent.getEmail()+" sec");
            msg.setVisible(true);
            phoneNumber.setText(adherent.getPhoneNumber());
            Scene scene = new Scene(root,600 ,332);
            Stage stage = new Stage();
            stage.setTitle("Sports Hub");
            stage.setScene(scene);
            stage.show();
            send("58411494",genCode);
            email.setText(adherent.getEmail());
        }

    }
    @FXML
    public void onEdit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please Correct invalid fields");
        if(!password.getText().equals("") && password.getText().length()<6){
            alert.setContentText("Password must be at least 6 charcters");
            alert.showAndWait();
            return;
        }else if(!password.getText().equals("") && !password.getText().equals(confirmPassword.getText())){
            alert.setContentText("Confirmation Pass is different then Password");
            alert.showAndWait();
            return;
        }
        if(!new EmailValidator().test(email.getText())){
            alert.setContentText("Invalid E-mail");
            alert.showAndWait();
            return;
        }
        if(!email.getText().equals(userService.getById(HomeController.getIdUser()).getEmail())){
            System.out.println("not the same");
            if(userService.getByEmail(email.getText()) != null){
                alert.setContentText("Email Exist");
                alert.showAndWait();
                return;
            }
            User user = userService.getById(HomeController.getIdUser());
            user.setEmail(email.getText());
            if(!password.getText().equals("")) user.setPassword(password.getText());
            userService.update(user);
            MyProfileController.getInstance().loadProfile();
        }else{
            if(!password.getText().equals("")){
                User user = userService.getById(HomeController.getIdUser());
                user.setPassword(password.getText());
                userService.update(user);
                MyProfileController.getInstance().loadProfile();
            }
        }
        MyProfileController.getInstance().loadProfile();
    }

    public void verified(){
        System.out.println("Verifier");
        container.getChildren().removeAll(verifContainer);
        email.setText(email.getText());
        changeContainer.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
        securityQuestion.getItems().add("What is your favorite color");
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public void send(String num,int code){
        Twilio.init("AC8a8f778a306890a8bec2188b6e4a10fd", "19d916d930b0becc033d16fe8df463ad");
        Message message = Message
                .creator(new PhoneNumber("+216"+num), // to
                        new PhoneNumber("+13306484806"), // from
                        "codeVerification: " +code).create();
    }
}
