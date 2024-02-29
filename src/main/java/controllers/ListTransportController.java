package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Transport;
import models.typeT;
import services.TransportService;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class ListTransportController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private Button ajouter2;
    TransportService TS = new TransportService();

    @FXML
    private TableColumn<Transport, Integer> idtcol;

    @FXML
    private TableColumn<Transport, String> sarivecol;

    @FXML
    private TableColumn<Transport, String> sdepartcol;

    @FXML
    private TableColumn<Transport, Time> tempacol;

    @FXML
    private TableColumn<Transport, Time> tempsdcol;

    @FXML
    private TableColumn<Transport, typeT> typecol;

    @FXML
    private TableView<Transport> table;
    @FXML
    private Button supprimerButton;

    @FXML
    void AjoutT(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutTransport.fxml"));
        Parent root = loader.load();
        AjoutTransport ajoutController = loader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void initialize() {
        try {
            List<Transport> transports = TS.afficher();
            ObservableList<Transport> observableList = FXCollections.observableList(transports);
            table.setItems(observableList);

            idtcol.setCellValueFactory(new PropertyValueFactory<>("idT"));
            typecol.setCellValueFactory(new PropertyValueFactory<>("typeT"));
            sdepartcol.setCellValueFactory(new PropertyValueFactory<>("station_depart"));
            sarivecol.setCellValueFactory(new PropertyValueFactory<>("station_arrive"));


                       //modifier les colonnes temps depart et temps arrivé//
            StringConverter<Time> timeConverter = new StringConverter<Time>() {
                @Override
                public String toString(Time object) {
                    return object.toString();
                }

                @Override
                public Time fromString(String string) {
                    return Time.valueOf(string);
                }
            };

            tempsdcol.setCellValueFactory(new PropertyValueFactory<>("temp_depart"));
            tempsdcol.setCellFactory(TextFieldTableCell.forTableColumn(timeConverter));
            tempsdcol.setOnEditCommit(event -> {
                Transport transport = event.getRowValue();
                transport.setTemp_depart(event.getNewValue());


                try {
                    TS.modifier(transport);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            tempacol.setCellValueFactory(new PropertyValueFactory<>("temp_arrive"));
            tempacol.setCellFactory(TextFieldTableCell.forTableColumn(timeConverter));
            tempacol.setOnEditCommit(event -> {
                Transport transport = event.getRowValue();
                transport.setTemp_arrive(event.getNewValue());

                try {
                    TS.modifier(transport);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


            table.setEditable(true);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void afficherDetailsTransport(int id) {
        try {
            TransportService transportService = new TransportService();
            Transport transportDetails = transportService.getTransportDetails(id);


            if (transportDetails != null) {
                System.out.println("Détails du transport : " + transportDetails);
            } else {
                System.out.println("Transport non trouvé.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierTransport(ActionEvent event) {
        Transport selectedTransport = table.getSelectionModel().getSelectedItem();

        if (selectedTransport != null) {

            afficherBoiteDialogueModification(selectedTransport);
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un transport à modifier.");
            alert.showAndWait();
        }
    }

    private void afficherBoiteDialogueModification(Transport selectedTransport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTransport.fxml"));
            Parent root = loader.load();


            ModifierTransportController modifierController = loader.getController();
            modifierController.initData(selectedTransport);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            List<Transport> transports = TS.afficher();
            ObservableList<Transport> observableList = FXCollections.observableList(transports);
            table.setItems(observableList);
        } catch (IOException | SQLException e) {
            e.printStackTrace();

        }
    }


    @FXML
    void supprimerTransport(ActionEvent event) {
        Transport selectedTransport = table.getSelectionModel().getSelectedItem();

        if (selectedTransport != null) {
            try {
                TS.supprimer(selectedTransport.getIdT());
                actualiserTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un transport à supprimer.");
            alert.showAndWait();
        }
    }

    private void actualiserTableView() {
        try {
            List<Transport> transports = TS.afficher();
            ObservableList<Transport> observableList = FXCollections.observableList(transports);
            table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
