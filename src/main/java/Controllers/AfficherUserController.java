package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Utilisateur;
import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AfficherUserController {
    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    @FXML
    private TableView<Utilisateur> tableview;

    @FXML
    private TableColumn<Utilisateur, Integer> id;

    @FXML
    private TableColumn<Utilisateur, String> nom;

    @FXML
    private TableColumn<Utilisateur, String> prenom;

    @FXML
    private TableColumn<Utilisateur, String> password;

    @FXML
    private TableColumn<Utilisateur, LocalDate> dateNaissance;

    @FXML
    private TableColumn<Utilisateur, String> role;

    @FXML
    private TableColumn<Utilisateur, String> email;

    @FXML
    private TableColumn<Utilisateur, Integer> numTel;

    @FXML
    private TableColumn<Utilisateur, String> preference;

    @FXML
    private TableColumn<Utilisateur, String> localisation;
    UtilisateurService ps = new UtilisateurService();

    @FXML
    private Button ajoutU;
    @FXML
    private Button modif;

    public void initialize(){
        try {
            // Récupérer les utilisateurs depuis le service (vous devez remplacer YourUtilisateurType par le vrai type de vos utilisateurs)
            List<Utilisateur> utilisateurs = ps.afficher();

            // Créer une liste observable à partir des utilisateurs
            ObservableList<Utilisateur> observableUtilisateurs = FXCollections.observableArrayList(utilisateurs);

            // Lier la liste observable à la TableView
            tableview.setItems(observableUtilisateurs);

            // Lier les propriétés des utilisateurs aux colonnes (vous devez remplacer les "?" par le vrai type de vos colonnes)
            id.setCellValueFactory(new PropertyValueFactory<>("idu"));
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
            dateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            numTel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
            preference.setCellValueFactory(new PropertyValueFactory<>("preference"));
            localisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void ajouterUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
        root = loader.load();
        AjouterUserController Auser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void modifierU(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
        root = loader.load();
        ModifierUserController ModifUser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void supp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeleteUser.fxml"));
        root = loader.load();
        DeleteUserController DelUser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


//    @FXML
//    void AfficherUser(ActionEvent event) {
//        try {
//            // Récupérer les utilisateurs depuis le service (vous devez remplacer YourUtilisateurType par le vrai type de vos utilisateurs)
//            List<Utilisateur> utilisateurs = ps.afficher();
//
//            // Créer une liste observable à partir des utilisateurs
//            ObservableList<Utilisateur> observableUtilisateurs = FXCollections.observableArrayList(utilisateurs);
//
//            // Lier la liste observable à la TableView
//            tableview.setItems(observableUtilisateurs);
//
//            // Lier les propriétés des utilisateurs aux colonnes (vous devez remplacer les "?" par le vrai type de vos colonnes)
//            id.setCellValueFactory(new PropertyValueFactory<>("idu"));
//            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
//            prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
//            password.setCellValueFactory(new PropertyValueFactory<>("password"));
//            dateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
//            role.setCellValueFactory(new PropertyValueFactory<>("role"));
//            email.setCellValueFactory(new PropertyValueFactory<>("email"));
//            numTel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
//            preference.setCellValueFactory(new PropertyValueFactory<>("preference"));
//            localisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
 //   }


}
