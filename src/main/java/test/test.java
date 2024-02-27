package test;

import models.Transport;
import models.typeT;
import services.TransportService;
import utils.DataBase;

import java.sql.SQLException;
import java.sql.Time;

public class test {
    public static void main(String[] args) throws SQLException {
        DataBase bd = new DataBase();
        TransportService TS = new TransportService();


      Transport nouveauTransport = new Transport(1, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00"));



        Transport transport = new Transport(1, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00"));


       //ajouter un moyen de transport//
        TS.ajouter(new Transport(2, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00")));


       //modifier un moyen de transport//
        try {
            TS.modifier(new Transport(3, typeT.taxi, "Ariana", "GHazela", Time.valueOf("08:00:00"), Time.valueOf("09:30:00")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
       }

        //supprimer un moyen de transport//
        try {
            TS.supprimer(3);
            System.out.println("Transport supprimée!!!");
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }
        //afficher un moyen de transport//
        try {
            System.out.println(TS.afficher());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }





    }
}





