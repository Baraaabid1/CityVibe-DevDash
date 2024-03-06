package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignLieuxController {

    @FXML
    private GridPane GridPaneV;

    @FXML
    private TextField seachbar;

    private publicationService publicationService;

    @FXML
    public void initialize() {
        publicationService = new publicationService();
        loadContent();
    }
    public void refreshView() throws IOException {
        loadContent();
    }

    private void loadContent() {
        try {
            List<publication> publicationList;
            if (seachbar.getText().isEmpty()) {
                publicationList = publicationService.afficher();
            } else {
                publicationList = publicationService.search(seachbar.getText());
            }

            // Clear existing content of GridPane
            GridPaneV.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of publications
            for (publication publication : publicationList) {
                // Load AjouterLieux(lieux).fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLieux(lieux).fxml"));
                Parent interfaceRoot = loader.load();
                AjouterLieuxcontroller itemController = loader.getController();
                itemController.setData(publication);
                itemController.setE(publication);

                // Add the loaded element to GridPane
                GridPaneV.add(interfaceRoot, col, row);
                GridPaneV.setHgap(20); // Set horizontal gap between elements

                // Adjust row and column indices
                col++;
                if (col == 1) {
                    col = 0;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }


    @FXML
    void Button_Acceuil(ActionEvent event) {

    }

    @FXML
    void Button_Events(ActionEvent event) {

    }

    @FXML
    void Button_Help(ActionEvent event) {

    }

    @FXML
    void Button_Lieux(ActionEvent event) {

    }

    @FXML
    void Button_Logout(ActionEvent event) {

    }

    @FXML
    void Button_Parametres(ActionEvent event) {

    }

    @FXML
    void Button_Profil(ActionEvent event) {

    }

    @FXML
    void Button_Reclamation(ActionEvent event) {

    }

    @FXML
    void Button_Reservation(ActionEvent event) {

    }

    @FXML
    void Button_Transport(ActionEvent event) {

    }

    @FXML
    void EcoModeButton(ActionEvent event) {

    }



    public void seachbar(ActionEvent actionEvent) {
    }
}
