package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import utiles.MyDataBase;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class ProfileController {
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
    private Circle circle;



    private Connection connection;
    public ProfileController() {
        connection= MyDataBase.getInstance().getConn();
    }




    public void initialize(int idUser) {
        this.idUser = idUser;
        try {
            String query = "SELECT * FROM utilisateur WHERE idu = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idUser);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String nom = resultSet.getString("nom");
                        String prenom = resultSet.getString("prenom");
                        String email = resultSet.getString("email");
                        String adresse = resultSet.getString("localisation");
                        String tel = resultSet.getString("num_tel");
                        LocalDate birth = resultSet.getDate("dateNaissance").toLocalDate();

                        // Assurez-vous que les labels sont correctement définis dans votre fichier FXML
                        nameLabel.setText(nom + " " + prenom);
                        emailLabel.setText(email);
                        adresseLabel.setText(adresse);
                        telLabel.setText(tel);
                        birthLabel.setText(birth.toString());
                        byte[] imageData = resultSet.getBytes("img");
                        if (imageData != null) {
                            // Convertir les données binaires en un objet Image
                            Image image = new Image(new ByteArrayInputStream(imageData));

//                            // Appliquer le masque du cercle à l'ImageView
//                            Circle circle = new Circle();
//                            circle.setCenterX(50); // Position X du centre du cercle
//                            circle.setCenterY(50); // Position Y du centre du cercle
//                            circle.setRadius(50); // Rayon du cercle
//                            imgUser.setClip(circle);

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
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de requête SQL
        }
    }

//public void initialize(int idUser) {
//    this.idUser = idUser;
//    try {
//        String query = "SELECT img FROM utilisateur WHERE idu = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, idUser);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    // Récupérer les données binaires de l'image depuis la base de données
//                    byte[] imageData = resultSet.getBytes("img");
//
//                    // Convertir les données binaires en un objet Image
//                    Image image = new Image(new ByteArrayInputStream(imageData));
//
//                    // Appliquer le masque du cercle à l'ImageView
//                    double centerX = imgUser.getFitWidth() / 2.0;
//                    double centerY = imgUser.getFitHeight() / 2.0;
//                    double radius = 60.0;
//                    Circle circle = new Circle(centerX, centerY, radius);
//                    imgUser.setClip(circle);
//
//                    // Afficher l'image dans l'ImageView
//                    imgUser.setImage(image);
//                } else {
//                    System.err.println("Utilisateur introuvable pour l'ID: " + idUser);
//                }
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        // Gérer les erreurs de requête SQL
//    }
//}

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
