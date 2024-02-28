package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.page;
import models.publication;

import java.io.IOException;

public class Pagcontroller {

    @FXML
    private Button consulter;

    @FXML
    private ImageView img;

    @FXML
    private ImageView logg;

    @FXML
    private Label nom;

    private page pA;

    void setData(page page) {
        this.pA = page; // Set the publication object
        // Setting name text
        nom.setText(page.getNom());
        // Setting image
        if (page.getImage() != null && !page.getImage().isEmpty()) {
            Image image = new Image("file:" + page.getImage());
            img.setImage(image);
        }
//        if (page.getLogo() != null && !page.getLogo().isEmpty()) {
//            Image logoImage = new Image("file:" + page.getLogo());
//            logg.setImage(logoImage);
//        }
    }
    @FXML
    void consulter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/page.fxml"));
        Parent root = loader.load();
         pagecontroller pagecontroller = loader.getController();
        pagecontroller.setData(pA);

        // Create a new stage for the pop-up
        Stage popUpStage = new Stage();
        popUpStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popUpStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.showAndWait(); // Show the pop-up and wait for it to be closed
    }

    public void setE(page page) {
    }

}
