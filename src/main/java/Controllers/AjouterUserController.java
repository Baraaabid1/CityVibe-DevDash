package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import utiles.MyDataBase;

import javax.mail.MessagingException;

public class AjouterUserController {

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
    private ChoiceBox<String> locationBox;
    private String[] location = {"Ariana Soghra"};
    private Connection connection;
    public ObservableList<String> preference = FXCollections.observableArrayList();

    public AjouterUserController() {
        connection = MyDataBase.getInstance().getConn();
    }

    public void initialize() {
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
                    locationBox.getValue(),
                    dateNaissanceU.getValue())
            );
            // Récupération de l'ID de l'utilisateur ajouté
            int idUser = ps.getIdUtilisateurByEmail(emailU.getText());
            System.out.println(preference);

            // Insertion des préférences dans la table preferences
            if (preference != null) {
                for (String preference : preference) {
                    String insertQuery = "INSERT INTO preferences (idu, types) VALUES (?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                        pstmt.setInt(1, idUser);
                        pstmt.setString(2, preference);
                        pstmt.executeUpdate();
                    }
                }
            }
            if(idUser!=-1) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite lors de l'ajout de l'utilisateur.");
        } catch (MessagingException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite lors de l'envoi de l'e-mail de confirmation.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void testPref(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Preferences.fxml"));
                    Parent root = loader.load();

                    PreferencesController prefController = loader.getController();
                    ObservableList<String> preferences = prefController.getPreferences(); // Utilisez une variable locale différente

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                    // Après la fermeture de la fenêtre de préférences, vous pouvez affecter la liste de préférences à la liste de classe
                    if (preferences != null) {
                        preference = preferences;
                        System.out.println("Liste de préférences récupérée avec succès : " + preference);
                    } else {
                        System.out.println("La liste de préférences est null.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


