package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.DataSingleton;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/AppWindow.fxml"));
        primaryStage.setTitle("ToDo list planner");
        primaryStage.setScene(new Scene(root, 800, 300));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        try{
            DataSingleton.getData().loadData();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        try{
            DataSingleton.getData().saveData();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
