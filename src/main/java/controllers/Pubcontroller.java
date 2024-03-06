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
import services.publicationService;
import java.io.IOException;
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
    @FXML
    private Label nomP;


    private publication pu;

    public GeneralDesignTestController pub;
    private Scene scene;

    public publication getPu() {
        return pu;
    }

    public void setPu(publication pu) {
        this.pu = pu;
    }
    private page page=new page();
    private publication publication=new publication();

    public void setPublication(models.publication publication) {
        this.publication = publication;
    }

    public Pubcontroller() {
    }

    @FXML
    void modif(ActionEvent event) {
        Button btn = (Button) event.getSource();
        publication pubToModify = (publication) btn.getUserData();

        if (pubToModify != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier(lieux).fxml"));
                Parent root = loader.load();

                Modifiercontroller modifierController = loader.getController();
                // Pass pubToModify and a reference to the Pubcontroller to initData
                modifierController.initData(pubToModify, pub);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Error: Publication object to modify is null.");
        }
    }

    /*@FXML
    void Modifier(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier(lieux).fxml"));
            Parent root = loader.load();

            ModifierEvenement ModifierEvenement = loader.getController();
            ModifierEvenement.initData(e, aff);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            }}*/

    @FXML
    void supp(ActionEvent event) {
        Button btn = (Button) event.getSource();
        publication pubToDelete = (publication) btn.getUserData();

        if (pubToDelete != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation de suppression");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette publication ?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    publicationService service = new publicationService();
                    service.supprimer(pubToDelete.getIdPub());

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression avec succès");
                    successAlert.setContentText("Publication supprimée");
                    successAlert.showAndWait();


                        pub.refreshView();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void refreshView() {
        // Implement refreshing logic here
    }

    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public void setData(publication publication) {
        if (publication != null) {
            if (publication.getPage() != null) {
                nomP.setText(publication.getPage().getNom());
            }
            decc.setText(publication.getDescription());
            nomm.setText(publication.getNom());
            Image image = new Image("file:" + publication.getImage());
            imgg.setImage(image);
            supp.setUserData(publication);
            modif.setUserData(publication);
        }
    }


    public void setE(publication publication) {
        // Not sure what this method is intended for
    }

    public void setRefresh( GeneralDesignTestController pub) {
        this.pub = pub;
    }
}
