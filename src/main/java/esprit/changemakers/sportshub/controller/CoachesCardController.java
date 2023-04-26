package esprit.changemakers.sportshub.controller;


import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoachesCardController {

    @FXML
    Label nomTitle;
    @FXML
    Label description;
    @FXML
    Label city;
    @FXML
    Label speciality;
    @FXML
    ImageView photoProfile;
    private Coach coach;

    private CertificationServiceImpl certificationService;
    private UserServiceImpl userService;

    public CoachesCardController(){
        certificationService = new CertificationServiceImpl();
        userService = new UserServiceImpl();
    }


    @FXML
    public void cardClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoachesProfileCard.fxml"));
        ScrollPane scrollPane = loader.load();
        CoachesProfileCardController coachesProfileCardController = loader.getController();
        if(coach != null){
            coachesProfileCardController.setData(coach);
        }
        CoachesInterfaceController.getInstance().getBorderPane().setLeft(scrollPane);
    }

    public void setData(String title,String des,String city ,String imgUrl, int id){
        nomTitle.setText(title);
        description.setText(des);
        this.city.setText(city);
        Image image = new Image(imgUrl);
        photoProfile.setImage(image);
        photoProfile.maxHeight(150);
        photoProfile.setFitHeight(150);
        photoProfile.setPreserveRatio(true);
        photoProfile.setSmooth(true);
        photoProfile.setCache(true);
        coach = (Coach) userService.getById(id);
        List<Certification> certificationList = certificationService.getAllCertifByUserId(id);
        List<String> spec = new ArrayList<>();
        for (Certification c : certificationList){
            if(!spec.contains(c.getSpeciality().name())){
                spec.add(c.getSpeciality().name());
            }
        }
        if(spec.size() > 1){
            String s = "";
            for (String sp:spec){
                s += ","+sp;
            }
            speciality.setText(s.substring(1,s.length()));
        }else if(spec.size() == 1){
        speciality.setText(spec.get(0));}
    }
}
