package  controllers ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralDesignTestController {
    @FXML
    private GridPane GridPane;
    @FXML
    private TextField seachbar;

    @FXML
    private Button lieu;

    @FXML
    private Button publication;

    private publicationService publicationService;

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

    @FXML
    public void initialize() {
        loadContent();
        // Set listener for search bar text changes
        seachbar.textProperty().addListener((observable, oldValue, newValue) -> {
            search(newValue);
        });
    }

    @FXML
    private void loadContent() {
        try {
            publicationService = new publicationService();
            List<publication> publicationList = publicationService.afficher();

            // Clear existing content of GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the list of publications
            for (publication publication : publicationList) {
                // Load Pub(lieux).fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub(lieux).fxml"));
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

    // Method to refresh view
    public void refreshView() {
        loadContent();
    }

    @FXML
    void seachbar(ActionEvent event) {
        // Handle search bar functionality
    }

    // Method to handle search functionality
    private void search(String query) {
        try {
            List<publication> searchResults = publicationService.search(query);

            // Clear existing content of GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Iterate through the search results
            for (publication publication : searchResults) {
                // Load Pub(lieux).fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub(lieux).fxml"));
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


    @FXML
    void Button_Acceuil(ActionEvent actionEvent) {
        // Handle Acceuil button action
    }

    // Implement other button action methods similarly

    @FXML
    void groupe(ActionEvent actionEvent) {
        String searchText = "groupe"; // Set the search text to filter by "groupe"
        loadContent(searchText);
    }

    @FXML
    void individuel(ActionEvent actionEvent) {
        String searchText = "individuel"; // Set the search text to filter by "individuel"
        loadContent(searchText);
    }

    @FXML
    void exterieur(ActionEvent actionEvent) {
        String searchText = "exterieur"; // Set the search text to filter by "exterieur"
        loadContent(searchText);
    }

    @FXML
    void aventure(ActionEvent actionEvent) {
        String searchText = "aventure"; // Set the search text to filter by "aventure"
        loadContent(searchText);
    }

    @FXML
    void sante(ActionEvent actionEvent) {
        String searchText = "sante"; // Set the search text to filter by "sante"
        loadContent(searchText);
    }

    @FXML
    void alimentation(ActionEvent event) {
        String searchText = "alimentation"; // Set the search text to filter by "groupe"
        loadContent(searchText);
    }
    @FXML
    void art(ActionEvent actionEvent) {
        String searchText = "art"; // Set the search text to filter by "art"
        loadContent(searchText);
    }

    private void loadContent(String searchText) {
        try {
            List<publication> filteredPublications;

            if (searchText == null || searchText.isEmpty()) {
                filteredPublications = publicationService.afficher();
            } else {
//                // Add filtering logic based on searchText
//                filteredPublications = publicationService.afficher().stream()
//                        .filter(publication ->
//                                // Your filtering condition based on searchText
//                                publication.getCategory().equalsIgnoreCase(searchText))
//                        .collect(Collectors.toList());
            }

//            displayPublications(filteredPublications);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
    private void displayPublications(List<publication> publications) {
        // Clear existing content of GridPane
        GridPane.getChildren().clear();

        int row = 1;
        int col = 0;

        // Iterate through the list of publications
        for (publication publication : publications) {
            try {
                // Load Pub(lieux).fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub(lieux).fxml"));
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshview() {
    }

    public void publication(ActionEvent actionEvent) {
//        // Add the action you want to perform when the publication button is clicked
//        // For example, opening a new page named PageTest
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubTest(lieux).fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    public void lieu(ActionEvent actionEvent) {
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

    public void EcoModeButton(ActionEvent actionEvent) {
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
}
