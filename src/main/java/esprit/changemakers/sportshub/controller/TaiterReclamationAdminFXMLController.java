/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import java.io.IOException;
import java.sql.SQLException;

import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.Impl.ReclamationServiceImpl;
import esprit.changemakers.sportshub.utils.Mailing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 *
 * @author med
 */
public class TaiterReclamationAdminFXMLController {
    
    @FXML
    private TextField typeReclamationtxt;
    @FXML
    private TextArea descriptiontxt;
    @FXML
    private TextField objettxt;
    @FXML
    private TextField DateReclamationtxt;
    @FXML
    private TextField statustxt;
    @FXML
    private TextField mailTxt;
    @FXML
    private TextField nomClientTxt;

    @FXML
    private Button repondre;
    @FXML
    private Button traiter;

    @FXML
    private TextArea messagetxt;
    @FXML
    private TextField objecttxt;
    
    Reclamation reclamation;
    ReclamationServiceImpl serviceReclamation = new ReclamationServiceImpl();
    UserDaoImpl c = new UserDaoImpl();
    //int id = HomeController.getInstance().getIdUser();
    
    public void setReclamation(Reclamation reclamation) throws SQLException {
        this.reclamation = reclamation;
        objettxt.setText(reclamation.getObject()); 
        objettxt.setDisable(true);
        typeReclamationtxt.setText(reclamation.getTypeReclamation());
        typeReclamationtxt.setDisable(true);
        descriptiontxt.setText(reclamation.getDescription());
        descriptiontxt.setDisable(true);
        
        DateReclamationtxt.setText(reclamation.getDateReclamation().toString());
        DateReclamationtxt.setDisable(true);
        statustxt.setText(reclamation.getStatus());
        statustxt.setDisable(true);
        User u = c.getById(reclamation.getIdUser());
        //User u = c.getById(29);
        nomClientTxt.setText(u.getNom());
        nomClientTxt.setDisable(true);
        mailTxt.setText(u.getEmail());
        mailTxt.setDisable(true);
    }
    
    public void onRepondre(ActionEvent event) throws IOException {
        Mailing.send(mailTxt.getText().toString(),objecttxt.getText() ,messagetxt.getText(), "", "");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Added");
        alert.setContentText("Reclamation treated succefully");
        alert.showAndWait();
    }

    public Reclamation getReclamation() {
        return reclamation;
    }
    
    public void onTraite(ActionEvent event){
        getReclamation().setStatus("Treated");
        serviceReclamation.updateReclamation(reclamation);
        User p = c.getById(getReclamation().getIdUser());
        //Notif Client
        Mailing.send(p.getEmail(), "Reclamation Treated", "Your reclamation of type "+getReclamation().getTypeReclamation()+" has treated succefully", "", "");
        Notifications n = Notifications.create()
                .title("Success")
                .text("Operation effectue")
                .graphic(null)
                .position(Pos.CENTER)
                .hideAfter(Duration.seconds(5));
        n.showConfirm();

        Stage stage = new Stage();
        stage.close();
    }
    
    
}
