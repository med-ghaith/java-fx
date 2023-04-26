/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author med
 */
public class Mailing {
     String user = "hamatalbi9921@gmail.com";
            String  password ="123456789hama";
     public static void send(String to, String sub,String msg, /*final*/ String user,  String pass) 
    {//un objet Properties qui contient les paramètres d'initialisation. Un tel objet est obligatoire
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");   // Assuming you are sending email from gmail
        props.put("mail.smtp.port", "587");	
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        System.setProperty("mail.pop3s.ssl.protocols", "TLSv1.2");
        
        
        
       
//La classe Session encapsule pour un client donné sa connexion avec le serveur de mails.
//Cette classe encapsule les données liées à la connexion (options de configuration et données d'authentification).
// C'est à partir de cet objet que toutes les actions concernant les mails sont réalisées.

//Les paramètres nécessaires sont fournis dans un objet de type Properties.
//Un objet de ce type est utilisé pour contenir les variables d'environnements :
// placer certaines informations dans cet objet permet de partager des données.
        
        Session session = Session.getDefaultInstance(props,new Authenticator() 
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() 
            {//un objet Authenticator optionnel qui permet d'authentifier l'utilisateur auprès du serveur de mails
                return new PasswordAuthentication("hamatalbi9921@gmail.com", "123456789hama");
            }
        });

        try 
        { // Create a default MimeMessage object.
            //La classe MimeMessage est la seule classe fille qui hérite de la classe Message.
            // Elle dispose de plusieurs constructeurs.
            Message message = new MimeMessage(session);
            
            
            // Set From: header field of the header.
            message.setFrom(new InternetAddress("hamatalbi9921@gmail.com"));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            
            // Set Subject: header field
            message.setSubject(sub);
            
               // Now set the actual message
       
            message.setText(msg);
 
        // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
            
           
        } catch (MessagingException e) 
        {
            JOptionPane.showMessageDialog(null,"Something happened!");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
    }
}