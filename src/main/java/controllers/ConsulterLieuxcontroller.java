package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class ConsulterLieuxcontroller {

    @FXML
    private Label DesC;
    @FXML
    private Parent root;
    private Scene stage;

    @FXML
    private Label NomC;

    @FXML
    private ImageView imageC;

    private publication pu;

    private ConsulterLieuxcontroller aff;




    @FXML
    void supprimer(ActionEvent event) {
        if (pu != null) {
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
                    publicationService service = new publicationService();
                    service.supprimer(pu.getidP()); // Delete the page

                    // Show deletion success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression avec succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Page supprimée");
                    successAlert.showAndWait();

                    // Refresh view
                    if (aff != null) {
                        aff.refreshView();
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
        pu = null;
        NomC.setText("");
        DesC.setText("");
        imageC.setImage(null);

    }

    private void refreshView() {
        // Implement logic to refresh the view after deletion
    }

    // Initialize method
    public void initialize() {
        // No need to initialize pu object here, it will be set via setData method
    }

    // Method to set data to the UI elements
    @FXML
    public void setData(publication publication) {
        this.pu = publication; // Set the publication object
        // Setting name text
        NomC.setText(publication.getNom());
        // Setting description text
        DesC.setText(publication.getDescription());
        // Setting image
        if (publication.getImage() != null && !publication.getImage().isEmpty()) {
            Image image = new Image("file:" + publication.getImage());
            imageC.setImage(image);
        }
    }

    // Method to set data to the UI elements
    public void setE(publication publication) {
        // Not sure what this method is intended for
    }

    public void setRefresh(ConsulterLieuxcontroller aff) {
        this.aff = aff;
    }

    @FXML
    void consulter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub.fxml"));
        Parent root = loader.load();
        Pubcontroller Pubcontroller = loader.getController();
        Pubcontroller.setData(pu);

        // Create a new stage for the pop-up
        Stage popUpStage = new Stage();
        popUpStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popUpStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.showAndWait(); // Show the pop-up and wait for it to be closed
    }
}
