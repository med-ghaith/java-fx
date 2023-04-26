/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.MyListener;
import esprit.changemakers.sportshub.entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */
public class CardProductController implements Initializable {

    @FXML
    private ImageView imagecard;
    @FXML
    private Label name;
    MyListener mylistener;
    private Product product;
    @FXML
    private Label prix;
    @FXML
    private TextArea desc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 public void AddProduit(Product product,MyListener mylistener){
        //product.getImage().getBinaryStream();
        this.product=product;
        this.mylistener = mylistener;
//        labelNom.setText(produit.getDesignation());
        name.setText(product.getName());
        prix.setText(Float.toString(product.getPrice())+" DT");
        desc.setText(product.getDescription());
       // category.getText()(product.getCategory().toString());
     imagecard.setImage(new Image("http://localhost/uploads/images/" + product.getImage_url()));
    }

    @FXML
    private void click(MouseEvent event) {
        mylistener.onclicklistener(product);

    }


}
