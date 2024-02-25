package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerReclamationController {
    @FXML
    private TextField idR;

    @FXML
    private Text affiche;
    public int id;
    ReclamationService rs = new ReclamationService();
    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    @FXML
    void AfficherReclamation(ActionEvent event) {
       /* id = Integer.parseInt(idR.getText());
        try {
            Reclamation reclamation = rs.afficherR(id); // assuming rs is an instance of your ReclamationService class
            if (reclamation != null) {
                String infoR = "ID utilisateur: " + reclamation.getIdu() + "\n" +
                        "Objet: " + reclamation.getObjet() + "\n" +
                        "Contenu: " + reclamation.getContenue() + "\n" +
                        "Localisation: " + reclamation.getLocalisation() + "\n" +
                        "Date: " + reclamation.getTemp();
                affiche.setText(infoR);
            } else {
                affiche.setText("Aucune réclamation trouvée avec l'ID spécifié");
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/


    }

    @FXML
    void ListeReclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeReclamations.fxml"));
        root = loader.load();
        ListeReclamationsController Lc = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void SupprimerReclamation(ActionEvent event) {
        id = Integer.parseInt(idR.getText());
        try {
            rs.supprimer(id);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }



    }
}
