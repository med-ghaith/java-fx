package esprit.changemakers.sportshub.controller.Modulegestionsalle;

import esprit.changemakers.sportshub.controller.HomeController;
import esprit.changemakers.sportshub.dao.Impl.modulegestionsalle.GymDaoImpl;
import esprit.changemakers.sportshub.entities.Modulegestionsalle.Gym;
import esprit.changemakers.sportshub.services.Impl.Modulegestionsalle.SalleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListeGymController{
    @FXML
    private GridPane grid;
    @FXML
    private TextField tfsearch;
    @FXML
    private ComboBox listeville;
    @FXML
    private ComboBox listedelegation;


    private ObservableList<Gym> gyms=null;
    private MyListener myListener;


    private ObservableList<Gym> getData(){
        GymDaoImpl data=new GymDaoImpl();
        return data.getAll();
    }

    private Parent root;
    private Boolean test=false;
    ObservableList<String> ville =
            FXCollections.observableArrayList("All of Tunisia",
                    "Ariana","Béja","Ben Arous","Bizerte","Gabes","Gafsa",
                    "Jendouba","Kairouan","Kasserine","Kebili","La Manouba","Le Kef","Mahdia",
                    "Médenine","Monastir","Nabeul","Sfax","Sidi Bouzid","Siliana","Sousse",
                    "Tataouine","Tozeur","Tunis","Zaghouan"
            );


    public void initialize() {
        listeville.getItems().addAll(ville);
        if(!this.test){
            gyms=getData();
        }
        if (gyms.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Gym gym) throws IOException {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/Modulegestionsalle/ProfilSalle.fxml"));
                    root=loader.load();
                    ProfilSalleController pr=loader.getController();

                    pr.setData(gym);
                    HomeController.getInstance().getBorderPane().setCenter(root);
                }
            };
        }
        int column = 0;
        int row = 1;
        System.out.println(gyms);
        try {
            for (int i = 0; i < gyms.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../../../../../views/Modulegestionsalle/gymitems.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                GymItemsController itemController = fxmlLoader.getController();
                itemController.setData(gyms.get(i),myListener);

                if (column == 1) {
                    column = 0;
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

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    methode qui filtre selon l'input ou combobox ville ou combobox delegation
    public void Filtre(String value){

        if(String.valueOf(listeville.getValue()).equals("All of Tunisia")&&tfsearch.getText().equals("")){
            gyms=getData();
            tfsearch.setText("");
            listedelegation.getItems().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            grid.getChildren().clear();
            initialize();
        }else{
            SalleService s=new SalleService();
            this.gyms=s.getGymbyfiltre(value);
            this.test=true;
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            grid.getChildren().clear();
            initialize();
        }
    }
//    btn search
    public void onSearch(ActionEvent actionEvent) {
       Filtre(tfsearch.getText());
    }
//    combobox choosedelegation
    public void ChooseDelegation(ActionEvent actionEvent) {
        Filtre(String.valueOf(listedelegation.getValue()));
    }
//    combobox choosing ville avec condition pour remplire combobox de delegation
    public void Chooseville(ActionEvent actionEvent) {
        tfsearch.setText("");
        Filtre(String.valueOf(listeville.getValue()));
        if(listeville.getValue().equals("All of Tunisia")){
            listedelegation.getItems().clear();
        }
        if(listeville.getValue().equals("Bizerte")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll("Bizerte Nord","Jarzouna","Bizerte Sud","Sejnane",
            "Joumine" , "Mateur" , "Ghezala" , "Menzel", "Bourguiba" , "Tinja" ,
            "Utique","Ghar El Melh" , "Menzel Jemil" , "El Alia" , "Ras Jebel");
        }else if (listeville.getValue().equals("Ariana")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Ariana Ville","Soukra","Raouède"
                    ,"Kalâat Andalous","Sidi Thabet","Cité Attadhamon","M’nihla");
        }else if (listeville.getValue().equals("Tunis")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Carthage" , "La Médina" , "Bab Bhar" , "Bab Souika" , "Omrane"
                    , "Omrane Supérieur" , "Attahrir" , "El Menzah" , "Cité Alkhadhra" , "Bardo" , 
                    "Séjoumi" , "Azzouhour" , "Alhrairia" , "Sidi Hsine" , "Ouardia" 
                    , "Kabaria" , "Sidi Elbéchir" , "Jebel Jelloud" , "La Goulette" , "Le Kram" , "La Marsa");
        }else if (listeville.getValue().equals("La Manouba")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Manouba" , "Oued Ellil" ,"Tebourba" , "Battan" 
                    , "Jedaida" , "Mornaguia" , "Borj Amri" , "Douar Hicher");
        }else if (listeville.getValue().equals("Béja")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Béja nord","Béja sud","Amdoun"
                   ,"Nefza","Teboursouk","Tibar","Testour","Goubellat","Mejez El Bab");
        }else if (listeville.getValue().equals("Jendouba")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Jendouba","Jendouba Nord","Boussalem","Tabarka" ,
                    "Ain Drahem","Fernana","Ghardimaou","Oued Mliz","Balta Bouaouen");
        }else if (listeville.getValue().equals("Ben Arous")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Ben Arous","Nouvelle Médina","Mourouj","Hammam Lif","Hammam Chatt" ,
                    "Boumhel Bassatine","Ezzahra","Radès","Megrine","M’hamdia","Fouchana","Mornag");
        }else if (listeville.getValue().equals("Nabeul")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Nabeul","Dar Chaâbane Elfehri","Béni Khiar","Korba" ,
                    "Menzel Temime","Mida","Kelibia","Hammam Ghezaz","Haouaria","Takelsa"
                   ,"Slimane","Menzel Bouzelfa","Béni Khalled","Grombalia","Bouârgoub","Hammamet");
        }else if (listeville.getValue().equals("Zaghouan")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Zaghouan","Zériba","Bir Mecharga","Fahs","Nadhour","Saouaf");
        }else if (listeville.getValue().equals("Siliana")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Siliana nord","Siliana sud","Bouarada" ,"Gâafour" ,
                    "El Aroussa","Le Krib","Bourouis","Makther","Rouhia","Kesra","Bargou");
        }else if (listeville.getValue().equals("Kef")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll(  "Kef Ouest","Kef Est","Nebeur","Sakiet Sidi Youssef","Tejerouine"
                   ,"Kalâat sinane","Kalâa El khasba","Jerissa","Gsour","Dahmani","Le Sers");
        }else if (listeville.getValue().equals("Kasserine")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Kasserine Nord","Kasserine Sud","Azzouhour","Hassi ferid","Sbitla","Sbiba"
                   ,"Jedliane","El Ayoun" ,"Tela","Hidra","Foussana","Feriana","Mejel Bel Abbes");
        }else if (listeville.getValue().equals("Kairouan")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Kairouan Nord" ,"Kairouan Sud","Chebika","Sebikha","Oueslatia","Haffouz" ,
                    "El Ala","Hajeb El Ayoun","Nasrallah","Cherarda","Bouhajla");
        }else if (listeville.getValue().equals("Sousse")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Sousse Ville","Zaouia Ksiba Thrayat","Sousse Ryadh" ,
                    "Sousse Jawhara","Sousse Sidi Abdelhamid","Hammam sousse","Akouda","Kalâa Elkébira","Sidi Bouali","Hergla" ,
                    "Enfidha","Bouficha","Koundar","Sidi Elheni","Msaken","Kalâa Ességhira");
        }else if (listeville.getValue().equals("Monastir")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Monastir","Ouerdanine","Sahline","Zéramdine","Béni Hassan" ,
                    "Jammel","Benbla","Moknine","Bekalta","Teboulba","Ksar Helal","Ksibet Medyouni","Sayada Lamta Bouhjar");
        }else if (listeville.getValue().equals("Mahdia")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Mahdia","Boumerdes","Ouled Chamekh","Chorbane","Hbira","Souassi" ,
                    "Eljem","Chebba","Malloulech","Sidi Alouane","Ksour Essef");
        }else if (listeville.getValue().equals("Sfax")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Sfax Ville","Sfax Ouest","Sakiet Ezzit","Sakiet Eddaier","Sfax sud",
                    "Tina","Agareb","Jebeniana","El Amra","El Hencha","Menzel chaker","Ghraiba","Bir Ali Ben Khelifa","Sekhira"
                   ,"Mahrès","Kerkennah");
        }else if (listeville.getValue().equals("Sidi Bouzid")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll(  "Sidi Bouzid Ouest","Sidi Bouzid Est","Jelma","Sabbalet Ouled Askar"
                   ,"Bir Hfay","Sidi Ali Benôun","Menzel Bouzayane","Meknassi","Souk Jedid","Mezouna","Regueb","Ouled Haffou");
        }else if (listeville.getValue().equals("Tataouine")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Tataouine Nord","Tataouine Sud","Smar","Bir Lahmer","Ghomrassen","Dhehiba","Remada");
        }else if (listeville.getValue().equals("Gafas")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Gafsa Nord","Sidi Aich","El Ksar","Gafsa Sud","Moulares"
                   ,"Redyef","Métlaoui","El Mdhilla","El Guettar","Belkhir","Sned");
        }else if (listeville.getValue().equals("Tozeur")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Tozeur","Degueche","Tameghza","Nefta","Hezoua");
        }else if (listeville.getValue().equals("Gabés")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Gabès ville","Gabès ouest","Gabès sud","Ghannouch"
                   ,"Metouia","Menzel habib","Hamma","Matmata","Matmata nouvelle","Mareth");
        }else if (listeville.getValue().equals("Kébili")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Kébili Sud","Kébili Nord","Souk El Ahad","Douz nord","Douz sud","El Faouar");
        }else if (listeville.getValue().equals("Medenine")){
            listedelegation.getItems().clear();
            listedelegation.getItems().addAll( "Mednine Nord","Mednine Sud","Béni khedach","Ben Guerdene"
                   ,"Zazis","Jerba Houmet Souk","Jerba Midoun","Jerba Ajim","Sidi Makhlouf");
        }
    }
//    btn selected
    public void Selected(ActionEvent actionEvent) {
        onSearch(actionEvent);
        listeville.getSelectionModel().select(0);
    }

    public void onTextChange(KeyEvent keyEvent) {
        Filtre(tfsearch.getText());
    }
}
