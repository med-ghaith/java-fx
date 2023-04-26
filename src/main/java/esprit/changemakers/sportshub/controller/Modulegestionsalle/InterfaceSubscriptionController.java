package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.CourseDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.SubscriptionDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Subscription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;


public class InterfaceSubscriptionController {
    @FXML
    private ChoiceBox choicebox;
    @FXML
    private CheckBox paymentligne;
    @FXML
    private CheckBox paymentplace;
    @FXML
    private Button imprimerbtn;
    @FXML
    private Button btnannuler;
    @FXML
    private Button btnadd;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfphone;

    private Subscription subscription=null;
    private SubscriptionDaoImpl daosub=new SubscriptionDaoImpl();
    private static final String ACCOUNT_SID =
            "AC72b7064f45e8ed8b68b3ecd7d8532c7d";
    private static final String AUTH_TOKEN =
            "fb67cf9f287eff6c58484cb0c0efe348";

    private String[] duree={"1mois","2mois","3mois","4mois","5mois"
            ,"6mois","7mois","8mois","9mois","10mois","11mois"
            ,"12mois"};

    private Gym gym;
    private int id;
    private Subscription sub=null;

    public void setVariable(Gym gym, int id){
        this.gym=gym;
        this.id=id;
        choicebox.getItems().addAll(duree);
    }

    public void Add(ActionEvent actionEvent) throws IOException {
        if(controleDeSaisi()&&paymentplace.isSelected()){
            subscription=new Subscription(id, gym.getId(), LocalDate.now(), (String) choicebox.getValue());
            sub=daosub.save(subscription);
                afficherbutton();
                send(tfphone.getText());
        }else if(controleDeSaisi()&&paymentligne.isSelected()){
                SubscriptionFactureController pr=setvariable();
                pr.imgqrcode.setVisible(false);
                HomeController.getInstance().getBorderPane().setCenter(root);
        }
    }

    private void send(String num){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber("+216"+num), // to
                        new PhoneNumber("+13132515103"), // from
                        "[Sportshub"+sub.getId()+":"+paymentplace.getText()+choicebox.getValue()
                                +"]\nDate "+String.valueOf(LocalDate.now())+
                                "\nname: "+tfnom.getText()+" "+tfprenom.getText()+"\n email:"+tfemail.getText()).create();
    }
    private void afficherbutton() {
        imprimerbtn.setVisible(true);
        tfprenom.setVisible(false);
        tfnom.setVisible(false);
        tfemail.setVisible(false);
        tfphone.setVisible(false);
        btnadd.setVisible(false);
        btnannuler.setVisible(false);
        choicebox.setVisible(false);
        paymentligne.setVisible(false);
        paymentplace.setVisible(false);
    }
//    retour au profil
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/ProfilSalle.fxml"));
        root=loader.load();
        ProfilSalleController pr=loader.getController();
        pr.setData(gym);
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
//    fct de la btn imprimer facture
    Parent root;
    FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/SubscriptionFacture.fxml"));
    public void Imprimer(ActionEvent actionEvent) throws IOException {
        SubscriptionFactureController pr=setvariable();
        pr.paye.setVisible(false);
        pr.Imprimer();


    }
//    fct pour set les variable dans l'interface subscriptionfacture
    public SubscriptionFactureController setvariable() throws IOException {
        String code= "C"+String.valueOf(gym.getId())+String.valueOf(id);
        String nomprenom= tfnom.getText()+" "+tfprenom.getText();
        String email=tfemail.getText();
        String num=tfphone.getText();
        String namesalle=gym.getName();
        String validity= (String) choicebox.getValue();
        String prix= String.valueOf(Integer.parseInt(((String) choicebox.getValue()).split("mois")[0])*90);
        int idfacture=0;
        String methodepayement="";
        if(paymentplace.isSelected()){
            idfacture=sub.getId();
            methodepayement=paymentplace.getText();
        }else if (paymentligne.isSelected()){
            methodepayement=paymentligne.getText();
        }
        root=loader.load();
        SubscriptionFactureController pr=loader.getController();
        pr.setVariable(gym,id,code,nomprenom,email,num,prix,idfacture,namesalle,validity,methodepayement);
        return pr;
    }
//    fct selecte chosebox
    public void Selectedplace(ActionEvent actionEvent) {
            paymentligne.setSelected(false);
    }
    public void Selectedligne(ActionEvent actionEvent) {
            paymentplace.setSelected(false);
    }
//    fct de controlle de saissi
    private boolean controleDeSaisi() {

        if (tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() || tfemail.getText().isEmpty()
                    || tfphone.getText().isEmpty()||choicebox.getValue().equals("")) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
                return false;
        } else {
            if (!Pattern.matches("[A-Z]*[a-z]*", tfnom.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le nom  ! ");
                    tfnom.requestFocus();
                    tfnom.selectEnd();
                    return false;
            }
            if (!Pattern.matches("[A-Z]*[a-z]*", tfprenom.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le prenom ! ");
                    tfprenom.requestFocus();
                    tfprenom.selectEnd();
                    return false;
            }
            if (!Pattern.matches("[A-Za-z]*[0-9]*@[A-Za-z]*[\\.][A-Za-z]*", tfemail.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le mail ! ");
                    tfemail.requestFocus();
                    tfemail.selectEnd();
                    return false;
            }
            if (!Pattern.matches("[0-9]*", tfphone.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez votre numero ! ");
                    tfphone.requestFocus();
                    tfphone.selectEnd();
                    return false;
            }

        }
            return true;
    }
//    fct alert
    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
