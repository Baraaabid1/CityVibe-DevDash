package Controller;

import Models.Commentaire;
import Models.Evenement;
import Models.Page;
import Services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EvenementComment {

    @FXML
    private Label nomE;

    @FXML
    private Label categorieE;

    @FXML
    private Label dateE;

    @FXML
    private Label heueE;

    @FXML
    private Label descriptionE;

    @FXML
    private ImageView photoE;

    @FXML
    private Label nbpE;

    @FXML
    private GridPane GridPane;

    @FXML
    private Button ajC;

    @FXML
    private Label nomP;

    private Evenement evenement;
    int userId;
    public EvenementComment() throws SQLException {
    }

    @FXML
    public void initialize() {
        try {
            loadContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initData(Evenement e, EvenementComment ec) {
        ec.setEvenement(e);
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        afficherDetailsEvenement();
        try {
            loadContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherDetailsEvenement() {
        if (evenement != null) {
            if (evenement.getPage1() != null) {
                nomP.setText(evenement.getPage1().getNom());
            }
            nomE.setText(evenement.getNom());
            categorieE.setText(String.valueOf(evenement.getCategorie()));
            dateE.setText(String.valueOf(evenement.getDate()));
            heueE.setText(String.valueOf(evenement.getHeure()));
            descriptionE.setText(evenement.getDescription());
            nbpE.setText(String.valueOf(evenement.getNbrPlaces()));
            // Charger l'image de l'événement depuis le chemin d'accès spécifié dans evenement.getPhoto()
            Image image = new Image("file:" + evenement.getPhoto());
            photoE.setImage(image);
        }
    }

    private void loadContent() throws IOException {
        if (evenement != null) {
            CommentaireService commentaireService = new CommentaireService();
            List<Commentaire> commentaireList = commentaireService.recuperer(evenement.getIdE());

            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            // Itérer à travers la liste des commentaires
            for (Commentaire commentaire : commentaireList) {
                // Charger GridComment.fxml pour chaque commentaire et définir ses données
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GridComment.fxml"));
                Parent interfaceRoot = loader.load();
                GridComment itemController = loader.getController();
                itemController.setData(commentaire);
                itemController.setC(commentaire);
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
        }
    }

    @FXML
    void AjouterComment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Comment.fxml"));
            Parent root = loader.load();

            // Passer l'événement actuel et l'identifiant de l'utilisateur au contrôleur du formulaire de commentaire
            Comment comment = loader.getController();
            comment.setEvenement(evenement);
            comment.setEvenementComment(this);

            comment.setUserId(userId); // Remplacer 'userId' par l'identifiant de l'utilisateur

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement du formulaire de commentaire
        }
    }


    public void refreshView() {
        try {
            loadContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
