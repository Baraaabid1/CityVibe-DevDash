package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Modifiercontroller {

    @FXML
    private Button ins;

    @FXML
    private Button mod;

    @FXML
    private TextField desscc;

    @FXML
    private ImageView imagess;

    @FXML
    private TextField nommm;

    private String imagePath = "";


    private publication currentPublication; // Variable to store the current publication

    private Pubcontroller pubcontroller; // Reference to the Pubcontroller for refreshing
    private publication pu;
    private Pubcontroller pubc;

    public void setData(publication publication) {
        // Set data to the UI elements based on the provided publication
        currentPublication = publication;
        desscc.setText(publication.getDescription());
        nommm.setText(publication.getNom());

        // Set the image if available
        if (publication.getImage() != null) {
            imagePath = publication.getImage();
            Image image = new Image("file:" + imagePath);
            imagess.setImage(image);
        }
    }

    @FXML
    void ins(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.toURI().toString();
            try {
                Image images = new Image(imagePath);
                imagess.setImage(images);
            } catch (Exception e) {
                showErrorAlert("Error loading image: " + e.getMessage());
            }
        }
    }
    @FXML
    public void mod(ActionEvent event) {
        if (pu != null) {
            try {
                if (!imagePath.isEmpty()) {
                    pu.setDescription(desscc.getText());
                    pu.setImage(imagePath);
                    pu.setNom(nommm.getText());
                } else {
                    pu.setDescription(desscc.getText());
                    pu.setImage(pu.getImage());
                    pu.setNom(nommm.getText());

                }

                publicationService pS = new publicationService();
                pS.modifier(pu);

                Stage stage = (Stage) mod.getScene().getWindow();
                stage.close();

                pubcontroller.refreshView();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData(publication pu ,Pubcontroller pubc) {
        this.pu = pu;
        this.pubc = pubc;
        populateFields();
    }

    private void populateFields() {
        nommm.setText(pu.getNom());
        desscc.setText(pu.getDescription());
        File file = new File(pu.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imagess.setImage(image);
        }
    }

/*
    @FXML
    void mod(ActionEvent event) {
        if (currentPublication != null) {
            // Check if imagePath is not empty and not null
            if (imagePath != null && !imagePath.isEmpty()) {
                // Assuming imagePath is already a correct path, no need to modify it
                currentPublication.setImage(imagePath);
            }
            currentPublication.setNom(nommm.getText());
            currentPublication.setDescription(desscc.getText());

            publicationService pSR = new publicationService();
            try {
                pSR.modifier(currentPublication);
                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Modification Successful", "Publication modified successfully.");

                // Refresh the page
                if (pubcontroller != null) {
                    pubcontroller.initialize(); // Call initialize method of Pubcontroller
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Show error message
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to modify publication: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No publication selected for modification.");
        }
    }

*/
    private void showErrorAlert(String message) {
        showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setRefresh(Pubcontroller pubcontroller) {
        this.pubcontroller = pubcontroller; // Set reference to Pubcontroller
    }


}
