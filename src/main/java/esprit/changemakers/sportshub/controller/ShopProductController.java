/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.Impl.CartDaoImp;
import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.dao.MyListener;
import esprit.changemakers.sportshub.entities.Cart;
import esprit.changemakers.sportshub.entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */
public class ShopProductController implements Initializable {

    ObservableList<Product> listprd = FXCollections.observableArrayList();
    private MyListener myListener;
    @FXML
    private GridPane grid;
    @FXML
    private Label prix;
    @FXML
    private TextArea desc;
    @FXML
    private Label name;
    @FXML
    private ImageView imagecard;
    CartDaoImp prod = new CartDaoImp();
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label id;
    @FXML
    private Label cunt;
    private int iduser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(HomeController.getInstance().getIdUser()!=0)
            iduser = HomeController.getInstance().getIdUser();

        try {
            // TODO
            ShowProduct();
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }


    }

    private void ShowProduct() throws SQLException, IOException {
        ProductDaoImp prods = new ProductDaoImp();

        if (prods.listProduit().size() > 0) {
            // setChosenOffre(prods.listProduit().get(0));

            setChosenOffre(prods.listProduit().get(0));
            myListener = new MyListener() {
                @Override
                public void onclicklistener(Product product) {
                    try {
                        setChosenOffre(product);
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }



            };

        }

        int column = 1;
        int row = 1;
        for (int i = 0; i < prods.listProduit().size(); i++) {

            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("/views/CardProduct.fxml"));
            try {
                AnchorPane anchorPane = cards.load();
                CardProductController ProduitServ = cards.getController();
                ProduitServ.AddProduit(prods.listProduit().get(i), myListener);

                if (column == 3) {
                    column = 1;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    private void setChosenOffre(Product product) throws SQLException, IOException {
        id.setText(Integer.toString(product.getId()));

        name.setText(product.getName());
        prix.setText(Float.toString(product.getPrice()));
        desc.setText(product.getDescription());
        imagecard.setImage(new Image("http://localhost/uploads/images/" + product.getImage_url()));
        String quantity = String.valueOf(prod.cunt_quantity(iduser));
        cunt.setText(quantity);

    }


    @FXML
    private void AddCart(ActionEvent event) throws IOException {
        Cart C = new Cart(Integer.parseInt(id.getText()), iduser, 1, Float.parseFloat(prix.getText()));
        prod.addCart(C);
        anchor.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/ShopProduct.fxml"));
        anchor.getChildren().add(an);
        if(iduser==0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("create you account !!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){


                anchor.getChildren().clear();
                AnchorPane an1 = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                anchor.getChildren().add(an);

            }



    }
    }



    private void aaaaaa(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        AnchorPane an1 = FXMLLoader.load(getClass().getResource("/views/Cart.fxml"));
        anchor.getChildren().add(an1);
    }

    @FXML
    private void panier(MouseEvent event) throws IOException {
        anchor.getChildren().clear();
        AnchorPane an1 = FXMLLoader.load(getClass().getResource("/views/Cart.fxml"));
        anchor.getChildren().add(an1);
    }

}
