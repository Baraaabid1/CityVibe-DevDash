package Controller;

import Models.CategorieE;
import Models.Evenement;
import Services.EvenementService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalTimeTextField;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterEvenement {
    EvenementService es = new EvenementService();
    @FXML
    private ComboBox<String> categorieE; // Ajoutez le type générique String à la ComboBox
    private String[] categorie = {"Arts" , "Sport", "Education" ,"Famille" , "Loisirs" , "Environnement" , "Santé" , "Communauté"};
    @FXML
    private DatePicker dateE;

    @FXML
    private TextField descriptionE;
    @FXML
    private ImageView logo;

    @FXML
    private LocalTimeTextField heueE;
    @FXML
    private Button ajouter;
    @FXML
    private Button inserer;
    @FXML
    private TextField nbrpE;

    @FXML
    private TextField nomE;

    @FXML
    private TextField pageE;
    @FXML
    private ImageView photoE;
    public String imgPath ="";
    private AffficherEvenement af;

    @FXML
    private void initialize() {
        // Initialise la ComboBox avec les valeurs de la liste des catégories
        categorieE.setItems(FXCollections.observableArrayList(categorie)); // Utilisez la liste des catégories au lieu de CategorieE.values()
    }

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            String selectedCategorieString = categorieE.getValue(); // Obtenez la chaîne de la ComboBox
            CategorieE selectedCategorie = CategorieE.valueOf(selectedCategorieString); // Convertissez la chaîne en enum CategorieE
            es.ajouter(new Evenement(Integer.parseInt(nbrpE.getText()), nomE.getText(), pageE.getText(), descriptionE.getText(), dateE.getValue(), heueE.getLocalTime(), selectedCategorie, imgPath));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Evenement Ajouter avec succée");
            alert.showAndWait();
            Stage stage = (Stage) ajouter.getScene().getWindow();
            stage.close();
            // Refresh the view in the Afficher controller
            if (af != null) {
                af.refreshView();
            }
        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void Inserer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            System.out.println("Image Path: " + imagePath); // Debugging: Print out image path

            try {
                Image image = new Image(imagePath);
                photoE.setImage(image);
                // Set the image to the ImageView
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.show();
            }

        }
        imgPath = file.getAbsolutePath();
    }


    }



