package tests;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class recaptcha extends Application {

    private static final String SITE_KEY = "6LcWzYYpAAAAADil1UtaRYiVrKZCHp2IDyYkLWlb";

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Loading the reCAPTCHA page with the site key
        String url = "https://www.google.com/recaptcha/api/fallback?k=" + SITE_KEY;
        webEngine.load(url);

        // Checking the loading state
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // The reCAPTCHA page is loaded, you can execute your logic here
                // For example, listening to reCAPTCHA events and validating the response

                // Adding a listener to detect clicks on the reCAPTCHA submit button
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", new JavaBridge(webEngine)); // Passing webEngine as an argument
                webEngine.executeScript("document.getElementById('submit').addEventListener('click', function() { java.onRecaptchaSubmit(); })");
            }
        });

        StackPane root = new StackPane(webView); // Creating a StackPane with the WebView
        Scene scene = new Scene(root, 600, 400); // Creating the scene with the StackPane

        primaryStage.setScene(scene);
        primaryStage.setTitle("reCAPTCHA Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
