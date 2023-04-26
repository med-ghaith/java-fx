/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.services.Impl.ReclamationServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.*;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Callback;



/**
 *
 * @author med
 */
public class ListReclamationUserController implements Initializable {
    
    ReclamationServiceImpl ser = new ReclamationServiceImpl();
    
    ObservableList<Reclamation>  RecList = null;
    int id = HomeController.getInstance().getIdUser();
    
    @FXML
    TableView<Reclamation> list;
    
    @FXML
    private TableColumn<Reclamation, Integer> iduser;
    
    @FXML
    private TableColumn<Reclamation, String> type;
    
    @FXML
    private TableColumn<Reclamation, String> object;
    
    @FXML
    private TableColumn<Reclamation, String> status;
    
    @FXML 
    private TableColumn<Reclamation, String> description;
    
    @FXML
    private TableColumn<Reclamation, Date> date;
    
    @FXML
    private TextField cherchertxt;
    
    @FXML
    private TableColumn<Reclamation, String> modify;
    
    @FXML
    private TableColumn<Reclamation, String> delete;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Reclamation>  RecList = null;
        try {
            RecList = FXCollections.observableArrayList(ser.getReclamationByUser(id));
            list.setItems(RecList); 
           list.setTableMenuButtonVisible(false);
            FilteredList<Reclamation> filteredData = new FilteredList<>(RecList, b -> true);
            
            cherchertxt.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(r -> {
				// If filter text is empty, display all reclamation.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// lowercase.
				String lowerCaseFilter = newValue.toUpperCase();
				
				if (r.getObject().toUpperCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches 
				} else if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                               else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				
                               else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                                else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                
                                
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(list.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		list.setItems(sortedData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());        }
        
        
        System.out.println(ser.getReclamationByUser(id));
        
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU;
        cellFactoryU = new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell;
                cell = new TableCell<Reclamation, String>() {
                    
                    final Button btn = new Button("Modify");
                    Button btn_up = ReclamationController.getInstance().getBtn();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (getTableView().getItems().get(getIndex()) == null) {

                                    } else {
                                        //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();
                                        
                                        
                                        try {
                                            Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                            
                                            //System.out.println(reclamationSelectione);
                                            
                                            ObservableList observableList =FXCollections.observableArrayList(ser.getReclamationByUser(id));
                                            list.setItems(observableList);
                                            //System.out.println(reclamationSelectione.getDescription());
                                            loadModifyRec(reclamationSelectione, event);
                                           
                                            //ser.updateReclamation(reclamationSelectione);
                                        } catch (Exception ex) {
                                            Logger.getLogger(ListReclamationUserController.class.getName()).log(Level.SEVERE, null, ex);
                                            System.out.println(ex.getMessage()+"**************************************************");
                                        }
                                    }
                                    // list();
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                            btn.setStyle(" -fx-base:#F96504;");

                        }
                    }

                    
                };
                return cell;
            }
        };
         //editer update
         modify.setCellFactory(cellFactoryU);
         
         
         /////////////////
         Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU2
                = 
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button btn = new Button("Delete");
                    
                    //btn.setStyle("-fx-border-color: #ff0000;");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                if (getTableView().getItems().get(getIndex()) == null) {

                                } else {
                                    //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();

                                    
                                    try {
                                        Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                        System.out.println(reclamationSelectione);
                                            deleteReclamation(reclamationSelectione);
                                        ObservableList observableList =FXCollections.observableArrayList(ser.getReclamationByUser(id));
                                         
                                        list.setItems(observableList); 
                                    //System.out.println(reclamationSelectione.getDescription());
                                       
                                    } catch (Exception ex) {
                                        Logger.getLogger(ListReclamationUserController.class.getName()).log(Level.SEVERE, null, ex);
                                        System.out.println(ex.getMessage()+"**************************************************");
                                    }
                                    }
                               // list();

                            });
                            setGraphic(btn);
                            setText(null);
                            btn.setStyle(" -fx-base:#F61B0E;");
                        }
                    }

                    
                };
                return cell;
            }
        };
         //editer update
         delete.setCellFactory(cellFactoryU2);
         /////////////////////////
        
        iduser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeReclamation"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        //list.setItems(ser.getAllReclamation()); 
    }
    
    
    public void loadModifyRec(Reclamation reclamationSelectione, ActionEvent event) throws IOException, SQLException {
                 try {
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReclamationMenu.fxml"));
                     Parent root = loader.load();
                     AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/ReclamationModif.fxml"));
                
                ReclamationController  c = loader.getController();
               
                c.getContent().getChildren().clear();
                c.getContent().getChildren().setAll(pane);
                HomeController.getInstance().getBorderPane().setCenter(root);
                
                ReclamationController.getInstance().setObject(reclamationSelectione.getObject());
                ReclamationController.getInstance().setDescription(reclamationSelectione.getDescription());
                ReclamationController.getInstance().setType(reclamationSelectione.getTypeReclamation());
                ReclamationController.getInstance().getBtn().setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
            ReclamationController.getInstance().setObject(ReclamationController.getInstance().getObject());
                ReclamationController.getInstance().setDescription(ReclamationController.getInstance().getDescription());
                ReclamationController.getInstance().setType(ReclamationController.getInstance().getType());
                Reclamation r = new Reclamation(reclamationSelectione.getIdUser(), ReclamationController.getInstance().getType(), ReclamationController.getInstance().getObject(),ReclamationController.getInstance().getDescription(), reclamationSelectione.getId());
        ser.updateReclamationUser(r);
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modify Reclamation");
        alert.setHeaderText(null);
        alert.setContentText("Information updated");
        alert.show();
    }
});;
               //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              //Scene scene = new Scene(root);
              //stage.setScene(scene);
              //stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ListReclamationUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void deleteReclamation(Reclamation reclamationSelectione){
        try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("are you sure you want to delete this reclamation");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);


            if(result.get() == ButtonType.OK){
                ser.deleteReclamation(reclamationSelectione);
            }
            //oke button is pressed
            else if(result.get() == ButtonType.CANCEL){
                System.out.println("cancel");
            }


            // cancel button is pressed
        } catch (Exception e) {
        }
    }



    
}
