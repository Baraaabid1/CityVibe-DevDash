package Controller;


import Models.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class SuperAdminEvent {

        @FXML
        private Label categorieE;

        @FXML
        private Label categorieE1;

        @FXML
        private Label dateE;

        @FXML
        private Label dateE1;

        @FXML
        private Label descriptionE;

        @FXML
        private Label descriptionE1;

        @FXML
        private Label heueE;

        @FXML
        private Label heueE1;

        @FXML
        private Label nbpE;

        @FXML
        private Label nbpE1;

        @FXML
        private Label nomE;

        @FXML
        private Label nomE1;

        @FXML
        private ImageView photoE;

        @FXML
        private Label nomP;

        @FXML
        private Button supp;


    private Evenement e;
    private SuperAdminView superAdminView;
    public void setE(Evenement e){
        this.e= e;
    }

    // Handle click event

    public void initialize(){



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
                superAdminView.refreshView();
                // Afficher une confirmation à l'utilisateur



            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    public void setRefresh(SuperAdminView superAdminView){
        this.superAdminView=superAdminView;
    }
    }


