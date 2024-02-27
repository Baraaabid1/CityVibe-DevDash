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
import models.ReponseR;
import services.ReclamationService;
import services.ReponseRService;

import java.io.IOException;
import java.sql.SQLException;

public class GestionRecController {

    @FXML
    private ListView<Reclamation> List;

    @FXML
    private TextField seachbar;
    @FXML
    private ListView<ReponseR> listReponses;


    private ReclamationService rs = new ReclamationService();
    private ReponseRService rrs = new ReponseRService();


    public void initialize() {

        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.afficher());
            List.setStyle("-fx-control-inner-background: rgba(244,244,244,255);-fx-border-width: 0;-fx-selection-bar: transparent;");

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
                                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;-fx-text-fill: black;");

                                Label typeTimeLabel = new Label(reclamation.getType() + " - " + reclamation.getTemp());
                                typeTimeLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: grey;");

                                Label contentLabel = new Label(reclamation.getContenu());
                                contentLabel.setStyle("-fx-font-size: 12pt;-fx-text-fill: black;");

                                // Add buttons to a horizontal box
                                HBox buttonsBox = new HBox();
                                Button responseButton = createStyledButton("Repondre");

                                // Set alignment of the buttons box
                                buttonsBox.setAlignment(Pos.CENTER_RIGHT);



                                responseButton.setOnAction(event -> {
                                    // Handle response action
                                    System.out.println("Response button clicked for reclamation ID: " + reclamation.getIdR());
                                });

                                buttonsBox.getChildren().addAll(responseButton);
                                buttonsBox.setSpacing(10);

                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel, buttonsBox);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                                 // Add mouse click event handler
                                setOnMouseClicked(event -> {
                                    if (event.getClickCount() == 1) { // Handle single-click event
                                        try {
                                            handleItemClick(reclamation.getIdR());
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
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

        private void handleItemClick(int idR) throws SQLException {
            ObservableList<ReponseR> reponses = FXCollections.observableArrayList(rrs.afficherReponsesForReclamation(idR));

            // Clear existing items
            listReponses.getItems().clear();

            // Add responses to the ListView
            listReponses.setItems(reponses);

            // Optionally, set a custom cell factory for the ListView to customize the appearance of each item
            listReponses.setCellFactory(param -> new ListCell<ReponseR>() {
                @Override
                protected void updateItem(ReponseR reponse, boolean empty) {
                    super.updateItem(reponse, empty);
                    if (empty || reponse == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText("ID: " + reponse.getIdRR() + ", Text: " + reponse.getTextR()); // Customize text as needed
                    }
                }
            });

    }



    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;-fx-min-width: 200px;-fx-min-height: 30px;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 0); -fx-background-radius: 10;");
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
    @FXML
    void Repondre(ActionEvent event) {

    }
}
