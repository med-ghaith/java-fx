/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.services.Impl.ReclamationServiceImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;


/**
 *
 * @author med
 */
public class ReclamationController implements Initializable {
    
    private static ReclamationController instance;
    
    public ReclamationController () { instance = this; }
    public static ReclamationController getInstance() { return instance; }
    
    private String[] ty = {"FRAUD",

    "RACISM",

    "FAKEUSER",

    "VIOLENCE",

    "HARASSEMENT",

    "SCAM",

    "POLICYVIOLATION"};
    ReclamationServiceImpl ser = new ReclamationServiceImpl();
    
    
     
    @FXML
    private TextField object_txtField;
    
    @FXML
    private ChoiceBox<String> type;
    
    @FXML
    private TextArea desc_textArea;
    
    @FXML
    private Button send_btn;
    
    @FXML 
    private VBox v;
    
    @FXML
    private AnchorPane content;
    
    @FXML
    private TextField object_txtField_mf;

    @FXML
    private TextArea desc_textArea_mf;
    
    @FXML
    private Button update_btn;
    
    
    
    
    
     

    
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try { 
            type.getItems().addAll(ty);
        //type.setOnAction(this::pushReclamation);
        } catch (Exception e) {
            System.out.println("error");
        }
        
    }
    
    public void pushReclamation(ActionEvent event){
        int id = HomeController.getInstance().getIdUser();
        System.out.println(id);
        if (id == 0){
            Alert alerts = new Alert(Alert.AlertType.WARNING);
            alerts.setTitle("Warning");
            alerts.setHeaderText(null);
            alerts.setContentText("You must log in first!");
            alerts.show();
        }else {
            String typeRec = type.getValue();

            Reclamation r = new Reclamation(id, typeRec, object_txtField.getText(), desc_textArea.getText());
            if (object_txtField.getText().isEmpty() || desc_textArea.getText().isEmpty() || type.getValue().isEmpty()){
                Alert alerts = new Alert(Alert.AlertType.WARNING);
                alerts.setTitle("Warning");
                alerts.setHeaderText(null);
                alerts.setContentText("Please fill in the fields!");
                alerts.show();
            }else {
                ser.addReclamation(r);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Reclamation added succefully!");
                alert.show();
                object_txtField.setText("");
                desc_textArea.setText("");
            }
        }

    }

     public void addRec(ActionEvent event) throws IOException{
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ReclamationAdd.fxml"));
    content.getChildren().setAll(pane);
    
    
    }
     
     public void affichRec(ActionEvent event) throws IOException{
         content.getChildren().clear();
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ReclamationListUser.fxml"));
    content.getChildren().setAll(pane);
    
    
    }
     
     public void updateRec(ActionEvent event) throws IOException{
         content.getChildren().clear();
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ReclamationModif.fxml"));
    content.getChildren().setAll(pane); 
    }
     
     public AnchorPane getContent(){
     return content;
     }
     
      public void setObject(String obj){
         object_txtField_mf.setText(obj);
     
     }
     
     public void setDescription(String desc){
         desc_textArea_mf.setText(desc);
     
     }
     public void setType(String desc){
         type.setValue(desc);
     
     }
     
     public Button getBtn(){
     return update_btn;
     }
     
     public String getObject(){
     return object_txtField_mf.getText();
     }
     public String getDescription(){
     return desc_textArea_mf.getText();
     }
     public String getType(){
         return type.getValue();
     
     }
     
      
     @FXML
     public void updateReclamation(ActionEvent event){
     
     }

    public void exit(ActionEvent event){
        Platform.exit();
    }


     
      
     
     
    
    
}
