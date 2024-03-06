package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.page;
import models.publication;
import services.pageService;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralDesignController {

    @FXML
    private GridPane GridPaneV;

    @FXML
    private TextField seachbar;

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

    @FXML
    private Button alimentation;

    private List<publication> allPublications; // Store all publications
    private publicationService PubS = new publicationService();

    @FXML
    public void seachbar(KeyEvent event) throws SQLException, IOException {
        String query = seachbar.getText();
        System.out.println(query);// Implement search logic here
        List<publication> list = PubS.search(query);
        displayPublications(list);
    }
    @FXML
    public void initialize() throws IOException, SQLException {

// Retrieve the list of pages
        List<publication> publicationList;
        publicationService publicationService = new publicationService();
        publicationList = publicationService.afficher();


        displayPublications(publicationList);
    }
//    @FXML
//    public void initialize() {
//        loadContent(""); // Load all publications initially
//
//        // Add event listeners for category buttons
//        art.setOnAction(this::filterByCategory);
//        aventure.setOnAction(this::filterByCategory);
//        exterieur.setOnAction(this::filterByCategory);
//        groupe.setOnAction(this::filterByCategory);
//        individuel.setOnAction(this::filterByCategory);
//        sante.setOnAction(this::filterByCategory);
//        alimentation.setOnAction(this::filterByCategory);
//
//        // Add event listener for search bar
//    }

    private void filterByCategory(ActionEvent event) {
        String category = ((Button) event.getSource()).getText().toLowerCase();
        loadContent(category);
    }




    private void loadContent(String category) {
        try {
            publicationService publicationService = new publicationService();
            if (category == null || category.isEmpty()) {
                // If no category specified, load all publications
                allPublications = publicationService.afficher();
            } else {
                // Load publications based on the specified category
//                allPublications = publicationService.afficher().stream()
//                        .filter(publication -> publication.getCategory().toLowerCase().equals(category))
//                        .collect(Collectors.toList());
            }

            displayPublications(allPublications);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void displayPublications(List<publication> publications) {
        // Clear existing content of GridPane
        GridPaneV.getChildren().clear();

        int row = 1;
        int col = 0;

        // Iterate through the list of publications
        for (publication publication : publications) {
            try {
                // Load Pub(lieux).fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/public(lieux).fxml"));
                Parent interfaceRoot = loader.load();
                publiccontroller itemController = loader.getController();
                itemController.setData(publication);
                itemController.setE(publication);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterPublications(String query) {
        if (query.isEmpty()) {
            // If the search query is empty, display all publications
            displayPublications(allPublications);
        } else {
            // Filter publications whose title contains the search query
            List<publication> filteredPublications = allPublications.stream()
                    .filter(publication -> publication.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            displayPublications(filteredPublications);
        }
    }

    // Other methods remain unchanged

    @FXML
    void publication(ActionEvent event) {
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

    @FXML
    void lieux(ActionEvent actionEvent) {
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

    @FXML
    void Button_Acceuil(ActionEvent event) {
        // Your implementation
    }

    @FXML
    void EcoModeButton(ActionEvent event) {
        // Your implementation
    }

    @FXML
    public void Button_Help(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Logout(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Parametres(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Reclamation(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Profil(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Reservation(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Transport(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Lieux(ActionEvent actionEvent) {
    }

    @FXML
    public void Button_Events(ActionEvent actionEvent) {
    }
    public void refreshView() throws IOException {
        loadContent("");
    }

    public void art(ActionEvent actionEvent) {
    }

    public void alimentation(ActionEvent actionEvent) {
    }

    public void sante(ActionEvent actionEvent) {
    }

    public void exterieur(ActionEvent actionEvent) {
    }

    public void aventure(ActionEvent actionEvent) {
    }

    public void individuel(ActionEvent actionEvent) {
    }

    public void groupe(ActionEvent actionEvent) {
    }
}
