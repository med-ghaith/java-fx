package esprit.changemakers.sportshub.network.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer extends Thread {


    private boolean isActive= true;
    private int idReciver=0;
    private int idSender=0;
    private List<Conversation> clients = new ArrayList<Conversation>();
    public static void main(String[] args ){
        new MyServer().start();
    }

    @Override
    public void run() {
        try {
            //InetAddress addr = InetAddress.getByName("192.168.241.182");
            //ServerSocket serverSocket = new ServerSocket(1234,0,addr);
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started, waiting for connections");
            while (isActive){
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    System.out.println("Server Socker !!:");
                    idSender = inputStream.read();
                    idReciver = inputStream.read();
                    System.out.println("sender to reciver : "+idSender+"=>"+idReciver);
                        if(verifConvBetwTwo(idSender,idReciver)){
                            System.out.println("cnv : "+idSender+"=>"+idReciver+" already exist");
                            Conversation conversation = new Conversation(idSender,idReciver,socket);
                            System.out.println("Client using id= "+ idSender+" has connected again with ip: "+socket.getRemoteSocketAddress().toString());
                            conversation.start();
                        }else {
                            System.out.println("Client using id= "+ idSender+" has connected with ip: "+socket.getRemoteSocketAddress().toString());//+" is connected using ip "+ipClient);
                            Conversation conversation = new Conversation(idSender,idReciver,socket);
                            clients.add(conversation);
                            conversation.start();
                        }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Conversation extends Thread{
        protected Socket socketClient;
        protected int idSender;
        protected int idReciver;

        public Conversation(int idSender,int idReciver,Socket socketClient){
            this.socketClient = socketClient;
            this.idSender = idSender;
            this.idReciver= idReciver;
        }

        public void sendMessage(String req,Socket socket,int idSender,int idReciver){
            try {
            for (Conversation client:clients) {
                    if(client.socketClient!=socket){
                        if(client.idSender != idSender){
                            if(client.idReciver == idSender){
                                if(client.idSender == idReciver){
                                    PrintWriter printWriter = new PrintWriter(client.socketClient.getOutputStream(),true);
                                    printWriter.println(req);
                                }
                            }
                        }
                    }

            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                InputStream inputStream = socketClient.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true){
                    String req = bufferedReader.readLine();
                    System.out.println("Hedhi akel requete "+req);
                    if ( req.contains("<START=OF=REQ>")){
                        String[] requestParam = req.split("<START=OF=REQ>");
                            String message = requestParam[2];
                            int idReciver = Integer.parseInt(requestParam[1]);
                            int idSender = Integer.parseInt(requestParam[0]);
                        System.out.println("Hedha l'id mtaa clientReciver"+idReciver);
                            sendMessage(message,socketClient,idSender,idReciver);
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean verifConvBetwTwo(int idSender,int idReciver){
        for (Conversation cnv:clients) {
            if(cnv.idSender == idSender && cnv.idReciver == idReciver){
                return true;
            }
        }
        return false;
    }

}
