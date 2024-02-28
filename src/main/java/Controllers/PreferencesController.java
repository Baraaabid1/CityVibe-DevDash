package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PreferencesController {
    private ObservableList<String> preferences = FXCollections.observableArrayList();

    @FXML
    void ArtPref(ActionEvent event) {
        ajouterPreference("Art");

    }

    @FXML
    void sportPref(ActionEvent event) {
        ajouterPreference("Sport");

    }
    @FXML
    void bienEtre(ActionEvent event) {
        ajouterPreference("Bien_Etre");

    }

    @FXML
    void dance(ActionEvent event) {
        ajouterPreference("Dance");

    }

    @FXML
    void education(ActionEvent event) {
        ajouterPreference("Education");

    }

    @FXML
    void lecture(ActionEvent event) {
        ajouterPreference("Lecture");

    }

    @FXML
    void musique(ActionEvent event) {
        ajouterPreference("Musique");
    }

    @FXML
    void sante(ActionEvent event) {
        ajouterPreference("Santé");

    }
    private void ajouterPreference(String preference) {
        if (!preferences.contains(preference)) {
            preferences.add(preference);
        }
    }
    public ObservableList<String> getPreferences() {
        System.out.println(preferences);
        return preferences;
    }

}
