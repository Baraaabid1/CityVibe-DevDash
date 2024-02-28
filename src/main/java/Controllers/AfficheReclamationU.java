package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AfficheReclamationU {
    @FXML
    private TextField seachbar;

    @FXML
    private ListView<Reclamation> listReclamation;

    private ReclamationService rs = new ReclamationService();
    private ReponseRService rrs = new ReponseRService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
    @FXML
    private Pane repPan;

    @FXML
    private ListView<ReponseR> listReponses;

    @FXML
    private TextField repText;


    int idU =1;
    int currRec ;

    public void initialize() {
        repPan.setVisible(false);
        int userId = 1;
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.MesReclamations(userId));
            FXCollections.reverse(reclamations);
            listReclamation.setStyle("-fx-control-inner-background: rgba(244,244,244,255);-fx-border-width: 0;-fx-selection-bar: transparent;");



            listReclamation.setCellFactory(new Callback<>() {
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
                                Button modifyButton = createStyledButton("Modify");
                                Button deleteButton = createStyledButton("Delete");
                                Button responseButton = createStyledButton("See Response");

                                buttonsBox.setAlignment(Pos.CENTER);

                                // Add action handlers to the buttons
                                modifyButton.setOnAction(event -> {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
                                    Parent root = null;
                                    try {
                                        root = loader.load();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    AjoutReclamationController aREC = loader.getController();
                                    aREC.modifierRec(reclamation.getIdR());
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(new Scene(root));
                                    System.out.println("Modify button clicked for reclamation ID: " + reclamation.getIdR());
                                });

                                deleteButton.setOnAction(event -> {
                                    try {
                                        rs.supprimer(reclamation.getIdR());
                                        reclamations.remove(reclamation); // Remove from the observable list
                                    } catch (SQLException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error");
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                    System.out.println("Delete button clicked for reclamation ID: " + reclamation.getIdR());
                                    //reclamations.remove(reclamation);
                                });

                                responseButton.setOnAction(event -> {
                                    try {
                                        updateReponsesListView(reclamation.getIdR());
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                buttonsBox.getChildren().addAll(modifyButton, deleteButton, responseButton);
                                buttonsBox.setSpacing(10);

                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel, buttonsBox);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px;-fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                            }
                        }
                    };
                }
            });

            // Set the observable list to the ListView
            listReclamation.setItems(reclamations);

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

    @FXML
    void Reclamer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    @FXML
    void hit_send(ActionEvent event) {
        String reponse =repText.getText();
        try {
            rrs.ajouter(new ReponseR(currRec,idU,reponse,currentTimestamp));
            updateReponsesListView(currRec);
            repText.clear();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void updateReponsesListView(int idR) throws SQLException {
        currRec =idR;
        repPan.setVisible(true);

        ObservableList<ReponseR> reponses = FXCollections.observableArrayList(rrs.afficherReponsesForReclamation(idR));
        listReponses.setStyle("-fx-control-inner-background: white;-fx-border-width: 0;-fx-selection-bar: white;");

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
                    // Create labels for Name and Text
                    Label nameLabel = new Label();
                    nameLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: black;-fx-font-weight: bold");

                    if (reponse.getIdU() == 0) {
                        nameLabel.setText("Admin");
                    } else {
                        nameLabel.setText("User 1");

                    }

                    Label textLabel = new Label(reponse.getTextR());
                    textLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: black;");

                    // Create a vertical box to hold labels
                    VBox reponseBox = new VBox();
                    reponseBox.getChildren().addAll(nameLabel, textLabel);
                    VBox.setVgrow(reponseBox, Priority.ALWAYS);

                    setGraphic(reponseBox);

                }
            }
        });

// Set a custom viewport to reverse the order visually
        listReponses.setFixedCellSize(50); // Adjust cell height as needed
        listReponses.setPrefHeight(reponses.size() * listReponses.getFixedCellSize());
        listReponses.scrollTo(reponses.size() - 1);
        listReponses.setMaxHeight(401);
    }


}
