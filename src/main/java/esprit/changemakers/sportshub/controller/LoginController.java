package esprit.changemakers.sportshub.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.*;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.EmailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    private UserServiceImpl userService;

    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
    }

    public void onLogin(ActionEvent event) throws IOException {

        User userVerif = null;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please Correct invalid fields");
        if(!new EmailValidator().test(emailField.getText())){
            alert.setContentText("Invalid Email");
            alert.showAndWait();
        }else if(passwordField.getText().split(" ").length == 0 || passwordField.getText().equals("")){
            alert.setContentText("Invalid Password");
            alert.showAndWait();
        }else{
        userVerif = userService.getByEmail(emailField.getText());

        if(userVerif == null){
            alert.setContentText("User with email:"+emailField.getText()+" notFound");
            alert.showAndWait();
        }else if(!BCrypt.verifyer().verify(passwordField.getText().toCharArray(),userVerif.getPassword()).verified) {
            alert.setContentText("Wrong Password");
            alert.showAndWait();
        }else{
            HomeController.getInstance().setIdUser(userVerif.getId());
            HomeController.getInstance().idUserLb.setText(String.valueOf(userVerif.getId()));
            HomeController.getInstance().setUser();
            HomeController.getInstance().welcomeMsg.setVisible(true);
            HomeController.getInstance().signInLabel.setText("MyProfile");
            HomeController.getInstance().signUpLabel.setText("Log-out");

            if(userVerif.getRole().toString().equals("ADMIN")){
                BorderPane root = FXMLLoader.load(getClass().getResource("/views/DashbordAdmin.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            }else{
                HomeController.getInstance().loadHome();
            }

        }
        }
    }

    public void onRestPassword() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ResetPassword.fxml"));
            Parent root = fxmlLoader.load();
            if(new EmailValidator().test(emailField.getText())){
            ResetPasswordController resetPasswordController = fxmlLoader.getController();
            resetPasswordController.setEmailField(emailField.getText());
            }
            Scene scene = new Scene(root, 700, 400);
            Stage stage = new Stage();
            stage.setTitle("Sports Hub");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}
