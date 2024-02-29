package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.publication;
import services.publicationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterPubcontroller {

    @FXML
    private Button aj;

    @FXML
    private TextField desscc;

    @FXML
    private ImageView imagess;

    @FXML
    private Button ins;

    @FXML
    private TextField nommm;

    private String imagePath = "";

    @FXML
    void ins(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Get the absolute path of the selected file
            imagePath = file.getAbsolutePath();
            try {
                // Load the image from the file path
                Image image = new Image(new FileInputStream(imagePath));
                // Set the loaded image to the ImageView
                imagess.setImage(image);
            } catch (FileNotFoundException e) {
                // Handle file not found error
                showErrorAlert("Error loading image: File not found.");
            }
        }
    }


    @FXML
    void aj(ActionEvent event) throws SQLException, IOException {
        // Vérifier si les champs sont vides
        if (desscc.getText().isEmpty() || nommm.getText().isEmpty()) {
            showErrorAlert("Veuillez remplir tous les champs.");
            return;
        }

        if (imagePath.isEmpty()) {
            showErrorAlert("Veuillez sélectionner une image.");
            return;
        }

        // Ajouter la publication si les champs sont remplis
        publicationService pSR = new publicationService();
        pSR.ajouter(new publication(desscc.getText(), imagePath, nommm.getText()));

        // Afficher une alerte de succès
        showSuccessAlert("Ajout effectué avec succès");

        // Effacer les champs après l'ajout réussi
        desscc.clear();
        imagess.setImage(null);
        nommm.clear();

        // Naviguer vers "PubTest.fxml" après avoir cliqué sur le bouton "aj"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubTest.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setData(publication publication) {
    }

    public void setE(publication publication) {
    }

    public void setRefresh(Pubcontroller pubcontroller) {
    }
}
