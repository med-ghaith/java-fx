package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.controller.Modulegestionsalle.ProfilSalleController;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Comment;
import esprit.changemakers.sportshub.entities.Review;
import esprit.changemakers.sportshub.services.Impl.CommentServiceImpl;
import esprit.changemakers.sportshub.services.Impl.ReviewServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddReviewGymController implements Initializable {
    @FXML
    private Rating rat;

    @FXML
    private TextField txt;

    @FXML
    private ImageView imageview;

    CommentServiceImpl commentservice = new CommentServiceImpl();
    ReviewServiceImpl reviewservice = new ReviewServiceImpl();
    UserDaoImpl u = new UserDaoImpl();
    int id = HomeController.getInstance().getIdUser();
    int gymI = Integer.parseInt(ProfilSalleController.getInstance().getGId());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Review r1 = reviewservice.getReviewByUserByGym(id, gymI);
        rat.setRating(r1.getRate());

        rat.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                try {

                    InputStream stream1 = new FileInputStream("src/main/resources/assets/imgs/rate1.png");
                    InputStream stream2 = new FileInputStream("src/main/resources/assets/imgs/rate2.png");
                    InputStream stream3 = new FileInputStream("src/main/resources/assets/imgs/rate3.png");
                    InputStream stream4 = new FileInputStream("src/main/resources/assets/imgs/rate4.png");
                    InputStream stream5 = new FileInputStream("src/main/resources/assets/imgs/rate5.png");

                    System.out.println(newValue);
                    //imageview.setImage(image);
                    if (newValue.floatValue() >= 0.0f && newValue.floatValue()<=1.0f){
                        imageview.setImage(new Image(stream1));
                    } else if(newValue.floatValue()> 1.0f && newValue.floatValue() <=2.0f){

                        imageview.setImage(new Image(stream2));
                    }else if(newValue.floatValue()> 2.0f && newValue.floatValue() <=3.0f){
                        imageview.setImage(new Image(stream3));
                    } else if(newValue.floatValue()> 3.0f && newValue.floatValue() <=4.0f){
                        imageview.setImage(new Image(stream4));
                    }
                    else if(newValue.floatValue()> 4.0f && newValue.floatValue() <=5.0f){
                        imageview.setImage(new Image(stream5));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //textField.setText(newValue.toString());

            }
        });
        rat.setPartialRating(true);
    }


    public void addReview(ActionEvent event){
        if (id == 0){
            Alert alerts = new Alert(Alert.AlertType.WARNING);
            alerts.setTitle("Warning");
            alerts.setHeaderText(null);
            alerts.setContentText("You must log in first!");
            alerts.show();
        }else {
            Comment c = new Comment(id, txt.getText(), gymI);

            Comment c1 = commentservice.getCommentByUserByGym(id, gymI);
            //System.out.println(r);
//       rat.setPartialRating(true);
            Review r = new Review(id, (float) rat.getRating(), gymI);
            Review r1 = reviewservice.getReviewByUserByGym(id, gymI);
            System.out.println(r1.getRate());
            //System.out.println(r1);
            if (r.getIdGym() != r1.getIdGym()) {
                commentservice.addCommentGym(c);
                reviewservice.addReviewGym(r);

            }
//        else if (r.getIdGym() == r1.getIdGym()){
//
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Review exist");
//        alert.setHeaderText(null);
//        alert.setContentText("you already had review!");
//        alert.show();
//        }
            else {

                commentservice.addCommentGym(c);

                reviewservice.updateReviewGym(new Review(r1.getId(), id, gymI, (float) rat.getRating()));

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Added");
            alert.setContentText("review added succefully");
            alert.setOnCloseRequest((e) -> {
                try {
                    //URL fxURL = getClass().getResource("/views/ReviewComment.fxml");
                    //FXMLLoader loader = new FXMLLoader(fxURL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShowReviewGym.fxml"));
                    Parent root = loader.load();
                    ShowReviewGymController rc = loader.getController();
                    Comment c2 = commentservice.getCommentByUserByGym(c.getIdUser(), c.getIdGym());
                    System.out.println(c2);
                    rc.setRate((float) rat.getRating());
                    rc.setComment(txt.getText());
//               DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateToStr = dateFormat.format(c2.getDate());
                    //System.out.println(c2.getDate());

                    rc.setUser(u.getById(id).getNom());
                    rc.setDate(dateToStr);

                    rc.setIdCom(String.valueOf(c2.getId()));
                    ListCommentController.getInstance().getpane().getChildren().clear();
                    ListCommentController.getInstance().getpane().getChildren().add(root);
                    // rc.setId(String.valueOf(c.getIdUser()));
                    //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    //Scene scene = new Scene(root);
                    //stage.setScene(scene);
                    //stage.show();
                    //txt.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(AddReviewGymController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            alert.showAndWait();

        }
    }
}
