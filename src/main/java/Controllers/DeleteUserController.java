package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Utilisateur;
import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteUserController {
    private Stage stage ;
    private Scene scene ;
    private Parent root ;

        @FXML
        private TextField idDelete;

        @FXML
        private Text afficheText;

        @FXML
        private Button chercher;

        @FXML
        private Button deleteButton;
    UtilisateurService us = new UtilisateurService();
    public Utilisateur utilisateur;

    @FXML
    void chercherUser(ActionEvent event) {
        try {
            int userId = Integer.parseInt(idDelete.getText());
            utilisateur = us.getUtilisateurById(userId);
            if (utilisateur != null) {
                afficheText.setText("Nom: " + utilisateur.getNom() + "\n" +
                        "Prénom: " + utilisateur.getPrenom() + "\n" +
                        "Date Naissance: " + utilisateur.getDateNaissance());
            } else {
            afficheText.setText("");
            showErrorAlert("Aucun utilisateur trouvé pour cet ID.");
        }
    } catch (NumberFormatException e) {
        afficheText.setText("");
        showErrorAlert("Veuillez entrer un ID valide.");
    } catch (
    SQLException e) {
        e.printStackTrace();
        showErrorAlert("Erreur lors de la récupération des informations de l'utilisateur.");
    }


    }

        @FXML
        void deleteUser(ActionEvent event) throws IOException {
            try {
                int userId = Integer.parseInt(idDelete.getText());
                us.supprimer(userId);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            root = loader.load();
            AfficherUserController Affuser = loader.getController();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
