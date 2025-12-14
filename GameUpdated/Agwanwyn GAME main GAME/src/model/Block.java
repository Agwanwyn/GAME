package model;

import java.awt.Color;


public class Block {
    private String letter;
    private int x, y;
    private int size; 
    private Color color;


    public Block(String letter, int x, int y, Color color) {
        this.letter = letter;
        this.x = x;
        this.y = y;
        size = 50;
        this.color = color;
    }

    public String getLetter() { return letter; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Color getColor() {
        return color;
    }

    
}
