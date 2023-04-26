package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.utils.SendMail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Random;

public class CheckEmailController {

    @FXML
    private TextField emailField;
    @FXML
    private Button closeButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label roleData;

    @FXML
    public void onSend(){

        try {
            String genCode = String.valueOf(gen());
            SendMail.sendMail(emailField.getText(),"Code Verification","Hello,\n"+"Code Verification: "+genCode);
            Parent root = FXMLLoader.load(getClass().getResource("/views/codeVerif.fxml"));
            Label label = (Label) root.lookup("#labelData");
            if(label != null) {
                if(roleData.getText().equals("Coach") || roleData.getText().equals("Adherent")){
                    label.setText(genCode+" "+emailField.getText()+" add");
                }else{
                label.setText(genCode+" "+emailField.getText()+" ident");}
            }
            this.borderPane.setCenter(root);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}
