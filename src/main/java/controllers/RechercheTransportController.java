package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Transport;
import services.TransportService;

import java.sql.SQLException;
import java.util.List;

public class RechercheTransportController {

    @FXML
    private TextField stationArriveeField;

    @FXML
    private TextField stationDepartField;

    @FXML
    private ListView<String> avisListView;

    private TransportService transportService = new TransportService();
    @FXML
    private TextField avisTextField;
    @FXML
    private TextField noteTextField;
    private String avis;

    @FXML
    private VBox avisBox;
    @FXML
    void onRechercherClicked(ActionEvent event) {
        String stationDepart = stationDepartField.getText();
        String stationArrivee = stationArriveeField.getText();

        try {
            List<Transport> transports = transportService.getTransportsWithAvis(stationDepart, stationArrivee);

            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Transport transport : transports) {
                String avis = transport.getAvis();
                if (avis == null || avis.isEmpty()) {
                    observableList.add(transport.toString() + " - Aucun avis disponible");
                } else {
                    observableList.add(transport.toString() + " - Avis : " + avis);
                }
            }


            avisListView.getItems().clear();


            avisListView.getItems().addAll(observableList);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    @FXML
    void onSoumettreAvisClicked(ActionEvent event) {
        String avis = avisTextField.getText();
        int note;

        try {

            note = Integer.parseInt(noteTextField.getText());
            Transport selectedTransport = getSelectedTransport();
            if (selectedTransport != null) {
                int idTransport = selectedTransport.getIdT();
                transportService.ajouterAvisTransport(idTransport, avis, note);

                onRechercherClicked(event);
                avisTextField.clear();
                noteTextField.clear();
            } else {

                System.out.println("Aucun transport sélectionné.");
            }

        } catch (NumberFormatException e) {

            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    private Transport getSelectedTransport() throws SQLException {
        int selectedIndex = avisListView.getSelectionModel().getSelectedIndex();
        List<Transport> transports = transportService.getTransportsWithAvis(stationDepartField.getText(), stationArriveeField.getText());
        return transports.get(selectedIndex);
    }


}
