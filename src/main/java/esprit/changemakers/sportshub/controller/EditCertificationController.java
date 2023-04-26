package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXButton;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCertificationController implements Initializable {
    @FXML
    VBox certificationCardContainer;
    @FXML
    JFXButton addCertif;

    private CertificationServiceImpl certificationService;

    @FXML
    public void onAddCertif(){
        try {
            HBox addCertifView = (HBox) FXMLLoader.load(getClass().getResource("/views/AddCertificationCard.fxml"));
            certificationCardContainer.getChildren().add(addCertifView);
            addCertif.setDisable(true);
            addCertif.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (HomeController.getInstance().getIdUser() != 0) {
            certificationService = new CertificationServiceImpl();
            List<Certification> certificationList = certificationService.getAllCertifByUserId(HomeController.getInstance().getIdUser());
            if (certificationList.size() != 0) {
                for (Certification certif : certificationList) {
                    try {
                        VBox certifCard = FXMLLoader.load(getClass().getResource("/views/CertificationCard.fxml"));
                        Label certifName = (Label) certifCard.lookup("#certifName");
                        Label certifSpeciaity = (Label) certifCard.lookup("#certifSpeciality");
                        Label certifId = (Label) certifCard.lookup("#certifId");
                        if (certifName != null && certifSpeciaity != null && certifId != null) {
                            certifId.setText(""+certif.getId());
                            certifName.setText(certif.getCertifName());
                            certifSpeciaity.setText(certif.getSpeciality().toString());
                        }
                        certificationCardContainer.getChildren().add(certifCard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
