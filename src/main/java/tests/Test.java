package tests;

import models.Utilisateur;
import services.UtilisateurService;
import utiles.MyDataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException, SQLException {
        String password ="1deb6393fac2d239a6d862fc37acf8ec9395ebae94cbb3c1afe5b8048b9d60b1";
        String hashedP = hashPassword(password);
        System.out.println(hashedP);
        MyDataBase.getInstance();
        UtilisateurService us = new UtilisateurService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        LocalDate dateNaissance = LocalDate.of(2001, 9, 18);
        Utilisateur aa = us.getUtilisateurById(6);
        System.out.println(aa.getNom());
    }

    private static String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}