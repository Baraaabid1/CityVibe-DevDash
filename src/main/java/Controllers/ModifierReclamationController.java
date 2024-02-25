package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ModifierReclamationController {
    @FXML
    private TextField idR;

    @FXML
    private TextField idU;

    @FXML
    private TextField Obj;

    @FXML
    private TextField Local;

    @FXML
    private TextField contenu;
    ReclamationService rs = new ReclamationService();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
    private Stage stage ;
    private Scene scene ;
    private Parent root ;


    @FXML
    void AfficherListe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeReclamations.fxml"));
        root = loader.load();
        ListeReclamationsController Lc = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ModifierReclamation(ActionEvent event) {
      /*  try {
            rs.modifier(new Reclamation(Integer.parseInt(idR.getText()),Integer.parseInt(idU.getText()),Obj.getText(),contenu.getText(), Local.getText(), currentTimestamp));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/

    }
}
