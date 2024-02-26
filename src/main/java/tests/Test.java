package tests;

import models.Utilisateur;
import services.UtilisateurService;
import utiles.MyDataBase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException, SQLException {
        MyDataBase.getInstance();
        UtilisateurService us = new UtilisateurService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        LocalDate dateNaissance = LocalDate.of(2001, 9, 18);
        Utilisateur aa = us.getUtilisateurById(6);
        System.out.println(aa.getNom());



//        try {
//            us.ajouter(new Utilisateur("maalaoui" ,
//                    "insaf",
//                    "kfgj",
//                    12457,
//                    "femme",
//                    "baraa@gmail",
//                    "sport",
//                    "ariana",
//                    dateNaissance));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            us.modifier(new Utilisateur(1,
//                    12457 ,
//                    "ben nacef",
//                    "zeyneb",
//                    "dkdf",
//                    "femme",
//                    "baraa@gmail",
//                    "sport",
//                    "ariana",
//                    dateNaissance));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            us.supprimer(1);
//            System.out.println("Personne supprimée!!!");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            System.out.println(us.afficher());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }
}