package services;

import javafx.scene.control.Alert;
import models.Utilisateur;
import utiles.MyDataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.regex.Pattern;

import static java.lang.constant.ConstantDescs.NULL;

public class UtilisateurService implements IService <Utilisateur> {
    private Connection connection;

    public UtilisateurService(){
        connection= MyDataBase.getInstance().getConn();
    }
    @Override
    public void ajouter(Utilisateur utilisateur) throws SQLException {
        if (!isValidEmail(utilisateur.getEmail())) {
            showAlert("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");

        }

        else if (!isValidPhoneNumber(utilisateur.getNum_tel())) {
            showAlert("Invalid phone number");
            throw new IllegalArgumentException("Invalid phone number");
        }else {
            String hashedPassword = hashPassword(utilisateur.getPassword());
            System.out.println(hashedPassword);

            String utilisateurreq = "INSERT INTO utilisateur (idu, nom, prenom, password, dateNaissance, email, num_tel, preference, localisation, role, img) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                pstmt.setString(8, utilisateur.getPreference());
                pstmt.setString(9, utilisateur.getLocalisation());
                pstmt.setString(10, utilisateur.getRole());
                byte[] defaultImageBytes = selectAndConvertDefaultImage();
                pstmt.setBytes(11, defaultImageBytes);
                pstmt.executeUpdate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
    private byte[] selectAndConvertDefaultImage() throws IOException {
        // Chemin vers l'image par défaut
        String defaultImagePath = "C:\\Users\\benna\\Desktop\\istockphoto-1337144146-612x612.jpg";


        // Charger l'image depuis le chemin spécifié
        File defaultImageFile = new File(defaultImagePath);

        // Convertir le fichier en tableau de bytes
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
        }else {
            String utilisateurreq = "UPDATE utilisateur SET nom=?, prenom=?, password=?, dateNaissance=?, email=?, num_tel=?, preference=?, localisation=?, role=? WHERE idu=?";
            System.out.println(utilisateur);
            try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
                pstmt.setString(1, utilisateur.getNom());
                pstmt.setString(2, utilisateur.getPrenom());
                pstmt.setString(3, utilisateur.getPassword());
                LocalDate localDate = LocalDate.from(utilisateur.getDateNaissance());
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
// Utilisation de la java.sql.Date pour définir le paramètre de la requête PreparedStatement
                pstmt.setDate(4, sqlDate);
                pstmt.setString(5, utilisateur.getEmail());
                pstmt.setInt(6, utilisateur.getNum_tel());
                pstmt.setString(7, utilisateur.getPreference());
                pstmt.setString(8, utilisateur.getLocalisation());
                pstmt.setString(9, utilisateur.getRole());
                pstmt.setInt(10, utilisateur.getIdu());
                pstmt.executeUpdate();
            }
        }
    }
    private boolean isValidDate(Object date) {
        return date instanceof LocalDate;
    }
    public void modifierESFEE(Utilisateur utilisateur) throws SQLException {
        String utilisateurreq = "UPDATE utilisateur SET nom=?, prenom=?, password=?, email=?, num_tel=?, preference=?, localisation=?, role=? WHERE idu=?";
        System.out.println(utilisateur);
        try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getPassword());
            pstmt.setString(5, utilisateur.getEmail());
            pstmt.setInt(6, utilisateur.getNum_tel());
            pstmt.setString(7, utilisateur.getPreference());
            pstmt.setString(8, utilisateur.getLocalisation());
            pstmt.setString(9, utilisateur.getRole());
            pstmt.setInt(10, utilisateur.getIdu());

            pstmt.executeUpdate();
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String utilisateurreq = "DELETE FROM utilisateur WHERE idu=?";
        try (PreparedStatement pstmt = connection.prepareStatement(utilisateurreq)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

    }
    public void modifierNomUtilisateur(int idUtilisateur, String nouveauNom, String nvPrenom,int nvTel,LocalDate nvNaiss) throws SQLException {
        String requete = "UPDATE utilisateur SET nom = ?, prenom = ?, num_tel = ?, dateNaissance = ? WHERE idu = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(requete)) {
            pstmt.setString(1, nouveauNom);
            pstmt.setString(2, nvPrenom);
            pstmt.setInt(3, nvTel);
            pstmt.setDate(4, Date.valueOf(nvNaiss));
            pstmt.setInt(5, idUtilisateur);
            pstmt.executeUpdate();
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
                utilisateur.setPreference(rs.getString("preference"));
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
                    utilisateur.setPreference(rs.getString("preference"));
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
}
