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

public class GeneralDesignAdminController {
    @FXML
    private GridPane GridPaneA;
    private page pageToModify; // Store the page to be modified

    // Set the page to be modified
    public void setPageToModify(page page) {
        this.pageToModify = page;
        loadContent(); // Load the content for modification
    }

    @FXML
    public void initialize() {
        loadContent();    }

    private void loadContent() {
        if (pageToModify != null) {
            try {
                // Clear existing content of GridPane
                GridPaneA.getChildren().clear();

                // Load PageAdmin.fxml for the page to be modified and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageAdmin.fxml"));
                Parent interfaceRoot = loader.load();
                PageAdmincontroller itemController = loader.getController();
                itemController.setData(pageToModify);
                itemController.setE(pageToModify);

                // Add the loaded element to GridPane
                GridPaneA.add(interfaceRoot, 0, 0); // Add at (0, 0) position

            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        }
    }

    public void refreshView() throws IOException {
        loadContent();
    }


    public void Button_Events(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Lieux(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Transport(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Reservation(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Profil(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Reclamation(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Parametres(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Logout(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Help(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void EcoModeButton(ActionEvent actionEvent) {
        // Implement button events as needed
    }

    public void Button_Acceuil(ActionEvent actionEvent) {
        // Implement button events as needed
    }


}
