package com.boris.pokemontool;

/**
 * Created by mightymyron on 6/14/2015.
 */
public class Toggle {
    private boolean state;

    public Toggle() {this.state = false;}
    public Toggle(boolean state) {this.state = state;}

    public boolean getState() {return this.state;}
    public void on() {this.state = true;}
    public void off() {this.state = false;}
    public boolean toggle() {
        this.state = !this.state;
        return this.state;
    }
}
