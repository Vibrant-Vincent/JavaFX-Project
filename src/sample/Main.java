package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double x,y;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
//        MysqlConnect mysqlConnect = new MysqlConnect();
//        mysqlConnect.connect();

//        //set stage borderless
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//
//        root.setOnMousePressed(mouseEvent -> {
//            x = mouseEvent.getSceneX();
//            y = mouseEvent.getSceneY();
//        });
//        root.setOnMouseDragged(mouseEvent -> {
//            primaryStage.setX(mouseEvent.getSceneX() - x);
//            primaryStage.setY(mouseEvent.getSceneY() - y);
//        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
