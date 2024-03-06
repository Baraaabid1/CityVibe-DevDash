package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignPageController {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField seachbar;
    pageService ps = new pageService();


    @FXML
    public void initialize() throws SQLException, IOException {
        List<page> pageList;
        pageList = ps.afficher();

        loadContent(pageList);
        // Add event listener for search bar text property
        seachbar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadContent(pageList); // Reload content when search bar text changes
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        });
    }

    private void loadContent(List<page> pageList) throws SQLException, IOException {
      /*  if (seachbar.getText().isEmpty()) {
            pageList = ps.afficher();
        } else {
            pageList = ps.search(seachbar.getText());
        }*/

        // Clear existing content of GridPane
        gridPane.getChildren().clear();

        int row = 1;
        int col = 0;

        // Iterate through the list of pages
        for (page page : pageList) {
            // Load page(lieux).fxml for each page and set its data
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pag(lieux).fxml"));
            Parent interfaceRoot = loader.load();
            Pagcontroller itemController = loader.getController();
            itemController.setData(page);
            itemController.setE(page);

            // Add the loaded element to GridPane
            gridPane.add(interfaceRoot, col, row);
            gridPane.setHgap(20); // Set horizontal gap between elements
            gridPane.setVgap(20); // Set vertical gap between elements

            // Adjust row and column indices
            col++;
            if (col == 1) {
                col = 0;
                row++;
            }
        }
    }

    public void refreshView() {

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


    @FXML
    void seachbar(KeyEvent event) throws SQLException, IOException {
        String s = seachbar.getText();
        List<page> listpage =ps.search(s);
        loadContent(listpage);

    }
    public void publication(ActionEvent actionEvent) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubVisiteurTest(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void lieux(ActionEvent actionEvent) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageTest(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void groupe(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Groupe");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void individuel(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Individuel");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void aventure(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Aventure");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void exterieur(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Extérieur");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sante(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Santé");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void alimentation(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Alimentation");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void art(ActionEvent actionEvent) {
        try {
            List<page> pageArt = ps.filtrerParCategorie("Art");
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void tout(ActionEvent event) {
        try {
            List<page> pageArt = ps.afficher();
            loadContent(pageArt);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
