package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.page;
import services.pageService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConsulterPagecontroller {

    @FXML
    private Label catP;

    @FXML
    private Label contP;

    @FXML
    private ImageView imageP;

    @FXML
    private Label locP;

    @FXML
    private ImageView logoP;

    @FXML
    private Label ouvP;

    @FXML
    private Label nomP;

    @FXML
    private Label descP;

    private page pA;
    private pagecontroller ad;

    public void initialize() {
        try {
            if (pA == null) {
                pageService ps = new pageService();
                pA = ps.afficherP(70); // Default ID, you may need to change this
            }

            afficherPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherPage() {
        if (pA != null) {
            nomP.setText(pA.getNom());
            contP.setText(Integer.toString(pA.getContact()));
            catP.setText(pA.getCategorie().toString());
            locP.setText(pA.getLocalisation());
            descP.setText(pA.getDescription());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = pA.getOuverture().format(timeFormatter);
            ouvP.setText(formattedTime);
            if (pA.getImage() != null) {
                Image image = new Image("file:" + pA.getImage());
                imageP.setImage(image);
            }
            if (pA.getLogo() != null) {
                Image logoImage = new Image("file:" + pA.getLogo());
                logoP.setImage(logoImage);
            }
        }
    }

    @FXML
    void setData(page page) {
        pA = page;
        afficherPage();
    }

    @FXML
    void supprimer(ActionEvent event) {
        if (pA != null) {
            // Display confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmation");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Voulez-vous vraiment supprimer ?");

            // Handling user response
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Call the service method to delete the page
                    pageService service = new pageService();
                    service.supprimer(pA.getIdP()); // Delete the page

                    // Show deletion success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression avec succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Page supprimée");
                    successAlert.showAndWait();

                    // Refresh view
                    if (ad != null) {
                        ad.refreshView();
                    }

                    // Clear displayed page after deletion
                    clearPage();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception as needed
                }
            }
        }
    }


    private void clearPage() {
        pA = null;
        nomP.setText("");
        contP.setText("");
        catP.setText("");
        locP.setText("");
        descP.setText("");
        ouvP.setText("");
        imageP.setImage(null);
        logoP.setImage(null);

    }

    public void setRefresh(pagecontroller ad) {
        this.ad = ad;
    }

    public void setE(page page) {
        // Not sure what this method is intended for
    }


    @FXML
    void consulter(ActionEvent event) {
        try {
            // Fetch data from the database when the "Consulter" button is clicked
            pageService ps = new pageService();
            // Replace 70 with the appropriate ID or logic to get the desired page
            pA = ps.afficherP(82);
            if (pA != null) {
                afficherPage(); // Display the fetched page data
            } else {
                // Page not found, show an alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Page non trouvée dans la base de données.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database access error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'accès à la base de données.");
            alert.showAndWait();
        }
    }
}
