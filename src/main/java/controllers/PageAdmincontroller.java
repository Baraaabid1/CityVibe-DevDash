package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.page;
import models.publication;
import services.pageService;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PageAdmincontroller {

    @FXML
    private Label catA;

    @FXML
    private Label contA;

    @FXML
    private Label desA;

    @FXML
    private ImageView imgA;

    @FXML
    private Label locA;

    @FXML
    private ImageView logoA;

    @FXML
    private Label nomA;

    @FXML
    private Label ouvA;

    private page page;
    private GeneralDesignAdminController pub;
    @FXML
    public void setData(page page) {
        // Setting nom text
        nomA.setText(page.getNom());
        // Setting description text
        desA.setText(page.getDescription());
        // Setting localisation text
        locA.setText(page.getLocalisation());
        // Setting categorie text
        catA.setText(page.getCategorie().toString());
        // Setting contact text
        contA.setText(Integer.toString(page.getContact()));
        // Setting localisation text
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = page.getOuverture().format(timeFormatter);
        ouvA.setText(formattedTime);
        // Setting image
        Image image = new Image("file:" + page.getImage());
        imgA.setImage(image);
        // Setting logo
        Image logoImage = new Image("file:" + page.getLogo());
        logoA.setImage(logoImage);
    }

    public void setE(page page) { // Corrected class name to start with an uppercase letter
        this.page = page;
    }

    public GeneralDesignAdminController getPub() {
        return pub;
    }

    public void setPub(GeneralDesignAdminController pub) {
        this.pub = pub;
    }

    @FXML
    void modifierA(ActionEvent event) {
//        Button btn = (Button) event.getSource();
//        page pageToModify = (page) btn.getUserData();
//
//        if (pageToModify != null) {
           try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLieux.fxml"));
                Parent root = loader.load();

                ModifierLieuxcontroller modifierController = loader.getController();
                // Pass pubToModify and a reference to the Pubcontroller to initData
                modifierController.initData(page, pub);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    @FXML
    void SupprimerA(ActionEvent event) {

        if (page != null) {
            try {
                // Appeler la méthode de service pour supprimer l'événement
                pageService service = new pageService();
                service.supprimer(page.getIdP());
                if (pub != null) {
                    pub.refreshView();
                } else {
                    // Handle the case where pub is null
                    System.err.println("Error: pub is null");
                }
                // Afficher une confirmation à l'utilisateur



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    }

