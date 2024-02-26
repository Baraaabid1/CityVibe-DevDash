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

public class GeneralDesignConsulterController {
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {
        try {
            pageService pageService = new pageService();
            List<page> pageList = pageService.afficher();

            // Clear existing content of GridPane
            if (gridPane != null) {
                gridPane.getChildren().clear();

                int row = 1;
                int col = 0;

                // Iterate through the list of pages
                for (page page : pageList) {
                    // Load page.fxml for each page and set its data
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterPage.fxml"));
                    Parent interfaceRoot = loader.load();
                    ConsulterPagecontroller itemController = loader.getController();
                    itemController.setData(page);

                    // Add the loaded element to GridPane
                    gridPane.add(interfaceRoot, col, row);
                    gridPane.setHgap(20); // Set horizontal gap between elements
                    gridPane.setVgap(20); // Set vertical gap between elements

                    // Adjust row and column indices
                    col++;
                    if (col == 1) {
                        col =0;
                        row++;
                    }
                }
            } else {
                System.out.println("GridPane is null. Please check your FXML file.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    public void refreshView() {
        loadContent();
    }

    public void Button_Acceuil(ActionEvent actionEvent) {
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
}
