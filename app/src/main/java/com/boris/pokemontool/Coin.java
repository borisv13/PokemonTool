package com.boris.pokemontool;

import java.util.Random;


public class Coin
{
    public int toss(){
        Random r = new Random();
        return (r.nextBoolean()) ? 0 : 1;
    }
}
