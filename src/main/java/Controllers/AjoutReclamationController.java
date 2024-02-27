package Controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
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


    @FXML
    private Button modif_button;

    @FXML
    private Button buttonAjout;

    @FXML
    private Text idRModif;

    @FXML
    private Text TypeAlert;

    @FXML
    private Text maxTitreAlert;

    @FXML
    private Text ContAlert;

    @FXML
    private Text apropoAlert1;

    @FXML
    private Text noTitreAlert1;

    @FXML
    private Text noRecAlert;


    ReclamationService rs = new ReclamationService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

    public void initialize() {
        idRModif.setVisible(false);
        TypeAlert.setVisible(false);
        maxTitreAlert.setVisible(false);
        ContAlert.setVisible(false);
        apropoAlert1.setVisible(false);
        noTitreAlert1.setVisible(false);
        noRecAlert.setVisible(false);
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
            String titreValue = titre.getText();
            String textValue = text.getText();
            String typeRecValue = type_rec.getValue();
             String apropoValue = apropo.getValue();

        if (typeRecValue == null|| typeRecValue.isEmpty()) {
            TypeAlert.setVisible(true);
            maxTitreAlert.setVisible(false);
            ContAlert.setVisible(false);
            apropoAlert1.setVisible(false);
            noTitreAlert1.setVisible(false);
            noRecAlert.setVisible(false);
            shakeButton();

            return;
        }
        if (apropoValue == null || apropoValue.isEmpty()) {
            apropoAlert1.setVisible(true);
            TypeAlert.setVisible(false);
            maxTitreAlert.setVisible(false);
            ContAlert.setVisible(false);
            noTitreAlert1.setVisible(false);
            noRecAlert.setVisible(false);
            shakeButton();

            return;
        }
        if (titreValue == null|| titreValue.isEmpty() ) {
            noTitreAlert1.setVisible(true);
            TypeAlert.setVisible(false);
            maxTitreAlert.setVisible(false);
            ContAlert.setVisible(false);
            apropoAlert1.setVisible(false);
            noRecAlert.setVisible(false);
            shakeButton();
            return;
        }


            if ( titreValue.length() > 100) {
                maxTitreAlert.setVisible(true);
                TypeAlert.setVisible(false);
                ContAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);
                shakeButton();
                return;
            }

        if (textValue == null|| textValue.isEmpty() ) {
            noRecAlert.setVisible(true);
            TypeAlert.setVisible(false);
            maxTitreAlert.setVisible(false);
            ContAlert.setVisible(false);
            apropoAlert1.setVisible(false);
            noTitreAlert1.setVisible(false);
            shakeButton();
            return;
        }

            if ( textValue.length() > 1000) {
                ContAlert.setVisible(true);
                TypeAlert.setVisible(false);
                maxTitreAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);
                shakeButton();

                return;
            }
            int idu = 1;


        String selectedApropo;

        if ("Autre".equals(apropoValue)) {
            selectedApropo = apropo.getValue();
        } else if ("Lieux".equals(apropoValue)) {
            selectedApropo = page.getText();
        } else {
            selectedApropo = apropoValue;
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setIdu(idu);
        reclamation.setTemp(currentTimestamp);
        reclamation.setTitre(titreValue);
        reclamation.setContenu(textValue);
        reclamation.setType(typeRecValue);
        reclamation.setApropo(apropoValue);


            try {
                rs.ajouter(reclamation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    private void shakeButton() {
        TranslateTransition shakeTransition = new TranslateTransition(Duration.seconds(0.05), buttonAjout);
        shakeTransition.setFromX(0);
        shakeTransition.setToX(10);
        shakeTransition.setCycleCount(6);
        shakeTransition.setAutoReverse(true);

        shakeTransition.play();
    }


    @FXML
    void AfficherListe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheReclamationU.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    public void modifierRec(int idR) {
        modif_button.setVisible(true);
        try {
            Reclamation reclamation = rs.afficherR(idR);
            titre.setText(reclamation.getTitre());
            text.setText(reclamation.getContenu());
            type_rec.setValue(reclamation.getType());
            apropo.setValue(reclamation.getApropo());
            idRModif.setText(String.valueOf(idR));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Modifier(ActionEvent event) throws IOException {
        try {
            int idR = Integer.parseInt(idRModif.getText());
            String titreValue = titre.getText();
            String textValue = text.getText();
            String typeRecValue = type_rec.getValue();
            String apropoValue = apropo.getValue();

            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(idR);
            reclamation.setIdu(1);
            reclamation.setTemp(currentTimestamp);
            reclamation.setTitre(titreValue);
            reclamation.setContenu(textValue);
            reclamation.setType(typeRecValue);
            reclamation.setApropo(apropoValue);

            rs.modifier(reclamation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheReclamationU.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
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
