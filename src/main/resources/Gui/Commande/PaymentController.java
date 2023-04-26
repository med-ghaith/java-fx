/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.Commande;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */
public class PaymentController implements Initializable {

    @FXML
    private WebView webview;
    @FXML
    private Pane banner;
    @FXML
    private JFXButton payerAction;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField moiExpiration;
    @FXML
    private TextField securityCode;
    @FXML
    private TextField anneeExpiration;
    @FXML
    private TextField cardHolderName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void payerAction(ActionEvent event) {
    }
    
}
