package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignTestController {
    @FXML
    private javafx.scene.layout.GridPane GridPane;

    @FXML
    public void initialize() {
        loadContent();
    }
    @FXML
    private void loadContent() {
        try {
            publicationService publicationService = new publicationService();
            List<publication> publicationList = publicationService.afficher();

            // Clear existing content of GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of publications
            for (publication publication : publicationList) {
                // Load Pub.fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub.fxml"));
                Parent interfaceRoot = loader.load();
                Pubcontroller itemController = loader.getController();
                itemController.setData(publication);
                itemController.setE(publication);
                 itemController.setRefresh(this);

                // Add the loaded element to GridPane
                GridPane.add(interfaceRoot, col, row);
                GridPane.setHgap(20); // Set horizontal gap between elements
                GridPane.setVgap(20); // Set vertical gap between elements

                // Adjust row and column indices
                col++;
                if (col == 1) {
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    public void refreshView() {
        loadContent();
    }
    @FXML
    public void Button_Acceuil(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Events(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Lieux(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Transport(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Reservation(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Profil(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Reclamation(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Parametres(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Logout(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Help(ActionEvent actionEvent) {
    }
    @FXML
    public void EcoModeButton(ActionEvent actionEvent) {
    }

    public void refreshview() { loadContent();
    }
}
