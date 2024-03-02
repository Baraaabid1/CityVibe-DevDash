package Controllers;

import API.WeatherAPI;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Reclamation;
import models.ReponseR;
import org.json.simple.JSONObject;
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
    private Pane meteoPane;

    @FXML
    private ListView<Reclamation> listReclamation;

    private ReclamationService rs = new ReclamationService();
    private ReponseRService rrs = new ReponseRService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
    @FXML
    private Pane repPan;

    @FXML
    private Pane gestionRepPan;

    @FXML
    private TextField gestRep;

    @FXML
    private ListView<ReponseR> listReponses;

    @FXML
    private TextField repText;


    @FXML
    private ImageView icon;

    @FXML
    private Text cond;

    @FXML
    private Text temp;

    @FXML
    private Text wind;

    @FXML
    private Text hum;


    @FXML
    private ComboBox<String> weatherLoc;



    int idU =1 ;
    int currRec ;
    int currRepense;
    public String Localisation = "Aryanah";


    public void initialize() throws SQLException {
        clearResponselist();
        meteoPane.setVisible(true);
        weatherLoc.setVisible(true);
        setWeather();
        ObservableList<String> weatherLocation = FXCollections.observableArrayList(
                "Nabeul",
                "Aryanah",
                "Mahdia"
        );
        weatherLoc.setItems(weatherLocation);
        repPan.setVisible(false);
        gestionRepPan.setVisible(false);
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(rs.MesReclamations(idU));
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
                                Label titleLabel = new Label(reclamation.getTitre());
                                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                                Label typeTimeLabel = new Label(reclamation.getType() + " - " + reclamation.getTemp());
                                typeTimeLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: grey;");

                                Label contentLabel = new Label(reclamation.getContenu());
                                contentLabel.setStyle("-fx-font-size: 12pt;");
                                //les bouttons

                                HBox buttonsBox = new HBox();
                                Button modifyButton = createStyledButton("Modify");
                                Button deleteButton = createStyledButton("Delete");
                                Button responseButton = createStyledButton("See Response");

                                buttonsBox.setAlignment(Pos.CENTER);
                                // click modifier

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

                                // click supprimer
                                deleteButton.setOnAction(event -> {
                                    try {
                                        rrs.deleteResponsesForReclamation(reclamation.getIdR());
                                        rs.supprimer(reclamation.getIdR());
                                        reclamations.remove(reclamation);
                                        clearResponselist();
                                        meteoPane.setVisible(true);
                                        weatherLoc.setVisible(true);
                                        repPan.setVisible(false);


                                    } catch (SQLException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error");
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                    System.out.println("Delete button clicked for reclamation ID: " + reclamation.getIdR());
                                });
                                //click repondre
                                responseButton.setOnAction(event -> {
                                    try {
                                        updateReponsesListView(reclamation.getIdR());
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                buttonsBox.getChildren().addAll(modifyButton, deleteButton, responseButton);
                                buttonsBox.setSpacing(10);

                                VBox reclamationBox = new VBox();
                                reclamationBox.getChildren().addAll(titleLabel, typeTimeLabel, contentLabel, buttonsBox);
                                VBox.setVgrow(reclamationBox, Priority.ALWAYS);

                                reclamationBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px;-fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 15px;");

                                setGraphic(reclamationBox);
                            }
                        }
                    };
                }
            });
            listReclamation.setItems(reclamations);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #F99D26; " +
                "-fx-background-radius: 10; " +
                "-fx-text-fill: WHITE; " +
                "-fx-effect: dropshadow(gaussian, rgba(200, 200, 200, 0.2), 10, 0.5, 0, 0);");

        button.setEffect(new DropShadow(10, Color.rgb(200, 200, 200, 0.2)));
        button.setFont(javafx.scene.text.Font.font("Arial Nova Bold", 15));
        button.setMinWidth(120);
        button.setMinHeight(30);

        return button;
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
        String reponse = repText.getText().trim();
        if (!reponse.isEmpty()) {
            try {
                rrs.ajouter(new ReponseR(currRec, idU, reponse, currentTimestamp));
                updateReponsesListView(currRec);
                repText.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

// effaser la liste des reponses
    public void clearResponselist(){
        ObservableList<ReponseR> items = listReponses.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            items.remove(i);
        }
    }

    //update et afficher liste des reponse
    public void updateReponsesListView(int idR) throws SQLException {
        meteoPane.setVisible(false);
        weatherLoc.setVisible(false);
        currRec =idR;
        repPan.setVisible(true);
        listReponses.getItems().clear();

        ObservableList<ReponseR> reponses = FXCollections.observableArrayList(rrs.afficherReponsesForReclamation(idR));
        listReponses.setStyle("-fx-control-inner-background: white;-fx-border-width: 0;-fx-selection-bar: white;");
        listReponses.setItems(reponses);

        listReponses.setCellFactory(param -> new ListCell<ReponseR>() {
            @Override
            protected void updateItem(ReponseR reponse, boolean empty) {
                super.updateItem(reponse, empty);
                if (empty || reponse == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label();
                    nameLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: black;-fx-font-weight: bold");
                    if (reponse.getIdU() == 0) {
                        nameLabel.setText("Admin");
                    } else {
                        //va etre modifier lors de la joiture avec user
                        nameLabel.setText("User 1");

                    }

                    TextFlow textFlow = new TextFlow();
                    textFlow.setPrefWidth(listReponses.getWidth());
                    Text text = new Text(reponse.getTextR());
                    text.setStyle("-fx-font-size: 10pt; -fx-fill: black;");
                    text.wrappingWidthProperty().bind(listReponses.widthProperty());
                    textFlow.getChildren().add(text);
                    double textFlowWidth = 0.8 * listReponses.getWidth();
                    textFlow.setPrefWidth(textFlowWidth);

                    VBox reponseBox = new VBox();
                    reponseBox.getChildren().addAll(nameLabel, textFlow);
                    VBox.setVgrow(reponseBox, Priority.ALWAYS);

                    setGraphic(reponseBox);
                    setPrefHeight(USE_COMPUTED_SIZE);
                    listReponses.setOnMouseClicked(event -> {
                        ReponseR selectedReponse = listReponses.getSelectionModel().getSelectedItem();
                        if (selectedReponse != null) {
                            if (selectedReponse.getIdU() == idU) {
                                gestionRepPan.setVisible(true);
                                gestRep.setText(selectedReponse.getTextR());
                                currRepense = selectedReponse.getIdRR();
                                System.out.println("Clicked item: " + selectedReponse.getIdRR());
                            }
                        }
                    });


                }
            }
        });
        listReponses.setFixedCellSize(50);
        listReponses.setPrefHeight(reponses.size() * listReponses.getFixedCellSize());
        listReponses.scrollTo(reponses.size() - 1);
        listReponses.setMaxHeight(401);
    }

    @FXML
    void SuppRep(ActionEvent event) {
        try {
            rrs.supprimer(currRepense);
            updateReponsesListView(currRec);
            gestionRepPan.setVisible(false);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ModifRep(ActionEvent event) {
        try {
            String modifiedText = gestRep.getText();
            ReponseR reponse = rrs.getReponseFromID(currRepense);
            reponse.setTextR(modifiedText);
            rrs.modifier(reponse);
            updateReponsesListView(currRec);
            gestionRepPan.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public void setWeather(){
        JSONObject weatherData = WeatherAPI.getWeatherData(Localisation);
        if (weatherData != null) {
            temp.setText(String.valueOf(weatherData.get("temperature")) + " °C");
            cond.setText(String.valueOf(weatherData.get("weather_condition")));
            hum.setText(String.valueOf(weatherData.get("humidity"))+ "%");
            wind.setText(String.valueOf(weatherData.get("windspeed")) + " km/h");

            String condition = cond.getText();

            if ("Clear".equals(condition)) {
                icon.setImage(new Image("assets/clear.png"));
            } else if ("Cloudy".equals(condition)) {
                icon.setImage(new Image("assets/cloudy.png"));
            } else if ("Rain".equals(condition)) {
                icon.setImage(new Image("assets/rain.png"));
            } else if ("Snow".equals(condition)) {
                icon.setImage(new Image("assets/snow.png"));
            } else {
                System.out.println("Unknown weather condition: " + condition);
            }

        } else {
            temp.setText("N/A");
            cond.setText("N/A");
            hum.setText("N/A");
            wind.setText("N/A");
        }

    }
    @FXML
    void changeWeatherLoc(ActionEvent event) {
        Localisation=weatherLoc.getValue();
        setWeather();

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
