package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class GymItemsController   {

    @FXML
    private Label lblname;

    @FXML
    private Label lbldesc;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(Gym);
    }

    private Gym Gym;
    private MyListener myListener;

    public void setData(Gym gym, MyListener myListener) {
        this.Gym = gym;
        this.myListener = myListener;
        lblname.setText(gym.getName());
        lbldesc.setText(gym.getDescription());
        if(Gym.getImage().isEmpty()){
            img.setImage(new Image("@../../assets/imgs/blank-profile-picture.png"));
        }else{
            img.setImage(new Image(Gym.getImage()));
        }
    }
}
