package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.print.PrinterJob;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.*;
import java.time.LocalDate;

public class SubscriptionFactureController {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label lbldate;
    @FXML
    private Label lblcode;
    @FXML
    private Label lblpayment;
    @FXML
    private Label lblnom;
    @FXML
    private Label lblemail;
    @FXML
    private Label lblnum;
    @FXML
    private Label lblnufacture;
    @FXML
    private Label lblprix;
    @FXML
    private Label lblnomsalle;
    @FXML
    private Label lblnumpayment;
    @FXML
    private Label lblvalidity;
    @FXML
    public ImageView imgqrcode;
    @FXML
    public Button paye;
    private String prix="";
    private Gym gym;
    private int id;
    public void setVariable(Gym gym, int id,String code, String nomprenom, String email, String num, String prix,
                            int idfacture, String namesalle, String validity, String methodepayement){
        this.gym=gym;
        this.id=id;
        this.prix=prix;
        lbldate.setText(String.valueOf(LocalDate.now()));
        lblcode.setText(code);
        lblpayment.setText(methodepayement);
        lblnom.setText(nomprenom);
        lblemail.setText(email);
        lblnum.setText(num);
        lblnufacture.setText(String.valueOf(idfacture));
        lblnumpayment.setText(String.valueOf(idfacture));
        lblprix.setText(prix);
        lblnomsalle.setText(namesalle);
        lblvalidity.setText(validity);
        try {

            ByteArrayOutputStream out = QRCode.from("[Sportshub"+lblcode.getText()+":"+lblpayment.getText()+" "+lblvalidity.getText()
                            +"] Date"+lbldate.getText()+
                            "name:"+lblnom.getText()+" email:"+lblemail.getText()+" phone:"+lblnum.getText())
                    .to(ImageType.PNG).stream();
            Image qrcode=new Image(new ByteArrayInputStream(out.toByteArray()));
            imgqrcode.setImage(qrcode);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Imprimer(){
        //        Creation du printer job
        PrinterJob job = PrinterJob.createPrinterJob();
//        Montre la boite de dialogue
        boolean proceed = job.showPrintDialog(null);
//        If user does not hit cancel then print.
        if (proceed) {
            // Imprime la zone anchorpane
            boolean printed = job.printPage(anchorpane);
            if (printed) {
                job.endJob();
            }
        }else {
            job.endJob();
        }
    }

    public void Paye(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/Paiement.fxml"));
        root=loader.load();

        if(paye.getText().equals("Payer")){
            PaiementController pr=loader.getController();
            pr.setVariable(gym,id,lblcode.getText(), lblnom.getText(), lblemail.getText(), lblnum.getText(),  prix,
                    Integer.parseInt(lblnufacture.getText()),  lblnomsalle.getText(),lblvalidity.getText(), lblpayment.getText());
            HomeController.getInstance().getBorderPane().setCenter(root);
        }else if (paye.getText().equals("Imprimer")){
            Imprimer();
        }

    }
}
