package com.example.checkers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    public Label whiteLabel = new Label("321");
    @FXML
    public Label redLabel = new Label("312");

    int redCounter = 0;
    int whiteCounter = 0;

    public int getRedCounter() {
        return redCounter;
    }

    public void setRedCounter(int redCounter) {
        this.redCounter = redCounter;
    }

    public void setWhiteLabel(Label whiteLabel) {
        this.whiteLabel = whiteLabel;
    }

    public void setRedLabel(int number) {
        redLabel.setText(Integer.toString(number));
    }

    public int getWhiteCounter() {
        return whiteCounter;
    }

}
