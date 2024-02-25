package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ListeReclamationsController {

    @FXML
    private TableView<Reclamation> table;
    @FXML
    private TableColumn<Reclamation, Integer> idR;

    @FXML
    private TableColumn<Reclamation, Integer> idU;

    @FXML
    private TableColumn<Reclamation, Timestamp> temp;

    @FXML
    private TableColumn<Reclamation, String> obj;

    @FXML
    private TableColumn<Reclamation, String> local;

    @FXML
    private TableColumn<Reclamation, String> text;

    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    @FXML
    void AjoutRec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ModifRec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
        root = loader.load();
        ModifierReclamationController modifierReclamationController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SuppRec(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerReclamation.fxml"));
        root = loader.load();
        SupprimerReclamationController supprimerReclamationController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ReclamationService rs = new ReclamationService();

    public void initialize(){

    idR.setCellValueFactory(new PropertyValueFactory<>("idR"));
    idU.setCellValueFactory(new PropertyValueFactory<>("idu"));
    temp.setCellValueFactory(new PropertyValueFactory<>("temp"));
    obj.setCellValueFactory(new PropertyValueFactory<>("objet"));
    local.setCellValueFactory(new PropertyValueFactory<>("localisation"));
    text.setCellValueFactory(new PropertyValueFactory<>("contenue"));

    try {
        List<Reclamation> reclamations = rs.afficher();
        table.getItems().addAll(reclamations);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

}



