package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXButton;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmationController {

    @FXML
    JFXButton confirmButton;
    @FXML
    JFXButton cancelButton;
    @FXML
    Label certifId;

    private CertificationServiceImpl certificationService;

    public ConfirmationController(){
        certificationService = new CertificationServiceImpl();
    }

    public void setCertifId(String certifId){
        this.certifId.setText(certifId);
    }

    @FXML
    public void onCofirm() throws IOException {
        certificationService.remove(Integer.parseInt(certifId.getText()));
        MyProfileController.getInstance().loadEditProfile();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onCancel(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
