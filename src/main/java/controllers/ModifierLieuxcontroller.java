package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.categorieP;
import models.page;
import models.publication;
import services.pageService;
import jfxtras.scene.control.LocalTimeTextField;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModifierLieuxcontroller {

    @FXML
    private TextField locali;

    @FXML
    private TextField nom;
    @FXML
    private Button mod;

    @FXML
    private ImageView image;
    @FXML
    private Button modifierA;
    @FXML
    private Button addPhoto;

    @FXML
    private ImageView logo;

    @FXML
    private Button addLogo;

    @FXML
    private TextField description;

    @FXML
    private ChoiceBox<categorieP> categorieChoiceBox;

    @FXML
    private TextField contact;
    @FXML
    private TextField ouverture;
    private String imagePath = "";
    private String logoPath = "";
    private GeneralDesignAdminController pub;

    public GeneralDesignAdminController getPub() {
        return pub;
    }

    public void setPub(GeneralDesignAdminController pub) {
        this.pub = pub;
    }

    private page pa;

    public page getPa() {
        return pa;
    }

    public void setPa(page pa) {
        this.pa = pa;
    }

    @FXML
    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.toURI().toString();
            try {
                Image img = new Image(imagePath);
                image.setImage(img);
            } catch (Exception e) {
                showErrorAlert("Error loading image: " + e.getMessage());
            }
        }
        imagePath= file.getAbsolutePath();
    }

    @FXML
    public void addLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            logoPath = file.toURI().toString();
            try {
                Image img = new Image(logoPath);
                logo.setImage(img);
            } catch (Exception e) {
                showErrorAlert("Error loading logo: " + e.getMessage());
            }
        }
        logoPath= file.getAbsolutePath();
    }

    public void setData(page page) {
        // Setting nom text
        nom.setText(page.getNom());
        // Setting description text
        description.setText(page.getDescription());
        // Setting localisation text
        locali.setText(page.getLocalisation());
        // Setting categorie text
        categorieChoiceBox.setValue(categorieP.valueOf(page.getCategorie().toString()));
        // Setting contact text
        contact.setText(Integer.toString(page.getContact()));
        // Setting localisation text
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = page.getOuverture().format(timeFormatter);
        ouverture.setText(formattedTime);
        // Setting image
        Image imagee = new Image("file:" + page.getImage());
        image.setImage(imagee);
        // Setting logo
        Image logoImage = new Image("file:" + page.getLogo());
        logo.setImage(logoImage);
    }


    @FXML
    private void initialize() {
        categorieChoiceBox.setItems(FXCollections.observableArrayList(categorieP.values()));
    }

    @FXML
        void modifier(ActionEvent event) {

                try {
                    if (!imagePath.isEmpty() && !logoPath.isEmpty() ) {
                        pa.setImage(imagePath);
                        pa.setLogo(logoPath);
                    }

                    pa.setNom(nom.getText());
                    pa.setDescription(description.getText());
                    pa.setLocalisation(locali.getText());
                    pa.setCategorie(categorieChoiceBox.getValue());
                    pa.setContact(Integer.parseInt(contact.getText()));
                    pa.setOuverture(LocalTime.parse(ouverture.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")));

                    pageService ES = new pageService();
                    ES.modifier(pa);

                    Stage stage = (Stage) modifierA.getScene().getWindow();
                    stage.close();

                    pub.refreshView();

                } catch (SQLException | DateTimeParseException ex) {
                    ex.printStackTrace();
                }

    }


    private boolean saisieValide() {
        return false;
    }



    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void setRefresh(PageAdmincontroller pageAdmincontroller) {

    }

    public void initData(page pa, PageAdmincontroller pageAdmincontroller) {
        // Your initialization code here

        this.pa = pa;
        populatedFields();

    }

    private void populatedFields() {
        nom.setText(pa.getNom());
        locali.setText(pa.getLocalisation());
        description.setText(pa.getDescription());
        contact.setText(String.valueOf(pa.getContact()));
        ouverture.setText(pa.getOuverture().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        categorieChoiceBox.setItems(FXCollections.observableArrayList(categorieP.values()));
        categorieChoiceBox.setValue(pa.getCategorie());

             File file = new File(pa.getImage());
               if (file.exists()) {
                   Image image1 = new Image(file.toURI().toString());
                   image.setImage(image1);
                   File file1 = new File(pa.getLogo()); // Corrected from pa.getImage() to pa.getLogo()
                   if (file1.exists()) {
                       Image logo1= new Image(file1.toURI().toString());
                       logo.setImage(logo1);
                   }

               }

        }
    }