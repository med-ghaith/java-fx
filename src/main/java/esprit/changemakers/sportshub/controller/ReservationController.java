package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IReservationDao;
import esprit.changemakers.sportshub.dao.Impl.ReservationDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Reservation;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
//import org.controlsfx.control.Notifications;
import org.controlsfx.control.Notifications;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.Notification;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    @FXML
    private TextField inputemail;

    @FXML
    private TextField inputlastname;

    @FXML
    private TextField inputname;

    @FXML
    private TextField inputphone;
    @FXML
    public Label lbl6;

    public String msg;
    private  int iduser;

    public int getIduser() {
        return iduser;
    }

    private static ReservationController instance;
    public ReservationController() { instance = this;}
    public static ReservationController getInstance() { return instance; }
    IEventService es = new EventServiceImpl();

    private static final String EMAIL_PATTERN

            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

// System.out.println(email+name+lastname+phone);
//ObservableList<Reservation> listEt = FXCollections.observableArrayList();
    IReservationDao reservationDao = new ReservationDaoImpl();

    public void send(String recepient) throws MessagingException {

        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "mohamedali.benfarah@esprit.tn";
        //Your gmail password
        String password = "211JMT3421";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    public Message prepareMessage(Session session, String myAccountEmail, String recepient) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("User "+inputlastname.getText()+" "+inputname.getText()+"  Email  "+inputemail.getText()+"  Phone  "+inputphone.getText()+"  has reserved this event with id : "+EventinfoController.getInstance().idEvent.getText());
            //String htmlCode = "<h1> WE LOVE JAVA </h1> <br/> <h2><b>Next Line </b></h2>";
            //message.setContent(ReservationController., "text/html");
            String x = inputemail.getText();
            message.setText(x);

            //System.out.println(msg);
            return message;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (HomeController.getInstance().getIdUser() != 0)
            iduser = HomeController.getInstance().getIdUser();
    }


    public void submit() {


        //String x= inputemail.getText();
        if (inputname.getText().isEmpty() || inputlastname.getText().isEmpty() || inputemail.getText().isEmpty() || inputphone.getText().isEmpty()) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();

        } else if (!inputphone.getText().matches("[0-9]*")||inputphone.getText().length()!=8) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Enter a valid phone number");
            err.showAndWait();
        } else if (!inputemail.getText().matches(EMAIL_PATTERN)) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Enter a valid Email");
            err.showAndWait();
        } else if (!inputlastname.getText().matches("^[a-zA-Z]+$") || !inputname.getText().matches("^[a-zA-Z]+$")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Enter a valid Name");
            err.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setHeaderText("Confirmation Of reservation");
            alert.setContentText("Are you Sure you want to submit ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Reservation reservation = new Reservation( Integer.parseInt(EventinfoController.getInstance().idEvent.getText()), iduser,"Reserved");
                System.out.println(iduser);
                reservationDao.save(reservation);
                //listEt.add(reservation);
                String title = "Congratulations !";
                String message = "You've successfully Made a reservation ";
                NotificationType notification = NotificationType.SUCCESS;

                TrayNotification tray = new TrayNotification();
                tray.setTitle(title);
                tray.setMessage(message);
                tray.setNotificationType(notification);
                tray.showAndWait();
                try {
                    send("mohamedali.benfarah@esprit.tn");
                } catch (MessagingException e) {
                    System.out.println(e);
                }

            } else {

            }


        }
    }

    public void back(ActionEvent actionEvent) {
        PlanningController.getInstance().getPnlOverview().getChildren().clear();
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/Item.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventinfoController.getInstance().idEvent.setText(lbl6.getText());
        EventinfoController.getInstance().desc.setText(es.getById(Integer.parseInt(lbl6.getText())).getDescription());
        EventinfoController.getInstance().lblstartdate.setText((es.getById(Integer.parseInt(lbl6.getText())).getStart_date()).toString());
        EventinfoController.getInstance().end.setText((es.getById(Integer.parseInt(lbl6.getText())).getEnd_date()).toString());
        EventinfoController.getInstance().nbr.setText(Integer.toString(es.getById(Integer.parseInt(lbl6.getText())).getNombreReservation()));
        EventinfoController.getInstance().price.setText(Integer.toString(es.getById(Integer.parseInt(lbl6.getText())).getFees()));
        EventinfoController.getInstance().imgevent.setImage(new Image(es.getById(Integer.parseInt(lbl6.getText())).getImageUrl()));

        PlanningController.getInstance().getPnlOverview().getChildren().setAll(node);


    }
}