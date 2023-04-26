package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.entities.Modulegestionsalle.Course;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;

public class ItemsVideoController {
    @FXML
    private Label nameLabel;

    @FXML
    private MediaView img;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListenerVideo(course);
    }

    private Course course;
    private MyListenerVideo myListener;

    public void setData(Course course, MyListenerVideo myListener) {
        this.course = course;
        this.myListener = myListener;
        nameLabel.setText(course.getName());
        String url = course.getVideo();
        file = new File(url);
        media=new Media(file.toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        img.setMediaPlayer(mediaPlayer);

    }
}
