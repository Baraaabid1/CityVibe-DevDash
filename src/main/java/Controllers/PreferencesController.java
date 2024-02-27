package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PreferencesController {
    private ObservableList<String> preferences = FXCollections.observableArrayList();

    @FXML
    void ArtPref(ActionEvent event) {
        preferences.add("art");

    }

    @FXML
    void sportPref(ActionEvent event) {
        preferences.add("sport");

    }
    @FXML
    void bienEtre(ActionEvent event) {
        preferences.add("Bien_Etre");

    }

    @FXML
    void dance(ActionEvent event) {
        preferences.add("Dance");

    }

    @FXML
    void education(ActionEvent event) {
        preferences.add("Eduction");

    }

    @FXML
    void lecture(ActionEvent event) {
        preferences.add("Lecture");

    }

    @FXML
    void musique(ActionEvent event) {
        preferences.add("Musique");

    }

    @FXML
    void sante(ActionEvent event) {
        preferences.add("Santé");

    }

    public ObservableList<String> getPreferences() {
        System.out.println(preferences);
        return preferences;
    }

}
