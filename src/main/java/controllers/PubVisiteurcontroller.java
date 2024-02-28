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
import javafx.stage.Stage;
import models.publication;
import services.pageService;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;

public class PubVisiteurcontroller {

    @FXML
    private Label descV;

    @FXML
    private ImageView imagV;

    @FXML
    private Label nomV;

    private Scene previousScene; // Store reference to the previous scene

    private publication pu;
//    private int id = 44;
    private AfficherController aff;

    // Initialize method
    public void initialize(int idP) {
        try {
            if (pu == null) {
                // If the pu object is null, initialize it
                publicationService pSR = new publicationService();
                pu = pSR.afficher(idP);
            }
            // Update UI elements with publication data
            if (pu != null) {
                nomV.setText(pu.getNom());
                descV.setText(pu.getDescription());

                // Load and set the images
                if (pu.getImage() != null) {
                    Image image = new Image("file:" + pu.getImage());
                    imagV.setImage(image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    // Method to set data to the UI elements
    public void setData(publication publication) {
        // Setting description text
        descV.setText(publication.getDescription());
        // Setting name text
        nomV.setText(publication.getNom());
        // Setting image
        Image image = new Image("file:" + publication.getImage());
        imagV.setImage(image);
    }

    // Method to set data to the UI elements
    public void setE(publication publication) {
        // Not sure what this method is intended for
    }

    public void setRefresh(AfficherController aff) {
        this.aff = aff;
    }
}

