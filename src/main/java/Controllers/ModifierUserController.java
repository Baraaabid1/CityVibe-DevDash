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
import utiles.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;

public class ModifierUserController {
    private Stage stage ;
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
    private TextField locationField;

    @FXML
    private TextField roleField;
    public Utilisateur utilisateur;
    private Connection connection;

    UtilisateurService us = new UtilisateurService();
    public ModifierUserController() {
        connection= MyDataBase.getInstance().getConn();
    }
    public void initialize(int idUser){
        try {

            utilisateur = us.getUtilisateurById(idUser);
            if (utilisateur != null) {
                nomField.setText(utilisateur.getNom());
                prenomField.setText(utilisateur.getPrenom());
                naissanceField.setText(String.valueOf(utilisateur.getDateNaissance()));
                emailField.setText(utilisateur.getEmail());
                telField.setText(String.valueOf(utilisateur.getNum_tel()));
                locationField.setText(utilisateur.getLocalisation());
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
    void modifierUser(ActionEvent event) throws IOException, SQLException {

        int id = 0;
        try {
            int tel = Integer.parseInt(telField.getText());
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String location = locationField.getText();
            String dateNaissanceStr = naissanceField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateNaissance = parse(dateNaissanceStr, formatter);

            id = 0;
            String query = "SELECT idu FROM utilisateur WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt("idu");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(id);

            us.modifier(new Utilisateur(id, tel, nom, prenom, email, location, dateNaissance));
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors de la mise à jour des informations de l'utilisateur.");
        } catch (NumberFormatException e) {
            showErrorAlert("Veuillez entrer des valeurs numériques valides.");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        root = loader.load();
        ProfileController profileController = loader.getController();
        if (profileController != null) {
            // Actualiser les données du profil
            profileController.initialize(id);
            // Fermer la fenêtre de modification0
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            System.err.println("Le contrôleur de profil est null.");
        }
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void cancel(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        root = loader.load();
        ProfileController pro = loader.getController();
        pro.initialize(utilisateur.getIdu());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
