package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCertificationCardController implements Initializable {

    @FXML
    JFXComboBox<String> certifSpeciality;
    @FXML
    JFXTextField certifName;

    private String[] specialityChoice = {SpecialityEnum.YOGA.name(),
            SpecialityEnum.KARATE.name(),
            SpecialityEnum.MMA.name(),
            SpecialityEnum.BOXING.name(),
            SpecialityEnum.BODYBUILDING.name(),
            SpecialityEnum.JUDO.name(),
            SpecialityEnum.KICKBOXING.name(),
            SpecialityEnum.DANCE.name()
    };

    private CertificationServiceImpl certificationService;

    @FXML
    public void onAdd(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please Correct invalid fields");
        if(certifSpeciality.getValue() == null || certifName.getText().equals("")){
            alert.setContentText("Missing Fields");
            alert.showAndWait();
            return;
        }
        if(HomeController.getInstance().getIdUser() > 0){
            try {
                Certification certification = new Certification(certifName.getText(),SpecialityEnum.valueOf(certifSpeciality.getValue().toUpperCase()),HomeController.getInstance().getIdUser());
                certificationService.create(certification);
                MyProfileController.getInstance().loadEditProfile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        certificationService = new CertificationServiceImpl();
        certifSpeciality.getItems().addAll(specialityChoice);
    }
}
