package com.example.coinspower2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Board extends MainActivity {
    Box[][] boardBoxes;
    Box[] blueBoxes;
    Box[] orangeBoxes;
    ImageView[][] view;

    public Board(ImageView[][] boardViews, ImageView[] blueViews, ImageView[] orangeViews){
        //Create board boxes
        boardBoxes = new Box[5][5];
        for(int i = 0; i < boardViews.length; i++){
            for(int j = 0; j < boardViews[i].length; j++){
                boardBoxes[i][j] = new Box(boardViews[i][j]);
            }
        }

        //Create blue boxes
        blueBoxes = new Box[5];
        for(int i = 0; i < blueViews.length; i++){
            blueBoxes[i] = new Box(blueViews[i]);
        }

        //Create orange boxes
        orangeBoxes = new Box[5];
        for(int i = 0; i < orangeViews.length; i++){
            orangeBoxes[i] = new Box(orangeViews[i]);
        }

        view = boardViews;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
