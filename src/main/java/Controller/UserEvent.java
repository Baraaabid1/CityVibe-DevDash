package Controller;

import Models.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class UserEvent {

    @FXML
    private Label categorieE;

    @FXML
    private Label dateE;

    @FXML
    private Label descriptionE;

    @FXML
    private Label heueE;

    @FXML
    private Label nbpE;

    @FXML
    private Label nomE;
    @FXML
    private Label nomP;

    @FXML
    private ImageView photoE;

    @FXML
    private Button voirPlus;

    private Evenement e;
    private VueUser vueUser;
    public void initialize() {
        if (e != null) {
            setData(e);
        }
    }


    @FXML
    public void setData(Evenement e) {
        nomP.setText(e.getPage1().getNom());
        nomE.setText(e.getNom());
        categorieE.setText(e.getCategorie().toString());
        nbpE.setText(Integer.toString(e.getNbrPlaces()));
        descriptionE.setText(e.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(e.getDate().toEpochDay());
        dateE.setText(formattedDate);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = e.getHeure().format(timeFormatter);
        heueE.setText(formattedTime);

        Image image = new Image("file:" + e.getPhoto());
        photoE.setImage(image);
    }

    public void setEvenement(Evenement e) {
        this.e = e;
    }

    public void setRefresh(VueUser vueUser) {
        this.vueUser=vueUser;
    }
    @FXML
    void voirPlus(ActionEvent event) {
        try {
            // Récupérer l'ID de l'événement actuel
            int idEvenement = e.getIdE();

            // Charger la vue EvenementComment.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EvenementComment.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé
            EvenementComment controller = loader.getController();

            // Appeler la méthode initData du contrôleur EvenementComment
            controller.initData(e, controller);

            // Afficher la nouvelle fenêtre modale
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de la vue
        }
    }
}

