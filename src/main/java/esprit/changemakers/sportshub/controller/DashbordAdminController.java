package esprit.changemakers.sportshub.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @author Jozef
 */
public class DashbordAdminController {


    @FXML
    private BorderPane borderPane;

    // To make ur controller ( borderPane ) accessible from other controller u gotta add these three things ( thank me later )
    private static DashbordAdminController instance;
    public DashbordAdminController() { instance = this; }
    public static DashbordAdminController getInstance() { return instance; }

    public BorderPane getBorderPane() {
        return borderPane;
    }



    @FXML
    public void loadProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/viewProduct.fxml"));
        this.borderPane.setCenter(root);
    }

    @FXML
    public void loadReclamation() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/ReclamationListAdmin.fxml"));
        this.borderPane.setCenter(root);
    }

    @FXML
    public void loadExercices() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../views/ExerciceAdminList.fxml"));
        this.borderPane.setCenter(root);
    }
}
