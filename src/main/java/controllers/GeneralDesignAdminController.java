package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignAdminController {
    @FXML
    private GridPane GridPaneA;
    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {
        try {
            pageService pageService = new pageService();
            List<page> pageList = pageService.afficher();

            // Clear existing content of GridPane
            GridPaneA.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of page
            for (page page : pageList) {
                // Load Pub.fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageAdmin.fxml"));
                Parent interfaceRoot = loader.load();
                PageAdmincontroller itemController = loader.getController();
                itemController.setData(page);
                itemController.setE(page);
                // itemController.setRefresh(this);

                // Add the loaded element to GridPane
                GridPaneA.add(interfaceRoot, col, row);
                GridPaneA.setHgap(20); // Set horizontal gap between elements
                GridPaneA.setVgap(20); // Set vertical gap between elements

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
}
