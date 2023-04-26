package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.PrivateMessage;
import esprit.changemakers.sportshub.entities.User;
import esprit.changemakers.sportshub.services.IPrivateMessageService;
import esprit.changemakers.sportshub.services.IUserService;
import esprit.changemakers.sportshub.services.Impl.PrivateMessageServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jozef
 */
public class ChatController implements Initializable {

    @FXML
    private TextField messageTextField;
    @FXML
    private ScrollPane messagesScroll;
    @FXML
    private VBox chatVbox;
    @FXML
    private TextField tfConx1;
    @FXML
    private TextField tfSendTo;
    @FXML
    private Label myId;
    @FXML
    private Label myFriendId;
    @FXML
    private VBox convVb;

    PrintWriter pw;
    IPrivateMessageService privateMessageService = new PrivateMessageServiceImpl();

    private int thisUserId;

    private int userFriendId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        convVb.setPadding(new Insets(0, 0, 0, 50));
        convVb.setSpacing(20);
        connectToUser();
        chatVbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                messagesScroll.setVvalue((Double) newValue);
            }
        });
    }

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public void loadAllConvUsers() {
        convVb.getChildren().clear();
        Set<Integer> conv = new HashSet<>();
        Set<Integer> revConv = new HashSet<>();
        revConv = privateMessageService.getAllUserConvInv(thisUserId);
        conv = privateMessageService.getAllUserConv(this.thisUserId);
        privateMessageService.getAllUserConver(thisUserId);
        Set<Integer> itConv = new HashSet<>();
        if (conv.isEmpty()) {
            itConv = revConv;
        } else if (revConv.isEmpty()) {
            itConv = conv;
        } else {
            itConv.addAll(revConv);
            itConv.addAll(conv);
        }
        if (itConv.isEmpty()) {
            System.out.println("start a new conversation");
            this.myFriendId = HomeController.getInstance().changIdC;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../views/ConversationWithUserItem.fxml"));
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            node.setOnMouseClicked(event -> {
                this.userFriendId = Integer.parseInt(HomeController.getInstance().changIdC.getText());
                this.thisUserId = Integer.parseInt(HomeController.getInstance().idUserLb.getText());
                System.out.println(" gotta load something like my id" + thisUserId + " friendId " + this.userFriendId);
                sendToFriend(this.thisUserId, this.userFriendId);
            });
            this.userFriendId = Integer.parseInt(HomeController.getInstance().changIdC.getText());
            ConversationWithUserItemController conversationWithUserItemController = loader.getController();
            conversationWithUserItemController.loadConvUser(this.userFriendId);
            convVb.getChildren().add(node);
        } else {
            for (Integer i : itConv) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../views/ConversationWithUserItem.fxml"));
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                node.setOnMouseClicked(event -> {
                    int myFrinedId = Integer.parseInt(((Label) ((HBox) ((HBox) event.getSource()).getChildren().get(1)).getChildren().get(0)).getText());
                    this.userFriendId = myFrinedId;
                    this.thisUserId = Integer.parseInt(HomeController.getInstance().idUserLb.getText());
                    sendToFriend(this.thisUserId, this.userFriendId);
                });
                ConversationWithUserItemController conversationWithUserItemController = loader.getController();
                conversationWithUserItemController.loadConvUser(i);
                convVb.getChildren().add(node);
            }
        }

    }

    public void loadAllConvUsersWithFriend(int friendId) {
        convVb.getChildren().clear();
        Set<Integer> conv = new HashSet<>();

        conv = privateMessageService.getAllUserConv(this.thisUserId);
        System.out.println(conv);
        if (conv.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../views/ConversationWithUserItem.fxml"));
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.userFriendId = Integer.parseInt(HomeController.getInstance().changIdC.getText());
            this.thisUserId = Integer.parseInt(HomeController.getInstance().idUserLb.getText());
            node.setOnMouseClicked(event1 -> {
                this.userFriendId = Integer.parseInt(HomeController.getInstance().changIdC.getText());
                this.thisUserId = Integer.parseInt(HomeController.getInstance().idUserLb.getText());
                sendToFriend(this.thisUserId, this.userFriendId);
            });
            ConversationWithUserItemController conversationWithUserItemController = loader.getController();
            conversationWithUserItemController.loadConvUser(friendId);


            convVb.getChildren().add(node);
            //   convVb.getChildren().add(label);
            System.out.println("Childreen " + convVb.getChildren());
        } else {
            for (Integer i : conv) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../views/ConversationWithUserItem.fxml"));
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                node.setOnMouseClicked(event -> {
                    this.userFriendId = friendId;
                    this.thisUserId = Integer.parseInt(HomeController.getInstance().idUserLb.getText());
                    sendToFriend(this.thisUserId, this.userFriendId);
                });
                ConversationWithUserItemController conversationWithUserItemController = loader.getController();
                conversationWithUserItemController.loadConvUser(i);
                convVb.getChildren().add(node);
            }
        }

    }

    public void loadConversation(int myId, int myFriendId) {
        chatVbox.getChildren().clear();
        ObservableList<PrivateMessage> privateMessages = privateMessageService.
                getTwoUsersDiscSortedByTimestamp(myId,
                        myFriendId);
        for (PrivateMessage pm : privateMessages) {
            if (pm.getIdFirstUser() == myId) {
                loadRightMsg(pm.getContent());
            } else {
                loadLeftMsg(pm.getContent());
            }
        }
    }

    // load the msg with the blue background on the right ( sender )
    public void loadRightMsg(String msg) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-color:rgb(239,242,255);" +
                "-fx-background-color: rgb(15,125,242);" +
                "-fx-background-radius:20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));

        hBox.getChildren().add(textFlow);
        chatVbox.getChildren().add(hBox);

    }

    // load the msg with the gray background on the left ( reciver )
    public void loadLeftMsg(String msg) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233,233,235);" +
                        "-fx-background-radius:20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));

        hBox.getChildren().add(textFlow);
        chatVbox.getChildren().add(hBox);

    }

    // Just like loadleft() but we added the thread effect so it can be run from another thread in javafx
    public void loadLeftMsgWithServer(String msg) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msg);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233,233,235);" +
                        "-fx-background-radius:20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatVbox.getChildren().add(hBox);
            }
        });

    }


    public User connectToUser() {
        IUserService userService = new UserServiceImpl();
        User user = userService.getById(Integer.parseInt(HomeController.getInstance().idUserLb.getText()));
        this.thisUserId = user.getId();
        //myId.setText(String.valueOf(user.getId()));
        System.out.println(user.getNom() + " Connected");

        //if()
        loadAllConvUsers();
        //loadAllConvUsersWithFriend(Integer.parseInt(this.myFriendId.getText()));

        return user;
    }

    public User sendToFriend(int myId, int idFriend) {
        this.userFriendId = idFriend;
        System.out.println("eee plsss" + this.userFriendId);
        chatVbox.getChildren().clear();
        IUserService userService = new UserServiceImpl();
        User user = userService.getById(idFriend);

        //myFriendId.setText(String.valueOf(user.getId()));
        System.out.println(" U can now start talking to " + user.getNom());
        loadConversation(myId, idFriend);

        try {
            Socket socket = new Socket("localhost", 1234);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // new logic added
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("j'envoie mon id " + myId + " et l'id de la personne avec laquelle je veux parler "
                    + idFriend + " au serveur");
            outputStream.write(myId);
            outputStream.write(idFriend);

            pw = new PrintWriter(socket.getOutputStream(), true);
            new Thread(() -> {
                try {
                    while (true) {
                        String response = bufferedReader.readLine();
                        loadLeftMsgWithServer(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

  /*  public User sendToFiend(){
        chatVbox.getChildren().clear();
        IUserService userService = new UserServiceImpl();
        User user =userService.getById(Integer.parseInt(tfSendTo.getText()));
        myFriendId.setText(String.valueOf(user.getId()));
        System.out.println(" U can now start talking to "+user.getNom());
        loadConversation();

        try {
            Socket socket = new Socket("localhost",1234);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new  InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // new logic added
            OutputStream outputStream =socket.getOutputStream();
            System.out.println("j'envoie mon id "+Integer.parseInt(myId.getText())+" et l'id de la personne avec laquelle je veux parler "
                    +Integer.parseInt(myFriendId.getText())+" au serveur");
            outputStream.write(Integer.parseInt(myId.getText()));
            outputStream.write(Integer.parseInt(myFriendId.getText()));

            pw = new PrintWriter(socket.getOutputStream(),true);
            new Thread(()->{
                try {
                    while (true) {
                        String response = bufferedReader.readLine();
                        loadLeftMsgWithServer(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }*/

   /* public void sendMessage(){
        String msg = messageTextField.getText();

        if(!msg.isEmpty()){
            IPrivateMessageService privateMessageService = new PrivateMessageServiceImpl();
            loadRightMsg(msg);
            PrivateMessage privateMessage = new PrivateMessage(Integer.parseInt(myId.getText())
                    ,Integer.parseInt(myFriendId.getText())
                    ,msg
                    ,null);
            privateMessageService.create(privateMessage);
        }
        // logique houni i9oul tshouf el id mtaa sayed eli ta7ki m3ah w tmapih bel num mte3ou aal server if ()
        String serverMsg = myId.getText() + "<START=OF=REQ>" + myFriendId.getText() + "<START=OF=REQ>" + msg;
          pw.println(serverMsg);
    }*/

    @FXML
    public void sendMessageWithId() {
        String msg = messageTextField.getText();

        if (!msg.isEmpty()) {
            IPrivateMessageService privateMessageService = new PrivateMessageServiceImpl();
            loadRightMsg(msg);
            System.out.println("userId " + thisUserId);
            System.out.println("userFriendId" + userFriendId);
            PrivateMessage privateMessage = new PrivateMessage(this.thisUserId
                    , this.userFriendId
                    , msg
                    , null);
            privateMessageService.create(privateMessage);
        }
        // logique houni i9oul tshouf el id mtaa sayed eli ta7ki m3ah w tmapih bel num mte3ou aal server if ()
        String serverMsg = this.thisUserId + "<START=OF=REQ>" + this.userFriendId + "<START=OF=REQ>" + msg;
        pw.println(serverMsg);
    }

}
