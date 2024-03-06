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

    public void setPu(publication pu) {
        this.pu = pu;
    }

    private GeneralDesignConsultController aff;

    public ConsulterLieuxcontroller() {
    }


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
                    service.supprimer(pu.getIdPub()); // Delete the page



                    aff.refreshView();



                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception as needed
                }
            }
        }
    }





    // Initialize method


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



    @FXML
    void consulter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubVisiteur(lieux).fxml"));
        Parent root = loader.load();
        PubVisiteurcontroller Pubcontroller = loader.getController();
        Pubcontroller.setData(pu);

        // Create a new stage for the pop-up
        Stage popUpStage = new Stage();
        popUpStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popUpStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.showAndWait(); // Show the pop-up and wait for it to be closed
    }

    public void setRefresh(GeneralDesignConsultController aff) {
        this.aff=aff;
    }
}
