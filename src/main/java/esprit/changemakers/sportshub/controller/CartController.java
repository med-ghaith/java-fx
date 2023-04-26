/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;



import esprit.changemakers.sportshub.dao.Impl.CartDaoImp;
import esprit.changemakers.sportshub.dao.Impl.CommandDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.dao.MyListener;
import esprit.changemakers.sportshub.entities.Command;
import esprit.changemakers.sportshub.entities.Product;
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
public class CartController implements Initializable {
    CartDaoImp prod = new CartDaoImp();
    ProductDaoImp produ = new ProductDaoImp();

    CommandDaoImpl com = new CommandDaoImpl();

    @FXML
    private GridPane grid;
    private MyListener myListener;
    @FXML
    private ImageView imagecard;
    @FXML
    private TextArea desc;
    @FXML
    private Label prix;
    @FXML
    private Label name;
    @FXML
    private Label total;
    @FXML
    private AnchorPane anchor1;
    CartDaoImp prods = new CartDaoImp();
    @FXML
    private Label id;
    @FXML
    private Label qq;
    private int iduser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {








        if (HomeController.getInstance().getIdUser() != 0)
            iduser = HomeController.getInstance().getIdUser();
        System.out.println(iduser + "aaaaaa");

        try {
            // TODO
            ShowProduct();
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }

    }

    private void ShowProduct() throws SQLException, IOException {
        ProductDaoImp prod = new ProductDaoImp();
        CartDaoImp prods = new CartDaoImp();

        if (prods.product_Cart(iduser).size() > 0) {
            // setChosenOffre(prods.listProduit().get(0));

            setChosenOffre(prods.product_Cart(iduser).get(0));
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
        for (int i = 0; i < prods.product_Cart(iduser).size(); i++) {

            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("/views/CardProduct.fxml"));
            try {
                AnchorPane anchorPane = cards.load();
                CardProductController ProduitServ = cards.getController();
                ProduitServ.AddProduit(prods.product_Cart(iduser).get(i), myListener);

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
        String price = String.valueOf(prods.TOTAL_PRICE(iduser));
        total.setText(price + " DT");
        String quantity = String.valueOf(prods.quantity_product(Integer.parseInt(id.getText())));
        qq.setText(quantity);


    }

    @FXML
    private void payment(ActionEvent event) throws IOException {



        if (prod.TOTAL_PRICE(iduser) == 0f){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(" please add a product to cart !!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){


                anchor1.getChildren().clear();
                AnchorPane an1 = FXMLLoader.load(getClass().getResource("/views/ShopProduct.fxml"));
                anchor1.getChildren().add(an1);
            }
        }else {
        anchor1.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/paymentV1.fxml"));
        anchor1.getChildren().add(an);
        String price = String.valueOf(prods.TOTAL_PRICE(iduser));
        total.setText(price + " DT");
        Command C = new Command("ref1", iduser, prods.TOTAL_PRICE(iduser), "aaaa");
        com.addCommand(C);
        System.out.println(iduser);

    }}

    @FXML
    private void vider(ActionEvent event) throws IOException {
        prod.deleteCartuser(iduser);
        anchor1.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/Cart.fxml"));
        anchor1.getChildren().add(an);

    }

    @FXML
    private void delete(ActionEvent event) throws IOException {
        prod.deleteCartProduct(Integer.parseInt(id.getText()));
        anchor1.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/Cart.fxml"));
        anchor1.getChildren().add(an);

    }



}


