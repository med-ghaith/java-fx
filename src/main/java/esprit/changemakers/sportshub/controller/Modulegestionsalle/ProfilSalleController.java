package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.GymDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.services.Impl.Modulegestionsalle.SalleService;
import esprit.changemakers.sportshub.utils.UploadFileToServer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class ProfilSalleController {
    private Gym gym=null;
    private ObservableList<Course> cours=null;
    private SalleService s;
    private int id=0;


    @FXML
    private Label lblname;
    @FXML
    private Label LabelId;
    @FXML
    private TextArea tfdescription;
    @FXML
    private TextField tflocation;
    @FXML
    private Button btn;
    @FXML
    private ImageView iconplus;
    @FXML
    private ImageView Imageprofil;
    @FXML
    private ImageView addphoto;
    @FXML
    private Button btncontact;
    @FXML
    private GridPane grid;
    @FXML
    private GridPane gridreview;

    private MyListenerVideo myListener;
    private File selectedFile;

    //    To make ur controller ( borderPane ) accesable from other controller u gotta add these three things
    private static ProfilSalleController instance;
    public ProfilSalleController() {
        instance = this;
        this.id=HomeController.getInstance().getIdUser();

    }
    public static ProfilSalleController getInstance() { return instance; }
    //    fait appel a la classe salleservice
    public void setData(Gym gym) {
        if(this.id==gym.getUser_id()){
            btn.setText("Modifier");
            iconplus.setVisible(true);
        }

        s =new SalleService();
        this.gym=gym;
        cours=s.getCoursbyGymId(gym.getId());
        LabelId.setText(String.valueOf(gym.getId()));
        lblname.setText(gym.getName());
        tfdescription.setText(gym.getDescription());
        tflocation.setText(gym.getLocation());
        if(gym.getImage().isEmpty()){
            Imageprofil.setImage(new Image("@../../assets/imgs/blank-profile-picture.png"));
        }else{
            Imageprofil.setImage(new Image(gym.getImage()));
        }

//        remplissage du gridpane avec la data video cours
        if (cours.size() > 0) {
            myListener = new MyListenerVideo() {
                @Override
                public void onClickListenerVideo(Course course) throws IOException {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/InterfaceVideo.fxml"));
                    root=loader.load();
                    InterfaceVideoController pr=loader.getController();
                    pr.setVariable(course,gym);
                    HomeController.getInstance().getBorderPane().setCenter(root);
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

                if (column == 3) {
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../../../../../views/ListCommentsFXML.fxml"));
        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gridreview.add(pane,0,1);

    }


    //    btn qui permet de switch a l'interface formulaireaddcours
    private Parent root;
    public void Add(MouseEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/Formulaireaddcours.fxml"));
        root=loader.load();
        FormulairecoursController Interfformulaire=loader.getController();
        Interfformulaire.setVariable(gym,cours);
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
    public void Abonner(ActionEvent actionEvent) throws IOException {
        if(this.id==0){
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setContentText("Connectez-vous ! \n");
            alert.show();
        }else if(btn.getText().toLowerCase(Locale.ROOT).equals("s'abonner")){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/InterfaceSubscription.fxml"));
            root=loader.load();
            InterfaceSubscriptionController interfaceSubscriptionController=loader.getController();
            interfaceSubscriptionController.setVariable(gym,this.id);
            HomeController.getInstance().getBorderPane().setCenter(root);
        }else if(!tfdescription.isEditable()){
            tfdescription.setEditable(true);
            tflocation.setEditable(true);
            addphoto.setVisible(true);
            tfdescription.setDisable(false);
            tflocation.setDisable(false);
        }else if(tfdescription.isEditable()){
            tfdescription.setEditable(false);
            tflocation.setEditable(false);
            addphoto.setVisible(false);
            Modifier(null);
            tfdescription.setDisable(true);
            tflocation.setDisable(true);
        }

    }
    //      fct pour modifier description et location
    public void Modifier(KeyEvent keyEvent) {
        UploadFileToServer.uploadFile(selectedFile.getAbsolutePath());
        gym.setLocation(tflocation.getText());
        gym.setDescription(tfdescription.getText());
        gym.setImage("http://localhost/sports-hub-images/"+selectedFile.getName());
        GymDaoImpl daogym=new GymDaoImpl();
        daogym.update(gym);
    }

    public String getGId() {
        return LabelId.getText();
    }

    public void onChoseFile(MouseEvent event){
        FileChooser fc = new FileChooser();
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                Imageprofil.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

