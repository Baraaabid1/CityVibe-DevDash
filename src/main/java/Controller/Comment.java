package Controller;

import Models.Commentaire;
import Models.Evenement;
import Models.Utilisateur;
import Services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Comment {

    @FXML
    private TextArea Comment;


    @FXML
    private Label nomE;

    @FXML
    private Button commentAj;

    // ID de l'utilisateur
    private int userId;
    private EvenementComment eventComment;


    // Service pour gérer les commentaires
    private CommentaireService commentaireService = new CommentaireService();

    // Événement associé au commentaire
    private Evenement evenementComment;

    // Méthode pour définir l'identifiant de l'utilisateur
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Méthode appelée lors du clic sur le bouton "Ajouter Commentaire"
    @FXML
    void CommentAjouter(ActionEvent event) {
        // Vérifier si l'événement de commentaire n'est pas nul
        if (evenementComment != null) {
            // Vérifier si le commentaire n'est pas vide
            if (Comment.getText() != null && !Comment.getText().isEmpty()) {
                // Récupérer le nom de l'utilisateur
                String nomUtilisateur = commentaireService.getNomUtilisateurById(userId);

                // Récupérer le nom de l'événement
                String nomEvenement = commentaireService.getNomEvenementById(evenementComment.getIdE());

                // Mettre à jour les étiquettes avec les noms récupérés;
                nomE.setText(nomEvenement);

                // Créer un nouveau commentaire avec l'identifiant de l'utilisateur,
                // l'identifiant de l'événement et le contenu saisi
                Utilisateur user = new Utilisateur(1);

                Commentaire nouveauCommentaire = new Commentaire();
                nouveauCommentaire.setContenu(Comment.getText());
                nouveauCommentaire.setUser(user);
                nouveauCommentaire.setEvenement(evenementComment);

                // Ajouter le commentaire à la base de données
                commentaireService.ajouter(nouveauCommentaire);


                // Fermer la fenêtre du formulaire de commentaire
                Stage stage = (Stage) commentAj.getScene().getWindow();
                stage.close();
                if (eventComment!=null){
                    eventComment.refreshView();
                }
            } else {
                // Afficher un message d'erreur si le commentaire est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le commentaire ne peut pas être vide");
                alert.showAndWait();
            }
        } else {
            // Afficher un message d'erreur si aucun événement n'est associé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Aucun événement associé");
            alert.showAndWait();
        }
    }

    // Méthode pour définir l'instance EvenementComment
    public void setEvenement(Evenement evenementComment) {
        this.evenementComment = evenementComment;
    }

    public void setEvenementComment(EvenementComment eventComment) {
        this.eventComment=eventComment;
    }
}
