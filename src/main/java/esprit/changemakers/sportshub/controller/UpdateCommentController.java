package esprit.changemakers.sportshub.controller;
import esprit.changemakers.sportshub.controller.Modulegestionsalle.ProfilSalleController;
import esprit.changemakers.sportshub.entities.Comment;
import esprit.changemakers.sportshub.services.Impl.CommentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateCommentController {

    @FXML
    private TextField comment_txt;

    @FXML
    private Label comId;

    CommentServiceImpl commentservice = new CommentServiceImpl();
    int id = HomeController.getInstance().getIdUser();
    int gymI = Integer.parseInt(ProfilSalleController.getInstance().getGId());

    public void setComment(String s){
        comment_txt.setText(s);
    }

    public void setComId(String i){
        comId.setText(i);
    }



    @FXML
    void updateComment(ActionEvent event) {
        Comment c = new Comment( id, gymI, comment_txt.getText(), Integer.parseInt(comId.getText()));
        commentservice.updateCommentGym(c);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("comment updated");
        alert.show();
    }
}
