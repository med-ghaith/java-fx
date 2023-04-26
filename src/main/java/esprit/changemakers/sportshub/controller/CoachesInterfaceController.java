package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.entities.Coach;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.CertificationServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoachesInterfaceController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML
    VBox coachesContainer;
    @FXML
    TextField searchBar;
    @FXML
    ComboBox<String> specialityTarget;

    private String[] specialityChoice = {SpecialityEnum.BODYBUILDING.name(),SpecialityEnum.YOGA.name()
            ,SpecialityEnum.KARATE.name(),SpecialityEnum.MMA.name(),SpecialityEnum.JUDO.name(),SpecialityEnum.DANCE.name()
            ,SpecialityEnum.KICKBOXING.name()
    };
    private List<User> users;
    private UserServiceImpl userService;
    private CertificationServiceImpl certificationService;

    private static CoachesInterfaceController instance;

    public CoachesInterfaceController(){
        instance = this;
    }

    public static CoachesInterfaceController getInstance(){
        return instance;
    }

    public BorderPane getBorderPane(){
        return this.borderPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBar.textProperty().addListener((observable,oldValue,newValue)->{
            filterOnSerachBarChange(newValue);
        });
        specialityTarget.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            List<User> userList;
            List<Certification> certificationList = certificationService.getAllCertifBySpeciality(SpecialityEnum.valueOf(specialityTarget.getValue().toUpperCase()));
            List<Integer> idUsers = new ArrayList<>();
            certificationList.stream().forEach(e-> idUsers.add(e.getIdUser()));
            userList = users.stream().filter(e-> (e.getNom()+" "+e.getPrenom()).contains(searchBar.getText()) && idUsers.contains(e.getId())).collect(Collectors.toList());
            loadCoachesCard(userList);
        }));
        userService = new UserServiceImpl();
        certificationService = new CertificationServiceImpl();
        specialityTarget.getItems().addAll(specialityChoice);
        users = userService.getAll().stream().filter(e-> e.getRole().toString().equals("COACH")).collect(Collectors.toList());
        try {
            for (User u : users){
                Coach coach = (Coach) u;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoachesCard.fxml"));
                VBox vBox = loader.load();
                CoachesCardController coachesCardController = loader.getController();
                coachesCardController.setData(
                        coach.getNom()+" "+coach.getPrenom(),
                        coach.getDescription(),
                        coach.getCity(),
                        coach.getImgURL(),
                        coach.getId()
                        );
                coachesContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void filterOnSerachBarChange(String value){
        List<User> userList;
        if(specialityTarget.getValue() == null){
            userList = users.stream().filter(e-> (e.getNom()+" "+e.getPrenom()).contains(searchBar.getText())).collect(Collectors.toList());
            loadCoachesCard(userList);
        }else{
            List<Certification> certificationList = certificationService.getAllCertifBySpeciality(SpecialityEnum.valueOf(specialityTarget.getValue().toUpperCase()));
            List<Integer> idUsers = new ArrayList<>();
            certificationList.stream().forEach(e-> idUsers.add(e.getIdUser()));
            userList = users.stream().filter(e-> (e.getNom()+" "+e.getPrenom()).contains(searchBar.getText())  && idUsers.contains(e.getId())).collect(Collectors.toList());
            loadCoachesCard(userList);
        }
    }

    public void loadCoachesCard(List<User> userList){
        try {
            coachesContainer.getChildren().removeAll(coachesContainer.getChildren());
            for (User u : userList){
                Coach coach = (Coach) u;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoachesCard.fxml"));
                VBox vBox = loader.load();
                CoachesCardController coachesCardController = loader.getController();
                coachesCardController.setData(
                        coach.getNom()+" "+coach.getPrenom(),
                        coach.getDescription(),
                        coach.getCity(),
                        coach.getImgURL(),
                        coach.getId()
                );
                coachesContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
