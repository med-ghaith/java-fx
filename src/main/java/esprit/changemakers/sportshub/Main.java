package esprit.changemakers.sportshub;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.entities.*;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.GymDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.services.IPrivateMessageService;
import esprit.changemakers.sportshub.services.IUserService;
import esprit.changemakers.sportshub.services.Impl.*;
import esprit.changemakers.sportshub.utils.enumerations.RoleEnum;
import esprit.changemakers.sportshub.utils.enumerations.SecurityQuestionEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
//        IUserService userService = new UserServiceImpl();
//        HomeController.getInstance().setIdUser(30);
//        HomeController.getInstance().idUserLb.setText(""+30);
//        HomeController.getInstance().userName.setText(userService.getById(30).getNom());
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
//        GymDaoImpl addgym =new GymDaoImpl();
//        Gym california=new Gym(37,"California", "California Gym est la première chaîne de clubs de fitness en Tunisie, et le leader dans le domaine depuis 20 ans. Offrant un service de qualité et équipée d’appareils à la pointe de la technologie, elle a été la première certifiée par LesMills, concept n°1 du fitness dans le monde, venu tout droit de Nouvelle-Zélande."
//                , "Urban Park 8ieme étage, Ariana 1080","");
//        Gym Bizerte_fitness=new Gym(33,"Bizerte fitness", "La plus salle recomander de Bizerte", "Bizerte","");
//        Gym Gold_Gym=new Gym(32,"Gold Gym", "La plus salle recomander de Sokra", "Sokra","");
//
//        addgym.save(california);
//        addgym.save(Bizerte_fitness);
//        addgym.save(Gold_Gym);


        //UserServiceImpl userService = new UserServiceImpl();
        //userService.create(new Admin("ahmed","ouni","ouni@gmail.com","123456", RoleEnum.ADMIN, SecurityQuestionEnum.FavoriteColor,"red"));
//        Coach coach = new Coach(
//                "shalbi",
//                "shalbob",
//                "salhbi@gmail.com",
//                "123456",
//                RoleEnum.COACH,
//                SecurityQuestionEnum.FavoriteColor,
//                "white",
//                "http://localhost/sports-hub-images/395762775",
//                "jajajazjjjajaa",
//                "25478963",
//                LocalDate.of(1998,07,02),
//                "Tunis"
//                );
//        Coach coach2 = new Coach(
//                "shalbi",
//                "shalbob",
//                "salhbi123@gmail.com",
//                "123456",
//                RoleEnum.COACH,
//                SecurityQuestionEnum.FavoriteColor,
//                "white",
//                "http://localhost/sports-hub-images/395762775",
//                "jajajazjjjajaa",
//                "25478963",
//                LocalDate.of(1998,07,02),
//                "Tunis"
//        );
//        Coach coach1 = new Coach(
//                "shalbi",
//                "shalbob",
//                "salhbiazd@gmail.com",
//                "123456",
//                RoleEnum.COACH,
//                SecurityQuestionEnum.FavoriteColor,
//                "white",
//                "http://localhost/sports-hub-images/395762775",
//                "jajajazjjjajaa",
//                "25478963",
//                LocalDate.of(1998,07,02),
//                "Tunis"
//        );
//        Coach coach3 = new Coach(
//                "shalbi",
//                "shalbob",
//                "salhazdazdbi@gmail.com",
//                "123456",
//                RoleEnum.COACH,
//                SecurityQuestionEnum.FavoriteColor,
//                "white",
//                "http://localhost/sports-hub-images/395762775",
//                "jajajazjjjajaa",
//                "25478963",
//                LocalDate.of(1998,07,02),
//                "Tunis"
//        );
//        userService.create(coach);
//        userService.create(coach1);
//        userService.create(coach2);
//        userService.create(coach3);
//        CertificationServiceImpl certificationService = new CertificationServiceImpl();
//        Certification certification = new Certification("certif in yoga", SpecialityEnum.YOGA,35);
//        Certification certification1 = new Certification(" yoga", SpecialityEnum.BODYBUILDING,34);
//        certificationService.create(certification);
//        certificationService.create(certification1);
        launch(args);
    }
}
