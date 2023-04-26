package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.services.APIs.SavePdf;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jozef
 */
public class ExerciceAdminListController implements Initializable {

    @FXML
    private HBox exercicesHbox;
    @FXML
    private VBox fullDisplayVbox;
    @FXML
    private VBox itemVb;
    @FXML
    private Button addItemBtn;

    private int pairAdd;
    public boolean mouseClicked =true;

    public ObservableList<HBox> hBoxes;


    public List<Exercice> exercices;

    ObservableList<Node> nodes;


    // To make ur controller ( borderPane ) accesable from other controller u gotta add these three things ( thank me later )
    private static ExerciceAdminListController instance;
    public ExerciceAdminListController() { instance = this; }
    public static ExerciceAdminListController getInstance() { return instance; }

    public HBox getExercicesHbox() {
        return exercicesHbox;
    }

    public void setExercicesHbox(HBox exercicesHbox) {
        this.exercicesHbox = exercicesHbox;
    }

    public VBox getFullDisplayVbox() {
        return fullDisplayVbox;
    }

    public void setFullDisplayVbox(VBox fullDisplayVbox) {
        this.fullDisplayVbox = fullDisplayVbox;
    }



    IExerciceService exerciceService = new ExerciceServiceImpl();

    public void loadExercices() throws IOException {
        pairAdd=0;
        nodes = FXCollections.observableArrayList();
        Node node=null;
        exercices = exerciceService.getAll();
        HBox hBox = new HBox();
        for (int i = 0; i < exercices.size(); i++) {

            if(pairAdd%2 == 0){
                hBox = new HBox();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExerciceAdminItem.fxml"));
            node = loader.load();

            ExerciceAdminItemController adminItemController = loader.getController();

            System.out.println(adminItemController);
            adminItemController.setExerciceItem(exercices.get(i));
            nodes.add(node);
            hBox.getChildren().add(node);
            hBox.setSpacing(30);
            hBox.setAlignment(Pos.CENTER);
            pairAdd++;
            if(pairAdd%2 != 0){
                itemVb.getChildren().add(hBox);
            }
            hBoxes.add(hBox);
        }




        //exercicesHbox.getChildren().addAll(nodes);
        // add first node by default
        if(exercices.get(0).getId()!=0){
            Exercice exercice = exerciceService.getById(exercices.get(0).getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExerciceItemAdminEditAdd.fxml"));
            Node fullExerciceNode = null;
            try {
                fullExerciceNode = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ExerciceItemAdminEditAddController addController = loader.getController();
            addController.setExerciceItem(exercice);
            //GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().clear();
            ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().add(fullExerciceNode);
        }

    }

    public void deleteNode(int hashcode){
        nodes.stream().filter(e->((int)e.hashCode())==hashcode).forEach(e-> System.out.println(e.hashCode()));
        nodes.removeIf(e->((int)e.hashCode())==hashcode);
        //itemVb.getChildren().stream().forEach();

        itemVb.getChildren().stream().forEach(e->((HBox)(e)).getChildren().removeIf(e1-> (((VBox)e1).hashCode()) == hashcode));

        itemVb.getChildren().removeIf(e->((HBox)(e)).getChildren().isEmpty());

        // TODO call service to delete from database

    }




    public void initialize(URL location, ResourceBundle resources) {
        //exercicesHbox.setSpacing(30);
        hBoxes = FXCollections.observableArrayList();
        try {
            loadExercices();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExerciceItemAdminEditAdd.fxml"));
                Node fullExerciceNode = null;
                try {
                    fullExerciceNode = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().clear();
                ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().add(fullExerciceNode);
                ExerciceItemAdminEditAddController.getInstance().idItemToEdit.setText("0");
                ExerciceItemAdminEditAddController.getInstance().descTA.setVisible(true);
                ExerciceItemAdminEditAddController.getInstance().nameTf.setVisible(true);
            }
        });
    }




}
