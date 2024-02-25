package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AjoutReclamationController {

    @FXML
    private TextField seachbar;

    @FXML
    private TextField titre;

    @FXML
    private TextArea text;


    @FXML
    private ChoiceBox<String> type_rec;

    @FXML
    private TextField page;

    @FXML
    private ChoiceBox<String> apropo;

    @FXML
    private TextField event;

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    ReclamationService rs = new ReclamationService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

    public void initialize() {
        ObservableList<String> typeReclamation = FXCollections.observableArrayList(
                "Réclamation Urgente",
                "Bugs ou plantages",
                "Contenu inapproprié",
                "Informations incorrectes",
                "Problèmes de sécurité",
                "Suggestions d'amélioration",
                "Problèmes de service client"
        );
        type_rec.setItems(typeReclamation);
        ObservableList<String> Reclamationapropo = FXCollections.observableArrayList(
                "Page",
                "Evenement",
                "Autre"
        );
        apropo.setItems(Reclamationapropo);
        page.setVisible(false);
        event.setVisible(false);
        apropo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Page":
                        page.setVisible(true);
                        event.setVisible(false);
                        break;
                    case "Evenement":
                        page.setVisible(false);
                        event.setVisible(true);
                        break;
                    default:
                        page.setVisible(false);
                        event.setVisible(false);
                }
            }
        });

    }
    @FXML
    void ajouterReclamation(ActionEvent event) {

    }
   /* private VBox createReclamationCard(Reclamation reclamation) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
        card.setSpacing(5);

        Label typeLabel = new Label("Type: " + reclamation.getType());
        Label dateLabel = new Label("Date: " + reclamation.getDate());
        Label titleLabel = new Label("Titre: " + reclamation.getTitre());
        Label contenuLabel = new Label("Contenu: " + reclamation.getContenu());

        card.getChildren().addAll(typeLabel, dateLabel, titleLabel, contenuLabel);

        return card;
    }*/


    @FXML
    void AfficherListe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeReclamations.fxml"));
        root = loader.load();
        ListeReclamationsController Lc = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
