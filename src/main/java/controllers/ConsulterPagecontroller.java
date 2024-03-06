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
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConsulterPagecontroller {
    @FXML
    private Parent root;
    private Scene stage;


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
    private GeneralDesignConsulterController ad;

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
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Suppression réussie !");
                    successAlert.showAndWait();

                    ad.refreshView();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception as needed
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void initialize() {

    }
    @FXML
    public void setData(page page) {
        this.pA= page; // Set the publication object
        // Setting name text
        nomP.setText(page.getNom());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = page.getOuverture().format(timeFormatter);
//        ouvP.setText(formattedTime);        // Setting image
        if (page.getImage() != null && !page.getImage().isEmpty()) {
            Image image = new Image("file:" + page.getImage());
            imageP.setImage(image);
        }

    }

    public void setE(page page) {
        this.pA=page;
    }

    public void setRefresh(GeneralDesignConsulterController ad) {
        this.ad = ad;
    }

    @FXML
    void consulter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/page(lieux).fxml"));
        Parent root = loader.load();
        pagecontroller PageAdmincontroller = loader.getController();
        PageAdmincontroller.setData(pA);

        // Create a new stage for the pop-up
        Stage popUpStage = new Stage();
        popUpStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popUpStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.showAndWait(); // Show the pop-up and wait for it to be closed
    }
}










