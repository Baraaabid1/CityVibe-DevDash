package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.categorieP;
import models.page;
import models.publication;
import services.pageService;
import jfxtras.scene.control.LocalTimeTextField;
import javafx.scene.control.Alert;

import java.io.File;
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
    void ajouter(ActionEvent event) throws SQLException {
        categorieP selectedCategorie = categorieChoiceBox.getValue();
        int contactValue;
        try {
            contactValue = Integer.parseInt(contact.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Contact must be a valid integer.");
            return;
        }

        pageService pS = new pageService();
        pS.ajouter(new page(nom.getText(), contactValue, selectedCategorie, locali.getText(), description.getText(), ouverture.getLocalTime(), imagePath, logoPath));

        // Show confirmation message
        showSuccessAlert("Ajout est fait avec succès");
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
