package Controller;

import Models.CategorieE;
import Models.Evenement;
import Models.Page;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        if (!saisieValide()) {
            return;
        }

        try {
            Page page = new Page(1);
            String selectedCategorieString = categorieE.getValue(); // Obtenez la chaîne de la ComboBox
            CategorieE selectedCategorie = CategorieE.valueOf(selectedCategorieString); // Convertissez la chaîne en enum CategorieE
            es.ajouter(new Evenement(Integer.parseInt(nbrpE.getText()), nomE.getText(), pageE.getText(), descriptionE.getText(), dateE.getValue(), heueE.getLocalTime(), selectedCategorie, imgPath,page));

            Stage stage = (Stage) ajouter.getScene().getWindow();
            stage.close();
            // Rafraîchir la vue dans le contrôleur Afficher
            if (af != null) {
                af.refreshView();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    //Fonction de saisie
    private boolean saisieValide() {
        // Vérification des champs obligatoires
        if (nomE.getText().isEmpty() || nbrpE.getText().isEmpty() || descriptionE.getText().isEmpty() || pageE.getText().isEmpty() || imgPath.isEmpty() || dateE.getValue() == null || heueE.getLocalTime() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return false;
        }

        // Vérification du format de la date (jj/mm/yyyy)
        LocalDate date = dateE.getValue();
        String datePattern = "\\d{2}/\\d{2}/\\d{4}";
        if (!date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).matches(datePattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une date au format jj/mm/yyyy.");
            alert.showAndWait();
            return false;
        }

        // Vérification du format de l'heure (hh:mm:ss)
        LocalTime heure = heueE.getLocalTime();
        String heurePattern = "\\d{2}:\\d{2}:\\d{2}";
        if (!heure.format(DateTimeFormatter.ofPattern("HH:mm:ss")).matches(heurePattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une heure au format hh:mm:ss.");
            alert.showAndWait();
            return false;
        }

        // Vérification de la date et de l'heure
        if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && heure.isBefore(LocalTime.now()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date et l'heure de l'événement doivent être ultérieures à la date et l'heure actuelles.");
            alert.showAndWait();
            return false;
        }

        // Vérification du nombre de participants
        try {
            int nbrParticipants = Integer.parseInt(nbrpE.getText());
            if (nbrParticipants <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un nombre de participants valide (entier positif).");
            alert.showAndWait();
            return false;
        }

        return true;
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
    public void setAfficherEvenementController(AffficherEvenement af) {
        this.af = af;
    }


    }



