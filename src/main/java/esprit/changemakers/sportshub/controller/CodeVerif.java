package esprit.changemakers.sportshub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;

public class CodeVerif implements Initializable {

    @FXML
    private Label labelData;
    @FXML
    private TextField firstNumber;
    @FXML
    private TextField sNumber;
    @FXML
    private TextField tNumber;
    @FXML
    private TextField fNumber;
    @FXML
    private TextField fifthNumber;
    @FXML
    private Label codeError;
    @FXML
    private Button verifButton;

    @FXML
    public void onTest() throws IOException {
        if(labelData.getText().split(" ")[2].equals("ident")){
            String codeVerif = labelData.getText().split(" ")[0];
            String codeEntred = firstNumber.getText()+sNumber.getText()+tNumber.getText()+fNumber.getText()+fifthNumber.getText();
            if(codeVerif.equals(codeEntred)){
                codeError.setVisible(false);
                SignUpController.getInstance().register();
                Stage stage = (Stage) verifButton.getScene().getWindow();
                stage.close();
            }else{
                codeError.setVisible(true);
            }
        }else if(labelData.getText().split(" ")[2].equals("add")){
            String codeVerif = labelData.getText().split(" ")[0];
            String codeEntred = firstNumber.getText()+sNumber.getText()+tNumber.getText()+fNumber.getText()+fifthNumber.getText();
            if(codeVerif.equals(codeEntred)){
                codeError.setVisible(false);
                AdditionalUserDataController.getInstance().register();
                Stage stage = (Stage) verifButton.getScene().getWindow();
                stage.close();
            }else{
                codeError.setVisible(true);
            }
        }else if(labelData.getText().split(" ")[2].equals("sec")){
            String codeVerif = labelData.getText().split(" ")[0];
            String codeEntred = firstNumber.getText()+sNumber.getText()+tNumber.getText()+fNumber.getText()+fifthNumber.getText();
            if(codeVerif.equals(codeEntred)){
                codeError.setVisible(false);
                ProfileSecurityController.getInstance().verified();
                Stage stage = (Stage) verifButton.getScene().getWindow();
                stage.close();
            }else{
                codeError.setVisible(true);
            }
        }else{
        String codeVerif = labelData.getText().split(" ")[0];
        String codeEntred = firstNumber.getText()+sNumber.getText()+tNumber.getText()+fNumber.getText()+fifthNumber.getText();
        if(codeVerif.equals(codeEntred)){
                codeError.setVisible(false);
                Parent root = FXMLLoader.load(getClass().getResource("/views/changePassword.fxml"));
                Label label = (Label) root.lookup("#userLab");
                if(label != null) label.setText(labelData.getText().split(" ")[1]);
                ResetPasswordController.getInstance().getResetBorderPane().setCenter(root);
        }else{
            codeError.setVisible(true);
        }}
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
