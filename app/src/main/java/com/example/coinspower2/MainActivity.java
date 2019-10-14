package com.example.coinspower2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    //Views
    ImageView[] blues;
    ImageView[] oranges;
    ImageView[][] boardViews;

    ImageView selectedView;
    ImageView destView;

    TextView turnIndicator;

    //Classes and Variables
    InGameScore inGameScore;
    Board board;
    HashMap<String, Integer> images;
    HashMap<ImageView, Box> boxes;
    HashSet<String> blueTags;
    HashSet<String> orangeTags;

    public void selectPiece(View view){
        if(selectedView == null || selectedView == view || isPiece(view)){
            selectedView = (ImageView) view;
            Log.i("selectedView Tag", selectedView.getTag().toString());
            if(selectedView.getTag().toString().equals("null")){
                selectedView = null;
                return;
            }
            return;
        }

        //Check if piece can move into the next view
        if (isAllowedBox(view)){
            destView = (ImageView) view;
            changeImages();
            Log.i("Number of Neighbors",getNumberOfNeighbors() + "");
            selectedView = null;
            destView = null;
        }
    }

    public void changeImages(){
        //if box is occupied do not change
        if(!destView.getTag().toString().equals("null")){
            Log.i("square", "occupied");
            selectedView = destView;
            destView = null;
            return;
        }

        //Exchange tags
        Object temp = selectedView.getTag();
        selectedView.setTag(destView.getTag());
        destView.setTag(temp);

        //Set Corresponding images
        String tag = selectedView.getTag().toString();
        Drawable drawable;

        //Set selectedView image
        if(tag.equals("null")){
            selectedView.setImageDrawable(null);
        }else{
            drawable = getResources().getDrawable(images.get(selectedView.getTag().toString()));
            selectedView.setImageDrawable(drawable);
        }

        //Set destView image
        tag = destView.getTag().toString();
        if(tag.equals("null")){
            selectedView.setImageDrawable(null);
        }else {
            drawable = getResources().getDrawable(images.get(destView.getTag().toString()));
            destView.setImageDrawable(drawable);
        }

        //Change turn
        changeTurn();

        //if game finished
        if(inGameScore.turnIsMax()){
            setViewsClickable(false);
            turnIndicator.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    //change the color and update
    public void changeTurn(){
        int orange = Color.parseColor("#FF5722");
        int blue = Color.parseColor("#45B6FE");

        inGameScore.changeTurn();

        if("blue".equals(inGameScore.turn)){
            turnIndicator.setBackgroundColor(blue);
        }else{
            turnIndicator.setBackgroundColor(orange);
        }
    }

    public boolean isBluePiece(View view){
        return blueTags.contains(view.getTag().toString());
    }

    public boolean isOrangePiece(View view){
        return orangeTags.contains(view.getTag().toString());
    }

    public boolean isPiece(View view){
        return isBluePiece(view) || isOrangePiece(view);
    }

    public boolean isAllowedBox(View view){
        ArrayList<ImageView> allowedBoxes = boxes.get(selectedView).allowedBoxes;
        return allowedBoxes.contains(view);
    }

    public boolean isNeighbor(View view){
        ArrayList<ImageView> neighbors = boxes.get(selectedView).neighbors;
        return neighbors.contains(view);
    }

    public int getNumberOfNeighbors(){
        ArrayList<ImageView> neighbors = boxes.get(destView).neighbors;
        int count = 0;
        for(int i = 0; i < neighbors.size(); i++){
            if (isPiece(neighbors.get(i))){
                count++;
            }
        }
        return count;
    }

    //Set "images" HashMap
    //Key: String "blue1"
    //Value: int image address
    public void prepareImagesHashMap(){
        images = new HashMap<String, Integer>();
        images.put("blue1", R.drawable.blue1);
        images.put("blue2", R.drawable.blue2);
        images.put("blue3", R.drawable.blue3);
        images.put("blue4", R.drawable.blue4);
        images.put("blue5", R.drawable.blue5);

        images.put("orange1", R.drawable.orange1);
        images.put("orange2", R.drawable.orange2);
        images.put("orange3", R.drawable.orange3);
        images.put("orange4", R.drawable.orange4);
        images.put("orange5", R.drawable.orange5);
    }

    //Set "boxes" HashMap
    //Key: ImageView
    //Value: Box
    public void prepareBoxesHashMap(){
        boxes = new HashMap<ImageView, Box>();
        for(int i = 0; i < 5; i++){
            boxes.put(blues[i], board.blueBoxes[i]);
            boxes.put(oranges[i], board.orangeBoxes[i]);
            for(int j = 0; j < 5; j++){
                boxes.put(boardViews[i][j], board.boardBoxes[i][j]);
            }
        }

    }

    //set images for the coins
    public void setBluesAndOranges(){
        blues = new ImageView[5];
        blues[0] = findViewById(R.id.blue1);
        blues[1] = findViewById(R.id.blue2);
        blues[2] = findViewById(R.id.blue3);
        blues[3] = findViewById(R.id.blue4);
        blues[4] = findViewById(R.id.blue5);

        oranges = new ImageView[5];
        oranges[0] = findViewById(R.id.orange1);
        oranges[1] = findViewById(R.id.orange2);
        oranges[2] = findViewById(R.id.orange3);
        oranges[3] = findViewById(R.id.orange4);
        oranges[4] = findViewById(R.id.orange5);
    }

    //Set views for the board
    public void initBoardViews(){
        boardViews = new ImageView[5][5];

        boardViews[0][0] = findViewById(R.id.row0col0);
        boardViews[0][1] = findViewById(R.id.row0col1);
        boardViews[0][2] = findViewById(R.id.row0col2);
        boardViews[0][3] = findViewById(R.id.row0col3);
        boardViews[0][4] = findViewById(R.id.row0col4);

        boardViews[1][0] = findViewById(R.id.row1col0);
        boardViews[1][1] = findViewById(R.id.row1col1);
        boardViews[1][2] = findViewById(R.id.row1col2);
        boardViews[1][3] = findViewById(R.id.row1col3);
        boardViews[1][4] = findViewById(R.id.row1col4);

        boardViews[2][0] = findViewById(R.id.row2col0);
        boardViews[2][1] = findViewById(R.id.row2col1);
        boardViews[2][2] = findViewById(R.id.row2col2);
        boardViews[2][3] = findViewById(R.id.row2col3);
        boardViews[2][4] = findViewById(R.id.row2col4);

        boardViews[3][0] = findViewById(R.id.row3col0);
        boardViews[3][1] = findViewById(R.id.row3col1);
        boardViews[3][2] = findViewById(R.id.row3col2);
        boardViews[3][3] = findViewById(R.id.row3col3);
        boardViews[3][4] = findViewById(R.id.row3col4);

        boardViews[4][0] = findViewById(R.id.row4col0);
        boardViews[4][1] = findViewById(R.id.row4col1);
        boardViews[4][2] = findViewById(R.id.row4col2);
        boardViews[4][3] = findViewById(R.id.row4col3);
        boardViews[4][4] = findViewById(R.id.row4col4);
    }

    //Sets the boxes that are allowed to be move into
    //and the neighbors for each box on the board
    public void addNeighborsAndAllowedBoxes(){
        //add corner neighbors and allowed boxes
        addNeighbor(boardViews[0][1], board.boardBoxes[0][0]);
        addNeighbor(boardViews[1][1], board.boardBoxes[0][0]);
        addNeighbor(boardViews[1][0], board.boardBoxes[0][0]);
        addAllowedBox(boardViews[1][0], board.boardBoxes[0][0]);
        addAllowedBox(boardViews[0][1], board.boardBoxes[0][0]);

        addNeighbor(boardViews[0][3], board.boardBoxes[0][4]);
        addNeighbor(boardViews[1][3], board.boardBoxes[0][4]);
        addNeighbor(boardViews[1][4], board.boardBoxes[0][4]);
        addAllowedBox(boardViews[1][4], board.boardBoxes[0][4]);
        addAllowedBox(boardViews[0][3], board.boardBoxes[0][4]);

        addNeighbor(boardViews[3][0]  , board.boardBoxes[4][0]);
        addNeighbor(boardViews[3][1]  , board.boardBoxes[4][0]);
        addNeighbor(boardViews[4][1]  , board.boardBoxes[4][0]);
        addAllowedBox(boardViews[4][1], board.boardBoxes[4][0]);
        addAllowedBox(boardViews[3][0], board.boardBoxes[4][0]);

        addNeighbor(boardViews[4][3]   , board.boardBoxes[4][4]);
        addNeighbor(boardViews[3][3]   , board.boardBoxes[4][4]);
        addNeighbor(boardViews[3][4]   , board.boardBoxes[4][4]);
        addAllowedBox(boardViews[4][3] , board.boardBoxes[4][4]);
        addAllowedBox(boardViews[3][4] , board.boardBoxes[4][4]);

        //add edge neighbors and allowed boxes
        for(int i = 1; i < 4; i++){ // UP
            addNeighbor(boardViews[0][i - 1]  , board.boardBoxes[0][i]);
            addNeighbor(boardViews[0][i + 1]  , board.boardBoxes[0][i]);
            addNeighbor(boardViews[1][i - 1]  , board.boardBoxes[0][i]);
            addNeighbor(boardViews[1][i]      , board.boardBoxes[0][i]);
            addNeighbor(boardViews[1][i + 1]  , board.boardBoxes[0][i]);

            addAllowedBox(boardViews[0][i - 1], board.boardBoxes[0][i]);
            addAllowedBox(boardViews[0][i + 1], board.boardBoxes[0][i]);
            addAllowedBox(boardViews[1][i]    , board.boardBoxes[0][i]);
        }

        for(int i = 1; i < 4; i++){ //LEFT
            addNeighbor(boardViews[i - 1][0]  , board.boardBoxes[i][0]);
            addNeighbor(boardViews[i + 1][0]  , board.boardBoxes[i][0]);
            addNeighbor(boardViews[i - 1][1]  , board.boardBoxes[i][0]);
            addNeighbor(boardViews[i][1]      , board.boardBoxes[i][0]);
            addNeighbor(boardViews[i + 1][1]  , board.boardBoxes[i][0]);

            addAllowedBox(boardViews[i - 1][0], board.boardBoxes[i][0]);
            addAllowedBox(boardViews[i + 1][0], board.boardBoxes[i][0]);
            addAllowedBox(boardViews[i][1]    , board.boardBoxes[i][0]);
        }

        for(int i = 1; i < 4; i++) { //RIGHT
            addNeighbor(boardViews[i - 1][4]  , board.boardBoxes[i][4]);
            addNeighbor(boardViews[i + 1][4]  , board.boardBoxes[i][4]);
            addNeighbor(boardViews[i - 1][3]  , board.boardBoxes[i][4]);
            addNeighbor(boardViews[i][3]      , board.boardBoxes[i][4]);
            addNeighbor(boardViews[i + 1][3]  , board.boardBoxes[i][4]);

            addAllowedBox(boardViews[i - 1][4], board.boardBoxes[i][4]);
            addAllowedBox(boardViews[i + 1][4], board.boardBoxes[i][4]);
            addAllowedBox(boardViews[i][3]    , board.boardBoxes[i][4]);
        }

        for(int i = 1; i < 4; i++){ //DOWN
            addNeighbor(boardViews[4][i - 1], board.boardBoxes[4][i]);
            addNeighbor(boardViews[4][i + 1], board.boardBoxes[4][i]);
            addNeighbor(boardViews[3][i - 1], board.boardBoxes[4][i]);
            addNeighbor(boardViews[3][i]    , board.boardBoxes[4][i]);
            addNeighbor(boardViews[3][i + 1], board.boardBoxes[4][i]);

            addAllowedBox(boardViews[4][i - 1], board.boardBoxes[4][i]);
            addAllowedBox(boardViews[4][i + 1], board.boardBoxes[4][i]);
            addAllowedBox(boardViews[3][i]    , board.boardBoxes[4][i]);
        }

        //add neighbors and allowed boxes in the middle
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                //add neighbors
                addNeighbor(boardViews[i - 1][j - 1], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i - 1][j + 0], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i - 1][j + 1], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i + 0][j - 1], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i + 0][j + 1], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i + 1][j - 1], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i + 1][j + 0], board.boardBoxes[i][j]);
                addNeighbor(boardViews[i + 1][j + 1], board.boardBoxes[i][j]);

                //add allowed boxes
                addAllowedBox(boardViews[i - 1][j + 0], board.boardBoxes[i][j]);
                addAllowedBox(boardViews[i + 0][j - 1], board.boardBoxes[i][j]);
                addAllowedBox(boardViews[i + 0][j + 1], board.boardBoxes[i][j]);
                addAllowedBox(boardViews[i + 1][j + 0], board.boardBoxes[i][j]);
            }
        }

        //add blue and orange allowed boxes
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addAllowedBox(boardViews[i][j], board.blueBoxes[0]);
                addAllowedBox(boardViews[i][j], board.blueBoxes[1]);
                addAllowedBox(boardViews[i][j], board.blueBoxes[2]);
                addAllowedBox(boardViews[i][j], board.blueBoxes[3]);
                addAllowedBox(boardViews[i][j], board.blueBoxes[4]);

                addAllowedBox(boardViews[i][j], board.orangeBoxes[0]);
                addAllowedBox(boardViews[i][j], board.orangeBoxes[1]);
                addAllowedBox(boardViews[i][j], board.orangeBoxes[2]);
                addAllowedBox(boardViews[i][j], board.orangeBoxes[3]);
                addAllowedBox(boardViews[i][j], board.orangeBoxes[4]);
            }
        }


    }

    //Sets if the board is clickable or not
    public void setViewsClickable(Boolean b){
        for(int i = 0; i < 5; i++){
            blues[i].setClickable(b);
            oranges[i].setClickable(b);
            for(int j = 0; j < 5; j++){
                boardViews[i][j].setClickable(b);
            }
        }
    }

    public void addNeighbor(ImageView imageView, Box box){
        box.neighbors.add(imageView);
    }

    public void addAllowedBox(ImageView imageView, Box box){
        box.allowedBoxes.add(imageView);
    }

    public void setPieceTags(){
        blueTags = new HashSet<>();
        orangeTags = new HashSet<>();
        for(int i = 1; i <= 5; i++){
            blueTags.add("blue" + i);
            orangeTags.add("orange" + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBluesAndOranges();
        initBoardViews();
        turnIndicator = findViewById(R.id.turnIndicator);

        inGameScore = new InGameScore(blues, oranges, boardViews);
        inGameScore.start();

        prepareImagesHashMap();
        board = new Board(boardViews, blues, oranges);
        prepareBoxesHashMap();
        addNeighborsAndAllowedBoxes();
        setPieceTags();

    }
}
