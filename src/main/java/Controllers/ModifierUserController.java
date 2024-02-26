package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Utilisateur;
import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifierUserController {
    private Stage stage ;
    private Scene scene ;
    private Parent root ;



    @FXML
    private TextField nomField;


    @FXML
    private TextField idField;


    @FXML
    private TextField prenomField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField naissanceField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telField;

    @FXML
    private TextField preferenceField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField roleField;
    public Utilisateur utilisateur;

    UtilisateurService us = new UtilisateurService();

    @FXML
    void lireUser(ActionEvent event) {
        try {
            int userId = Integer.parseInt(idField.getText());
            utilisateur = us.getUtilisateurById(userId);
            if (utilisateur != null) {
                nomField.setText(utilisateur.getNom());
                prenomField.setText(utilisateur.getPrenom());
                passwordField.setText(utilisateur.getPassword());
                naissanceField.setText(String.valueOf(utilisateur.getDateNaissance()));
                emailField.setText(utilisateur.getEmail());
                telField.setText(String.valueOf(utilisateur.getNum_tel()));
                preferenceField.setText(utilisateur.getPreference());
                locationField.setText(utilisateur.getLocalisation());
                roleField.setText(utilisateur.getRole());
            } else {
                nomField.setText("");
                showErrorAlert("Aucun utilisateur trouvé pour cet ID.");
            }
        } catch (NumberFormatException e) {
            nomField.setText("");
            showErrorAlert("Veuillez entrer un ID valide.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors de la récupération des informations de l'utilisateur.");
        }
    }

    @FXML
    void modifierUser(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(idField.getText());
            int tel = Integer.parseInt(telField.getText());
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String preference = preferenceField.getText();
            String location = locationField.getText();
            String role = roleField.getText();
            String dateNaissanceStr = naissanceField.getText();
            System.out.println("Date de naissance en texte : " + dateNaissanceStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr, formatter);


            // Afficher la date de naissance convertie
            System.out.println("Date de naissance convertie : " + dateNaissance);


            // Modify user using UtilisateurService

            us.modifier(new Utilisateur(id, tel, nom, prenom, password, email, preference, location, dateNaissance, role));
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors de la mise à jour des informations de l'utilisateur.");
        } catch (NumberFormatException e) {
            showErrorAlert("Veuillez entrer des valeurs numériques valides.");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
        root = loader.load();
        AfficherUserController Affuser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
