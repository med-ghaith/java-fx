package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CertificationCardController {

    @FXML
    Label certifSpeciality;
    @FXML
    Label certifName;
    @FXML
    Label certifId;



    @FXML
    public void onDeletCertif() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/confirmation.fxml"));
        Parent root = loader.load();
        ConfirmationController controller = loader.getController();
        controller.setCertifId(certifId.getText());
        Scene scene = new Scene(root, 500, 200);
        Stage stage = new Stage();
        stage.setTitle("Sports Hub");
        stage.setScene(scene);
        stage.show();
    }

}
