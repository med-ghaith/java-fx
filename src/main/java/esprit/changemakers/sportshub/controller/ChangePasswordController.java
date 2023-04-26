package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ChangePasswordController implements Initializable {

    UserServiceImpl userService;

    @FXML
    private PasswordField newPassField ;
    @FXML
    private PasswordField confirmField;
    @FXML
    private Label passError;
    @FXML
    private Label userLab;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancleButton;

    @FXML
    public void onConfirm(){
        if(!newPassField.getText().equals(confirmField.getText())){
            passError.setText("Confirmation password is not the same as new password !");
            passError.setVisible(true);
        }else if(newPassField.getText().length() <6){
            passError.setText("Password length must be atleast 6");
            passError.setVisible(true);
        }else{
            User user = userService.getByEmail(userLab.getText());
            user.setPassword(newPassField.getText());
            System.out.println("controller"+user.getPassword());
            userService.update(user);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    public void onClose(){
        Stage stage = (Stage) cancleButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
    }
}
