package com.example.checkers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StopWatch extends StackPane {
    StringProperty text = new SimpleStringProperty();
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;


    void change(StringProperty text) {
        if (millis == 1000) {
            secs++;
            millis = 0;
        }
        if (secs == 60) {
            mins++;
            secs = 0;
        }
        text.set(((((mins / 10) == 0) ? "0" : "") + mins + ":"
                + (((secs / 10) == 0) ? "0" : "") + secs + ":"
                + (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++));
    }

    public StopWatch() {

        timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> change(text)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.playFromStart();
    }

    void reset() {
        mins = 0;
        secs = 0;
        millis = 0;
    }


}
