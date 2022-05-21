package com.example.checkers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    public Label whiteLabel = new Label();
    @FXML
    public Label redLabel = new Label();
    @FXML
    public Label timerLabel = new Label();

    public int redCounter=12;
    public int whiteCounter=12;


    public void decrementRed(){
        redCounter--;
    }
    public void decrementWhite(){whiteCounter--;}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        whiteLabel.setText(Integer.toString(whiteCounter=12));
        redLabel.setText(Integer.toString(redCounter=12));
    }
}
