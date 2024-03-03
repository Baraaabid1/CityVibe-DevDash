package Controllers;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.math3.util.Precision;
import services.ReclamationService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class StatisticsController {

    @FXML
    private BarChart<String, Integer> barchart;

    @FXML
    private PieChart pieChartType;

    @FXML
    private PieChart pieChatApropo;

    @FXML
    private Text typetext;

    @FXML
    private Text categotext;
    private PieChart.Data previousClickedData;


    private ReclamationService reclamationService = new ReclamationService();;

    public void initialize() {
        pieChartType.setVisible(true);
        pieChatApropo.setVisible(false);
        barchart.setVisible(false);
        categotext.setVisible(false);
        typetext.setVisible(true);
        updateTypePieChart();
        updateApropoPieChart();


    }

    private void updateTypePieChart() {
            try {
                Map<String, Integer> typeFrequency = reclamationService.getTypeFrequency();

                ObservableList<PieChart.Data> typePieChartData = FXCollections.observableArrayList();
                int total = typeFrequency.values().stream().mapToInt(Integer::intValue).sum();
                for (Map.Entry<String, Integer> entry : typeFrequency.entrySet()) {
                    double percentage = Precision.round(((double) entry.getValue() / total) * 100, 2);
                    String label = entry.getKey() + " (" + percentage + "%)";
                    PieChart.Data data = new PieChart.Data(label, entry.getValue());
                    typePieChartData.add(data);
                    Tooltip tooltip = new Tooltip(label);
                    Tooltip.install(data.getNode(), tooltip);
                }

                pieChartType.setData(typePieChartData);
                pieChartType.setLegendSide(Side.LEFT); // Set legend position
                pieChartType.setLabelsVisible(true); // Hide default labels on chart
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    private void updateApropoPieChart() {
        try {
            Map<String, Integer> apropoFrequency = reclamationService.getApropoFrequency();

            ObservableList<PieChart.Data> apropoPieChartData = FXCollections.observableArrayList();

            // Check if the map contains the specific categories
            if (apropoFrequency.containsKey("Page")) {
                PieChart.Data pageData = new PieChart.Data("Page", apropoFrequency.get("Page"));
                pageData.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseClicked(event -> {
                            // Handle the click event for "Page" segment
                            System.out.println("Page segment clicked!");
                            // Add your custom action here

                            // Remove effect from previously clicked segment
                            removeEffect(previousClickedData);

                            // Add a visual effect to the clicked segment
                            addEffect(newValue);

                            // Update previousClickedData
                            previousClickedData = pageData;
                            updateBarChart("Page");
                            barchart.setVisible(true);


                        });
                    }
                });
                apropoPieChartData.add(pageData);

            }
            if (apropoFrequency.containsKey("Evenement")) {
                PieChart.Data evenementData = new PieChart.Data("Evenement", apropoFrequency.get("Evenement"));
                evenementData.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseClicked(event -> {
                            // Handle the click event for "Evenement" segment
                            System.out.println("Evenement segment clicked!");
                            // Add your custom action here

                            // Remove effect from previously clicked segment
                            removeEffect(previousClickedData);

                            // Add a visual effect to the clicked segment
                            addEffect(newValue);

                            // Update previousClickedData
                            previousClickedData = evenementData;
                            updateBarChart("Evenement");
                            barchart.setVisible(true);



                        });
                    }
                });
                apropoPieChartData.add(evenementData);
            }
            if (apropoFrequency.containsKey("Autre")) {
                PieChart.Data autreData = new PieChart.Data("Autre", apropoFrequency.get("Autre"));
                autreData.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseClicked(event -> {
                            // Handle the click event for "Autre" segment
                            System.out.println("Autre segment clicked!");
                            // Add your custom action here

                            // Remove effect from previously clicked segment
                            removeEffect(previousClickedData);

                            // Add a visual effect to the clicked segment
                            addEffect(newValue);

                            // Update previousClickedData
                            previousClickedData = autreData;
                            barchart.setVisible(false);






                        });
                    }
                });
                apropoPieChartData.add(autreData);
            }

            pieChatApropo.setData(apropoPieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEffect(Node node) {
        node.setStyle(" -fx-pie-constant: 20; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 0, 0);");

        // Create a TranslateTransition for a floating animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), node);
        translateTransition.setFromY(0);
        translateTransition.setToY(-20);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);

        // Create a ScaleTransition for making the segment bigger
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), node);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);

        // Play the animations
        translateTransition.play();
        scaleTransition.play();
    }

    private void removeEffect(PieChart.Data data) {
        if (data != null) {
            Node node = data.getNode();
            node.setStyle("");
            // Create the TranslateTransition for reversing the floating animation
            TranslateTransition reverseTranslateTransition = new TranslateTransition(Duration.seconds(1), node);
            reverseTranslateTransition.setToY(0); // Set the final Y position to 0
            reverseTranslateTransition.setCycleCount(1); // Run the animation only once
            reverseTranslateTransition.setAutoReverse(false); // Do not auto-reverse

            // Create the ScaleTransition for reversing the scaling animation
            ScaleTransition reverseScaleTransition = new ScaleTransition(Duration.seconds(0.5), node);
            reverseScaleTransition.setToX(1); // Set the final scale to 1
            reverseScaleTransition.setToY(1); // Set the final scale to 1
            reverseScaleTransition.setCycleCount(1); // Run the animation only once
            reverseScaleTransition.setAutoReverse(false); // Do not auto-reverse

            // Play the reverse animations
            reverseTranslateTransition.play();
            reverseScaleTransition.play();
        }

    }

    private void updateBarChart(String dataType) {
        try {
            // Get the frequency of data
            Map<String, Integer> frequencyMap;
            if ("Evenement".equals(dataType)) {
                frequencyMap = reclamationService.getEvenementFrequency();
            } else if ("Page".equals(dataType)) {
                frequencyMap = reclamationService.getPageFrequency();
            } else {
                throw new IllegalArgumentException("Invalid data type: " + dataType);
            }

            // Create a new series for the BarChart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Add data to the series and collect labels
            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            // Clear existing data and categories in the BarChart
            barchart.getData().clear();
            CategoryAxis xAxis = (CategoryAxis) barchart.getXAxis();
            xAxis.getCategories().clear();

            // Add the series to the BarChart
            barchart.getData().setAll(series);

            // Set labels to the x-axis
            xAxis.setCategories(FXCollections.observableArrayList(frequencyMap.keySet()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void par_catergorie(ActionEvent event) {
        pieChartType.setVisible(false);
        pieChatApropo.setVisible(true);
        barchart.setVisible(false);
        categotext.setVisible(true);
        typetext.setVisible(false);


    }

    @FXML
    void partype(ActionEvent event) {
        pieChartType.setVisible(true);
        pieChatApropo.setVisible(false);
        barchart.setVisible(false);
        categotext.setVisible(false);
        typetext.setVisible(true);
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionRec.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }










    @FXML
    private TextField seachbar;

    @FXML
    void Button_Acceuil(ActionEvent event) {

    }

    @FXML
    void Button_Events(ActionEvent event) {

    }

    @FXML
    void Button_Help(ActionEvent event) {

    }

    @FXML
    void Button_Lieux(ActionEvent event) {

    }

    @FXML
    void Button_Logout(ActionEvent event) {

    }

    @FXML
    void Button_Parametres(ActionEvent event) {

    }

    @FXML
    void Button_Profil(ActionEvent event) {

    }

    @FXML
    void Button_Reclamation(ActionEvent event) {

    }

    @FXML
    void Button_Reservation(ActionEvent event) {

    }

    @FXML
    void Button_Transport(ActionEvent event) {

    }

    @FXML
    void EcoModeButton(ActionEvent event) {

    }





}
