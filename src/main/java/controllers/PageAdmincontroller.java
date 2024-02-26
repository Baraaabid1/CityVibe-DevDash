package controllers ;
import controllers.pagecontroller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.page;
import services.pageService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PageAdmincontroller {

    @FXML
    private Label catA;

    @FXML
    private Label contA;

    @FXML
    private Label desA;

    @FXML
    private Label locA;

    @FXML
    private ImageView imgA;

    @FXML
    private ImageView logoA;

    @FXML
    private Label nomA;

    @FXML
    private Label ouvA;

    private List<page> pages;
    private int currentPageIndex = 0;
    private pagecontroller ad;

    public void initialize() {
        try {
            loadPages(); // Fetch data from the database when initializing
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPages() throws SQLException {
        pageService ps = new pageService();
        pages = ps.getAllPages();

        // Display the first page by default, you can change this as needed
        if (!pages.isEmpty()) {
            afficherPage(pages.get(currentPageIndex));
        }
    }

    private void afficherPage(page p) {
        if (p != null) {
            nomA.setText(p.getNom());
            contA.setText(String.valueOf(p.getContact()));
            catA.setText(p.getCategorie().toString());
            locA.setText(p.getLocalisation());
            desA.setText(p.getDescription());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = p.getOuverture().format(timeFormatter);
            ouvA.setText(formattedTime);
            if (p.getImage() != null) {
                Image image = new Image("file:" + p.getImage());
                imgA.setImage(image);
            }
            if (p.getLogo() != null) {
                Image logoImage = new Image("file:" + p.getLogo());
                logoA.setImage(logoImage);
            }
        }
    }

    @FXML
    void modifierA(ActionEvent event) {
        // Implement your modification logic here
    }

    @FXML
    void SupprimerA(ActionEvent event) {
        // Implement your deletion logic here
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setRefresh(pagecontroller ad) {
        this.ad = ad;
    }

    public void setE(page page) {
        // Not sure what this method is intended for
    }

    public void setData(page page) {
        // Not sure what this method is intended for
    }
}
