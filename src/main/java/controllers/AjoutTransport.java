package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Transport;
import models.typeT;
import services.TransportService;

import javafx.event.ActionEvent; // Assurez-vous d'importer la classe correcte pour ActionEvent
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AjoutTransport {
    private Parent root;
    private Stage stage;

    TransportService TS = new TransportService();
    private TableView<Transport> table;
    @FXML
    private TextField hello;

    @FXML
    private TextField stationArriveField;

    @FXML
    private TextField stationDepartField;

    @FXML
    private TextField tempsArriveField;

    @FXML
    private TextField tempsDepartField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    public void ajouter(javafx.event.ActionEvent event) throws IOException {
        try {
            // Récupérer les valeurs des champs
            String typeTransportStr = typeComboBox.getValue();
            String stationDepart = stationDepartField.getText();
            String stationArrive = stationArriveField.getText();
            String tempsDepartStr = tempsDepartField.getText();
            String tempsArriveStr = tempsArriveField.getText();

            // Effectuer le contrôle de saisie
            if (typeTransportStr == null || stationDepart.isEmpty() || stationArrive.isEmpty() || tempsDepartStr.isEmpty() || tempsArriveStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Arrêter le traitement si les champs ne sont pas correctement remplis
            }

            // Convertir les champs nécessitant une conversion
            typeT transportType = typeT.valueOf(typeTransportStr);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            java.util.Date tempsDepartDate;
            java.util.Date tempsArriveDate;

            try {
                tempsDepartDate = sdf.parse(tempsDepartStr);
                tempsArriveDate = sdf.parse(tempsArriveStr);
            } catch (ParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de format");
                alert.setContentText("Format d'heure invalide. Utilisez le format HH:mm:ss.");
                alert.showAndWait();
                return; // Arrêter le traitement en cas d'erreur de format
            }

            Time tempsDepart = new Time(tempsDepartDate.getTime());
            Time tempsArrive = new Time(tempsArriveDate.getTime());

            // Créer l'objet Transport et l'ajouter à la base de données
            Transport transport = new Transport(transportType, stationDepart, stationArrive, tempsDepart, tempsArrive);
            TS.ajouter(transport);

            // Afficher une notification de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Transport ajouté avec succès !");
            alert.showAndWait();

            // Rediriger vers la liste des transports
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListTransport.fxml"));
            root = loader.load();
            ListTransportController trans = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (SQLException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite : " + e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML
    public void EcoMode(javafx.event.ActionEvent actionEvent) {
        // Ajoutez le code pour activer le mode économie d'énergie ici
    }
}
