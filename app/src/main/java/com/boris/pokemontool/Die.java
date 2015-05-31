package com.boris.pokemontool;

import java.util.Random;


public class Die {
    private int numberOfSides;

    public Die(int numberOfSides){
        this.numberOfSides = numberOfSides;
    }

    public Die(){
        this.numberOfSides = 2;
    }

    public int roll (){
        int x;

        Random r = new Random();
        x = r.nextInt(numberOfSides) + 1;

        return x;
    }
}
