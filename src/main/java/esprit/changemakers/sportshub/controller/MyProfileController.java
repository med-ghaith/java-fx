package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.Adherent;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.UploadFileToServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class MyProfileController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML
    Circle profilePhoto;
    @FXML
    Label firstName;
    @FXML
    Label lastName;
    @FXML
    Label city;
    @FXML
    Label birthDate;
    @FXML
    Label description;
    @FXML
    Label phone;
    @FXML
    Label email;
    @FXML
    Label titleName;



    private static MyProfileController instance;
    private UserServiceImpl userService;

    public MyProfileController(){
        instance = this;
    }

    public static MyProfileController getInstance(){
        return instance;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();

        if (HomeController.getInstance().getIdUser() != 0) {
            User user = userService.getById(HomeController.getInstance().getIdUser());

            profilePhoto.setStroke(Color.ORANGE);
            Image img = new Image(user.getImgURL());
            profilePhoto.setFill(new ImagePattern(img));
            profilePhoto.setEffect(new DropShadow(+25d, 0d, +2, Color.DARKORANGE));
            if (user.getRole().toString().equals("COACH")) {
                Coach coach = (Coach) user;
                firstName.setText(coach.getNom());
                lastName.setText(coach.getPrenom());
                firstName.setText(coach.getNom());
                city.setText(coach.getCity());
                birthDate.setText(coach.getBirthDate().toString());
                description.setText(coach.getDescription());
                email.setText(coach.getEmail());
                phone.setText(coach.getPhoneNumber());
                titleName.setText(coach.getNom()+" "+coach.getPrenom());
            } else if (user.getRole().toString().equals("ADHERENT")) {
                Adherent adherent = (Adherent) user;
                firstName.setText(adherent.getNom());
                lastName.setText(adherent.getPrenom());
                firstName.setText(adherent.getNom());
                city.setText(adherent.getCity());
                birthDate.setText(adherent.getBirthDate().toString());
                description.setText(adherent.getDescription());
                email.setText(adherent.getEmail());
                phone.setText(adherent.getPhoneNumber());
                titleName.setText(adherent.getNom()+" "+adherent.getPrenom());
            }
        }
    }

    @FXML
    public void onManagePlanning() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/GestPlanning.fxml"));
        HomeController.getInstance().getBorderPane().setCenter(root);
    }

    @FXML
    public void loadSecurity() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ProfileSecurity.fxml"));
        borderPane.setCenter(root);
    }

    @FXML
    public void onPhotoClicked() throws IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            String newFileName = "http://localhost/sports-hub-images/"+gen();
            User user = userService.getById(HomeController.getInstance().getIdUser());
            if(user != null && user.getRole().toString().equals("ADHERENT")){
                Adherent adherent = (Adherent) user;
                adherent.setImgURL(newFileName);
                userService.update(adherent);
                UploadFileToServer.uploadFile(selectedFile.getAbsolutePath(),newFileName);
                HomeController.getInstance().Planning();
            }else if (user != null && user.getRole().toString().equals("COACH")){
                Coach coach = (Coach) user;
                coach.setImgURL(newFileName);
                userService.update(coach);
                UploadFileToServer.uploadFile(selectedFile.getAbsolutePath(),newFileName);
                HomeController.getInstance().Planning();
            }
        }else{
            System.out.println("Hey");
        }
    }

    @FXML
    public void loadProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/MyProfile.fxml"));
        VBox vBox = (VBox) root.lookup("#vBoxProfile");
        borderPane.setCenter(vBox);
    }

    @FXML
    public void loadEditProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/editProfile.fxml"));
        borderPane.setCenter(root);
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 100000000 + r.nextInt(1000000000));
    }

}
