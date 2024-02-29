package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PubAdd.fxml"));
        try {
            //package li fi wostou l package lkol
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Bienvenue"); // tatala3 fel barre  l fouganiya
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}
