package com.example.checkers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    public Label whiteLabel = new Label();
    @FXML
    public Label redLabel = new Label();


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
    public String playerWon() {
        if (redCounter == 0) {
            return "Gracz 2 wygral";
        }else if (whiteCounter ==0){
            return "Gracz 1 wygral";
        }
        else return  "";
    }
}
