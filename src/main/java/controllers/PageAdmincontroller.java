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
    @FXML
    private Button SupprimerA;
    private GeneralDesignAdminController ad= new GeneralDesignAdminController();

    private page pageS;
    private GeneralDesignAdminController pub;
    @FXML
    public void setData(page page) {
        pageS = page ;
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
        this.pageS = page;
    }

    public GeneralDesignAdminController getPub() {
        return pub;
    }

    public void setPub(GeneralDesignAdminController pubb) {
        this.pub = pubb;
    }

    @FXML
    void modifierA(ActionEvent event) {
        Button btn = (Button) event.getSource();
        page pageToModify = pageS;
//        System.out.println("hellooooo"+pageToModify.getIdP());

//
        if (pageToModify != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLieux(lieux).fxml"));
            Parent root = loader.load();

            ModifierLieuxcontroller modifierController = loader.getController();
            // Pass pubToModify and a reference to the Pubcontroller to initData
            modifierController.initData(pageS, pub);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }}
    public void setRefresh(GeneralDesignAdminController pub) {
        this.pub = pub;}


//    @FXML
//    void SupprimerA(ActionEvent event) {
//        Button btn = (Button) event.getSource();
//        page pageToDelete = (page) btn.getUserData();
//
//        if (pageToDelete != null) {
//            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
//            confirmAlert.setTitle("Confirmation de suppression");
//            confirmAlert.setHeaderText(null);
//            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette publication ?");
//
//            Optional<ButtonType> result = confirmAlert.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                try {
//                    pageService service = new pageService();
//                    service.supprimer(pageToDelete.getIdP());
//
//                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                    successAlert.setTitle("Suppression avec succès");
//                    successAlert.setContentText("Publication supprimée");
//                    successAlert.showAndWait();
//
//
//                   // pub.refreshView();
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        @FXML
        void SupprimerA(ActionEvent event) {

            if (pageS != null) {
                try {
                    // Appeler la méthode de service pour supprimer l'événement
                    pageService service = new pageService();
                    service.supprimer(pageS.getIdP());
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmation");
                    confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet élément ?");
                    Optional<ButtonType> result = confirmation.showAndWait();
                    ad.refreshView();
                    // Afficher une confirmation à l'utilisateur



                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
//        if (pageS != null) {
//            // Display confirmation dialog
//            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
//            confirmDialog.setTitle("Confirmation");
//            confirmDialog.setHeaderText(null);
//            confirmDialog.setContentText("Voulez-vous vraiment supprimer ?");
//
//            // Handling user response
//            Optional<ButtonType> result = confirmDialog.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                try {
//                    // Call the service method to delete the page
//                    pageService service = new pageService();
//                    service.supprimer(pageS.getIdP()); // Delete the page
//
//                    // Show deletion success message
//                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                    successAlert.setTitle("Succès");
//                    successAlert.setHeaderText(null);
//                    successAlert.setContentText("Suppression réussie !");
//                    successAlert.showAndWait();
//
//                    // Refresh view
//                    pub.initialize();
//
//                    // Clear displayed page after deletion
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    // Handle the exception as needed
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }



    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

}

