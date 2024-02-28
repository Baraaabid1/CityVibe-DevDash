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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Utilisateur;
import services.UtilisateurService;
import utiles.MyDataBase;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class ProfileController {
    private Stage stage ;
    private Parent root ;
    @FXML
    private Button modifImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label birthLabel;
    @FXML
    private ImageView imgUser;
    private int idUser;

    @FXML
    private ListView<String> prefList;
    UtilisateurService us = new UtilisateurService();

    @FXML
    private Circle circle;


    public Connection connection;

    public void initialize(int id) throws SQLException {
        connection= MyDataBase.getInstance().getConn();

        idUser = id;
        try {
            // Récupérer les informations de l'utilisateur
            String query = "SELECT * FROM utilisateur WHERE idu = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idUser);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Récupérer les informations de l'utilisateur
                        String nom = resultSet.getString("nom");
                        String prenom = resultSet.getString("prenom");
                        String email = resultSet.getString("email");
                        String adresse = resultSet.getString("localisation");
                        String tel = resultSet.getString("num_tel");
                        LocalDate birth = resultSet.getDate("dateNaissance").toLocalDate();

                        // Afficher les informations dans les labels
                        nameLabel.setText(nom + " " + prenom);
                        emailLabel.setText(email);
                        adresseLabel.setText(adresse);
                        telLabel.setText(tel);
                        birthLabel.setText(birth.toString());
                        System.out.println("rrrrrrrr"+nom);

                        // Récupérer et afficher l'image de l'utilisateur
                        byte[] imageData = resultSet.getBytes("img");
                        if (imageData != null) {
                            // Convertir les données binaires en un objet Image
                            Image image = new Image(new ByteArrayInputStream(imageData));

                            // Afficher l'image dans l'ImageView
                            imgUser.setImage(image);
                        } else {
                            System.err.println("Aucune donnée d'image trouvée pour l'utilisateur ID: " + idUser);
                        }
                    } else {
                        System.err.println("Utilisateur introuvable pour l'ID: " + idUser);
                    }
                }
            }

            // Récupérer les préférences de l'utilisateur
            String prefQuery = "SELECT types FROM preferences WHERE idu=?";
            try (PreparedStatement prefStatement = connection.prepareStatement(prefQuery)) {
                prefStatement.setInt(1, idUser);
                try (ResultSet prefResultSet = prefStatement.executeQuery()) {
                    // Créer une liste pour stocker les préférences
                    ObservableList<String> preferences = FXCollections.observableArrayList();
                    // Ajouter les préférences à la liste
                    while (prefResultSet.next()) {
                        String preference = prefResultSet.getString("types");
                        preferences.add(preference);
                    }
                    // Afficher les préférences dans la ListView
                    prefList.setItems(preferences);
                    prefList.setCellFactory(param -> new ListCell<String>() {
                        private final ImageView imageView = new ImageView(); // Créer une ImageView pour chaque cellule

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                                setPrefHeight(0);
                            } else {
                                // Définir le texte de la cellule comme l'élément de la liste
                                setText(item);
                                // Ajouter une image à gauche de chaque élément de la liste
                                ImageView imageView = new ImageView();
                                switch (item) {
                                    case "Art":
                                        imageView.setImage(new Image("img/art.png"));
                                        break;
                                    case "Sport":
                                        imageView.setImage(new Image("img/sport.png"));
                                        break;
                                    case "Bien_Etre":
                                        imageView.setImage(new Image("img/bien_etre.png"));
                                        break;
                                    case "Dance":
                                        imageView.setImage(new Image("img/dance.png"));
                                        break;
                                    case "Education":
                                        imageView.setImage(new Image("img/education.png"));
                                        break;
                                    case "Lecture":
                                        imageView.setImage(new Image("img/lecture.png"));
                                        break;
                                    case "Musique":
                                        imageView.setImage(new Image("img/music.png"));
                                        break;
                                    case "Santé":
                                        imageView.setImage(new Image("img/icons8-home-26.png"));
                                        break;
                                    default:
                                        // Définir une image par défaut si nécessaire
                                        imageView.setImage(new Image("img/icons8-home-26.png"));
                                }
                                int numItems = prefList.getItems().size();
                                double prefHeight =numItems * 65; // 25 est la hauteur approximative de chaque élément
                                prefList.setPrefHeight(prefHeight);
                                setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                                setGraphic(imageView);
                            }
                        }
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void modifImage(ActionEvent event) {
        // Créez un FileChooser pour permettre à l'utilisateur de sélectionner une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtre pour ne montrer que les fichiers d'image
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Affichez la boîte de dialogue pour choisir un fichier
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Convertir le fichier en tableau de bytes
                byte[] imageBytes = convertFileToBytes(selectedFile);

                // Mettre à jour l'image de l'utilisateur dans la base de données
                updateUserImage(idUser, imageBytes);

                // Mettre à jour l'ImageView avec la nouvelle image
                imgUser.setImage(new Image(new ByteArrayInputStream(imageBytes)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour convertir un fichier en tableau de bytes
    private byte[] convertFileToBytes(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); // Lire le fichier en bytes
        fis.close();
        return bytesArray;
    }

    // Méthode pour mettre à jour l'image de l'utilisateur dans la base de données
    private void updateUserImage(int userId, byte[] imageBytes) {
        String query = "UPDATE utilisateur SET img = ? WHERE idu = ?"; // Corrected column name and WHERE condition

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBytes(1, imageBytes);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifProfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
        root = loader.load();
        ModifierUserController modif = loader.getController();
        modif.initialize(idUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void changerMDP(ActionEvent event) {
        // Créez une boîte de dialogue modale pour saisir les anciens et nouveaux mots de passe
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Changer de mot de passe");
        dialog.setHeaderText("Entrez votre ancien et nouveau mot de passe");

        // Créez un bouton pour valider les saisies
        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        // Créez des champs de texte pour les mots de passe
        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Ancien mot de passe");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Nouveau mot de passe");

        // Ajoutez les champs de texte à la boîte de dialogue
        GridPane grid = new GridPane();
        grid.add(new Label("Ancien mot de passe:"), 0, 0);
        grid.add(oldPasswordField, 1, 0);
        grid.add(new Label("Nouveau mot de passe:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        // Rendez le bouton de validation désactivé par défaut
        Node validerButton = dialog.getDialogPane().lookupButton(validerButtonType);
        validerButton.setDisable(true);

        // Validez les saisies lorsque les champs de texte ne sont pas vides
        oldPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validerButton.setDisable(newValue.trim().isEmpty() || newPasswordField.getText().trim().isEmpty());
        });
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validerButton.setDisable(oldPasswordField.getText().trim().isEmpty() || newValue.trim().isEmpty());
        });

        // Convertissez les résultats des champs de texte en une paire de mots de passe
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validerButtonType) {
                return new Pair<>(oldPasswordField.getText(), newPasswordField.getText());
            }
            return null;
        });

        // Affichez la boîte de dialogue et attendez les saisies
        Optional<Pair<String, String>> result = dialog.showAndWait();

        // Traitez les résultats si présents
        result.ifPresent(oldAndNewPassword -> {
            String oldPassword = oldAndNewPassword.getKey();
            String newPassword = oldAndNewPassword.getValue();

            // Vérifiez si l'ancien mot de passe est correct
            if (isOldPasswordCorrect(oldPassword)) {
                // Mettez à jour le mot de passe dans la base de données avec le nouveau mot de passe
                String hashedNewPassword = hashPassword(newPassword);
                try {
                    // Exécuter une requête SQL pour mettre à jour le mot de passe dans la base de données
                    String updateQuery = "UPDATE utilisateur SET password = ? WHERE idu = ?";
                    try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                        statement.setString(1, hashedNewPassword); // Nouveau mot de passe hashé
                        statement.setInt(2, idUser); // Remplacez 'utilisateur.getId()' par l'ID de l'utilisateur actuellement connecté
                        statement.executeUpdate();
                        System.out.println("Mot de passe mis à jour avec succès.");
                    }
                    // Afficher un message de succès ou effectuer d'autres actions nécessaires
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer les exceptions ou afficher un message d'erreur si la mise à jour échoue
                    System.err.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
                }
            } else {
                // Affichez un message d'erreur si l'ancien mot de passe est incorrect
                showAlert("Ancien mot de passe incorrect.");
            }
        });
    }



    private boolean isOldPasswordCorrect(String oldPassword) {
        try {
            // Récupérer le mot de passe actuel de l'utilisateur depuis la base de données
            String query = "SELECT password FROM utilisateur WHERE idu = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idUser); // Remplacez 'idUser' par l'ID de l'utilisateur actuellement connecté
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Récupérer le mot de passe hashé de la base de données
                        String hashedPasswordFromDB = resultSet.getString("password");

                        // Hasher l'ancien mot de passe entré par l'utilisateur
                        String hashedOldPassword = hashPassword(oldPassword);

                        // Comparer les deux hashages des mots de passe
                        return hashedPasswordFromDB.equals(hashedOldPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Si une exception se produit ou si aucun résultat n'est trouvé, renvoyer false
        return false;
    }


    private void showAlert(String message) {
        // Affichez une boîte de dialogue d'alerte avec le message spécifié
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    void SupprimeCompte(ActionEvent event) throws IOException {
        try {

            us.supprimer(idUser);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        root = loader.load();
        LoginController Log = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void Button_Acceuil(ActionEvent actionEvent) {
    }

    public void Button_Events(ActionEvent actionEvent) {
    }

    public void Button_Lieux(ActionEvent actionEvent) {
    }

    public void Button_Transport(ActionEvent actionEvent) {
    }

    public void Button_Reservation(ActionEvent actionEvent) {
    }

    public void Button_Profil(ActionEvent actionEvent) {
    }

    public void Button_Reclamation(ActionEvent actionEvent) {
    }

    public void Button_Parametres(ActionEvent actionEvent) {
    }

    public void Button_Logout(ActionEvent actionEvent) {
    }

    public void Button_Help(ActionEvent actionEvent) {
    }

    public void EcoModeButton(ActionEvent actionEvent) {
    }

}
