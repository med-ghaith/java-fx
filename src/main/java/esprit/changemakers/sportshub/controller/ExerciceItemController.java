package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jozef
 */
public class ExerciceItemController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label diff;
    @FXML
    private Label equip;
    @FXML
    private Label muscle;
    @FXML
    private Label numberOfSets;
    @FXML
    private Label numberOfReps;
    @FXML
    private Label restTime;
    @FXML
    private Text textDesc;
    @FXML
    private VBox equipments;
    @FXML
    private VBox muscles;
    @FXML
    private ImageView exerciceImg;
    @FXML
    private VBox item;
    @FXML
    private Label idEx;

    boolean mouseClicked=true;


    ObservableList<Exercice> exercices = FXCollections.observableArrayList();
    IExerciceService exerciceService;


    public ExerciceItemController() {
        exerciceService = new ExerciceServiceImpl();
    }

    public void initialize(URL location, ResourceBundle resources) {
        name.setStyle("-fx-font-size: 30");
        textDesc.setWrappingWidth(290);
        diff.setStyle("-fx-text-fill: #3b8fae");
        //equipments.setStyle("-fx-text-fill: #3b8fae");

        item.setOnMouseEntered(event -> {
            System.out.println(GeneratedProgramController.getInstance().mouseClicled);
            if(GeneratedProgramController.getInstance().mouseClicled){

                Exercice exercice = exerciceService.getById(Integer.parseInt(idEx.getText()));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FullExerciceItem.fxml"));
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FullExerciceItemController fullExerciceItemController = loader.getController();
                fullExerciceItemController.setExerciceItem(exercice);
                GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().clear();
                GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().add(node);

            }
            //mouseClicked = true;
        });

        item.setOnMouseClicked(event -> {
            GeneratedProgramController.getInstance().mouseClicled = !GeneratedProgramController.getInstance().mouseClicled;
            Exercice exercice = exerciceService.getById(Integer.parseInt(idEx.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FullExerciceItem.fxml"));
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FullExerciceItemController fullExerciceItemController = loader.getController();
            fullExerciceItemController.setExerciceItem(exercice);
            GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().clear();
            GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().add(node);
            if(GeneratedProgramController.getInstance().mouseClicled == true){
                GeneratedProgramController.getInstance().mouseClicled = false;
            }
        });
    }

    public void setExerciceItem(Exercice exercice1){
        name.setText(exercice1.getName());
        textDesc.setText(exercice1.getDescription());
        diff.setText(exercice1.getDifficultyLevel().name());
        idEx.setText(String.valueOf(exercice1.getId()));

        exerciceImg.setImage(new Image(exercice1.getImageUrl()));
        if (exercice1.getEquipments().size() <= 3) {
            for (int i = 0; i < exercice1.getEquipments().size(); i++) {
                Label equip = new Label(exercice1.getEquipments().get(i).getName());
                equip.setStyle("-fx-text-fill: #3b8fae");
//                equipments.getChildren().add(equip);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                Label equip = new Label(exercice1.getEquipments().get(i).getName());
                equip.setStyle("-fx-text-fill: #3b8fae");
 //               equipments.getChildren().add(equip);
            }
        }
        if (exercice1.getMuscles().size() <= 3) {
            for (int i = 0; i < exercice1.getMuscles().size(); i++) {
                Label muscle = new Label(exercice1.getMuscles().get(i).getName());
                muscle.setStyle("-fx-text-fill: #3b8fae");
       //         muscles.getChildren().add(muscle);
            }
        } else {
            for (int i = 0; i < 3; i++) {
               // Label muscle = new Label(exercice1.getMuscles().get(i).getName());
               // muscle.setStyle("-fx-text-fill: #3b8fae");
               // muscles.getChildren().add(muscle);
            }
        }
        numberOfSets.setText(String.valueOf(exercice1.getNumberOfSets()));
        numberOfReps.setText(String.valueOf(exercice1.getNumberOfRepetition()));
        restTime.setText(exercice1.getRestTime());
    }

    public void loadFullExercice() {
        exercices = exerciceService.getAll();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FullExerciceItem.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FullExerciceItemController fullExerciceItemController = loader.getController();
        fullExerciceItemController.setExerciceItem(exercices.get(1));
        GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().clear();
        GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().add(node);
    }


}





