package controllers;
import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import models.publication;

        import java.io.IOException;

public class publiccontroller {


    @FXML
    private ImageView imagV;

    @FXML
    private Label descV;

    @FXML
    private Label nomV;

    private publication pu;


    void setData(publication publication) {
        this.pu = publication; // Set the publication object
        // Setting name text
        nomV.setText(publication.getNom());
        descV.setText(publication.getDescription());
        // Setting image
        if (publication.getImage() != null && !publication.getImage().isEmpty()) {
            Image image = new Image("file:" + publication.getImage());
            imagV.setImage(image);
        }
    }


    public void setE(publication publication) {
    }

    public void cons(ActionEvent actionEvent) {
    }
}
