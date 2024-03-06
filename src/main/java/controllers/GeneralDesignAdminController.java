package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.page;
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignAdminController {
    @FXML
    private GridPane GridPaneA;
    @FXML
    private TextField seachbar;
    @FXML
    private Button AjouterPub;
    @FXML
    private Button alimentation;

    @FXML
    private Button art;

    @FXML
    private Button aventure;

    @FXML
    private Button exterieur;

    @FXML
    private Button groupe;

    @FXML
    private Button individuel;

    @FXML
    private Button sante;

    private page pageToModify; // Store the page to be modified
    private pageService PS = new pageService();
    private List<page> pageList;

    // Set the page to be modified

    public void setPageToModify(page page) throws IOException, SQLException {
        this.pageToModify = page;

    }

    @FXML
    public void initialize() throws IOException, SQLException {

// Retrieve the list of pages
        List<page> pageList;
        pageService pageService = new pageService();
        pageList = pageService.afficher();


        loadContent(pageList);
    }

    private void loadContent(List<page> list) {
        ObservableList<page> pageObservableList = FXCollections.observableArrayList();
        pageObservableList.addAll(list);

        GridPaneA.getChildren().clear();

        VBox pagesContainer = new VBox(10);

        for (page page : pageObservableList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageAdmin(lieux).fxml"));
                Parent interfaceRoot = loader.load();
                PageAdmincontroller itemController = loader.getController();
                itemController.setData(page);
                pagesContainer.getChildren().add(interfaceRoot);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load page", Alert.AlertType.ERROR);
            }
        }

        ScrollPane scrollPane = new ScrollPane(pagesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        GridPaneA.add(scrollPane, 0, 0);
    }
    @FXML
    void AjouterPub(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubAdd(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void seachbar(KeyEvent event) {
        try {
            String query = seachbar.getText();
            List<page> list = PS.search(query);
            loadContent(list);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to search pages", Alert.AlertType.ERROR);
        }
    }

    public void refreshView() {
        try {
            // Rafraîchir la vue en rechargeant les données
            List<page> pageList = PS.afficher();
            loadContent(pageList);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
    @FXML
    void alimentation(ActionEvent event) {
        try {
            List<page> pageAlimentation = PS.filtrerParCategorie("Alimentation");
            loadContent(pageAlimentation);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    @FXML
    void sante(ActionEvent event) {
        try {
            List<page> pageSante = PS.filtrerParCategorie("Santé");
            loadContent(pageSante);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }


    @FXML
    private void art(ActionEvent event) {
        try {
            List<page> pageArt = PS.filtrerParCategorie("Art");
            loadContent(pageArt);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    @FXML
    private void aventure(ActionEvent event) {
        try {
            List<page> pageAventure = PS.filtrerParCategorie("Aventure");
            loadContent(pageAventure);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    @FXML
    private void exterieur(ActionEvent event) {
        try {
            List<page> pageExterieur = PS.filtrerParCategorie("Extérieur");
            loadContent(pageExterieur);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    @FXML
    private void groupe(ActionEvent event) {
        try {
            List<page> pageGroupe = PS.filtrerParCategorie("Groupe");
            loadContent(pageGroupe);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    @FXML
    private void individuel(ActionEvent event) {
        try {
            List<page> pageIndividuel = PS.filtrerParCategorie("Individuel");
            loadContent(pageIndividuel);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }
    @FXML
    void lieu(ActionEvent event) {
// Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Test(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void publication(ActionEvent event) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubTest(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tout(ActionEvent actionEvent) {
        try {
            List<page> pageIndividuel = PS.afficher();
            loadContent(pageIndividuel);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }


}
