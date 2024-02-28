package services;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import models.Utilisateur;
import utiles.MyDataBase;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

import static java.lang.constant.ConstantDescs.NULL;

public class UtilisateurService implements IService <Utilisateur> {
    private Connection connection;

    public UtilisateurService(){
        connection= MyDataBase.getInstance().getConn();
    }
    @Override
    public void ajouter(Utilisateur utilisateur) throws SQLException, IOException, MessagingException {
        if (utilisateur.getNom().isEmpty() || utilisateur.getPrenom().isEmpty() || utilisateur.getLocalisation().isEmpty() || utilisateur.getEmail().isEmpty()) {
            showAlert("Veuillez remplir tous les champs obligatoires.");
        } else if (isEmailExists(utilisateur.getEmail())) {
            showAlert("Cette adresse e-mail est déjà utilisée. Veuillez en choisir une autre.");
        } else if (!isValidEmail(utilisateur.getEmail())) {
            showAlert("Adresse e-mail invalide");
            throw new IllegalArgumentException("Adresse e-mail invalide");
        } else if (!isValidPhoneNumber(utilisateur.getNum_tel())) {
            showAlert("Numéro de téléphone invalide");
            throw new IllegalArgumentException("Numéro de téléphone invalide");
        } else if (utilisateur.getPassword().length() < 8) {
            showAlert("Le mot de passe doit comporter au moins 8 caractères.");
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères.");
        } else {
            String hashedPassword = hashPassword(utilisateur.getPassword());
            LocalDate currentDate = LocalDate.now();
            LocalDate minAllowedDate = currentDate.minusYears(5);
            if (utilisateur.getDateNaissance().isAfter(minAllowedDate)) {
                showAlert("La date de naissance doit être inférieure de 5 ans à la date actuelle.");
                throw new IllegalArgumentException("La date de naissance doit être inférieure de 5 ans à la date actuelle.");
            }


            String confirmationCode = generateConfirmationCode();
            // Générer et envoyer le code de confirmation
            String title ="code de confirmation";
            String contenu ="Cher utilisateur,\n" +
                    "\n" +
                    "Nous vous remercions de votre inscription sur notre application. Veuillez utiliser le code suivant pour confirmer votre compte :\n" +
                    "\n" +
                    "Code de confirmation :" + confirmationCode+
                    "\n" +
                    "Veuillez saisir ce code dans l'application pour finaliser votre inscription.";
            sendEmail(utilisateur.getEmail(), confirmationCode, title, contenu);

            // Demander à l'utilisateur de saisir le code de confirmation
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Confirmation");
            dialog.setHeaderText("Veuillez saisir le code de confirmation envoyé par e-mail :");
            dialog.setContentText("Code de confirmation :");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String userInputCode = result.get().trim();

                // Vérifier si le code de confirmation saisi correspond au code généré
                if (!userInputCode.equals(confirmationCode)) {
                    showAlert("Code de confirmation incorrect. Veuillez vérifier votre e-mail et saisir le code correctement.");
                    return;
                }
            } else {
                showAlert("Aucun code de confirmation saisi. L'opération a été annulée.");
                return;
            }

            // Insérer l'utilisateur dans la base de données si le code de confirmation est correct
            String utilisateurreq = "INSERT INTO utilisateur (idu, nom, prenom, password, dateNaissance, email, num_tel, localisation, img) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
                pstmt.setInt(1, utilisateur.getIdu());
                pstmt.setString(2, utilisateur.getNom());
                pstmt.setString(3, utilisateur.getPrenom());
                pstmt.setString(4, hashedPassword);
                LocalDate dateOfBirth = LocalDate.from(utilisateur.getDateNaissance());
                LocalDateTime dateTimeOfBirth = dateOfBirth.atStartOfDay(); // Start of the day (00:00)
                pstmt.setTimestamp(5, Timestamp.valueOf(dateTimeOfBirth));
                pstmt.setString(6, utilisateur.getEmail());
                pstmt.setInt(7, utilisateur.getNum_tel());
                pstmt.setString(8, utilisateur.getLocalisation());
                byte[] defaultImageBytes = selectAndConvertDefaultImage();
                pstmt.setBytes(9, defaultImageBytes);
                pstmt.executeUpdate();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Utilisateur ajouté avec succès !");
                successAlert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean isEmailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private static final int CODE_LENGTH = 6; // Longueur du code de confirmation
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateConfirmationCode() {
        // Générer un code aléatoire sécurisé
        byte[] randomBytes = new byte[CODE_LENGTH];
        SECURE_RANDOM.nextBytes(randomBytes);

        // Convertir les bytes en une chaîne hexadécimale
        BigInteger bigInteger = new BigInteger(1, randomBytes);
        String confirmationCode = bigInteger.toString(16);

        // Assurer que le code a la longueur spécifiée
        while (confirmationCode.length() < CODE_LENGTH) {
            confirmationCode = "0" + confirmationCode;
        }
        System.out.println("generated code"+confirmationCode);

        return confirmationCode;
    }
    private String hashPassword(String password) {
        try{
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
    public void sendEmail(String email, String newPassword, String title, String contenu) {
        String from = "bennacefzeyneb@gmail.com";
        String pass = "upty vtmf fddr jctq";
        // Configuration de la session SMTP pour l'envoi d'e-mails
        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        // Création d'une nouvelle session SMTP
        Session session = Session.getDefaultInstance(props);

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Adresse e-mail du destinataire
            message.setSubject(title); // Objet de l'e-mail
            message.setText(contenu); // Contenu de l'e-mail

            // Envoi du message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "bennacefzeyneb@gmail.com", "upty vtmf fddr jctq");
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();

            System.out.println("E-mail envoyé avec succès à " + email + " avec le nouveau mot de passe.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail à " + email + " : " + e.getMessage());
        }
    }

    private byte[] selectAndConvertDefaultImage() throws IOException {
        String defaultImagePath = "C:\\Users\\benna\\Desktop\\istockphoto-1337144146-612x612.jpg";
        File defaultImageFile = new File(defaultImagePath);
        return convertFileToBytes(defaultImageFile);
    }

    private byte[] convertFileToBytes(File defaultImageFile) throws IOException {
        FileInputStream fis = null;
        byte[] fileBytes = null;

        try {
            fis = new FileInputStream(defaultImageFile);
            fileBytes = new byte[(int) defaultImageFile.length()];
            fis.read(fileBytes); // Lecture du fichier et stockage dans le tableau de bytes
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                fis.close(); // Fermeture du flux d'entrée
            }
        }

        return fileBytes;

    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    private boolean isValidPhoneNumber(int phoneNumber) {
        // Phone number validation: must be exactly 8 digits
        return String.valueOf(phoneNumber).matches("\\d{8}");
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void modifier(Utilisateur utilisateur) throws SQLException {
        if (!isValidEmail(utilisateur.getEmail())) {
            showAlert("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        } else if (!isValidPhoneNumber(utilisateur.getNum_tel())) {
            showAlert("Invalid phone number");
            throw new IllegalArgumentException("Invalid phone number");
        } else if (!isValidDate(utilisateur.getDateNaissance())) {
            showAlert("Invalid date of birth");
            throw new IllegalArgumentException("Invalid date of birth");
        } else {
            String utilisateurreq = "UPDATE utilisateur SET nom=?, prenom=?, dateNaissance=?, email=?, num_tel=?, localisation=? WHERE idu=?";
            try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
                pstmt.setString(1, utilisateur.getNom());
                pstmt.setString(2, utilisateur.getPrenom());
                LocalDate localDate = LocalDate.from(utilisateur.getDateNaissance());
                pstmt.setDate(3, java.sql.Date.valueOf(utilisateur.getDateNaissance()));
                pstmt.setString(4, utilisateur.getEmail());
                pstmt.setInt(5, utilisateur.getNum_tel());
                pstmt.setString(6, utilisateur.getLocalisation());
                pstmt.setInt(7, utilisateur.getIdu());
                pstmt.executeUpdate();
            }
        }
    }

    private boolean isValidDate(Object date) {
        return date instanceof LocalDate;
    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            // Supprimer les entrées associées dans la table preferences
            String preferencesReq = "DELETE FROM preferences WHERE idu=?";
            try (PreparedStatement prefStmt = connection.prepareStatement(preferencesReq)) {
                prefStmt.setInt(1, id);
                prefStmt.executeUpdate();
            }

            // Supprimer l'utilisateur de la table utilisateur
            String utilisateurReq = "DELETE FROM utilisateur WHERE idu=?";
            try (PreparedStatement userStmt = connection.prepareStatement(utilisateurReq)) {
                userStmt.setInt(1, id);
                userStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de suppression
        }
    }

    @Override
    public List<Utilisateur> afficher() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String utilisateurreq = "SELECT * FROM utilisateur";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(utilisateurreq)) {
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdu(rs.getInt("idu"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setDateNaissance(rs.getDate("dateNaissance").toLocalDate());
                utilisateur.setRole(rs.getString("role"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setNum_tel(rs.getInt("num_tel"));
                utilisateur.setLocalisation(rs.getString("localisation"));
                utilisateurs.add(utilisateur);
            }
        }
        return utilisateurs;
    }
    public Utilisateur getUtilisateurById(int id) throws SQLException {
        Utilisateur utilisateur = null;
        String utilisateurreq = "SELECT * FROM utilisateur WHERE idu = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Utilisateur();
                    utilisateur.setIdu(rs.getInt("idu"));
                      utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setPassword(rs.getString("password"));
                    utilisateur.setDateNaissance(rs.getDate("dateNaissance").toLocalDate());
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setNum_tel(rs.getInt("num_tel"));
                    utilisateur.setLocalisation(rs.getString("localisation"));
                }
            }
        }
        return utilisateur;
    }

    public boolean authentifier(String nomUtilisateur, String motDePasse) {
        String requete = "SELECT * FROM utilisateur WHERE nom = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(requete)) {
            pstmt.setString(1, nomUtilisateur);
            pstmt.setString(2, motDePasse);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next(); // Retourne true si un utilisateur correspondant est trouvé, sinon false
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
            return false; // En cas d'erreur, considérez l'authentification comme échouée
        }
    }

    public int getIdUtilisateurByEmail(String email) throws SQLException {
        int idUser = -1;
        String query = "SELECT idu FROM utilisateur WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idUser = resultSet.getInt("idu");
                }
            }
        }
        return idUser;
    }
}
