package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.GymDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.utils.UploadFileToServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormulairesalleController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLocation;
    @FXML
    private TextArea tfDescription;
    @FXML
    private Button closeButton;
    @FXML
    private Button chosefile;
    @FXML
    private ImageView imgprofil;
    private File selectedFile;
    private int id =0;

    public FormulairesalleController() {
        this.id=HomeController.getInstance().getIdUser();
    }

    public void Add(ActionEvent event) throws IOException {
        GymDaoImpl s = new GymDaoImpl();
        UploadFileToServer.uploadFile(selectedFile.getAbsolutePath());
        Gym g = new Gym(38,tfName.getText(), tfDescription.getText() , tfLocation.getText(),"http://localhost/sports-hub-images/"+selectedFile.getName());
        s.save(g).getId();
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        root=loader.load();
        HomeController.getInstance().getBorderPane().setCenter(root);

    }
    public void Annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void onChoseFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                imgprofil.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("image plz");
        }
    }
}
