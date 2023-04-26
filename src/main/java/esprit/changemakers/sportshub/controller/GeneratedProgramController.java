package esprit.changemakers.sportshub.controller;

import com.itextpdf.text.Image;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.services.APIs.SavePdf;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
public class GeneratedProgramController implements Initializable {
    @FXML
    private HBox exercicesHbox;
    @FXML
    private VBox fullDisplayVbox;
    @FXML
    private BorderPane borderPane;
    @FXML
    public VBox selectedItem;

    public boolean mouseClicled = true;

    public List<byte[]> imagesBytePdf = new ArrayList<>();

    public List<Exercice> exercices;
    @FXML
    private Button downloadPdf;

    // To make ur controller ( borderPane ) accesable from other controller u gotta add these three things ( thank me later )
    private static GeneratedProgramController instance;
    public GeneratedProgramController() { instance = this; }
    public static GeneratedProgramController getInstance() { return instance; }

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
        //Exercice exercice = exerciceService.getAll().get(1);
        Node node=null;
         exercices = exerciceService.getAll();
        for (int i = 0; i < 4; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExerciceItem.fxml"));
            node = loader.load();
            ExerciceItemController itemController = loader.getController();
            itemController.setExerciceItem(exercices.get(i));
            exercicesHbox.getChildren().add(node);
            WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            ImageIO.write( SwingFXUtils.fromFXImage( snapshot, null ), "png", byteOutput );
            byte[] bytes =byteOutput.toByteArray();
            //javafx.scene.image.Image image = new Scene(FXMLLoader.load(node.getClass().getResource(node.lookup())).snapshot(null);
            //imagesPdf.add(snapshot)
            imagesBytePdf.add(bytes);
        }
        // add first node by default
        if(exercices.get(0).getId()!=0){
            Exercice exercice = exerciceService.getById(exercices.get(0).getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FullExerciceItem.fxml"));
            Node fullExerciceNode = null;
            try {
                fullExerciceNode = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FullExerciceItemController fullExerciceItemController = loader.getController();
            fullExerciceItemController.setExerciceItem(exercice);
            //GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().clear();
            GeneratedProgramController.getInstance().getFullDisplayVbox().getChildren().add(fullExerciceNode);
        }

    }

    public void downloadExercicePdf(){

    }

    public void initialize(URL location, ResourceBundle resources) {
        exercicesHbox.setSpacing(30);
        try {
            loadExercices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        downloadPdf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SavePdf.downloadPDF(imagesBytePdf,((Node)event.getSource()).getScene().getWindow());
            }
        });

    }
}
