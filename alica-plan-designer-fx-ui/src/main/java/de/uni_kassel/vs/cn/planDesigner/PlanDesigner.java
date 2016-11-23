package de.uni_kassel.vs.cn.planDesigner;
/**
 * Created by marci on 16.10.16.
 */

import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PlanDesigner extends Application {

    private MainController mainController;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("test.fxml"));
        Parent root = fxmlLoader.load();
        mainController = fxmlLoader.getController();
        primaryStage.setTitle("Carpe Noctem Plan Designer");
        primaryStage.setScene(new Scene(root));

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }
}
