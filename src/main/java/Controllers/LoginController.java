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

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.io.IOException;
import java.net.Authenticator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.Properties;

public class LoginController {
    private Stage stage;
    private Parent root;
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
        connection = MyDataBase.getInstance().getConn();
    }

    @FXML
    void LoginU(ActionEvent event) throws IOException, SQLException {
        String email = emailU.getText();
        String motDePasse = hashPassword(mdpU.getText());
        System.out.println("logtest" + motDePasse);// Hashage du mot de passe entré par l'utilisateur
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
        // Créer une boîte de dialogue modale pour saisir l'adresse e-mail
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mot de passe oublié");
        dialog.setHeaderText("Veuillez entrer votre adresse e-mail pour récupérer votre mot de passe");
        dialog.setContentText("Adresse e-mail:");

        // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur
        Optional<String> result = dialog.showAndWait();

        // Traiter la saisie de l'utilisateur
        result.ifPresent(email -> {
            try {
                // Vérifier si l'adresse e-mail correspond à un utilisateur dans la base de données
                if (isValidEmail(email)) {
                    // Générer un nouveau mot de passe aléatoire
                    String newPassword = generateRandomPassword();

                    // Mettre à jour le mot de passe de l'utilisateur dans la base de données
                    updatePassword(email, newPassword);

                    // Envoyer le nouveau mot de passe à l'utilisateur par e-mail
                    sendPasswordByEmail(email, newPassword);

                    // Afficher un message de succès à l'utilisateur
                    showAlert("Un nouveau mot de passe a été envoyé à votre adresse e-mail.");
                } else {
                    // Si l'adresse e-mail n'est pas valide, afficher un message d'erreur
                    showAlert("Adresse e-mail invalide. Veuillez vérifier votre saisie.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les exceptions liées à la base de données
                showAlert("Une erreur s'est produite lors de la récupération du mot de passe.");
            }
        });
    }

    // Méthode pour générer un nouveau mot de passe aléatoire
    private String generateRandomPassword() {
        // Longueur du mot de passe souhaitée
        int length = 10;

        // Caractères autorisés pour le mot de passe
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";

        // Créer un objet StringBuilder pour construire le mot de passe
        StringBuilder password = new StringBuilder();

        // Générer le mot de passe en ajoutant des caractères aléatoires à partir de allowedChars
        for (int i = 0; i < length; i++) {
            // Générer un index aléatoire pour sélectionner un caractère de allowedChars
            int randomIndex = (int) (Math.random() * allowedChars.length());

            // Ajouter le caractère correspondant à l'index aléatoire au mot de passe
            password.append(allowedChars.charAt(randomIndex));
        }

        // Retourner le mot de passe généré
        return password.toString();
    }


    // Méthode pour mettre à jour le mot de passe de l'utilisateur dans la base de données
    private void updatePassword(String email, String newPassword) throws SQLException {
        // Hasher le nouveau mot de passe
        String hashedNewPassword = hashPassword(newPassword);

        try {
            // Exécuter une requête SQL pour mettre à jour le mot de passe dans la base de données
            String updateQuery = "UPDATE utilisateur SET password = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, hashedNewPassword); // Nouveau mot de passe hashé
                statement.setString(2, email); // Adresse e-mail de l'utilisateur
                statement.executeUpdate();
            }
            // Afficher un message de succès ou effectuer d'autres actions nécessaires
            System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur avec l'adresse e-mail : " + email);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions ou afficher un message d'erreur si la mise à jour échoue
            System.err.println("Erreur lors de la mise à jour du mot de passe pour l'utilisateur avec l'adresse e-mail " + email + " : " + e.getMessage());
        }
    }


    // Méthode pour envoyer le nouveau mot de passe à l'utilisateur par e-mail
    private void sendPasswordByEmail(String email, String newPassword) {
        String from = "bennacefzeyneb@gmail.com";
        String pass = "upty vtmf fddr jctq";
        // Configuration de la session SMTP pour l'envoi d'e-mails
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        // Création d'une nouvelle session SMTP
        Session session = Session.getDefaultInstance(props);
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//               return new PasswordAuthentication("bennacefzeyneb@gmail.com","upty vtmf fddr jctq");
//            }

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Adresse e-mail du destinataire
            message.setSubject("Réinitialisation de votre mot de passe"); // Objet de l'e-mail
            message.setText("Votre nouveau mot de passe est : " + newPassword); // Contenu de l'e-mail

            // Envoi du message
            Transport transport = session.getTransport("smtp");
            transport.connect(host, "bennacefzeyneb@gmail.com", "upty vtmf fddr jctq");
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();

            System.out.println("E-mail envoyé avec succès à " + email + " avec le nouveau mot de passe.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail à " + email + " : " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
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
