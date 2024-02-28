package tests;

import models.Utilisateur;
import services.UtilisateurService;
import utiles.MyDataBase;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException, SQLException {
//        SendEmail test = new SendEmail();
//        test.envoyer();
//        String password ="1deb6393fac2d239a6d862fc37acf8ec9395ebae94cbb3c1afe5b8048b9d60b1";
//        String hashedP = hashPassword(password);
//        System.out.println(hashedP);
//        MyDataBase.getInstance();
//        UtilisateurService us = new UtilisateurService();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        LocalDate dateNaissance = LocalDate.of(2001, 9, 18);
//        Utilisateur aa = us.getUtilisateurById(6);
//        System.out.println(aa.getNom());
//        String smtpHost = "smtp.gmail.com";
//        int smtpPort = 587; // Port SMTP habituel
//        int timeout = 5000; // Temps d'attente en millisecondes
//
//        boolean isReachable = isSMTPServerReachable(smtpHost, smtpPort, timeout);
//        if (isReachable) {
//            System.out.println("Connexion au serveur SMTP réussie.");
//        } else {
//            System.out.println("Impossible de se connecter au serveur SMTP.");
//        }
//    }
//    public static boolean isSMTPServerReachable(String host, int port, int timeout) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress(host, port), timeout);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }

    }
}