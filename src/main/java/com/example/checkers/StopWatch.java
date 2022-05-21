package com.example.checkers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.Timer;

public class StopWatch extends StackPane {
    StringProperty text = new SimpleStringProperty();
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;


    void change(StringProperty text) {
        if(millis == 1000) {
            secs++;
            millis = 0;
        }
        if(secs == 60) {
            mins++;
            secs = 0;
        }
        text.set(((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs + ":"
                + (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++));
    }
    public StopWatch() {

        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change(text);

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.playFromStart();
    }
    void reset(){
        mins = 0;
        secs = 0;
        millis = 0;
    }


}
