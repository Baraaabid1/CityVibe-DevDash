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
import javafx.stage.Stage;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class Pubcontroller {

    @FXML
    private Label decc;

    @FXML
    private ImageView imgg;

    @FXML
    private Button modif;

    @FXML
    private Label nomm;

    @FXML
    private Button supp;

    private publication pu;
    private Pubcontroller pubc;
    private Connection conn;

    // Initialize method
    public void initialize() {
        // No need to initialize the publication object here, as it will be set later by setData method
    }

    // Method to set data to the UI elements
    @FXML
    public void setData(publication publication) {
        if (publication != null) {
            // Setting description text
            decc.setText(publication.getDescription());
            // Setting name text
            nomm.setText(publication.getNom());
            // Setting image
            Image image = new Image("file:" + publication.getImage());
            imgg.setImage(image);
            // Set the publication object as user data of the delete button
            supp.setUserData(publication);
            // Set the publication object as user data of the modify button
            modif.setUserData(publication);
        }
    }

   /* @FXML
    void modif(ActionEvent event) {
        Button btn = (Button) event.getSource(); // Get the button that was clicked
        publication pubToModify = (publication) btn.getUserData(); // Retrieve the publication associated with the button

        if (pubToModify != null) {
            try {
                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the new FXML file
                Modifiercontroller modifierController = loader.getController();

                // Pass the publication object to the new controller
                modifierController.setData(pubToModify);
                modifierController.setRefresh(this);

                // Show the new scene
                Stage stage = (Stage) modif.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // Update the database
                updatePublicationInDatabase(pubToModify);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQLException
                showErrorAlert("Error updating publication in the database.");
            }
        }
    }

    // Method to update publication in the database
    private void updatePublicationInDatabase(publication pubToModify) throws SQLException {
        try {
            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cityvibe", "username", "password");

            // SQL update statement
            String sql = "UPDATE publication SET description=?, image=?, nom=? WHERE IdP=?";

            // Create a PreparedStatement
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set parameters
            statement.setString(1, pubToModify.getDescription());
            statement.setString(2, pubToModify.getImage());
            statement.setString(3, pubToModify.getNom());
            statement.setInt(4, pubToModify.getidP());

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Publication updated successfully!");
            }

            // Close resources
            statement.close();
            conn.close();
        } finally {
            // Close resources
            if (conn != null) {
                conn.close();
            }
        }
    }
*/

    @FXML
    void modif(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
            Parent root = loader.load();

            Modifiercontroller Modifierlieux = loader.getController();
            Modifierlieux.initData(pu,pubc);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    void supp(ActionEvent event) {
        Button btn = (Button) event.getSource(); // Get the button that was clicked
        publication pubToDelete = (publication) btn.getUserData(); // Retrieve the publication associated with the button

        if (pubToDelete != null) {
            // Confirmation dialog before deletion
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation de suppression");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette publication ?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Call the service method to delete the publication
                    publicationService service = new publicationService();
                    service.supprimer(pubToDelete.getidP()); // Delete the publication

                    // Show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression avec succès");
                    successAlert.setContentText("Publication supprimée");
                    successAlert.showAndWait();

                    // Refresh view
                    if (pubc != null) {
                        pubc.refreshView();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception as needed
                }
            }
        }
    }

    // Method to refresh the view
    void refreshView() {
        // Implement refreshing logic here
    }

    // Method to show error alert
    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    // Method to set data to the UI elements
    public void setE(publication publication) {
        // Not sure what this method is intended for
    }

    // Method to set the refresh controller
    public void setRefresh(Pubcontroller pubc) {
        this.pubc = pubc;
    }
}
