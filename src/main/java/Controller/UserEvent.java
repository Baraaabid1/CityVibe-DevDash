package Controller;

import Models.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ImageView photoE;

    private VueUser vueUser;
    private Evenement e;

    public void setEvenement(Evenement e) {
        this.e = e;
    }

    public void initialize() {
        setData(e);
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

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = e.getHeure().format(timeFormatter);
        heueE.setText(formattedTime);

        Image image = new Image("file:" + e.getPhoto());
        photoE.setImage(image);
    }

    public void setVueUser(VueUser vueUser) {
        this.vueUser = vueUser;
    }
}
