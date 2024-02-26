package tests;

import models.ReponseR;
import services.ReclamationService;
import services.ReponseRService;
import utils.MyDataBase;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class testReponseR {
    public static void main(String[] args) {
        MyDataBase.getInstance();
        ReponseRService rrs = new ReponseRService();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        ReponseR newReponseR = new ReponseR();
        newReponseR.setIdR(19);
        newReponseR.setIdU(2);
        newReponseR.setTextR("Sample response text");
        newReponseR.setDate_repR(Timestamp.valueOf(LocalDateTime.now()));
        try {
            rrs.ajouter(newReponseR);
            System.out.println("New ReponseR added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding new ReponseR: " + e.getMessage());
        }

        // Modification (assuming there's at least one ReponseR to modify)
        try {
            List<ReponseR> reponseRs = rrs.afficher();
            if (!reponseRs.isEmpty()) {
                ReponseR existingReponseR = reponseRs.get(0);
                existingReponseR.setTextR("Modified response text");
                rrs.modifier(existingReponseR);
                System.out.println("ReponseR modified successfully.");
            } else {
                System.out.println("No ReponseR records to modify.");
            }
        } catch (SQLException e) {
            System.err.println("Error modifying ReponseR: " + e.getMessage());
        }

        // Displaying all ReponseR records
        try {
            List<ReponseR> allReponseRs = rrs.afficher();
            System.out.println("All ReponseR records:");
            for (ReponseR reponseR : allReponseRs) {
                System.out.println(reponseR);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying ReponseR records: " + e.getMessage());
        }


    }
}