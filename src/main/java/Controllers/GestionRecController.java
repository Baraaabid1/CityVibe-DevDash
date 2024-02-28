package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

public class GestionRecController {

    @FXML
    private ListView<Reclamation> List;
    @FXML
    private ComboBox<String> trieType;

    @FXML
    private TextField seachbar;

    @FXML
    private TextField repText;
    @FXML
    private ListView<ReponseR> listReponses;
    @FXML
    private Pane repPan;


    private ReclamationService rs = new ReclamationService();
    private ReponseRService rrs = new ReponseRService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
    int idU =0;
    int currRec ;


    public void initialize() {
        repPan.setVisible(false);

        ObservableList<String> typeReclamation = FXCollections.observableArrayList(
                "Tout",
                "Réclamation Urgente",
                "Bugs ou plantages",
                "Contenu inapproprié",
                "Informations incorrectes",
                "Problèmes de sécurité",
                "Suggestions d'amélioration",
                "Problèmes de service client"
        );
        trieType.setItems(typeReclamation);

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



                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                                 // Add mouse click event handler
                                setOnMouseClicked(event -> {
                                    currRec=reclamation.getIdR();
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
            // ####### list from the buttom
            List.scrollTo(List.getItems().size() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }
    }

    private void updateReponsesListView(int idR) throws SQLException {
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

        private void handleItemClick(int idR) throws SQLException {
        updateReponsesListView(idR);
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
    @FXML
    void dateASCE(ActionEvent event) {
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.trierReclamationsParDateAscendante());
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



                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                                // Add mouse click event handler
                                setOnMouseClicked(event -> {
                                    currRec=reclamation.getIdR();
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
            // ####### list from the buttom
            List.scrollTo(List.getItems().size() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }
    }



    @FXML
    void dateDESC(ActionEvent event) {
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.trierReclamationsParDateDescendante());
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



                                // Create a vertical box to hold labels and buttons
                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                // Set padding and spacing for the reclamation box
                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                                // Add mouse click event handler
                                setOnMouseClicked(event -> {
                                    currRec=reclamation.getIdR();
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
            // ####### list from the buttom
            List.scrollTo(List.getItems().size() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

    }



    @FXML
    public void parType(ActionEvent event) {
        try {
            ObservableList<Reclamation> reclamations;

            if (trieType.getValue().equals("Tout")) {
                reclamations = FXCollections.observableArrayList(rs.afficher());
            } else {
                reclamations = FXCollections.observableArrayList(rs.trierReclamationsParType(trieType.getValue()));
            }

            List.setStyle("-fx-control-inner-background: rgba(244,244,244,255);-fx-border-width: 0;-fx-selection-bar: transparent;");

            List.setCellFactory(param -> new ListCell<>() {
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

                        // Create a vertical box to hold labels and buttons
                        VBox reclamationBox = new VBox();
                        reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel);
                        VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                        // Set padding and spacing for the reclamation box
                        reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px; -fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                        setGraphic(reclamationBox);

                        // Add mouse click event handler
                        setOnMouseClicked(event -> {
                            currRec = reclamation.getIdR();
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
            });

            List.setItems(reclamations);
            // ####### list from the bottom
            List.scrollTo(List.getItems().size() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }
    }
}

