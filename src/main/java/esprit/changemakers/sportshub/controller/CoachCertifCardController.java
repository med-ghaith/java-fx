package esprit.changemakers.sportshub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoachCertifCardController {

    @FXML
    Label certifName;
    @FXML
    Label certifSpeciality;

    public void setCertifdata(String name,String spec){
        certifName.setText(name);
        certifSpeciality.setText(spec);
    }
}
