package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.controller.Modulegestionsalle.ProfilSalleController;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Comment;
import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.services.Impl.CommentServiceImpl;
import esprit.changemakers.sportshub.services.Impl.ReviewServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

import java.util.*;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ListCommentController implements Initializable {

    private static ListCommentController instance;
    public ListCommentController() { instance = this; }
    public static ListCommentController getInstance() { return instance; }

    public Pane getpane(){
        return commPane;
    }



    @FXML
    private Rating gr;

    @FXML
    private TableView<Comment> ta;

    @FXML
    private TableColumn<Comment, String> user;

    @FXML
    private TableColumn<Comment, String> comm;

    @FXML
    private TableColumn<Comment, Date> da;

    @FXML
    private TableColumn<Comment, String> delete;

    @FXML
    private TableColumn<Comment, String> modify;

    @FXML
    private Pane commPane;

    @FXML
    private ComboBox<String> sortCombo;
    private String[] ty = {"Latest Comments", "Oldest Comments"};
    ArrayList s = new ArrayList(Arrays.stream(ty).collect(Collectors.toList()));

    CommentServiceImpl commentservice = new CommentServiceImpl();
    ReviewServiceImpl reviewservice = new ReviewServiceImpl();
    UserDaoImpl u = new UserDaoImpl();
    int id = HomeController.getInstance().getIdUser();
    int gymI = Integer.parseInt(ProfilSalleController.getInstance().getGId());

    ObservableList<Comment> list = FXCollections.observableArrayList();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int gymI = Integer.parseInt(ProfilSalleController.getInstance().getGId());
        System.out.println(gymI);
        sortCombo.getItems().addAll(s);
        list = commentservice.getAllCommentsByGym(gymI);

        ObservableList<Comment> data2 =
                list.stream()
                        .sorted(Comparator.
                                comparing(Comment::getDate))
                        .peek(System.out::println)
                        .collect(Collectors.toCollection(()->FXCollections.observableArrayList()));
        ObservableList<Comment> data =
                list.stream()
                        .sorted(Comparator.
                                comparing(Comment::getDate).reversed())
                        .peek(System.out::println)
                        .collect(Collectors.toCollection(()->FXCollections.observableArrayList()));


        ta.setItems(data);
        sortCombo.valueProperty().addListener((obs, oldVal, newVal) ->{
                    if (newVal == "Latest Comments"  ){
                        ta.getItems().clear();
                        System.out.println(data);
                        //ta.setItems(list);
                        ta.getItems().addAll(data);
                       // list.addAll(data2);
                        //System.out.println(data2);
                        //ta.setItems(data2);
                    } else if(newVal == "Oldest Comments"  ) {

                        ta.getItems().clear();
                        ta.getItems().addAll(data2);
                        //System.out.println(data);
                        //ta.setItems(data2);
                    }
                }
        );
        //ta.setItems(commentservice.getAllCommentsByGym(gymI));
        Callback<TableColumn<Comment, String>, TableCell<Comment, String>> cellFactoryU
                =
                ( TableColumn<Comment, String> param) -> {
                    TableCell<Comment, String> cell = new TableCell<Comment, String>() {

                        Label btn = new Label();
                        String pe = "";


                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {


                                pe = u.getById(getTableView().getItems().get(getIndex()).getIdUser()).getNom();
                                //System.out.println(u.getById(p.getIdUser()).getNom());
                                btn.setText(pe);

                                ObservableList observableList = FXCollections.observableArrayList(commentservice.getAllCommentsByGym(gymI));
                                //ta.setItems(observableList);
                                // list();


                                setGraphic(btn);
                                setText(null);
                            }
                        }


                    };
                    return cell;
                };
        //editer update
        user.setCellFactory(cellFactoryU);

        Callback<TableColumn<Comment, String>, TableCell<Comment, String>> cellFactoryU2
                =
                new Callback<TableColumn<Comment, String>, TableCell<Comment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Comment, String> param) {
                        final TableCell<Comment, String> cell = new TableCell<Comment, String>() {

                            final Button btn = new Button("Delete");

                            //btn.setStyle("-fx-border-color: #ff0000;");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else if (!empty) {
                                    btn.setOnAction(event -> {

                                        if (getTableView().getItems().get(getIndex()) == null) {

                                        } else  {
                                            try {
                                                Comment commentSelectione = getTableView().getItems().get(getIndex());
                                                System.out.println(commentSelectione);
                                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                alert.setTitle("Information Dialog");
                                                alert.setHeaderText(null);
                                                alert.setContentText("are you sure you want to delete this reclamation");
                                                Optional<ButtonType> result = alert.showAndWait();
                                                ButtonType button = result.orElse(ButtonType.CANCEL);


                                                if(result.get() == ButtonType.OK){
                                                    commentservice.deleteCommentGym(commentSelectione);
                                                }
                                                //oke button is pressed
                                                else if(result.get() == ButtonType.CANCEL){
                                                    System.out.println("cancel");
                                                }

                                                ObservableList observableList = FXCollections.observableArrayList(commentservice.getAllCommentsByGym(gymI));

                                                //ta.setItems(observableList);

                                                //System.out.println(reclamationSelectione.getDescription());

                                            } catch (Exception ex) {
                                                Logger.getLogger(ListCommentController.class.getName()).log(Level.SEVERE, null, ex);
                                                System.out.println(ex.getMessage()+"**************************************************");
                                            }
                                        }
                                        // list();

                                    });
                                    if(getTableView().getItems().get(getIndex()).getIdUser() == id){

                                        btn.setVisible(true);
                                        btn.setStyle(" -fx-base:#F61B0E;");
                                        setGraphic(btn);
                                        setText(null);

                                    }else {
                                        btn.setVisible(false);
                                    }
                                }
                            }


                        };
                        return cell;
                    }
                };


        delete.setCellFactory(cellFactoryU2);


        /////////////////////////
        Callback<TableColumn<Comment, String>, TableCell<Comment, String>> cellFactoryU3
                =
                new Callback<TableColumn<Comment, String>, TableCell<Comment, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Comment, String> param) {
                        final TableCell<Comment, String> cell = new TableCell<Comment, String>() {

                            final Button btn = new Button("Modify");

                            //btn.setStyle("-fx-border-color: #ff0000;");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else if (!empty) {
                                    btn.setOnAction(event -> {

                                        if (getTableView().getItems().get(getIndex()) == null) {

                                        } else  {
                                            //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();


                                            try {
                                                Comment commentSelectione = getTableView().getItems().get(getIndex());
                                                FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/CommentUpdate.fxml"));
                                                Parent root=loader.load();
                                                UpdateCommentController pr=loader.getController();
                                                pr.setComment(commentSelectione.getCommentText());
                                                pr.setComId(String.valueOf(commentSelectione.getId()));
                                                Stage stage = new Stage();
                                                //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                                Scene scene = new Scene(root);
                                                stage.setScene(scene);
                                                stage.show();


                                                System.out.println(commentSelectione);

                                                ObservableList observableList =FXCollections.observableArrayList(commentservice.getAllCommentsByGym(gymI));

                                                //ta.setItems(observableList);

                                                //System.out.println(reclamationSelectione.getDescription());

                                            } catch (Exception ex) {
                                                Logger.getLogger(ListCommentController.class.getName()).log(Level.SEVERE, null, ex);
                                                System.out.println(ex.getMessage()+"**************************************************");
                                            }
                                        }
                                        // list();

                                    });
                                    if(getTableView().getItems().get(getIndex()).getIdUser() == id){

                                        btn.setVisible(true);
                                        btn.setStyle(" -fx-base:#333;");

                                        setGraphic(btn);
                                        setText(null);

                                    }else {
                                        System.out.println(getTableView().getItems().get(getIndex()).getIdUser());
                                        btn.setVisible(false);
                                    }
                                }
                            }


                        };
                        return cell;
                    }
                };
        //editer update

        modify.setCellFactory(cellFactoryU3);

        ///////////////////////////

        // user.setCellValueFactory(new PropertyValueFactory<>("nom"));
        comm.setCellValueFactory(new PropertyValueFactory<>("commentText"));
        da.setCellValueFactory(new PropertyValueFactory<>("date"));

        float r = reviewservice.getGymReview(gymI);
        gr.setPartialRating(true);
        //System.out.println(r);
        gr.setRating(r);
        //gr.setDisable(true);




    }

    @FXML
    public void getScene(ActionEvent event) throws IOException{
        Pane pane = FXMLLoader.load(getClass().getResource("/views/AddReviewGym.fxml"));
        commPane.getChildren().add(pane);

    }


}
