package esprit.changemakers.sportshub.controller;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.entities.Equipment;
import esprit.changemakers.sportshub.entities.Exercice;
import esprit.changemakers.sportshub.entities.Muscle;
import esprit.changemakers.sportshub.entities.Product;
import esprit.changemakers.sportshub.services.IEquipmentService;
import esprit.changemakers.sportshub.services.IExerciceService;
import esprit.changemakers.sportshub.services.IMuscleService;
import esprit.changemakers.sportshub.services.Impl.EquipmentServiceImpl;
import esprit.changemakers.sportshub.services.Impl.ExerciceServiceImpl;
import esprit.changemakers.sportshub.services.Impl.MuscleServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jozef
 */
public class ExerciceItemAdminEditAddController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private ChoiceBox<String> diffCb;
    @FXML
    private Label equip;
    @FXML
    private Label muscle;
    @FXML
    private FontAwesomeIcon addEq;
    @FXML
    private TextField numberOfSetsTf;
    @FXML
    private TextField numberOfRepsTf;
    @FXML
    private TextField restTimeTf;
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
    public TextField nameTf;
    @FXML
    public TextArea descTA;
    @FXML
    public Label idItemToEdit;
    @FXML
    public Button saveBtn;
    @FXML
    public FontAwesomeIcon addMusc;

    public Set<String> addedEquipmentList = new HashSet<>();

    // To make ur controller ( borderPane ) accesable from other controller u gotta add these three things ( thank me later )
    private static ExerciceItemAdminEditAddController instance;

    public ExerciceItemAdminEditAddController() {
        instance = this;
    }

    public static ExerciceItemAdminEditAddController getInstance() {
        return instance;
    }

    private IEquipmentService equipmentService = new EquipmentServiceImpl();

    ProductDaoImp productDaoImp = new ProductDaoImp();

    ObservableList<Exercice> exercices = FXCollections.observableArrayList();
    IExerciceService exerciceService;

    public static int idExercice = 0;

    public void initialize(URL location, ResourceBundle resources) {

        name.setStyle("-fx-font-size: 30");
        textDesc.setWrappingWidth(290);

        diffCb.getItems().addAll(Exercice.Difficulty.easy.name(), Exercice.Difficulty.medium.name(), Exercice.Difficulty.hard.name());
        diffCb.setStyle("-fx-text-fill: #3b8fae");
        equipments.setStyle("-fx-text-fill: #3b8fae");
        // see if the product in the photo exist in our shop, if so give it's price and propose to the user if he wants to buy it



        addEq.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                IEquipmentService equipmentService = new EquipmentServiceImpl();
                ObservableList<Equipment> allEquipments = equipmentService.getAll();
                HBox hBox = new HBox();
                Button button = new Button("d");
                button.setStyle("-fx-font-size: 12px;" + "-fx-background-color: red");

                hBox.setSpacing(18);
                hBox.setAlignment(Pos.CENTER);

                ChoiceBox<String> choiceBox = new ChoiceBox<>();

//TODO make choice box contains only unused equipment
//                ObservableList<String> listToShow = allEquipments.stream().map(e->e.getName()).collect(Collectors.toCollection(FXCollections::observableArrayList));
//                System.out.println("aaa\n"+listToShow);
//                listToShow.removeIf(e->addedEquipmentList.contains(e));
//                System.out.println("aaa\n"+listToShow);
                choiceBox.getItems().addAll(allEquipments.stream().map(e->e.getName()).collect(Collectors.toList()));

                //addedEquipmentList.add(choiceBox.getSelectionModel().getSelectedItem());
                //System.out.println(addedEquipmentList);
                choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);
                        addedEquipmentList.add(newValue);
                        System.out.println(addedEquipmentList);

                    }
                });

                hBox.getChildren().addAll(choiceBox,button);
                if (equipments.getChildren().size() < 3) {
                    equipments.getChildren().add(hBox);
                }

                button.setOnAction(event2 -> {

                    //System.out.println(((ChoiceBox) ((HBox) ((Button) event2.getTarget()).getParent()).getChildren().get(0)).getText());
                    int thisHbHashC = ((HBox) ((Button) event2.getTarget()).getParent()).hashCode();
                    ((VBox) ((HBox) ((Button) event2.getTarget()).getParent()).getParent()).getChildren().removeIf(e -> e.hashCode() == thisHbHashC);
                });
            }
        });


        addMusc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                IMuscleService muscleService = new MuscleServiceImpl();
                ObservableList<Muscle> allMuscles = muscleService.getAll();
                HBox hBox = new HBox();
                Button button = new Button("d");
                button.setStyle("-fx-font-size: 12px;" + "-fx-background-color: red");

                hBox.setSpacing(18);
                hBox.setAlignment(Pos.CENTER);

                ChoiceBox<String> choiceBox = new ChoiceBox<>();

//TODO make choice box contains only unused equipment
//                ObservableList<String> listToShow = allEquipments.stream().map(e->e.getName()).collect(Collectors.toCollection(FXCollections::observableArrayList));
//                System.out.println("aaa\n"+listToShow);
//                listToShow.removeIf(e->addedEquipmentList.contains(e));
//                System.out.println("aaa\n"+listToShow);
                choiceBox.getItems().addAll(allMuscles.stream().map(e->e.getName()).collect(Collectors.toList()));

                //addedEquipmentList.add(choiceBox.getSelectionModel().getSelectedItem());
                //System.out.println(addedEquipmentList);
                choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);
                        addedEquipmentList.add(newValue);
                        System.out.println(addedEquipmentList);
                    }
                });

                hBox.getChildren().addAll(choiceBox,button);
                if (muscles.getChildren().size() < 3) {
                    muscles.getChildren().add(hBox);
                }

                button.setOnAction(event2 -> {
                    //System.out.println(((ChoiceBox) ((HBox) ((Button) event2.getTarget()).getParent()).getChildren().get(0)).getText());
                    int thisHbHashC = ((HBox) ((Button) event2.getTarget()).getParent()).hashCode();
                    ((VBox) ((HBox) ((Button) event2.getTarget()).getParent()).getParent()).getChildren().removeIf(e -> e.hashCode() == thisHbHashC);
                });
            }
        });

        name.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nameTf.setVisible(true);
            }
        });

        textDesc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                descTA.setVisible(true);
            }
        });

        saveBtn.setOnAction(event -> {
            IExerciceService exerciceService = new ExerciceServiceImpl();
            Exercice exercice = new Exercice();
            //List<Equipment>
            // modify an existing exercice
            if(idItemToEdit.getText().equals("0")){


                //exerciceService.create();
            }else {// create a new exercice

            }
            System.out.println(idItemToEdit.getText());
        });
    }


    public void setExerciceItem(Exercice exercice1) {
        addedEquipmentList.addAll(exercice1.getEquipments().stream().map(e->e.getName()).collect(Collectors.toList()));
        idExercice = exercice1.getId();
        idItemToEdit.setText(""+idExercice);
        name.setText(exercice1.getName());
        textDesc.setText(exercice1.getDescription());
        diffCb.setValue(exercice1.getDifficultyLevel().name());
        //diffC.setText(exercice1.getDifficultyLevel().name());
        diffCb.setStyle("-fx-text-fill: #3b8fae");

        exerciceImg.setImage(new Image(exercice1.getImageUrl()));
        // if there is less than 3 display them
        if (exercice1.getEquipments().size() <= 3) {
            for (int i = 0; i < exercice1.getEquipments().size(); i++) {
                HBox hBox = new HBox();
                Label equip = new Label(exercice1.getEquipments().get(i).getName());

                Button button = new Button("d");
                button.setStyle("-fx-font-size: 12px;" + "-fx-background-color: red");
                equip.setStyle("-fx-text-fill: #3b8fae;" + "-fx-cursor: hand;");
                hBox.setSpacing(18);
                equip.setOnMouseClicked(event -> {
                    System.out.println(((Text) (event.getTarget())).getText());
                    System.out.println(event.getTarget());

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

                hBox.getChildren().addAll(equip, button);

                equipments.getChildren().add(hBox);
                button.setOnAction(event -> {
                    IExerciceService exerciceService = new ExerciceServiceImpl();
                    IEquipmentService equipmentService = new EquipmentServiceImpl();
                    Equipment equipment = equipmentService.getEquipmentByName(((Label) ((HBox) ((Button) event.getTarget()).getParent()).getChildren().get(0)).getText());
                    if (exercice1 != null) {
                        exerciceService.deleteEquipmentFromExercice(exercice1, equipment);
                    }
                    System.out.println(((Label) ((HBox) ((Button) event.getTarget()).getParent()).getChildren().get(0)).getText());
                    int thisHbHashC = ((HBox) ((Button) event.getTarget()).getParent()).hashCode();
                    ((VBox) ((HBox) ((Button) event.getTarget()).getParent()).getParent()).getChildren().removeIf(e -> e.hashCode() == thisHbHashC);
                });
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
        numberOfSetsTf.setText(String.valueOf(exercice1.getNumberOfSets()));
        numberOfRepsTf.setText(String.valueOf(exercice1.getNumberOfRepetition()));
        restTimeTf.setText(exercice1.getRestTime());
    }

}
