package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Transport;
import services.TransportService;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ModifierTransportController {
    @FXML
    private VBox modifierTransportVBox;

    @FXML
    private TextField nouvelleStationDepartTextField;

    @FXML
    private TextField nouvelleStationArriveeTextField;
    @FXML

    private Button validerModification;

    private Transport transportAModifier;

    public void initData(Transport transport) {
        transportAModifier = transport;
        // Pré-remplissez les champs avec les valeurs actuelles du transport
        nouvelleStationDepartTextField.setText(transport.getStation_depart());
        nouvelleStationArriveeTextField.setText(transport.getStation_arrive());
    }

    @FXML
    void validerModification (javafx.event.ActionEvent actionEvent) {
        // Récupérez les nouvelles valeurs des champs
        String nouvelleStationDepart = nouvelleStationDepartTextField.getText();
        String nouvelleStationArrivee = nouvelleStationArriveeTextField.getText();

        // Mettez à jour l'objet Transport avec les nouvelles valeurs
        transportAModifier.setStation_depart(nouvelleStationDepart);
        transportAModifier.setStation_arrive(nouvelleStationArrivee);

        // Mettez à jour la base de données (TransportService) avec les nouvelles valeurs
        try {
            TransportService transportService = new TransportService();
            transportService.modifier(transportAModifier);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de base de données de manière appropriée
        }

        // Fermez la fenêtre de modification
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
