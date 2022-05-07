package com.example.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final  int WIDTH = 8;
    public static  final  int HEIGHT = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
        for (int y=0;y<HEIGHT;y++){
            for (int x = 0;x<WIDTH;x++){
                Tile tile = new Tile((x+y)%2==0,x,y);

                tileGroup.getChildren().add(tile);
            }
        }
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}