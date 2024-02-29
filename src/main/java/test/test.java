package test;

import models.Transport;
import models.typeT;
import services.TransportService;
import utils.DataBase;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class test {
    public static void main(String[] args) throws SQLException {
        DataBase bd = new DataBase();
        TransportService TS = new TransportService();


        Transport nouveauTransport = new Transport(1, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00"));


        Transport transport = new Transport(1, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00"));


        //ajouter un moyen de transport//
        TS.ajouter(new Transport(2, typeT.bus, "Gare A", "Gare B", Time.valueOf("07:00:00"), Time.valueOf("08:30:00")));


                            //modifier un moyen de transport//
        System.out.println("Modifier un moyen de transport");
        try {
            TS.modifier(new Transport(3, typeT.taxi, "Ariana", "GHazela", Time.valueOf("08:00:00"), Time.valueOf("09:30:00")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

                           //supprimer un moyen de transport//
        System.out.println("supprimer un moyen de transport");
        try {
            TS.supprimer(3);
            System.out.println("Transport supprimée!!!");
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

                        //afficher un moyen de transport//
        System.out.println("affiché les moyen de transports");
        try {
            System.out.println(TS.afficher());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
       }

                        // recupere des details de moyen de transport//
        System.out.println("les details d'un transport avec id donné");
        int transportId = 46; // Replace with the desired transport ID
        Transport transportDetails = TS.getTransportDetails(transportId);
        if (transportDetails != null) {
            System.out.println("Transport Details:");
            System.out.println(transportDetails);
        } else {
            System.out.println("Transport intruvable!");
        }

                            // recupere des moyens de transport avec avis//

        System.out.println("les transport avec avis");
        String stationDepart = "la marsa";
        String stationArrivee = "la sokra";
        List<Transport> transportsWithAvis = TS.getTransportsWithAvis(stationDepart, stationArrivee);
        if (!transportsWithAvis.isEmpty()) {
            System.out.println("Transports avec Avis:");
            for (Transport transport1 : transportsWithAvis) {
                System.out.println(transport);
            }
        } else {
            System.out.println("pas de transport trouvé avec cette station donné!");
        }


    }
}










