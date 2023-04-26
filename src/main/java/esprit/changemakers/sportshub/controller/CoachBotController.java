package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.*;
import esprit.changemakers.sportshub.services.*;
import esprit.changemakers.sportshub.services.APIs.ExcelApiImpl;
import esprit.changemakers.sportshub.services.Impl.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jozef
 */
public class CoachBotController implements Initializable {

    @FXML
    private TextField messageTextField;
    @FXML
    private ScrollPane messagesScroll;
    @FXML
    private VBox chatVbox;
    @FXML
    private Button btnSend;

    private boolean askedAboutWeight=false;

    private boolean askedAboutHeight=false;

    private boolean endedEquip=false;

    private boolean endedMuscle=false;

    private int weight=-1;

    private int height=-1;

    private double imc=-1;

    private int timesAskedAboutEquipment=0;

    private int timesAskedAboutMuscle=0;


    private List<Equipment> equipments= new ArrayList<>();

    private List<Muscle> muscles = new ArrayList<>();

    private List<String> messages = new ArrayList<>();

    ICoachBotService coachBotService = new CoachBotServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatVbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                messagesScroll.setVvalue((Double)newValue);
            }
        });
        loadLeftMsg("aaslema");
        askAboutWeight();
        messageTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    sendMessage();
                    if (weight<0){
                        System.out.println(messages.get(0));
                        handleWeightQuest(messages.get(0));
                        if(weight>0){
                            askAboutHeight();
                        }
                    }else if( height < 0){
                        handleHeightQuest(messages.get(0));
                        if(height > 0){
                            handleImcResp();
                                if(timesAskedAboutEquipment==0){
                                    askAboutEquipments();
                                }
                        }
                    }else if(endedEquip==false ){
                        if(timesAskedAboutEquipment<4){
                            if(coachBotService.extractPositiveOrNegativeResp(messages.get(0))==0){
                                endedEquip=true;
                                if(timesAskedAboutMuscle==0){
                                    askAboutMuscle();
                                }
                            }else {
                                handleEquipmentResp(messages.get(0));
                            }
                        }

                    }else if(endedMuscle==false){
                        if(timesAskedAboutMuscle<4){
                            if(coachBotService.extractPositiveOrNegativeResp(messages.get(0))==0){
                                endedMuscle=true;
                                loadLeftMsg("kamalnaaaaaaaaa");
//                                GeneratedProgramController.getInstance().exercices.addAll(coachBotService.generateExercice(equipments,muscles));
                                loadGeneretedExercice(coachBotService.generateExercice(equipments,muscles));
                            }else {
                                handleMuscleResp(messages.get(0));
                            }
                        }

                    }else {
                        loadLeftMsg("ouf hamdoullah kamalt ..");
                    }
                }
            }
        });

        // the same handle above just for the send button
        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    sendMessage();
                    if (weight<0){
                        System.out.println(messages.get(0));
                        handleWeightQuest(messages.get(0));
                        if(weight>0){
                            askAboutHeight();
                        }
                    }else if( height < 0){
                        handleHeightQuest(messages.get(0));
                        if(height > 0){
                            handleImcResp();
                            if(timesAskedAboutEquipment==0){
                                askAboutEquipments();
                            }
                        }
                    }else if(endedEquip==false ){
                        if(timesAskedAboutEquipment<4){
                            if(coachBotService.extractPositiveOrNegativeResp(messages.get(0))==0){
                                endedEquip=true;
                                if(timesAskedAboutMuscle==0){
                                    askAboutMuscle();
                                }
                            }else {
                                handleEquipmentResp(messages.get(0));
                            }
                        }

                    }else if(endedMuscle==false){
                        if(timesAskedAboutMuscle<4){
                            if(coachBotService.extractPositiveOrNegativeResp(messages.get(0))==0){
                                endedMuscle=true;
                                loadLeftMsg("kamalnaaaaaaaaa");
//                                GeneratedProgramController.getInstance().exercices.addAll(coachBotService.generateExercice(equipments,muscles));
                                loadGeneretedExercice(coachBotService.generateExercice(equipments,muscles));
                            }else {
                                handleMuscleResp(messages.get(0));
                            }
                        }

                    }else {
                        loadLeftMsg("ouf hamdoullah kamalt ..");
                    }
                }
        });

    }

    private void loadGeneretedExercice(List<Exercice> generateExercice)  {
        try {
            HomeController.getInstance().loadGeneratedItem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askAboutMuscle(){
        if(timesAskedAboutMuscle==0){
            loadLeftMsg("Beh juste sou2el lekher w nkamlou");
            loadLeftMsg("bech naatik liste mtaa des muscles wenti 9oli ( bel ka3ba bel ka3ba ) chnowa t7eb tekhdem");
            IMuscleService muscleService = new MuscleServiceImpl();
            muscleService.getAll().forEach(e->{
                loadLeftMsg(e.getName());
            });
        }else if(timesAskedAboutMuscle<4) {
            loadLeftMsg("fama muscle ekhor ?");
        }else {
            loadLeftMsg("yeziha");
        }
        timesAskedAboutMuscle++;
    }
    private void handleMuscleResp(String msg) {
        ICoachBotService coachBotService = new CoachBotServiceImpl();
        if(timesAskedAboutMuscle==0){
            askAboutMuscle();
        }
        if(coachBotService.extractMuscle(msg) == null){
            loadLeftMsg("Sorry fhemtech chneya 9olt");
            loadLeftMsg("Aawed aatini muscle mel liste eli lfou9");
        }else {
            Muscle muscle = coachBotService.extractMuscle(msg);
            loadLeftMsg("donc tu veux cibler "+muscle.getName());
            muscles.add(muscle);
            askAboutMuscle();
        }
    }

    private void handleSalemMsg(String msg) {
        //ExcelApiImpl.readColumnByName()

    }



    public void sendMessage(){
        String msg = messageTextField.getText();
        if(!msg.isEmpty()){
            loadRightMsg(msg);
            messages.clear();
            messages.add(msg);
        }
        messageTextField.clear();
    }

    public void askAboutWeight(){
        if(askedAboutWeight){
            loadLeftMsg("Bon n3awed nes2lk bel anglais belek fhemtnich");
            loadLeftMsg("How many kilos do you weigh?");
            loadLeftMsg("taamel mzeya bel kg :p");
        }else {
            loadLeftMsg("Bon nebdew bsou2el seheel");
            loadLeftMsg("9adeh touzen ?");
            askedAboutWeight=true;
        }
    }
    public void handleWeightQuest(String msg){
        ICoachBotService coachBotService = new CoachBotServiceImpl();
        int youzen = coachBotService.extractWeight(msg);
            if(youzen == -3){
                loadLeftMsg("3andkom maje3a w 3andkom internet ?");
                askAboutWeight();
            }else if (youzen == -2){
                loadLeftMsg("a7kiw m3aya bel we7ed bel we7ed taamlou mzeya");
                askAboutWeight();
            }else if(youzen == -1){
                loadLeftMsg("Sorry fhemtekch ch9olt");
                askAboutWeight();
            }else {
                loadLeftMsg("niice");
                weight = coachBotService.extractWeight(msg);
            }
    }

    public void askAboutHeight(){
        if(askedAboutHeight){
            loadLeftMsg("Bon n3awed nes2lk bel anglais belek fhemtnich");
            loadLeftMsg("How tall are you?");
            loadLeftMsg("taamel mzeya bel cm :p");
        }else {
            loadLeftMsg("Sou2el théni sehel zeda");
            loadLeftMsg("9adeh toulek?");
            askedAboutHeight=true;
        }
    }

    public void askAboutEquipments(){
        ICoachBotService coachBotService = new CoachBotServiceImpl();
        if(timesAskedAboutEquipment==0){
            loadLeftMsg("Beh taw net3adew lel rasmi");
            loadLeftMsg("bech naatik liste mtaa des equipement wenti 9oli ( bel ka3ba bel ka3ba ) ch3andek fedar w chnowa tnejem testaamel");
            IEquipmentService equipmentService = new EquipmentServiceImpl();
            equipmentService.getAll().forEach(e->{
                loadLeftMsg(e.getName());
            });
        }else if(timesAskedAboutEquipment<4) {
            loadLeftMsg("aandek 7aja okhra ?");
        }else {
            loadLeftMsg("yeziha");
        }
        timesAskedAboutEquipment++;
    }
    private void handleEquipmentResp(String msg) {
        ICoachBotService coachBotService = new CoachBotServiceImpl();
        if(timesAskedAboutEquipment==0){
            askAboutEquipments();
        }
        if(coachBotService.extractEquipment(msg) == null){
            loadLeftMsg("Sorry fhemtech chneya 9olt");
            loadLeftMsg("Aatini des equipments mel liste eli lfou9");
        }else {
            Equipment equipment = coachBotService.extractEquipment(msg);
            loadLeftMsg("donc aandek "+equipment.getName());
            equipments.add(equipment);
            askAboutEquipments();
        }

    }

    public void handleHeightQuest(String msg){
        ICoachBotService coachBotService = new CoachBotServiceImpl();
        int toulou = coachBotService.extractHeight(msg);
        if(toulou == -3){
            loadLeftMsg("being too short mch 3yb ama mch lahal darja");
            askAboutHeight();
        }else if (toulou == -2){
            loadLeftMsg("Salafni chwaya xD");
            askAboutHeight();
        }else if(toulou == -1){
            loadLeftMsg("Sorry fhemtekch ch9olt");
            askAboutHeight();
        }else {
            loadLeftMsg("Mrigueel");
            height = coachBotService.extractHeight(msg);
        }
    }

    public void handleImcResp(){
        if(weight > 0 && height > 0){
            loadLeftMsg("Bon bech ne7sbou el IMC mte3ek ");
            loadLeftMsg("donc touzen "+weight+"kg w toulek "+height+" cm");
            imc = weight / Math.pow((double)(height)/100,2);
            loadLeftMsg("donc el imc mte3ek iji: "+imc);
            if(imc < 18.5d){
                loadLeftMsg("Vous avez une insuffisance pondérale (maigreur)");
            }else if(imc>= 18.5d && imc<= 25){
                loadLeftMsg("vous avez une corpulence normale");
            }else if(imc>25 && imc < 30){
                loadLeftMsg("vous etes en surpoid nous vous conseillant de suivre notre régime");
            }else if(imc >= 30 && imc < 35){
                loadLeftMsg("vous avez une obésité sévère, on vas vous proposer un programme adéquoi");
            }else if(imc > 40){
                loadLeftMsg("vous avez une obésité morbide ou massive");
            }
        }
    }

    // load the msg with the blue background on the right ( sender )
    public void loadRightMsg(String msg){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-color:rgb(239,242,255);"+
                "-fx-background-color: rgb(15,125,242);"+
                "-fx-background-radius:20px;"+
                "-fx-font-size: 20px");

        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0.934,0.945,0.996));

        hBox.getChildren().add(textFlow);
        chatVbox.getChildren().add(hBox);

    }
    // load the msg with the orange background on the left ( reciver )
    public void loadLeftMsg(String msg){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(255,188,97);"+
                        "-fx-background-radius:20px;"+"-fx-font-size: 20px");

        textFlow.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlow);
        chatVbox.getChildren().add(hBox);


    }

}
