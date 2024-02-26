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
        if (e != null) {
            try {
                if (!path.isEmpty()) {
                    e.setNom(nomE.getText());
                    e.setDescription(descriptionE.getText());
                    e.setPage(pageE.getText());
                    e.setCategorie(categorieE.getValue());
                    e.setNbrPlaces(Integer.parseInt(nbrpE.getText()));
                    e.setDate(LocalDate.parse(dateE.getText()));
                    e.setHeure(LocalTime.parse(heureE.getText()));
                    e.setPhoto(path);
                } else {
                    e.setNom(nomE.getText());
                    e.setDescription(descriptionE.getText());
                    e.setPage(pageE.getText());
                    e.setCategorie(categorieE.getValue());
                    e.setNbrPlaces(Integer.parseInt(nbrpE.getText()));
                    e.setDate(LocalDate.parse(dateE.getText()));
                    e.setHeure(LocalTime.parse(heureE.getText()));
                    e.setPhoto(e.getPhoto());
                }

                EvenementService ES = new EvenementService();
                ES.modifier(e);

                Stage stage = (Stage) modif.getScene().getWindow();
                stage.close();

                afff.refreshView();

            } catch (SQLException e) {
                e.printStackTrace();
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
        dateE.setText(e.getDate().toString());
        heureE.setText(e.getHeure().toString());

        categorieE.setItems(FXCollections.observableArrayList(CategorieE.values()));
        categorieE.setValue(e.getCategorie());

        File file = new File(e.getPhoto());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            photoE.setImage(image);
        }
    }
}
