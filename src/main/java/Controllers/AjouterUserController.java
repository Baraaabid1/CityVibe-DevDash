package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Utilisateur;
import services.UtilisateurService;

import javax.print.DocFlavor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.SelectionMode;
import utiles.MyDataBase;

public class AjouterUserController {
    private Stage stage ;
    private Parent root ;

    UtilisateurService ps = new UtilisateurService();
    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField emailU;

    @FXML
    private DatePicker dateNaissanceU;

    @FXML
    private TextField numTelU;

    @FXML
    private PasswordField passwordU;
    @FXML
    private ChoiceBox<String> prefrenceBox;

    @FXML
    private ChoiceBox<String> locationBox;
    private String[] prefer= {"Art","Sport","Bien etre","Aventure"};

    private String[] location= {"Ariana Soghra"};
    private Connection connection;
    public AjouterUserController() {
        connection= MyDataBase.getInstance().getConn();
    }

    public void initialize(){
        prefrenceBox.getItems().addAll(prefer);
        locationBox.getItems().addAll(location);
    }

    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        try {
            // Ajout de l'utilisateur
            ps.ajouter(new Utilisateur(
                    Integer.parseInt(numTelU.getText()),
                    nomU.getText(),
                    prenomU.getText(),
                    passwordU.getText(),
                    emailU.getText(),
                    prefrenceBox.getValue(),
                    locationBox.getValue(),
                    dateNaissanceU.getValue())
            );

            // Récupération de l'ID de l'utilisateur ajouté
            int idUser = ps.getIdUtilisateurByEmail(emailU.getText());

            // Affichage de la page de profil avec l'ID de l'utilisateur ajouté
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            ProfileController Prof = loader.getController();
            Prof.initialize(idUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermeture de la fenêtre actuelle
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite lors de l'ajout de l'utilisateur.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

}}
