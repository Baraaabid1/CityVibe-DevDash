package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;

public class GestionRecController {

    @FXML
    private ListView<Reclamation> List;

    @FXML
    private TextField seachbar;


    private ReclamationService rs = new ReclamationService();

    public void initialize() {

        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.afficher());
            List.setStyle("-fx-control-inner-background: rgba(244,244,244,255);-fx-border-color: transparent;-fx-selection-bar: transparent;");

            List.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Reclamation> call(ListView<Reclamation> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Reclamation reclamation, boolean empty) {
                            super.updateItem(reclamation, empty);
                            if (empty || reclamation == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Create labels for title, type, content, and time
                                Label titleLabel = new Label(reclamation.getTitre());
                                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                                Label typeTimeLabel = new Label(reclamation.getType() + " - " + reclamation.getTemp());
                                typeTimeLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: grey;");

                                Label contentLabel = new Label(reclamation.getContenu());
                                contentLabel.setStyle("-fx-font-size: 12pt;");

                                // Add buttons to a horizontal box
                                HBox buttonsBox = new HBox();
                                Button deleteButton = createStyledButton("Delete");
                                Button responseButton = createStyledButton("Repondre");

                                // Set alignment of the buttons box
                                buttonsBox.setAlignment(Pos.CENTER);

                                // Set button actions
                                deleteButton.setOnAction(event -> {
                                    try {
                                        rs.supprimer(reclamation.getIdR());
                                        getListView().getItems().remove(reclamation); // Remove from the observable list
                                    } catch (SQLException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error");
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                    System.out.println("Delete button clicked for reclamation ID: " + reclamation.getIdR());
                                });

                                responseButton.setOnAction(event -> {
                                    // Handle response action
                                    System.out.println("Response button clicked for reclamation ID: " + reclamation.getIdR());
                                });

                                buttonsBox.getChildren().addAll(deleteButton, responseButton);
                                buttonsBox.setSpacing(10);

                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel, buttonsBox);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                            }
                        }
                    };
                }
            });

            List.setItems(reclamations);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-min-width: 120px; -fx-min-height: 30px;");
        return button;
    }

    @FXML
    void Button_Acceuil(ActionEvent event) {

    }

    @FXML
    void Button_Events(ActionEvent event) {

    }

    @FXML
    void Button_Help(ActionEvent event) {

    }

    @FXML
    void Button_Lieux(ActionEvent event) {

    }

    @FXML
    void Button_Logout(ActionEvent event) {

    }

    @FXML
    void Button_Parametres(ActionEvent event) {

    }

    @FXML
    void Button_Profil(ActionEvent event) {

    }

    @FXML
    void Button_Reclamation(ActionEvent event) {

    }

    @FXML
    void Button_Reservation(ActionEvent event) {

    }

    @FXML
    void Button_Transport(ActionEvent event) {

    }

    @FXML
    void EcoModeButton(ActionEvent event) {

    }
}
