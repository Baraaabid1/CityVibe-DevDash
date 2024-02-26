package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherController {

    @FXML
    private GridPane gridPane; // Assuming you're injecting via FXML

    public void initialize() {
        if (gridPane == null) {
            // Initialize gridPane if it's null
            gridPane = new GridPane();
        }

        // Now you can safely access gridPane.getChildren() or any other methods/properties
        loadContent();
    }

    private void loadContent() {
        // Now you can safely use gridPane
        ObservableList<Node> children = gridPane.getChildren();
        // Your logic to load content into the gridPane


        try {
            pageService pageS = new pageService();
            List<page> pageList = pageS.afficher();

            // Effacer le contenu existant de GridPane
            gridPane.getChildren().clear();

            int row = 0;
            int col = 0;

            // Itérer à travers la liste des pages
            for (page page : pageList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/page.fxml"));
                Parent interfaceRoot = loader.load();
                pagecontroller itemController = loader.getController();
               // itemController.setData(page);

                // Ajouter l'élément chargé à GridPane
                gridPane.add(interfaceRoot, col, row);
                gridPane.setHgap(20); // Définir l'espacement horizontal entre les éléments
                gridPane.setVgap(20); // Définir l'espacement vertical entre les éléments

                // Ajuster les indices de ligne et de colonne
                col++;
                if (col == 3) { // Changer cette valeur en fonction de votre mise en page
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer les exceptions de manière appropriée
        }
    }

    public void refreshView() {
        loadContent();
    }
}
