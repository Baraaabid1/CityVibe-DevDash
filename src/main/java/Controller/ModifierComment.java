package Controller;

import Models.Commentaire;
import Services.CommentaireService;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ModifierComment {

    @FXML
    private TextArea Comment;

    @FXML
    private Button commentMod;
    private Commentaire c ;
    private EvenementComment ec;

    @FXML
    void CommentModifier(ActionEvent event) throws SQLException {
        if (c != null) {

            c.setContenu(Comment.getText());


            CommentaireService ES = new CommentaireService();
            ES.modifier(c);

            Stage stage = (Stage) commentMod.getScene().getWindow();
            stage.close();

            ec.refreshView();

        }
    }


public void initData(Commentaire c, EvenementComment ec) {
    this.c = c;
    this.ec = ec;
    populateFields();
    }

    private void populateFields() {
        Comment.setText(c.getContenu());
    }
}
