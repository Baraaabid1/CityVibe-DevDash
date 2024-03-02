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
import java.util.Optional;
import java.util.regex.Pattern;

public class LoginController {
    private Stage stage;
    private Parent root;
    @FXML
    private TextField emailU;

    @FXML
    private PasswordField mdpU;
    @FXML
    private Label erreur;
    UtilisateurService us = new UtilisateurService();
    private Connection connection;

    public LoginController() {
        connection = MyDataBase.getInstance().getConn();
    }

    @FXML
    void LoginU(ActionEvent event) throws IOException, SQLException {
        String email = emailU.getText();
        String motDePasse = hashPassword(mdpU.getText());
        int idUser = -1;
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
        // boîte de dialogue pour saisir l'adresse e-mail
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mot de passe oublié");
        dialog.setHeaderText("Veuillez entrer votre adresse e-mail pour récupérer votre mot de passe");
        dialog.setContentText("Adresse e-mail:");
        Optional<String> result = dialog.showAndWait();

        // Traiter la saisie de l'utilisateur
        result.ifPresent(email -> {
            try {
                if (isValidEmail(email)) {
                    String newPassword = generateRandomPassword();
                    updatePassword(email, newPassword);
                    String title ="Réinitialisation de votre mot de passe";
                    String contenu ="Cher utilisateur " +
                            "Votre mot de passe a été réinitialisé avec succès. Voici votre nouveau mot de passe : "
                            + newPassword+
                            "\n" +
                            "Nous vous recommandons de vous connecter à votre compte et de modifier ce mot de passe dès que possible pour des raisons de sécurité.\n" +
                            "\n" +
                            "Cordialement,";

                    // Envoyer le nouveau mot de passe par e-mail
                    us.sendEmail(email, newPassword, title, contenu);
                    showAlert("Un nouveau mot de passe a été envoyé à votre adresse e-mail.");
                } else {
                    showAlert("Adresse e-mail invalide. Veuillez vérifier votre saisie.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Une erreur s'est produite lors de la récupération du mot de passe.");
            }
        });
    }

    // Méthode pour générer mot de passe aléatoire
    private String generateRandomPassword() {
        // Longueur du mot de passe souhaitée
        int length = 10;

        // Caractères autorisés pour le mot de passe
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";

        // un objet StringBuilder cree une chaine non modifiable
        StringBuilder password = new StringBuilder();

        // Générer le mot de passe en ajoutant des caractères aléatoires à partir de allowedChars
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * allowedChars.length());
            // Ajouter le caractère correspondant à l'index aléatoire au mot de passe
            password.append(allowedChars.charAt(randomIndex));
        }
        return password.toString();
    }

    private void updatePassword(String email, String newPassword) throws SQLException {
        String hashedNewPassword = hashPassword(newPassword);

        try {
            String updateQuery = "UPDATE utilisateur SET password = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, hashedNewPassword); // Nouveau mot de passe hashé
                statement.setString(2, email); // Adresse e-mail de l'utilisateur
                statement.executeUpdate();
            }
            System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur avec l'adresse e-mail : " + email);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du mot de passe pour l'utilisateur avec l'adresse e-mail " + email + " : " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        // regex fournissent un moyen puissant de rechercher, manipuler et valider des chaînes de caractères complexes
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
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
