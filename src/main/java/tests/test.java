package tests;

import models.Reclamation;
import services.ReclamationService;
import utils.MyDataBase;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        MyDataBase.getInstance();
        ReclamationService rs = new ReclamationService();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        Reclamation nouvelleReclamation = new Reclamation();
        nouvelleReclamation.setIdu(1);
        nouvelleReclamation.setTemp(currentTimestamp);
        nouvelleReclamation.setTitre("Titre de la réclamation");
        nouvelleReclamation.setContenu("Contenu de la réclamation");
        nouvelleReclamation.setType("Type de la réclamation");
        nouvelleReclamation.setApropo("À propos de la réclamation");

        try {
            // Ajouter la réclamation
            rs.ajouter(nouvelleReclamation);
            System.out.println("Réclamation ajoutée avec succès !");

            // Afficher toutes les réclamations
            System.out.println("Liste des réclamations :");
            for (Reclamation reclamation : rs.afficher()) {
                System.out.println(reclamation);
            }

            // Modifier une réclamation (supposons que nous avons déjà une réclamation avec l'ID 1)
            Reclamation reclamationAModifier = rs.afficherR(1);
            if (reclamationAModifier != null) {
                reclamationAModifier.setTitre("Nouveau titre de la réclamation");
                rs.modifier(reclamationAModifier);
                System.out.println("Réclamation modifiée avec succès !");
            } else {
                System.out.println("Réclamation non trouvée !");
            }

            // Supprimer une réclamation (supposons que nous avons déjà une réclamation avec l'ID 1)
            rs.supprimer(1);
            System.out.println("Réclamation supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            System.out.println(rs.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(rs.MesReclamations(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




    }
   }
