package Controller;

import Models.Evenement;
import Services.EvenementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VueUser {

    @FXML
    private GridPane GridPane;

    @FXML
    public void initialize() {
        loadContent();
    }

    private void loadContent() {
        try {
            EvenementService evenementService = new EvenementService();
            List<Evenement> evenementList = evenementService.afficher();

            GridPane.getChildren().clear();

            int row = 1;
            int col = 0;

            for (Evenement evenement : evenementList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserEvent.fxml"));
                Parent interfaceRoot = loader.load();
                UserEvent itemController = loader.getController();
                itemController.setData(evenement);
                itemController.setVueUser(this);

                GridPane.add(interfaceRoot, col, row);
                GridPane.setHgap(20);
                GridPane.setVgap(20);

                col++;
                if (col == 1) {
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
