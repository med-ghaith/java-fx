package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.CourseDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.services.Impl.Modulegestionsalle.SalleService;
import esprit.changemakers.sportshub.utils.UploadFileToServer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


public class FormulairecoursController  {

    private Gym gym;
    private ObservableList<Course> cours;
    private SalleService s;
    private CourseDaoImpl courseDao=new CourseDaoImpl();
    @FXML
    private Label lblname;
    @FXML
    private TextArea tfdescription;
    @FXML
    private TextField tfnamecours;
    @FXML
    private TextField tfurlvideo;
    @FXML
    private TableView table;
    @FXML
    private GridPane grid;
    @FXML
    private TableColumn <Course,String>namecours;
    @FXML
    private TableColumn <Course,String>descriptioncours;
    @FXML
    private TableColumn <Course,String>urlvideor;
    private MyListenerVideo myListener;

//    pour ne pas acceder a la BD je set les variable gym et cours
    public void setVariable(Gym gym,ObservableList<Course> cours){
        this.gym =gym;
        this.cours =cours;
        lblname.setText(gym.getName());
        if (cours.size() > 0) {
            myListener = new MyListenerVideo() {
                @Override
                public void onClickListenerVideo(Course course) throws IOException {
                    System.out.println(course);
                    data=course;
                    onSelect();

                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < cours.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../../../../../views/Modulegestionsalle/ItemsVideo.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemsVideoController itemController = fxmlLoader.getController();
                itemController.setData(cours.get(i),myListener);

                if (column == 7) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//   fct de controlle de saissi
    private boolean controleDeSaisi() {

        if (tfnamecours.getText().isEmpty()|| tfdescription.getText().isEmpty()|| tfurlvideo.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {
            if (!Pattern.matches("[A-Z a-z ]+[0-9]*", tfnamecours.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le nom du cours  ! ");
                tfnamecours.requestFocus();
                tfnamecours.selectEnd();
                return false;
            }
            if (!Pattern.matches("[A-Z a-z ]+[0-9]*", tfdescription.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez la description ! ");
                tfdescription.requestFocus();
                tfdescription.selectEnd();
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
//    fct du btn Ajouter cours permet l ajout du cours
//    je verifier les input si ils sont vide sinn je fais l'ajout
//    et je verifier si les deux case tfnbmax et tfprice sont belle et bien des entiers
    public void Add(ActionEvent actionEvent) throws IOException {

            if(controleDeSaisi()){
            Course c = new Course(gym.getId(), tfnamecours.getText(), tfdescription.getText(), tfurlvideo.getText());
            courseDao = new CourseDaoImpl();
            courseDao.save(c);
            cours.add(c);
//            set input to null
            Reset();
            cours.removeAll();
            SalleService salleService=new SalleService();
            cours=salleService.getCoursbyGymId(gym.getId());
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            grid.getChildren().clear();
            setVariable(gym,cours);

        }
    }
//    fct du btn Modifier cours permet l modifier du cours
//    elle set les input avec les donnees du data et permet la modification dans la BD
    public void Update(ActionEvent actionEvent)  {
            if(controleDeSaisi()){
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifier");
            alert.setContentText("Vous etes sur de vouloir Modifier cette cours");
            if(alert.showAndWait().get()==ButtonType.OK){
                cours.remove(data);
                data.setName(tfnamecours.getText());
                data.setDescription(tfdescription.getText());
                data.setVideo(tfurlvideo.getText());
                setInput();
                courseDao.update(data);
                grid.getColumnConstraints().clear();
                grid.getRowConstraints().clear();
                grid.getChildren().clear();
                setVariable(gym,cours);
                Reset();
            }
        }
    }
//    fct du btn Supprimer cours permet l supprimer du cours
    public void Delete(ActionEvent actionEvent) throws IOException {
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setContentText("Vous etes sur de vouloir supprimer cette cours");
        if(alert.showAndWait().get()==ButtonType.OK){
            cours.remove(data);
            courseDao.deleteById(data.getId());
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            grid.getChildren().clear();
            setVariable(gym,cours);
            Reset();
        }

    }
//   fct du btn Reset pour vider tout les input
    public void Reset(ActionEvent actionEvent) {
        Reset();
    }
//    fct pour vider tout les input
    public void Reset() {
        tfnamecours.setText("");
        tfdescription.setText("");
        tfurlvideo.setText("");

    }
//    fct du btn retour qui permet de revenir a l'interface Profilsalle
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/ProfilSalle.fxml"));
        root=loader.load();
        ProfilSalleController pr=loader.getController();
        pr.setData(gym);
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
//    fct qui permet de set les input de l'interface avec les données de la line table
//    et supprime l'ancien data de la liste cours et l ajoute apres avoir set les inputs
    public void setInput(){
        cours.remove(data);
        data.setName(tfnamecours.getText());
        data.setVideo(tfurlvideo.getText());
        data.setDescription(tfdescription.getText());
        cours.add(data);

    }
//    fct onselect permet de selectionne l'items
//    et rempli les input avec les donnees necessaire
//    data est une variable de type cours,sera recupere de l'items choisi pour la modification ou la supprimation
    Course data;
    public void onSelect() {
        tfnamecours.setText(data.getName());
        tfdescription.setText(data.getDescription());
        tfurlvideo.setText(data.getVideo());
    }

    public void Addvideo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );
        File filepath=fileChooser.showOpenDialog(null);
        if(filepath!=null){
            tfurlvideo.setText(String.valueOf(filepath));
            UploadFileToServer.uploadFile(String.valueOf(filepath));
        }

    }
}
