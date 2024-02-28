package Controller;

import Models.CategorieE;
import Models.Evenement;
import Services.EvenementService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModifierEvenement {

    @FXML
    private TextField dateE;

    @FXML
    private TextField descriptionE;

    @FXML
    private TextField heureE;

    @FXML
    private Button inserer;

    @FXML
    private Button modif;

    @FXML
    private TextField nbrpE;

    @FXML
    private TextField nomE;

    @FXML
    private TextField pageE;

    @FXML
    private ChoiceBox<CategorieE> categorieE;

    @FXML
    private ImageView photoE;

    private Evenement e;
    private AffficherEvenement afff;
    private String path = "";

    private String[] categories = {"Arts", "Sport", "Education", "Famille", "Loisirs", "Environnement", "Santé", "Communauté"};

    @FXML
    void inserer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toURL().toString());
                photoE.setImage(image);
                path = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        if (e != null && saisieValide()) {
            try {
                if (!path.isEmpty()) {
                    e.setPhoto(path);
                }

                e.setNom(nomE.getText());
                e.setDescription(descriptionE.getText());
                e.setPage(pageE.getText());
                e.setCategorie(categorieE.getValue());
                e.setNbrPlaces(Integer.parseInt(nbrpE.getText()));
                e.setDate(LocalDate.parse(dateE.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                e.setHeure(LocalTime.parse(heureE.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));

                EvenementService ES = new EvenementService();
                ES.modifier(e);

                Stage stage = (Stage) modif.getScene().getWindow();
                stage.close();

                afff.refreshView();

            } catch (SQLException | DateTimeParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void initData(Evenement e, AffficherEvenement aff) {
        this.e = e;
        this.afff = aff;
        populateFields();
    }

    private void populateFields() {
        nomE.setText(e.getNom());
        descriptionE.setText(e.getDescription());
        pageE.setText(e.getPage());
        nbrpE.setText(String.valueOf(e.getNbrPlaces()));
        dateE.setText(e.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        heureE.setText(e.getHeure().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        categorieE.setItems(FXCollections.observableArrayList(CategorieE.values()));
        categorieE.setValue(e.getCategorie());

        File file = new File(e.getPhoto());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            photoE.setImage(image);
        }
    }

    private boolean saisieValide() {
        // Vérification des champs obligatoires
        if (nomE.getText().isEmpty() || descriptionE.getText().isEmpty() || pageE.getText().isEmpty() || nbrpE.getText().isEmpty() || dateE.getText().isEmpty() || heureE.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        // Vérification du format de la date (jj/mm/yyyy)
        String datePattern = "\\d{2}/\\d{2}/\\d{4}";
        if (!dateE.getText().matches(datePattern)) {
            afficherAlerte("Veuillez saisir une date au format jj/mm/yyyy.");
            return false;
        }

        // Vérification du format de l'heure (hh:mm:ss)
        String heurePattern = "\\d{2}:\\d{2}:\\d{2}";
        if (!heureE.getText().matches(heurePattern)) {
            afficherAlerte("Veuillez saisir une heure au format hh:mm:ss.");
            return false;
        }

        // Vérification de la date
        try {
            LocalDate.parse(dateE.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (LocalDate.parse(dateE.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())) {
                afficherAlerte("La date de l'événement doit être ultérieure à la date d'aujourd'hui.");
                return false;
            }
        } catch (DateTimeParseException e) {
            afficherAlerte("Veuillez saisir une date valide au format jj/mm/yyyy.");
            return false;
        }

        // Vérification de l'heure
        try {
            LocalTime.parse(heureE.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            if (LocalTime.parse(heureE.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")).isBefore(LocalTime.now())) {
                afficherAlerte("L'heure de l'événement doit être ultérieure à l'heure actuelle.");
                return false;
            }
        } catch (DateTimeParseException e) {
            afficherAlerte("Veuillez saisir une heure valide au format hh:mm:ss.");
            return false;
        }

        // Vérification du nombre de participants
        try {
            int nbrParticipants = Integer.parseInt(nbrpE.getText());
            if (nbrParticipants <= 0) {
                afficherAlerte("Veuillez saisir un nombre de participants valide (entier positif).");
                return false;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Veuillez saisir un nombre de participants valide (entier positif).");
            return false;
        }

        return true;
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
