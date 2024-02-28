package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignConsultController {
    @FXML
    private GridPane GridPaneV;

    @FXML
    private TextField seachbar;
    @FXML
    public void initialize() {
        loadContent();
    }


    private void loadContent() {
        try {
            publicationService publicationService = new publicationService();
            List<publication> publicationList = publicationService.afficher();

            // Clear existing content of GridPane
            GridPaneV.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of publications
            for (publication publication : publicationList) {
                // Load Pub.fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterLieux.fxml"));
                Parent interfaceRoot = loader.load();
                ConsulterLieuxcontroller itemController = loader.getController();
                itemController.setData(publication);
                itemController.setE(publication);
                // itemController.setRefresh(this);

                // Add the loaded element to GridPane
                GridPaneV.add(interfaceRoot, col, row);
                GridPaneV.setHgap(20); // Set horizontal gap between elements
                GridPaneV.setVgap(20); // Set vertical gap between elements

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
    public void Button_Events(ActionEvent actionEvent) {
    }

    public void Button_Lieux(ActionEvent actionEvent) {
    }

    public void Button_Transport(ActionEvent actionEvent) {
    }

    public void Button_Reservation(ActionEvent actionEvent) {
    }

    public void Button_Profil(ActionEvent actionEvent) {
    }

    public void Button_Reclamation(ActionEvent actionEvent) {
    }

    public void Button_Parametres(ActionEvent actionEvent) {
    }

    public void Button_Logout(ActionEvent actionEvent) {
    }

    public void Button_Help(ActionEvent actionEvent) {
    }

    public void EcoModeButton(ActionEvent actionEvent) {
    }

    public void Button_Acceuil(ActionEvent actionEvent) {
    }

    public void lieux(ActionEvent actionEvent) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageConsult.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publication(ActionEvent actionEvent) {

    }
}
