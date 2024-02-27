package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UtilisateurService;
import utiles.MyDataBase;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class LoginController {
    private Stage stage ;
    private Parent root ;
    @FXML
    private TextField emailU;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField mdpU;
    @FXML
    private Label erreur;
    UtilisateurService us = new UtilisateurService();
    private Connection connection;
    public LoginController() {
        connection= MyDataBase.getInstance().getConn();
    }
    @FXML
    void LoginU(ActionEvent event) throws IOException {
        String email = emailU.getText();
        String motDePasse = hashPassword(mdpU.getText()); // Hashage du mot de passe entré par l'utilisateur
        int idUser = -1; // Initialiser l'ID de l'utilisateur à -1 par défaut

        try {
            String query = "SELECT idu FROM utilisateur WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, motDePasse);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idUser = resultSet.getInt("idu");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite lors de la connexion à la base de données.");
            return;
        }

        // Vérification des informations d'identification dans la base de données
        if (idUser != -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            root = loader.load();
            ProfileController profil = loader.getController();
            profil.initialize(idUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Informations d'identification incorrectes, affichez un message d'erreur
            erreur.setVisible(true);
            emailU.clear();
            mdpU.clear();
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void mdpOublie(ActionEvent event) {

    }




    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void signUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
        root = loader.load();
        AjouterUserController Ajuser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
