package com.exercise;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"));
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setTitle("Generate Chart");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/images/barchart.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
