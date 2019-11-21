package com.example.coinspower2;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class InGameScore extends MainActivity{
    Player bluePlayer;
    Player orangePlayer;
    String turn;
    int moveNumber;
    HashMap<String, ImageView> imageViewMap;


    public InGameScore(ImageView[] blues, ImageView[] oranges, ImageView[][] boardViews){
        bluePlayer = new Player(blues);
        orangePlayer = new Player(oranges);
        moveNumber = 0;
        turn = "blue";
    }

    public void start(){
        bluePlayer.start();
        orangePlayer.reset();
        moveNumber = 0;
        turn = "blue";
    }

    public void changeTurn(){
        bluePlayer.changeTurn();
        orangePlayer.changeTurn();
        increaseMove();
        if("blue".equals(turn)){
            turn = "orange";
        }else{
            turn = "blue";
        }
    }

    public boolean turnIsMax(){
        return moveNumber == 30;
    }

    public void increaseMove(){
        Log.i("moveNumber", moveNumber + "");
        moveNumber++;
    }

    public void addPoints(int points){
        if ("blue".equals(turn)){
            bluePlayer.score += points;
        }else{
            orangePlayer.score += points;
        }
    }

}
