package com.example.coinspower2;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Box extends AppCompatActivity {
    ImageView imageView;
    ArrayList<ImageView> neighbors;
    ArrayList<ImageView> allowedBoxes;

    public Box(ImageView imageView){
        this.imageView = imageView;
        this.neighbors = new ArrayList<ImageView>();
        this.allowedBoxes = new ArrayList<ImageView>();
    }

    public Box(ImageView imageView, ArrayList<ImageView> neighbors, ArrayList<ImageView> allowedBoxes){
        this.imageView = imageView;
        this.neighbors = neighbors;
        this.allowedBoxes = allowedBoxes;
    }
}
