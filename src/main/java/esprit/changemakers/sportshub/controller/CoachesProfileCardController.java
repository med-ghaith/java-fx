package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.*;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoachesProfileCardController implements Initializable {

    @FXML
    Circle profilePhoto;
    @FXML
    Label nomTitle;
    @FXML
    Label email;
    @FXML
    Label phoneNumber;
    @FXML
    Label birthDate;
    @FXML
    Label description;
    @FXML
    Label city;
    @FXML
    VBox certifContainer;
    @FXML
    Label idCoach;
    @FXML
    JFXButton contact;

    public static int idCaoch=0;


    private CertificationServiceImpl certificationService;

    public CoachesProfileCardController(){
        certificationService = new CertificationServiceImpl();
    }


    public void setData(Coach coach){
        System.out.println("beh hedha fel set data +++"+coach.getId());
        idCaoch=coach.getId();
        HomeController.getInstance().changIdC.setText(""+idCaoch);
        //idCoach.setText(""+coach.getId());
        nomTitle.setText(coach.getNom()+" "+coach.getPrenom());
        email.setText(coach.getEmail());
        phoneNumber.setText(coach.getPhoneNumber());
        birthDate.setText(coach.getBirthDate().toString());
        city.setText(coach.getCity());
        description.setText(coach.getDescription());
        profilePhoto.setStroke(Color.ORANGE);
        Image img = new Image(coach.getImgURL());
        profilePhoto.setFill(new ImagePattern(img));
        profilePhoto.setEffect(new DropShadow(+25d, 0d, +2, Color.DARKORANGE));
        if(coach != null){
            List<Certification> certificationList = certificationService.getAllCertifByUserId(coach.getId());
            if (certificationList.size() > 0){
                for(Certification cert : certificationList){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoachCertifCard.fxml"));
                        VBox vBox = loader.load();
                        CoachCertifCardController coachCertifCardController = loader.getController();
                        coachCertifCardController.setCertifdata(cert.getCertifName(),cert.getSpeciality().name());
                        certifContainer.getChildren().add(vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @FXML
    public void onCheckPlanning() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
        HomeController.getInstance().getBorderPane().setCenter(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Akeeel coaaachhhh "+idCaoch);


        contact.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../views/Chat.fxml"));
            try {
                Node chat = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatController chatController = loader.getController();
            try {
                HomeController.getInstance().loadChat();
            } catch (IOException e) {
                e.printStackTrace();
            }

            chatController.setUserFriendId(idCaoch);
            chatController.loadAllConvUsersWithFriend(idCaoch);
            //chatController.sendToFriend(Integer.parseInt(HomeController.getInstance().idUserLb.getText()),29);

        });
    }
}
