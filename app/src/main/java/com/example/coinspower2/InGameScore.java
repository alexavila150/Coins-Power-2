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

public class InGameScore extends AppCompatActivity{
    static Player bluePlayer;
    static Player orangePlayer;
    static int moveNumber;
    static HashMap<String, ImageView> imageViewMap;


    public InGameScore(ImageView[] blues, ImageView[] oranges, ImageView[][] boardViews){
        bluePlayer = new Player(blues);
        orangePlayer = new Player(oranges);
        moveNumber = 0;
    }


    public void start(){
        bluePlayer.start();
        orangePlayer.reset();
        moveNumber = 0;
    }

    public void changeTurn(){
        bluePlayer.changeTurn();
        orangePlayer.changeTurn();
        increaseMove();
    }

    public boolean turnIsMax(){
        return moveNumber == 16;
    }

    public void increaseMove(){
        Log.i("moveNumber", moveNumber + "");
        moveNumber++;
    }



}
