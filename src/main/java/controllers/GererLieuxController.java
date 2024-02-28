package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GererLieuxController {

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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageTest.fxml"));
                    Parent interfaceRoot = loader.load();
                    pagecontroller itemController = loader.getController();
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
}
