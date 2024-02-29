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
        nouvelleStationDepartTextField.setText(transport.getStation_depart());
        nouvelleStationArriveeTextField.setText(transport.getStation_arrive());
    }

    @FXML
    void validerModification (javafx.event.ActionEvent actionEvent) {
        String nouvelleStationDepart = nouvelleStationDepartTextField.getText();
        String nouvelleStationArrivee = nouvelleStationArriveeTextField.getText();


        transportAModifier.setStation_depart(nouvelleStationDepart);
        transportAModifier.setStation_arrive(nouvelleStationArrivee);


        try {
            TransportService transportService = new TransportService();
            transportService.modifier(transportAModifier);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
