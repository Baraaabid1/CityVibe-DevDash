package Controllers;

import API.EmailService;
import API.WeatherAPI;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Reclamation;
import org.json.simple.JSONObject;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.MessagingException;


public class AjoutReclamationController {

    @FXML
    private TextField seachbar;

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
    private TextField evenement;


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
    public  int idu = 1;
    public String Localisation = "Aryanah";




    ReclamationService rs = new ReclamationService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

    public void initialize() {
        setWeather();
        idRModif.setVisible(false);
        TypeAlert.setVisible(false);
        maxTitreAlert.setVisible(false);
        ContAlert.setVisible(false);
        apropoAlert1.setVisible(false);
        noTitreAlert1.setVisible(false);
        noRecAlert.setVisible(false);
        ObservableList<String> weatherLocation = FXCollections.observableArrayList(
                "Nabeul",
                "Aryanah",
                "Mahdia"
        );
        weatherLoc.setItems(weatherLocation);
        ObservableList<String> typeReclamation = FXCollections.observableArrayList(
                "Réclamation Urgente",
                "Bugs ou plantages",
                "Contenu inapproprié",
                "Informations incorrectes",
                "Problèmes de sécurité",
                "Suggestions d amélioration",
                "Problèmes de service client"
        );
        type_rec.setItems(typeReclamation);
        ObservableList<String> Reclamationapropo = FXCollections.observableArrayList(
                "Page",
                "Evenement",
                "Autre"
        );
        apropo.setItems(Reclamationapropo);
        evenement.setVisible(false);
        page.setVisible(false);
        apropo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Page":
                        page.setVisible(true);
                        evenement.setVisible(false);
                        break;
                    case "Evenement":
                        page.setVisible(false);
                        evenement.setVisible(true);
                        break;
                    default:
                        page.setVisible(false);
                        evenement.setVisible(false);
                }
            }
        });

    }

    @FXML
    void changeWeatherLoc(ActionEvent event) {
        Localisation=weatherLoc.getValue();
        setWeather();



    }
    @FXML
    void ajouterReclamation(ActionEvent event) {

            String titreValue = titre.getText();
            String textValue = text.getText();
            String typeRecValue = type_rec.getValue();
             String apropoValue = apropo.getValue();

        String selectedApropo;

        String aprovoValue = apropo.getValue();
        if ("Autre".equals(aprovoValue)) {
            selectedApropo = apropo.getValue();
        } else if ("Page".equals(aprovoValue)) {
            selectedApropo = "P_" + page.getText();
        } else {
            selectedApropo = "E_" + evenement.getText();
        }

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
        if (selectedApropo == null || selectedApropo.isEmpty()) {
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




        Reclamation reclamation = new Reclamation();
        reclamation.setIdu(idu);
        reclamation.setTemp(currentTimestamp);
        reclamation.setTitre(titreValue);
        reclamation.setContenu(textValue);
        reclamation.setType(typeRecValue);
        reclamation.setApropo(selectedApropo);


            try {
                rs.ajouter(reclamation);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationAjoutee.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                titre.clear();
                text.clear();
                type_rec.getSelectionModel().clearSelection();
                apropo.getSelectionModel().clearSelection();
                page.clear();
                evenement.clear();




            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        if (reclamation.getType().equals("Réclamation Urgente")) {

        String senderEmail = "abidbaraa33@gmail.com";
        String senderPassword = "xzgr oscn mrei dpxc";
        String toAddress = "baraaabid33@gmail.com";
        String subject = "Notification de réclamation urgente déposée ";
        String content = "Nous vous informons qu'une réclamation urgente a été déposée. Votre attention immédiate est requise.\n" +
                "\n" +
                "Merci de prendre les mesures nécessaires pour résoudre ce problème dans les plus brefs délais.\n" +
                "\n" +
                "Cordialement,";

        EmailService emailService = new EmailService(senderEmail, senderPassword);

        try {
            emailService.sendEmail(toAddress, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }}
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
        buttonAjout.setVisible(false);


        try {
            Reclamation reclamation = rs.afficherR(idR);
            titre.setText(reclamation.getTitre());
            text.setText(reclamation.getContenu());
            type_rec.setValue(reclamation.getType());
            if (reclamation.getApropo().equals("Autre")) {
                apropo.setValue(reclamation.getApropo());
            }
            idRModif.setText(String.valueOf(idR));
        } catch (SQLException e) {
            e.printStackTrace();
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

    //click boutton modifier
    @FXML
    void Modifier(ActionEvent event) throws IOException {
        try {
            int idR = Integer.parseInt(idRModif.getText());
            String titreValue = titre.getText();
            String textValue = text.getText();
            String typeRecValue = type_rec.getValue();
            String selectedApropo;

            String aprovoValue = apropo.getValue();
            if ("Autre".equals(aprovoValue)) {
                selectedApropo = apropo.getValue();
            } else if ("Page".equals(aprovoValue)) {
                selectedApropo = "P_" + page.getText();
            } else {
                selectedApropo = "E_" + evenement.getText();
            }

            if (typeRecValue == null|| typeRecValue.isEmpty()) {
                TypeAlert.setVisible(true);
                maxTitreAlert.setVisible(false);
                ContAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);

                return;
            }
            if (selectedApropo == null || selectedApropo.isEmpty()) {
                apropoAlert1.setVisible(true);
                TypeAlert.setVisible(false);
                maxTitreAlert.setVisible(false);
                ContAlert.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);

                return;
            }
            if (titreValue == null|| titreValue.isEmpty() ) {
                noTitreAlert1.setVisible(true);
                TypeAlert.setVisible(false);
                maxTitreAlert.setVisible(false);
                ContAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noRecAlert.setVisible(false);

                return;
            }


            if ( titreValue.length() > 100) {
                maxTitreAlert.setVisible(true);
                TypeAlert.setVisible(false);
                ContAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);
                return;
            }

            if (textValue == null|| textValue.isEmpty() ) {
                noRecAlert.setVisible(true);
                TypeAlert.setVisible(false);
                maxTitreAlert.setVisible(false);
                ContAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                return;
            }

            if ( textValue.length() > 1000) {
                ContAlert.setVisible(true);
                TypeAlert.setVisible(false);
                maxTitreAlert.setVisible(false);
                apropoAlert1.setVisible(false);
                noTitreAlert1.setVisible(false);
                noRecAlert.setVisible(false);

                return;
            }




            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(idR);
            reclamation.setIdu(1);
            reclamation.setTemp(currentTimestamp);
            reclamation.setTitre(titreValue);
            reclamation.setContenu(textValue);
            reclamation.setType(typeRecValue);
            reclamation.setApropo(selectedApropo);

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
