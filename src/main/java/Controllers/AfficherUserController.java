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
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.List;

public class AfficherUserController {
    private Stage stage ;
    private Parent root ;

    @FXML
    private TableView<Utilisateur> tableview;

    @FXML
    private TableColumn<Utilisateur, Integer> id;

    @FXML
    private TableColumn<Utilisateur, String> nom;

    @FXML
    private TableColumn<Utilisateur, String> prenom;

    @FXML
    private TableColumn<Utilisateur, String> password;

    @FXML
    private TableColumn<Utilisateur, LocalDate> dateNaissance;

    @FXML
    private TableColumn<Utilisateur, String> role;

    @FXML
    private TableColumn<Utilisateur, String> email;

    @FXML
    private TableColumn<Utilisateur, Integer> numTel;
    @FXML
    private TableColumn<Utilisateur, String> localisation;
    @FXML
    private TextField chercherField;
    UtilisateurService ps = new UtilisateurService();
    private Connection connection;

    public AfficherUserController(){

        connection= MyDataBase.getInstance().getConn();
    }

    public void initialize() {
        initializeTableView();
        initializeSearchListener();
    }

    private void initializeTableView() {
        try {
            List<Utilisateur> utilisateurs = ps.afficher();
            ObservableList<Utilisateur> observableUtilisateurs = FXCollections.observableArrayList(utilisateurs);
            tableview.setItems(observableUtilisateurs);

            id.setCellValueFactory(new PropertyValueFactory<>("idu"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            dateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            numTel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
            localisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void initializeSearchListener() {
        chercherField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Chercher les utilisateurs dont le nom ou le prénom contient la chaîne saisie
                List<Utilisateur> utilisateurs = chercherParNomOuPrenom(newValue);
                ObservableList<Utilisateur> observableUtilisateurs = FXCollections.observableArrayList(utilisateurs);
                tableview.setItems(observableUtilisateurs);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }
    public List<Utilisateur> chercherParNomOuPrenom(String recherche) throws SQLException {
        List<Utilisateur> utilisateursRecherches = new ArrayList<>();

        try {
            // Préparer la requête SQL
            String query = "SELECT * FROM utilisateur WHERE nom LIKE ? OR prenom LIKE ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Remplacer les ? dans la requête par les valeurs de recherche
                statement.setString(1, recherche + "%");
                statement.setString(2, recherche + "%");

                // Exécuter la requête
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Itérer sur le ResultSet et ajouter les utilisateurs à la liste
                    while (resultSet.next()) {
                        int id = resultSet.getInt("idu");
                        String nom = resultSet.getString("nom");
                        String prenom = resultSet.getString("prenom");
                        String email = resultSet.getString("email");
                        LocalDate dateN = resultSet.getDate("dateNaissance").toLocalDate();
                        String role = resultSet.getString("role");
                        int numm = resultSet.getInt("num_tel");
                        String location = resultSet.getString("localisation");


                        // Créer un nouvel utilisateur avec les données récupérées de la base de données
                        Utilisateur utilisateur = new Utilisateur( id,numm, nom,prenom, email, location, dateN,role);
                        // Ajouter l'utilisateur à la liste des utilisateurs recherchés
                        utilisateursRecherches.add(utilisateur);
                    }
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception SQL
            e.printStackTrace();
        }

        return utilisateursRecherches;
    }

    @FXML
    void supp(ActionEvent event) throws IOException, SQLException {
        // Récupérer l'utilisateur sélectionné dans la tableView
        Utilisateur utilisateurSelectionne = tableview.getSelectionModel().getSelectedItem();

        // Vérifier si un utilisateur est sélectionné
        if (utilisateurSelectionne != null) {
            // Supprimer l'utilisateur
            ps.supprimer(utilisateurSelectionne.getIdu());

            // Mettre à jour la tableView après la suppression
            initializeTableView();

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Utilisateur supprimé");
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a été supprimé avec succès.");
            alert.showAndWait();
        } else {
            // Afficher un message d'erreur si aucun utilisateur n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
        }
    }

}
