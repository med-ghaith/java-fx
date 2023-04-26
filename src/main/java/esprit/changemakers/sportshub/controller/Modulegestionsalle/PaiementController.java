package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.SubscriptionDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Subscription;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.Parent;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.controlsfx.control.Notifications;


public class PaiementController implements Initializable {

    @FXML
    private TextField carte;
    @FXML
    private TextField month;
    @FXML
    private TextField cvc;
    @FXML
    private TextField year;
    @FXML
    private TextField prix;
    @FXML
    private Button valider;
    @FXML
    private Button Annuler;

    private String code;
    private String nomprenom;
    private String email;
    private String num;
    private String namesalle;
    private String validity;
    private int idfacture;
    private String methodepayement;
    private Gym gym;
    private int id;
    private Subscription sub=null;
    private Subscription subscription=null;
    private SubscriptionDaoImpl daosub=new SubscriptionDaoImpl();
    private static final String ACCOUNT_SID =
            "AC72b7064f45e8ed8b68b3ecd7d8532c7d";
    private static final String AUTH_TOKEN =
            "fb67cf9f287eff6c58484cb0c0efe348";

    Parent root;
    FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/SubscriptionFacture.fxml"));
    public void setVariable(Gym gym, int id,String code, String nomprenom, String email, String num, String prix,
                            int idfacture, String namesalle, String validity,String methodepayement) throws IOException {
        this.gym=gym;
        this.id=id;
        this.prix.setText(prix);
        this.code= "C"+code;
        this.nomprenom= nomprenom;
        this.email=email;
        this.num=num;
        this.namesalle=namesalle;
        this.validity= validity;
        this.idfacture=idfacture;
        this.methodepayement=methodepayement;
        root=loader.load();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valider.setOnAction((ActionEvent event) -> {
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
                    chargeParams.put("amount", Integer.parseInt(prix.getText()));
                    chargeParams.put("currency", "usd");
                    chargeParams.put("customer", a.getId());

                    Charge.create(chargeParams);
                    Notifications.create()
                            .title("Félicitation")
                            .text("Payment Salle Succes !!").position(Pos.CENTER)
                            .showInformation();
                    subscription=new Subscription(id, gym.getId(), LocalDate.now(), validity);
                    sub=daosub.save(subscription);
                    SubscriptionFactureController pr=loader.getController();
                    pr.setVariable(gym,id,code,nomprenom,email,num, prix.getText(),sub.getId(),namesalle,validity,methodepayement);
                    pr.paye.setText("Imprimer");
                    pr.imgqrcode.setVisible(true);
                    HomeController.getInstance().getBorderPane().setCenter(root);
                    send(num);
                }
            } catch (StripeException ex) {
                Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });


    }
//    fct sms api twilio
    public void send(String num){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber("+216"+num), // to
                        new PhoneNumber("+13132515103"), // from
                        "[Sportshub"+code+":"+methodepayement+" "+validity
                                +"] Date"+String.valueOf(LocalDate.now())+
                                "name:"+nomprenom+" email:"+email).create();
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
            }if (!Pattern.matches("[0-9][0-9]", year.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'année ! ");
                year.requestFocus();
                year.selectEnd();
                return false;
            }if (!Pattern.matches("[0-9]*", cvc.getText())) {
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
    private void annuler(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/Listegym.fxml"));
        root=loader.load();
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
}

