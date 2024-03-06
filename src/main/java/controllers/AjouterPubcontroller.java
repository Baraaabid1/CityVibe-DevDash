package controllers;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.type.PhoneNumber;
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
import models.page;
import models.publication;
import services.publicationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import com.twilio.rest.api.v2010.account.Message;

import javax.mail.MessagingException;


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
    private int idPP;
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
    void aj(ActionEvent event) throws SQLException, IOException, MessagingException {
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
        page page= new page(198);
        pSR.ajouter(new publication(page, desscc.getText(), imagePath, nommm.getText()));
        if (nommm.getText().equals("offre")){
            String numeroTelephone="+21652186638";

                String msg = "Nouvelle offre disponible!";
                envoyerSMS(numeroTelephone, msg);

        }

        // Afficher une alerte de succès
        showSuccessAlert("Ajout effectué avec succès");

        // Effacer les champs après l'ajout réussi
        desscc.clear();
        imagess.setImage(null);
        nommm.clear();

        // Naviguer vers "PubTest(lieux).fxml" après avoir cliqué sur le bouton "aj"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Test(lieux).fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

        // Méthode pour envoyer un SMS
        private void envoyerSMS(String numeroTelephone, String messageBody) throws MessagingException {
            String twilioNumber = "+18432716407"; // Remplacez par votre numéro Twilio

            try {
                // Initialisez la bibliothèque Twilio avec vos identifiants
                Twilio.init("ACcdd4affbedc96aa111f440c787a92245", "c395274e821dc9983c67d6d5feae4d5e");

                // Envoi du SMS
                Message message = Message.creator(
                                new PhoneNumber(numeroTelephone),
                                new PhoneNumber(twilioNumber),
                                "Nous avons lancer une nouvelle offre  :  " + messageBody + " Bienvenue chez nous.")
                        .create();

                // Affichage de l'identifiant du message si l'envoi réussit
                System.out.println("Message SID: " + message.getSid());
            } catch (ApiException e) {
                // Gérer l'exception Twilio
                System.err.println("Erreur lors de l'envoi du SMS: " + e.getMessage());
                throw new MessagingException("Erreur lors de l'envoi du SMS", e);
            }
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
