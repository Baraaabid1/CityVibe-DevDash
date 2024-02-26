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
        String motDePasse = mdpU.getText();
        int idUser = -1; // Initialiser l'ID de l'utilisateur à -1 par défaut

        try {
            String query = "SELECT idu FROM utilisateur WHERE password = ? AND email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, motDePasse);
                statement.setString(2, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        idUser = resultSet.getInt("idu");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion à la base de données
            showAlert("Une erreur s'est produite lors de la connexion à la base de données.");
            return; // Quitter la méthode si une exception se produit
        }

        // Vérification des informations d'identification dans la base de données
        if (idUser != -1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            root = loader.load();
            ProfileController profil = loader.getController();
            profil.initialize (idUser);
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
