//package Tests;
//
//import Models.Commentaire;
//import Services.CommentaireService;
//import Utils.MyDataBase;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class Test1 {
//        public static void main(String[] args) {
//            CommentaireService service = new CommentaireService();
//
//            // Test d'ajout de commentaire avec la date actuelle
//            Commentaire nouveauCommentaire = new Commentaire();
//            nouveauCommentaire.setContenu("Ceci est un nouveau commentaire avec la date actuelle");
//            nouveauCommentaire.setIdE(75); // Remplacez par l'ID de l'événement concerné
//            nouveauCommentaire.setIdU(1); // Remplacez par l'ID de l'utilisateur concerné
//            service.ajouter(nouveauCommentaire);
//
//            // Test de récupération des commentaires pour un événement spécifique
//            List<Commentaire> commentaires = service.recuperer(1); // Remplacez par l'ID de l'événement
//            System.out.println("Liste des commentaires pour l'événement :");
//            for (Commentaire commentaire : commentaires) {
//                System.out.println(commentaire);
//            }
//
//            // Test de modification d'un commentaire existant avec mise à jour de la date
//            Commentaire commentaireAModifier = service.detail(1); // Remplacez par l'ID du commentaire à modifier
//            commentaireAModifier.setContenu("Contenu modifié du commentaire avec la date actuelle");
//            service.modifier(commentaireAModifier);
//
//            // Test de suppression d'un commentaire
//            int idCommentaireASupprimer = 1; // Remplacez par l'ID du commentaire à supprimer
//            service.supprimer(idCommentaireASupprimer);
//        }
//    }
//
