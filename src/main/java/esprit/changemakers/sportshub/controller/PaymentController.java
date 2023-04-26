/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import esprit.changemakers.sportshub.dao.Impl.CartDaoImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */
public class PaymentController implements Initializable {
    CartDaoImp prods = new CartDaoImp();


    @FXML
    private TextField cvc;
    @FXML
    private Label total;
    @FXML
    private Button valider;
    @FXML
    private TextField carte;
    @FXML
    private TextField month;
    @FXML
    private TextField year;
    private String price = "150";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        valider.setOnAction((ActionEvent event) -> {

            if (controleDeSaisi()){
                Twilio.init("ACb69d5d067deb23f315591c6ffd9e137f", "c95c565b0f9909667a34a34b5731649d");
                Message message = Message.creator(new PhoneNumber("+21650826004"),
                        new PhoneNumber("+13185451800"),
                        "votre payment a ete effectuer avec succee").create();
            }
            try {
                if (controleDeSaisi()) {
                    if (carte.getText().isEmpty()) {
                        carte.setText("");
                    }
                    if (month.getText().isEmpty()) {
                        month.setText("");
                    }
                    if (year.getText().isEmpty()) {
                        year.setText("");
                    }
                    if (cvc.getText().isEmpty()) {
                        cvc.setText("");
                    }
                    Stripe.apiKey = "sk_test_flb9vUYyiwC85Wx2ONpANeYf";

                    Customer a = Customer.retrieve("cus_EabQCFUrV5J5qW");
                    Map<String, Object> cardParams = new HashMap<String, Object>();
                    cardParams.put("number", Long.parseLong(carte.getText()));
                    cardParams.put("exp_month", Integer.parseInt(month.getText()));
                    cardParams.put("exp_year", Integer.parseInt(year.getText()));
                    cardParams.put("cvc", Integer.parseInt(cvc.getText()));

                    Map<String, Object> tokenParams = new HashMap<String, Object>();
                    tokenParams.put("card", cardParams);

                    Token token = Token.create(tokenParams);

                    Map<String, Object> source = new HashMap<String, Object>();
                    source.put("source", token.getId());

                    Map<String, Object> chargeParams = new HashMap<String, Object>();
                    chargeParams.put("amount", Integer.parseInt(price));
                    chargeParams.put("currency", "usd");
                    chargeParams.put("customer", a.getId());

                    Charge.create(chargeParams);
                    showAlert(Alert.AlertType.INFORMATION, "Payment Succes ",
                            "Félicitattion!", "Succes payment félicitattion! ");
                }

            } catch (StripeException ex) {
                Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    private boolean controleDeSaisi() {

        if (carte.getText().isEmpty() || month.getText().isEmpty() || year.getText().isEmpty()
                || cvc.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", carte.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le code de votre carte ! ");
                carte.requestFocus();
                carte.selectEnd();
                return false;
            }

            if (!Pattern.matches("[0-9][0-9]", month.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le Mois ! ");
                month.requestFocus();
                month.selectEnd();
                return false;
            }
            if (!Pattern.matches("[0-9][0-9]", year.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'année ! ");
                year.requestFocus();
                year.selectEnd();
                return false;
            }
            if (!Pattern.matches("[0-9]*", cvc.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le cvc ! ");
                cvc.requestFocus();
                cvc.selectEnd();
                return false;
            }

        }
        return true;
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    @FXML
    private void telid(ActionEvent event) {
    }



    


}



