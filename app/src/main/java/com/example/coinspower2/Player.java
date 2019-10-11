package com.example.coinspower2;



import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Player extends AppCompatActivity {
    int score = 0;
    boolean isNext = false;
    Piece selectedPiece;
    Piece[] pieces;

    public Player(ImageView[] imageViews){
        pieces = new Piece[5];
        for(int i = 0; i < 5; i++){
            pieces[i] = new Piece(imageViews[i]);
        }
    }

    public void reset(){
        //reset attributes
        score = 0;
        isNext = false;
        selectedPiece = null;

        //reset piece
    }

    public void start(){
        reset();
        isNext = true;
    }

    public void changeTurn(){
        isNext = !isNext;
    }
}
