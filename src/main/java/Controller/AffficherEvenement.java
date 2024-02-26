package Controller;

import Models.Evenement;
import Services.EvenementService;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffficherEvenement {
    @FXML
    private JFXButton ajE;
    @FXML
    private GridPane GridPane;

    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {

        try {
            EvenementService evenementService = new EvenementService();
            List<Evenement> evenementList = evenementService.afficher();

            // Effacer le contenu existant de GridPane
            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Itérer à travers la liste des événements
            for (Evenement evenement : evenementList) {
                // Charger Event.fxml pour chaque événement et définir ses données
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event.fxml"));
                Parent interfaceRoot = loader.load();
                Event itemController = loader.getController();
                itemController.setData(evenement);
                itemController.setE(evenement);
                itemController.setRefresh(this);


                // Ajouter l'élément chargé à GridPane
                GridPane.add(interfaceRoot, col, row);
                GridPane.setHgap(20); // Définir l'espacement horizontal entre les éléments
                GridPane.setVgap(20); // Définir l'espacement vertical entre les éléments

                // Ajuster les indices de ligne et de colonne
                col++;
                if (col == 1) { // Changer cette valeur en fonction de votre mise en page
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer les exceptions de manière appropriée
        }
    }
    @FXML
    void AjouterEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouter.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle update view loading error
        }

    }
    public void refreshView() {
        loadContent();
    }
}
