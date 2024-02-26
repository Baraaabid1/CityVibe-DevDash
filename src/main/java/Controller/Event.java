package Controller;

import Models.CategorieE;
import Models.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {
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
    private ImageView photoE;
    @FXML
    private Button supp;

    @FXML
    private Button mod;
    @FXML
    private Button voirPlus;

    private Evenement e;
    private AffficherEvenement aff;
    public void setE(Evenement e){
        this.e= e;
    }

    // Handle click event

    public void initialize(){



    }

    @FXML


    public void setData(Evenement e) {

        nomE.setText(e.getNom());
        categorieE.setText(e.getCategorie().toString());
        nbpE.setText(Integer.toString(e.getNbrPlaces()));
        descriptionE.setText(e.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(e.getDate().toEpochDay());
        dateE.setText(formattedDate);

        // Format the time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = e.getHeure().format(timeFormatter);
        heueE.setText(formattedTime);


        Image image = new Image("file:" + e.getPhoto());
        photoE.setImage(image);



        // Set the loaded image to the ImageView


    }
    @FXML
    void Supprimer(ActionEvent event) {

        if (e != null) {
                try {
                    // Appeler la méthode de service pour supprimer l'événement
                    EvenementService service = new EvenementService();
                    service.supprimer(e.getIdE());
                  aff.refreshView();
                    // Afficher une confirmation à l'utilisateur



                } catch (SQLException e) {
                  e.printStackTrace();
                }

            }
        }
    @FXML
    void Modifier(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
            Parent root = loader.load();

            ModifierEvenement ModifierEvenement = loader.getController();
            ModifierEvenement.initData(e, aff);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle update view loading error
        }

    }
    public void setRefresh(AffficherEvenement aff){
        this.aff=aff;
    }
    @FXML
    void voirPlus(ActionEvent event) {

    }

}




