package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.page;
import services.pageService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import java.sql.Time;
import java.time.LocalTime;



public class pagecontroller {

    @FXML
    private Label cat;
    @FXML
    private Label nom;

    @FXML
    private Label contact;

    @FXML
    private Label descrip;

    @FXML
    private ImageView img;

    @FXML
    private Label loc;

    @FXML
    private ImageView logo;

    @FXML
    private Label ouvr;

    private page p;




     public void initialize() throws SQLException {
        if (p == null) {
            // If the p object is null, initialize it
            pageService ps = new pageService();

        }
        // Update UI elements with publication data
        if (p != null) {
            nom.setText(p.getNom());
        descrip.setText(p.getDescription());
        loc.setText(p.getLocalisation());
        contact.setText(Integer.toString(p.getContact()));
        cat.setText(p.getCategorie().toString());
        // Format the time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = p.getOuverture().format(timeFormatter);
        //if (p.getOuverture().equals(LocalTime.now()))  condition wa9t
            //send sms
        ouvr.setText(formattedTime);
            // Load and set the images
            if (p.getImage() != null) {
                Image image = new Image("file:" + p.getImage());
                img.setImage(image);
            }
    // Load and set the logos
            if (p.getLogo() != null) {
                Image logoImage = new Image("file:" + p.getLogo());
                logo.setImage(logoImage);
            }
        }
    }



    // Method to set data to the UI elements
    @FXML
    public void setData(page page) {
        // Setting nom text
        nom.setText(page.getNom());
        // Setting description text
        descrip.setText(page.getDescription());
        // Setting localisation text
       loc.setText(page.getLocalisation());
        // Setting categorie text
        cat.setText(page.getCategorie().toString());
        // Setting contact text
        contact.setText(Integer.toString(page.getContact()));
        // Setting localisation text
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = page.getOuverture().format(timeFormatter);
        ouvr.setText(formattedTime);
        // Setting image
        Image image = new Image("file:" + page.getImage());
        img.setImage(image);
        // Setting logo
        Image logoImage = new Image("file:" + page.getLogo());
        logo.setImage(logoImage);
    }

    public void setE(page page) {

    }

//    public void setRefresh(GererLieuxController gererLieuxController) {
//
//    }


    public void refreshView() {
    }
}


