package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.categorieP;
import models.page;
import models.publication;
import services.pageService;
import jfxtras.scene.control.LocalTimeTextField;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterLieuxcontroller {

    @FXML
    private TextField locali;

    @FXML
    private TextField nom;

    @FXML
    private ImageView image;

    @FXML
    private Button addPhoto;

    @FXML
    private ImageView logo;

    @FXML
    private Button addLogo;

    @FXML
    private TextField description;

    @FXML
    private ChoiceBox<categorieP> categorieChoiceBox;

    @FXML
    private TextField contact;

    @FXML
    private LocalTimeTextField ouverture;

    private String imagePath = "";
    private String logoPath = "";

    @FXML
    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.toURI().toString();
            try {
                Image img = new Image(imagePath);
                image.setImage(img);
            } catch (Exception e) {
                showErrorAlert("Error loading image: " + e.getMessage());
            }
        }
        imagePath= file.getAbsolutePath();
    }

    @FXML
    public void addLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            logoPath = file.toURI().toString();
            try {
                Image img = new Image(logoPath);
                logo.setImage(img);
            } catch (Exception e) {
                showErrorAlert("Error loading logo: " + e.getMessage());
            }
        }
        logoPath= file.getAbsolutePath();
    }

    @FXML
    private void initialize() {
        categorieChoiceBox.setItems(FXCollections.observableArrayList(categorieP.values()));
    }



    @FXML
    void ajouter(ActionEvent event) throws SQLException, IOException {
        // Check if any of the fields is empty
        if (nom.getText().isEmpty() || locali.getText().isEmpty() || description.getText().isEmpty() || contact.getText().isEmpty() || categorieChoiceBox.getValue() == null || ouverture.getLocalTime() == null || imagePath.isEmpty() || logoPath.isEmpty()) {
            showErrorAlert("Please fill in all fields.");
            return;
        }

        // Validate contact field
        // Validate contact field
        int contactValue;
        try {
            // Check if the length of the contact number is 8 digits
            String contactText = contact.getText().trim(); // Trim any leading or trailing whitespace
            if (contactText.length() != 8) {
                showErrorAlert("Contact must be an 8-digit integer.");
                return;
            }

            // Parse the contact number to an integer
            contactValue = Integer.parseInt(contactText);
        } catch (NumberFormatException e) {
            showErrorAlert("Contact must be a valid 8-digit integer.");
            return;
        }

        // Perform additional validations for other fields as needed

        // If all validations pass, proceed with adding the page
        pageService pS = new pageService();
        pS.ajouter(new page(nom.getText(), contactValue, categorieChoiceBox.getValue(), locali.getText(), description.getText(), ouverture.getLocalTime(), imagePath, logoPath));

        // Show confirmation message
        showSuccessAlert("Ajout est fait avec succès");

        // Navigate to the PageTest.fxml when the "ajouter" button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageConsult.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }




    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setData(publication publication) {
    }

    public void setE(publication publication) {
    }
}
