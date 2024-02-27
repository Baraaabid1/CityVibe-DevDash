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
    private VBox avisBox; // Assurez-vous que cet attribut est défini dans votre contrôleur

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

            // Effacez la liste existante avant d'ajouter la nouvelle liste
            avisListView.getItems().clear();

            // Ajoutez la nouvelle liste observable mise à jour
            avisListView.getItems().addAll(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception (affichez une boîte de dialogue, un message d'erreur, etc.)
        }
    }

    @FXML
    void onSoumettreAvisClicked(ActionEvent event) {
        String avis = avisTextField.getText();
        int note;

        try {
            // Convertissez la note en entier (vous pouvez ajouter une validation supplémentaire ici)
            note = Integer.parseInt(noteTextField.getText());

            // Obtenez l'ID du transport à partir de la liste ou d'un autre moyen
            Transport selectedTransport = getSelectedTransport();
            if (selectedTransport != null) {
                int idTransport = selectedTransport.getIdT(); // Assurez-vous d'utiliser la méthode correcte

                // Appelez la méthode de service pour ajouter l'avis
                transportService.ajouterAvisTransport(idTransport, avis, note);

                // Rafraîchissez la liste des transports après l'ajout d'avis
                onRechercherClicked(event);

                // Effacez les champs du formulaire après l'ajout
                avisTextField.clear();
                noteTextField.clear();
            } else {
                // Aucun transport sélectionné, vous pouvez gérer cela (par exemple, afficher un message d'erreur)
                System.out.println("Aucun transport sélectionné.");
            }

        } catch (NumberFormatException e) {
            // Gérez l'exception si la conversion de la note en entier échoue
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception (affichez une boîte de dialogue, un message d'erreur, etc.)
        }
    }

    // Ajoutez cette méthode pour obtenir le transport sélectionné de la liste
    private Transport getSelectedTransport() throws SQLException {
        int selectedIndex = avisListView.getSelectionModel().getSelectedIndex();
        // Obtenez le transport correspondant à l'index sélectionné
        // Assurez-vous d'avoir une liste de transports correspondante à la liste d'avis
        List<Transport> transports = transportService.getTransportsWithAvis(stationDepartField.getText(), stationArriveeField.getText());
        return transports.get(selectedIndex);
    }






}
