/*package Tests;

import Models.CategorieE;
import Models.Evenement;
import Services.EvenementService;
import Utils.MyDataBase;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class test {
    public static void main(String [] args){

        MyDataBase bd = MyDataBase.getInstance();

        EvenementService es = new EvenementService();
        try {
            LocalDate date = LocalDate.of(2024, 12, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(12, 0); // Exemple d'heure spécifiée
            es.ajouter(new Evenement(24, "PARTY", "Café Brown", "Une soirée envoûtante de musique", date, heure ,CategorieE.Loisirs));
            System.out.println("Evenement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
        try {
            LocalDate date = LocalDate.of(2024, 11, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(8, 0); // Exemple d'heure spécifiée
            es.ajouter(new Evenement(24, "ANNIV", "Café Brown", "Une soirée envoûtante de musique", date, heure ,CategorieE.Loisirs));
            System.out.println("Evenement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
        try {
            LocalDate date = LocalDate.of(2024, 11, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(8, 0); // Exemple d'heure spécifiée
            es.modifier(new Evenement(2,2,"workout","GYM","blablabla",date,heure,CategorieE.Sport));
            System.out.println("Evenement modifiée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            // Créer un objet Evenement avec les nouvelles valeurs
            Evenement evenement = new Evenement();
            evenement.setIdE(1);  // Remplacez 1 par l'ID de l'événement que vous souhaitez modifier
            evenement.setNom("Nouveau nom");
            evenement.setCategorie(CategorieE.Sport);
            evenement.setDate(LocalDate.of(2024, 12, 31));
            evenement.setHeure(LocalTime.of(18, 30));
            evenement.setPage("Nouvelle page");
            evenement.setDescription("Nouvelle description");
            evenement.setNbrPlaces(30);

            // Appeler la méthode modifier pour mettre à jour l'événement
            es.modifier(evenement);

            System.out.println("Evenement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'événement : " + e.getMessage());
        }
        try {
            es.supprimer(2);
            System.out.println("Evenement supprimée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(es.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
*/

