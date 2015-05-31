package com.boris.pokemontool;

import java.util.Random;


public class Dice {
    private int numberOfSides;

    public Dice (int numberOfSides){
        this.numberOfSides = numberOfSides;
    }

    public Dice (){
        this.numberOfSides = 2;
    }

    public int roll (){
        int x;

        Random r = new Random();
        x = r.nextInt(numberOfSides) + 1;

        return x;
    }
}
