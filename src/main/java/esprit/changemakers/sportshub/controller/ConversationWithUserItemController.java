package esprit.changemakers.sportshub.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.IUserService;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.text.Text;

/**
 * @author Jozef
 */
public class ConversationWithUserItemController {
    @FXML
    private Text userName;
    @FXML
    private Text msgContent;
    @FXML
    public Label idUser;
    @FXML
    private ImageView userImg;

    // To make ur controller ( borderPane ) accessible from other controller u gotta add these three things ( thank me later )
    private static ConversationWithUserItemController instance;

    public ConversationWithUserItemController() {
        instance = this;
    }


    public static ConversationWithUserItemController getInstance() {
        return instance;
    }

    // load conv if exist, create one else
    public void loadConvUser(int idUser){
        System.out.println("load conv "+idUser);
        IUserService userService= null;
        this.idUser.setText(""+idUser);
        if(idUser!=0){

            userService = new UserServiceImpl();
            User user = userService.getById(idUser);
            userName.setText(user.getNom());
            Image image = new Image(user.getImgURL());
            userImg.setImage(image);
        }

    }

}
