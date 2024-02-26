package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Utilisateur;
import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterUserController {
    private Stage stage ;
    private Scene scene ;
    private Parent root ;

    UtilisateurService ps = new UtilisateurService();
    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField emailU;

    @FXML
    private DatePicker dateNaissanceU;

    @FXML
    private TextField numTelU;

    @FXML
    private PasswordField passwordU;
    @FXML
    private ChoiceBox<String> prefrenceBox;
    @FXML
    private ChoiceBox<String> roleBox;
    @FXML
    private ChoiceBox<String> locationBox;
    private String[] prefer= {"Art","Sport","Bien etre","Aventure"};

    private String[] role= {"Simple Utilisateur","Admin de page"};
    private String[] location= {"Ariana Soghra"};

    public void initialize(){
        prefrenceBox.getItems().addAll(prefer);
        roleBox.getItems().addAll(role);
        locationBox.getItems().addAll(location);
    }

    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        try {

            ps.ajouter(new Utilisateur(Integer.parseInt(numTelU.getText()),nomU.getText(),prenomU.getText(),passwordU.getText(),emailU.getText(),prefrenceBox.getValue(),locationBox.getValue(),dateNaissanceU.getValue(), String.valueOf(roleBox.getValue())));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
        root = loader.load();
        AfficherUserController Affuser = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }

}
