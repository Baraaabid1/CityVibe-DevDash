package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import models.publication;
import services.publicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GeneralDesignTestController {
    @FXML
    private ListView<Parent> publist;

    @FXML
    public void initialize() {
        loadContent();
    }
    @FXML
<<<<<<< HEAD
    private void loadContent() {
=======
    public void loadContent() {
>>>>>>> 3c6b76b146eaa79534c1fc586dc86674cb956c96
        try {
            try {
                publicationService publicationService = new publicationService();

                // Retrieve the list of publications as an ObservableList
                List<publication> publicationList = publicationService.afficher();
                ObservableList<publication> observablePublicationList = FXCollections.observableArrayList(publicationList);

                // Clear existing content of ListView
                publist.getItems().clear();

<<<<<<< HEAD
            // Iterate through the list of publications
            for (publication publication : publicationList) {
                // Load Pub.fxml for each publication and set its data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub.fxml"));
                Parent interfaceRoot = loader.load();
                Pubcontroller itemController = loader.getController();
                itemController.setData(publication);
                itemController.setE(publication);
                 itemController.setRefresh(this);
=======
                // Iterate through the ObservableList of publications
                for (publication publication : observablePublicationList) {
                    // Load Pub.fxml for each publication and set its data
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pub.fxml"));
                    Parent interfaceRoot = loader.load();
                    Pubcontroller itemController = loader.getController();
                    itemController.setData(publication);
                    itemController.setE(publication);
                    // itemController.setRefresh(this);
>>>>>>> 3c6b76b146eaa79534c1fc586dc86674cb956c96

                    // Add the loaded element to ListView
                    publist.getItems().add(interfaceRoot);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        } finally {

        }
    }

    public void refreshView() {
        loadContent();
    }
    @FXML
    public void Button_Acceuil(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Events(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Lieux(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Transport(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Reservation(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Profil(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Reclamation(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Parametres(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Logout(ActionEvent actionEvent) {
    }
    @FXML
    public void Button_Help(ActionEvent actionEvent) {
    }
    @FXML
    public void EcoModeButton(ActionEvent actionEvent) {
    }

    public void refreshview() { loadContent();
    }
}
