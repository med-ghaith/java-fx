package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceVideoController {
    @FXML
    MediaView mediaview;
    @FXML
    Button playBT;
    @FXML
    Button pauseBT;
    @FXML
    Button stopBT;
    @FXML
    Slider volumeSD;
    @FXML
    BorderPane controlBorderPane;
    @FXML
    ImageView imgplay;
    @FXML
    Button volumeoffBT;
    @FXML
    Button volumeonBT;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean play=false;
    Boolean isMute = false;
    Stage stage;
    Boolean isFullWindow = false;

//    pour ne pas acceder a la BD je set les variable gym et cours
    private Course cours;
    private Gym gym;
    public void setVariable(Course data,Gym gym){
        this.cours=data;
        this.gym=gym;
        String url = cours.getVideo();
        file = new File(url);
        media=new Media(file.toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaview.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(volumeSD.getValue() / 100);
        volumeSD.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100);
            }
        });
    }
//      fct play qui met le video en play grace au btn play
    public void playMedia(ActionEvent actionEvent) {
        if(!play){
            mediaPlayer.play();
            play=true;
            imgplay.setVisible(false);
            pauseBT.setVisible(true);
            playBT.setVisible(false);
        }
    }
//    fct pour mettre le video en pause
    public void pauseMedia(ActionEvent actionEvent) {
        if(play){
            mediaPlayer.pause();
            play=false;
            imgplay.setVisible(true);
            pauseBT.setVisible(false);
            playBT.setVisible(true);
        }
    }

    public void resetMedia(ActionEvent actionEvent) {
        if(mediaPlayer.getStatus()!=MediaPlayer.Status.READY){
            mediaPlayer.seek(Duration.seconds(0.0));
        }
    }
//      fct pour revenir a l'interface principale
    public void Back(ActionEvent actionEvent) throws IOException {
        mediaPlayer.pause();
        Parent root;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/ProfilSalle.fxml"));
        root=loader.load();
        ProfilSalleController pr=loader.getController();
        pr.setData(gym);
        HomeController.getInstance().getBorderPane().setCenter(root);
    }
//    fct pour click sur la video est la lance
    public void onPlayMouse(MouseEvent mouseEvent) {
        if(!play){
            mediaPlayer.play();
            play=true;
            imgplay.setVisible(false);
            pauseBT.setVisible(true);
            playBT.setVisible(false);
        }else if(play){
            mediaPlayer.pause();
            play=false;
            imgplay.setVisible(true);
            pauseBT.setVisible(false);
            playBT.setVisible(true);
        }
    }
//    fct pour rendre le video mute
    public void changeMute(MouseEvent mouseEvent) {
        if (media != null) {
            if (isMute == false) {
                isMute = true;
                volumeoffBT.setVisible(true);
                volumeonBT.setVisible(false);
                mediaPlayer.setMute(true);

            } else {
                isMute = false;
                volumeoffBT.setVisible(false);
                volumeonBT.setVisible(true);
                mediaPlayer.setMute(false);

            }
        }
    }
//    fct pour rendre la video plus grande
    public void setMaximizeButton() {
        if (isFullWindow == false) {
            stage.setFullScreen(true);
            isFullWindow = true;
        } else {
            stage.setFullScreen(false);
            isFullWindow = false;
        }
    }

}
