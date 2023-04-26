package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.IUserService;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * @author Jozef
 */
public class HomeController implements Initializable {
    private static int idUser;

    @FXML
    private BorderPane borderPane;

    @FXML
    public Label idUserLb;
    @FXML
    public Label userName;

    @FXML
    private Pane revpane;

    public void setIdUser(int id) {
        this.idUser = id;
    }

    public static int getIdUser() {
        return idUser;
    }


    @FXML
    Label signUpLabel;

    @FXML
    Label signInLabel;

    @FXML
    Label welcomeMsg;

    @FXML
    Label loadChat;

    @FXML
    private Pane recPane;

    @FXML
    public Label changIdC;



    @FXML
    private HBox navBar;
    @FXML
    private HBox navBarHbox;
    @FXML
    private Label shop;

    // To make ur controller ( borderPane ) accessible from other controller u gotta add these three things ( thank me later )
    private static HomeController instance;

    public HomeController() {
        instance = this;
    }


    public static HomeController getInstance() {
        return instance;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadHome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/InterfaceHome.fxml"));
        this.borderPane.setCenter(root);
    }


    @FXML
    public void loadChatBot() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/CoachBotView.fxml"));
        this.borderPane.setCenter(root);
    }

    @FXML
    public void loadChat() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/Chat.fxml"));
        this.borderPane.setCenter(root);
    }

    void goToReclam(MouseEvent event) throws IOException {
        Pane reclamation = FXMLLoader.load(getClass().getResource("/views/ReclamationMenu.fxml"));
        this.borderPane.setCenter(reclamation);
    }

    @FXML
    public void loadGym() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/Modulegestionsalle/Listegym.fxml"));
        this.borderPane.setCenter(root);
    }


    @FXML
    public void loadShop() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/ShopProduct.fxml"));
        this.borderPane.setCenter(root);
    }


    @FXML
    public void loadLogin() throws IOException {
        if (signInLabel.getText().equals("Sign-in")) {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            this.borderPane.setCenter(root);
        } else if (signInLabel.getText().equals("MyProfile")) {
            Parent root = FXMLLoader.load(getClass().getResource("/views/MyProfile.fxml"));
            this.borderPane.setCenter(root);
        }
    }

    @FXML
    void goToReview(MouseEvent event) throws IOException {
        Parent review = FXMLLoader.load(getClass().getResource("/views/ReclamationMenu.fxml"));
        this.borderPane.setCenter(review);
    }


    public void loadSignup() throws IOException {
        if (signUpLabel.getText().equals("Sign-up")) {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Signup.fxml"));
            this.borderPane.setCenter(root);
        } else if (signUpLabel.getText().equals("Log-out")) {
            this.idUserLb.setText("");
            this.setIdUser(0);
            signInLabel.setText("Sign-in");
            signUpLabel.setText("Sign-up");
            welcomeMsg.setVisible(false);
            userName.setText("");
            loadLogin();
        }
    }


    public void loadGeneratedItem() throws IOException {
        IExerciceService exerciceService = new ExerciceServiceImpl();
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/GeneratedProgram.fxml"));
        //GeneratedProgramController.getInstance().exercices.addAll(exerciceService.getAll());
        this.borderPane.setCenter(root);
    }




    public void setUser() {
        IUserService userService = new UserServiceImpl();
        if (!idUserLb.getText().isEmpty()) {
            User user = userService.getById(Integer.parseInt(idUserLb.getText()));
            userName.setText(user.getNom());

        }
    }


    private void affiche() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
        borderPane.setCenter(pane);

    }

    @FXML
    private void onclick(MouseEvent event) throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ShopProduct.fxml"));
        borderPane.setCenter(pane);
    }




    public void loadMembers() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/memberView.fxml"));
        this.borderPane.setCenter(root);
    }


   /* public void setUser() {
        IUserService userService = new UserServiceImpl();
        if (!idUserLb.getText().isEmpty()) {
            User user = userService.getById(Integer.parseInt(idUserLb.getText()));
            userName.setText(user.getNom());
        }
    }*/


    /*private void affiche() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
        borderPane.setCenter(pane);

    }*/

   /* private void onclick(MouseEvent event) throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ShopProduct.fxml"));
        borderPane.setCenter(pane);
    }*/


 /*   @FXML
    public void Planning() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
        borderPane.setCenter(pane);
    }*/

    public void Planning() throws IOException {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
        BorderPane borderPane2 = FXMLLoader.load(getClass().getResource("/views/CoachesInterface.fxml"));
        borderPane.setCenter(borderPane2);
    }


}
