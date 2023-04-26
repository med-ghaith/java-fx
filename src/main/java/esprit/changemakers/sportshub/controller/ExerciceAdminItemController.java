package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Product;
import esprit.changemakers.sportshub.services.IEquipmentService;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.Impl.EquipmentServiceImpl;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Jozef
 */
public class ExerciceAdminItemController implements Initializable {

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
    private ImageView eqMuscleImg;
    @FXML
    private Button deleteBtn;
    @FXML
    private VBox item;
    @FXML
    private Label idEx;

    private boolean clicked = false;

    private boolean secondClick = true;

    private IEquipmentService equipmentService = new EquipmentServiceImpl();

    ProductDaoImp productDaoImp = new ProductDaoImp();

    ObservableList<Exercice> exercices = FXCollections.observableArrayList();
    IExerciceService exerciceService;

    public static int idExercice = 0;

    public void initialize(URL location, ResourceBundle resources) {
        name.setStyle("-fx-font-size: 30");
        textDesc.setWrappingWidth(290);
        diff.setStyle("-fx-text-fill: #3b8fae");
        equipments.setStyle("-fx-text-fill: #3b8fae");
        // see if the product in the photo exist in our shop, if so give it's price and propose to the user if he wants to buy it
        exerciceService = new ExerciceServiceImpl();

        item.setOnMouseEntered(event -> {
            if(ExerciceAdminListController.getInstance().mouseClicked){
                System.out.println(idEx.getText());
                Exercice exercice = exerciceService.getById(Integer.parseInt(idEx.getText()));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExerciceItemAdminEditAdd.fxml"));
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ExerciceItemAdminEditAddController editAddController = loader.getController();
                editAddController.setExerciceItem(exercice);
                ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().clear();
                ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().add(node);
            }
        });


        item.setOnMouseClicked(event1 -> {
            System.out.println(idEx.getText());
            Exercice exercice1 = exerciceService.getById(Integer.parseInt(idEx.getText()));
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/views/ExerciceItemAdminEditAdd.fxml"));
            Node node1 = null;
            try {
                node1 = loader1.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ExerciceItemAdminEditAddController editAddController1 = loader1.getController();
            editAddController1.setExerciceItem(exercice1);
            ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().clear();
            ExerciceAdminListController.getInstance().getFullDisplayVbox().getChildren().add(node1);

            if(ExerciceAdminListController.getInstance().mouseClicked == true){
                ExerciceAdminListController.getInstance().mouseClicked = false;
            }



        });


        deleteBtn.setOnMouseClicked(event -> {
            Node node = ((Node) (((Node) ((Button) event.getSource())).getParent()).getParent()).getParent();
            Node node1 = ((VBox) ((HBox) ((Node) ((Button) event.getSource())).getParent()).getParent());
            System.out.println(((VBox) node1).hashCode());
            ExerciceAdminListController.getInstance().deleteNode(((VBox) node1).hashCode());
            // TODO delete form database
        });

    }

    public void setExerciceItem(Exercice exercice1) {
        name.setText(exercice1.getName());
        textDesc.setText(exercice1.getDescription());
        diff.setText(exercice1.getDifficultyLevel().name());
        diff.setStyle("-fx-text-fill: #3b8fae");
        idEx.setText(String.valueOf(exercice1.getId()));

        exerciceImg.setImage(new Image(exercice1.getImageUrl()));
        // if there is less than 3 display them
        if (exercice1.getEquipments().size() <= 3) {
            for (int i = 0; i < exercice1.getEquipments().size(); i++) {
                Label equip = new Label(exercice1.getEquipments().get(i).getName());
                equip.setStyle("-fx-text-fill: #3b8fae;" + "-fx-cursor: hand;");
                equip.setOnMouseClicked(event -> {
                    System.out.println(((Text) (event.getTarget())).getText());

                    eqMuscleImg.setImage(new Image(equipmentService.getEquipmentByName(((Text) (event.getTarget())).getText()).getImageUrl()));
                    eqMuscleImg.setOnMouseEntered(event3 -> {
                        if (productDaoImp.listProduit().stream().anyMatch(e -> e.getName().equals(equipmentService.getEquipmentByName(((Text) (event.getTarget())).getText()).getName()))) {
                            eqMuscleImg.setStyle("-fx-cursor: hand");
                        } else {
                            eqMuscleImg.setStyle("-fx-cursor: null");
                        }
                    });
                    /*
                     * Check if the product is in the shop, display an alert and navigate to it of it's true
                     * */
                    eqMuscleImg.setOnMouseClicked(event1 -> {
                        if (productDaoImp.listProduit().stream().anyMatch(e -> e.getName().equals(equipmentService.getEquipmentByName(((Text) (event.getTarget())).getText()).getName()))) {
                            Product product = productDaoImp.listProduit().stream().filter(e -> e.getName().equals(equipmentService.getEquipmentByName(((Text) (event.getTarget())).getText()).getName())).findFirst().get();
                            System.out.println(product);
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Select");
                            alert.setHeaderText("This product is available in our shop\n It costs " + product.getPrice() + "DT\n do you want to go check it ?");
                            alert.setContentText("Are you sure you want to leave this page ?");
                            ButtonType check = new ButtonType("Check Product");
                            ButtonType cancel = new ButtonType("Cancel");

                            // Remove default ButtonTypes
                            alert.getButtonTypes().clear();

                            alert.getButtonTypes().addAll(check, cancel);

                            // option != null.
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get() == null) {
                                System.out.println("nothing selectd");
                            } else if (option.get() == check) {
                                try {
                                    // change it here so we get only the product
                                    HomeController.getInstance().loadShop();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (option.get() == cancel) {
                                System.out.println("cancled");
                            } else {
                                System.out.println("Cancled also");
                            }
                        }

                    });

                });
                equipments.getChildren().add(equip);
            }
            // if there is more than 3 display only the first 3
        } else {
            for (int i = 0; i < 3; i++) {
                Label equip = new Label(exercice1.getEquipments().get(i).getName());
                equip.setStyle("-fx-text-fill: #3b8fae");
                equipments.getChildren().add(equip);
            }
        }
        // same logic for muscles
        if (exercice1.getMuscles().size() <= 3) {
            for (int i = 0; i < exercice1.getMuscles().size(); i++) {
                Label muscle = new Label(exercice1.getMuscles().get(i).getName());
                muscle.setStyle("-fx-text-fill: #3b8fae");
                muscles.getChildren().add(muscle);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                Label muscle = new Label(exercice1.getMuscles().get(i).getName());
                muscle.setStyle("-fx-text-fill: #3b8fae");
                muscles.getChildren().add(muscle);
            }
        }
        numberOfSets.setText(String.valueOf(exercice1.getNumberOfSets()));
        numberOfReps.setText(String.valueOf(exercice1.getNumberOfRepetition()));
        restTime.setText(exercice1.getRestTime());
    }


}
