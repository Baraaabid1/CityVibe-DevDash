

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

//    @FXML
//    private Button cons;

    @FXML
    private ImageView imagV;


    @FXML
    private Label nomV;

    private publication pu;
//    private Parent root;


    @FXML
    void cons(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubVisiteur.fxml"));
        Parent root = loader.load();
        PubVisiteurcontroller pubVisiteurController = loader.getController();
        pubVisiteurController.setData(pu);

        // Create a new stage for the pop-up
        Stage popUpStage = new Stage();
        popUpStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popUpStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.showAndWait(); // Show the pop-up and wait for it to be closed
    }

    void setData(publication publication) {
        this.pu = publication; // Set the publication object
        // Setting name text
        nomV.setText(publication.getNom());
        // Setting image
        if (publication.getImage() != null && !publication.getImage().isEmpty()) {
            Image image = new Image("file:" + publication.getImage());
            imagV.setImage(image);

        }
    }

    public void setE(publication publication) {
    }
}
