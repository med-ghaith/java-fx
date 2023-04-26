package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.EmailValidator;
import esprit.changemakers.sportshub.utils.SendMail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class ResetPasswordController implements Initializable {

    private UserServiceImpl userService;

    @FXML
    private BorderPane resetBorderPane;
    @FXML
    private TextField emailResetField;
    @FXML
    private Button closeButton;
    @FXML
    private Label emailError;

    private static ResetPasswordController instance;

    public ResetPasswordController(){
        instance = this;
    }

    public static ResetPasswordController getInstance() {
        return instance;
    }

    public BorderPane getResetBorderPane(){
        return this.resetBorderPane;
    }

    public void setEmailField(String email){
        emailResetField.setText(email);
    }


    @FXML
    public void onReset(ActionEvent event) throws IOException {
        if(new EmailValidator().test(emailResetField.getText()) && !emailResetField.getText().equals("")){
        emailError.setVisible(false);
        User user = userService.getByEmail(emailResetField.getText());
        if(user == null){
            emailError.setVisible(true);
            emailError.setText("Email does not Exist");
        }else{
            emailError.setVisible(false);
            try {
                String genCode = String.valueOf(gen());
                SendMail.sendMail(emailResetField.getText(),"Code Verification","Hello "+user.getNom()+
                        " "+user.getPrenom()+",\n"+"Code Verification: "+genCode);
                Parent root = FXMLLoader.load(getClass().getResource("/views/codeVerif.fxml"));
                Label label = (Label) root.lookup("#labelData");
                if(label != null) {label.setText(genCode+" "+user.getEmail()+" res");}
                this.resetBorderPane.setCenter(root);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        }else{
            emailError.setText("Invalid Email!");
            emailError.setVisible(true);
        }
    }

    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

}
