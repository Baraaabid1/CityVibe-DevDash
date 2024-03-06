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
import services.pageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignConsulterController {
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField seachbar;
    @FXML
    private Button alimentation;

    @FXML
    private Button art;

    @FXML
    private Button aventure;

    @FXML
    private Button exterieur;

    @FXML
    private Button sante;

    @FXML
    private Button groupe;

    @FXML
    private Button individuel;
    pageService pageService = new pageService();


    @FXML
    public void initialize() throws SQLException, IOException {
        List<page> pageList = pageService.afficher();


        loadContent(pageList); // Pass an empty string to signify no search text
    }

    private void loadContent(List<page>pageList ) throws IOException {




            // Clear existing content of GridPane
            if (gridPane != null) {
                gridPane.getChildren().clear();

                int row = 1;
                int col = 0;

                // Iterate through the list of pages
                for (page page : pageList) {
                    // Check if the page's category matches the search text
                    //   if (page.getCategory().equalsIgnoreCase(searchText) || searchText.isEmpty()) {
                    // Load page(lieux).fxml for each page and set its data
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterPage(lieux).fxml"));
                    Parent interfaceRoot = loader.load();
                    ConsulterPagecontroller itemController = loader.getController();
                    itemController.setData(page);
                    itemController.setE(page);
                    itemController.setRefresh(this);

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
    }



    public void refreshView() throws SQLException, IOException {

    initialize();
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

    public void lieux(ActionEvent actionEvent) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PageConsult(lieux).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publication(ActionEvent actionEvent) {
        // Add the action you want to perform when the publication button is clicked
        // For example, opening a new page named PageTest
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LieuxConsult(lieu).fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void seachbar(KeyEvent event) throws SQLException, IOException {
        String query = seachbar.getText();
        System.out.println(query);// Implement search logic here
        List list = pageService.search(query);
        loadContent(list);
    }

    @FXML
    void alimentation(ActionEvent event) throws SQLException, IOException {
        String searchText = "alimentation"; // Set the search text to filter by "groupe"
        List list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }



    @FXML
    void sante(ActionEvent event) throws SQLException, IOException {
        String searchText = "sante"; // Set the search text to filter by "groupe"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }


    @FXML
    private void art(ActionEvent event) throws SQLException, IOException {
        String searchText = "art"; // Set the search text to filter by "art"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }

    @FXML
    private void aventure(ActionEvent event) throws SQLException, IOException {
        String searchText = "aventure"; // Set the search text to filter by "aventure"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }

    @FXML
    private void exterieur(ActionEvent event) throws SQLException, IOException {
        String searchText = "exterieur"; // Set the search text to filter by "exterieur"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }

    @FXML
    private void groupe(ActionEvent event) throws SQLException, IOException {
        String searchText = "groupe"; // Set the search text to filter by "groupe"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }

    @FXML
    private void individuel(ActionEvent event) throws SQLException, IOException {
        String searchText = "individuel"; // Set the search text to filter by "individuel"
        List<page> list = pageService.filtrerParCategorie(searchText);

        loadContent(list);
    }
}
