/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.Impl.ProductDaoImp;
import esprit.changemakers.sportshub.dao.MyListener;
import esprit.changemakers.sportshub.entities.Product;
import esprit.changemakers.sportshub.utils.UploadAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author bilel abbassi
 */
public class ViewProductController implements Initializable {

     ProductDaoImp prod = new ProductDaoImp();
    ObservableList<Product> listprd =FXCollections.observableArrayList();
    Product p = new Product();
    private static Stage pStage;
File file;
File file1;
    private MyListener myListener;


    public static Stage getpStage() {
        return pStage;
    }

    public static void setpStage(Stage pStage) {
        ViewProductController.pStage = pStage;
    }
    
private String[] ty = {"halters",

    "proteins"};
    
    private TableView<Product> idTableView;
    private TableColumn<String, Product> idname;
    private TableColumn<Float, Product> idprice;
    private TableColumn<String, Product> iddesc;
    private TableColumn<String, Product> idcategory;
    @FXML
    private TextField nom;
    @FXML
    private TextField price;
    @FXML
    private TextArea desc;
    private TextField image;
    private ChoiceBox<String> category;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private ImageView imageProduct;
    @FXML
    private ImageView ivProduit;
    @FXML
    private Button imageBrowse;
    @FXML
    private GridPane grid;
    @FXML
    private Label id;
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        


        
        
        
        try {
            category.getItems().addAll(ty);
        //type.setOnAction(this::pushReclamation);
        } catch (Exception e) {
            System.out.println("error");
        }// TODO


        try {
            // TODO
            ShowProduct();
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }


    }
        
    
        

  

    @FXML
    private void update(ActionEvent event) throws Exception {
        String nameF = "";
        try {
            //-------
            nameF = UploadAPI.upload(file);
        } catch (Exception ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Product produ = prod.getProduitById(Integer.parseInt(id.getText()));

        produ.setName(nom.getText());
        produ.setImage_url(nameF);
        produ.setPrice(Float.parseFloat(price.getText()));
        produ.setDescription(desc.getText());
        //produ.setCategory(Product.Category.valueOf("category"));

       //prod.UpdateProduit(produ);

                prod.UpdateProduit(produ);
                anchor2.getChildren().clear();
                AnchorPane an1 = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
                anchor2.getChildren().addAll(an1);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("product Modified !!");
                alert.show();

            }





    @FXML
    private void delete(ActionEvent event) throws FileNotFoundException, IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("are you sure!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){


                    prod.deleteProduit(Integer.parseInt(id.getText()));
                    anchor2.getChildren().clear();
                    AnchorPane an = FXMLLoader.load(getClass().getResource("/views/viewProduct.fxml"));
                    anchor2.getChildren().addAll(an);

        }
    }





   

    @FXML
    private void addproduct(ActionEvent event) throws IOException {
       anchor2.getChildren().clear();
        AnchorPane an = FXMLLoader.load(getClass().getResource("/views/addProduct.fxml"));
       anchor2.getChildren().addAll(an);

    }

    @FXML
    private void imageBrowse(ActionEvent event) {
         try{        
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
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void onselect(MouseEvent event) throws IOException {
        
        
        TablePosition tablePosition = idTableView.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Product ctable = idTableView.getItems().get(row);
        nom.setText(ctable.getName());
        //imageBrowse.setText(ctable.getImage_url());

        price.setText(Float.toString(ctable.getPrice()));
        desc.setText(ctable.getDescription());
        
        
        
        
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
            cards.setLocation(getClass().getResource("/views/ProductAdmin.fxml"));
            try {
                AnchorPane anchorPane = cards.load();
                ProductAdminController ProduitServ = cards.getController();
                ProduitServ.AddProduit(prods.listProduit().get(i), myListener);

                if (column == 2) {
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

        nom.setText(product.getName());
        price.setText(Float.toString(product.getPrice()));
        desc.setText(product.getDescription());
         ivProduit.setImage(new Image("http://localhost/uploads/images/" + product.getImage_url()));


    }

    
    }

   
    
    
    
    
    
    

    
