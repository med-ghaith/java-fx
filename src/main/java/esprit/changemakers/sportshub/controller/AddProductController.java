/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.entities.Product;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import static java.util.Collections.list;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import esprit.changemakers.sportshub.utils.UploadAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */

public class AddProductController implements Initializable {

    private static Stage pStage;
    File file;
    File file1;

    public static Stage getpStage() {
        return pStage;
    }

    public static void setpStage(Stage pStage) {
        AddProductController.pStage = pStage;
    }

    private String[] ty = {"halters",

            "proteins",

    };
    // ProductDaoImp prod = new ProductDaoImp();
    ObservableList<Product> RecList = null;

    @FXML
    private Button addProduct;
    @FXML
    private TextField nom;
    @FXML
    private TextField price;
    @FXML
    private TextArea desc;
    private TextField image;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private AnchorPane anchor1;
    /**
     * Initializes the controller class.
     */
    ProductDaoImp prod = new ProductDaoImp();

    ObservableList<Product> listpr = FXCollections.observableArrayList();
    private TableView<Product> idTableView;
    @FXML
    private Button imageBrowse;
    @FXML
    private ImageView ivProduit;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            category.getItems().addAll(ty);
            //type.setOnAction(this::pushReclamation);
        } catch (Exception e) {
            System.out.println("error");
        }// TODO


    }

    @FXML
    private void ajouterProduit(ActionEvent event) throws IOException {
        String nameF = "";
        String catego = category.getValue();
        if (event.getSource() == addProduct) {
            if( validatenname(nom) )
            if ( validateDESC(desc) & validatenumber(price) ) {
                try {
                    nameF = UploadAPI.upload(file);
                } catch (Exception ex) {
                    Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Product p = new Product(nom.getText(), nameF, Float.parseFloat(price.getText()), desc.getText(), Product.Category.valueOf(catego));

                prod.addProduit(p);
                listpr.add(p);
                System.out.println(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("product add !!");
                alert.show();
                anchor1.getChildren().clear();
                AnchorPane an = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
                anchor1.getChildren().add(an);
            }
        }
    }

    @FXML
    private void imageBrowse(ActionEvent event) {
        try {
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(.jpg)", ".jpg");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("JPEG files(.jpeg)", ".jpeg");
            FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("PNG files(.png)", ".png");
            //fc.getExtensionFilters().addAll(ext1, ext2, ext3);

            file = fc.showOpenDialog(AddProductController.getpStage());
            System.out.println(file.getPath());
            File file1 = new File(file.getPath());
            BufferedImage bf;
            bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            ivProduit.setImage(image);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        anchor1.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
        anchor1.getChildren().add(an);


    }

    private boolean validateDESC(TextArea desc) {

        if ((desc.getText().length() != 0)) {

            desc.setEffect(null);
            return true;

        } else {
            new animatefx.animation.Shake(desc).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            desc.setEffect(in);
            return false;
        }
    }



    boolean validatenname(TextField name) {
        Pattern p = Pattern.compile("[a-z A-Z 0-9_]+");
        Matcher m = p.matcher(name.getText());
        if ((name.getText().length() != 0)
                && (m.find() && m.group().equals(name.getText()))) {


            name.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(name).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            name.setEffect(in);
            return false;


        }
    }

    private boolean validatenumber(TextField number) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(number.getText());

        if ((number.getText().length() != 0)
                && (m.find() && m.group().equals(number.getText()))) {


            number.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(number).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            number.setEffect(in);
            return false;


        }
    }
    boolean validate() {
        Pattern p = Pattern.compile("[a-z A-Z 0-9_]+");
        Matcher m = p.matcher(nom.getText());
        if ((nom.getText().length() != 0)
                && (m.find() && m.group().equals(nom.getText()))) {


            nom.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(nom).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            nom.setEffect(in);
            return false;


        }
    }
    private boolean validateprice() {
        Pattern p = Pattern.compile("[0-9.0-9]+");
        Matcher m = p.matcher(price.getText());

        if ((price.getText().length() != 0)
                && (m.find() && m.group().equals(price.getText()))) {


            price.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(price).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            price.setEffect(in);
            return false;


        }
    }


    @FXML
    private void price(KeyEvent event) {
        if (price.getText().isEmpty() == false) {
            if (validateprice()) {

                InnerShadow in = new InnerShadow();
                in.setColor(Color.web("#52FF00"));

                price.setEffect(in);
            } else {
                InnerShadow in = new InnerShadow();
                in.setColor(Color.web("#f80000"));

                price.setEffect(in);
            }
        }
    }


    @FXML
    private void name(KeyEvent event) {
        if (nom.getText().isEmpty() == false) {
            if (validate()) {

                InnerShadow in = new InnerShadow();
                in.setColor(Color.web("#52FF00"));

                nom.setEffect(in);
            } else {
                InnerShadow in = new InnerShadow();
                in.setColor(Color.web("#f80000"));

                nom.setEffect(in);
            }
        }
    }



    }
