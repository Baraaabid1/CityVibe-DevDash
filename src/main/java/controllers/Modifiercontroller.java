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
import models.categorieP;
import models.publication;
import services.pageService;
import services.publicationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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


    public GeneralDesignTestController getPub() {
        return pub;
    }


    private GeneralDesignTestController pub;

    private publication pu;

    public void setPu(publication pu) {
        this.pu = pu;
    }

    public void initData(publication pu , GeneralDesignTestController pub) {
        this.pu = pu;
        this.pub = pub;
        populatedFields();
    }
    private void populatedFields() {
        nommm.setText(pu.getNom());
        desscc.setText(pu.getDescription());

        File file = new File(pu.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
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
        imagePath= file.getAbsolutePath();
    }


    @FXML
    void mod(ActionEvent event) {
        try {
            // Validate inputs before updating the publication
            if (!validateInputs()) {
                return; // Exit method if inputs are not valid
            }

            // Update publication fields
            if (!imagePath.isEmpty()) {
                pu.setImage(imagePath);
            }
            pu.setNom(nommm.getText());
            pu.setDescription(desscc.getText());

            // Modify publication in the database
            publicationService ES = new publicationService();
            ES.modifier(pu);

            // Close the window and refresh the view
            Stage stage = (Stage) mod.getScene().getWindow();
            stage.close();
            pub.refreshview();
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorAlert("Error modifying publication: " + ex.getMessage());
        }
    }
    // Validate inputs method
    private boolean validateInputs() {
        // Validate the name field
        String nom = nommm.getText().trim();
        if (nom.isEmpty()) {
            showErrorAlert("Please enter a name for the publication.");
            return false;
        }

        // Validate the description field
        String description = desscc.getText().trim();
        if (description.isEmpty()) {
            showErrorAlert("Please enter a description for the publication.");
            return false;
        }

        // Optional: Validate image file if required
        if (imagePath.isEmpty()) {
            showErrorAlert("Please select an image for the publication.");
            return false;
        }

        // Additional validation checks can be added here...

        // All inputs are valid
        return true;
    }



    public void setRefresh( GeneralDesignTestController pub) {
        this.pub = pub;
    }




    private void showErrorAlert(String message) {
        showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
