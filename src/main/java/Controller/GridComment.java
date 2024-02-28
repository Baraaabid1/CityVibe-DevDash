package Controller;

import Models.Commentaire;
import Models.Evenement;
import Models.Utilisateur;
import Services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GridComment {

    @FXML
    private Label comment;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Button modC;

    @FXML
    private Button supC;

    private EvenementComment EC;
    private Commentaire c;

    public void setC(Commentaire c) {
        this.c = c;
    }
    public void setData(Commentaire c) {
        comment.setText(c.getContenu());

        // Assurez-vous que la méthode getUtilisateur() existe dans la classe Commentaire
        Utilisateur utilisateur = c.getUser();

        if (utilisateur != null) {
            // Afficher le nom et le prénom de l'utilisateur
            nom.setText(utilisateur.getNom());
            prenom.setText(utilisateur.getPrenom());
        }
    }

    @FXML
    void modifierComment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierComment.fxml"));
            Parent root = loader.load();

            ModifierComment ModifierComment = loader.getController();
            ModifierComment.initData(c, EC);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de la vue de modification
        }
    }

    @FXML
    void supprimerComment(ActionEvent event) {
        if (c != null) {
            // Appeler la méthode de service pour supprimer le commentaire
            CommentaireService service = new CommentaireService();
            service.supprimer(c.getIdC());
            EC.refreshView();
            // Afficher une confirmation à l'utilisateur
        }
    }


    public void setRefresh(EvenementComment EC) {
        this.EC = EC;
    }

    public void setEvenementComment(Evenement evenement) {
    }


    }

