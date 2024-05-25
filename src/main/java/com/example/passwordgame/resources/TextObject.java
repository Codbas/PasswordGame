// TextObjects hold data for words to be displayed horizontally
// in GraphicsContext while retaining the ability to move
package com.example.passwordgame.resources;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextObject {
    private final double SPACING = 20; // pixels to move each letter down by
    private final double speed = 0.20; // Speed that the word falls
    private final int length; // Length of the word
    private final double[] x; // Array for x coordinates (could actually be one double...)
    private final double[] y; // Array for y coordinates
    private final String[] chars; // Array of letters to create the word
    private double height; // The height of the word... ~9px per character
    private boolean active;
    private boolean caught;
    public boolean falling;
    private double timeSinceCreated;
    private double timeSinceDisabled;


    public TextObject(String passwordPiece, double x, double y) {
        active = true;
        caught = false;
        falling = false;
        timeSinceCreated = 0;
        timeSinceDisabled = 0;
        length = passwordPiece.length(); // Set the length
        chars = new String[length];

        // Set the height
        setHeight();

        for (int i = 0; i < length; i++) { // Create String array
            chars[i] = Character.toString(passwordPiece.charAt(i));
        }

        // Instantiate x and y for each letter
        this.x = new double[length];
        this.y = new double[length];

        setX(x);
        setY(y);
    }

    private void setHeight() {
        height = 5;
        for (int i = 1; i < chars.length; i++) {
            height += SPACING;
        }
    }

    public double getTimeSinceDisabled() {
        return timeSinceDisabled;
    }
    public void setTimeSinceDisabled(double timeSinceDisabled) {
        this.timeSinceDisabled = timeSinceDisabled;
    }
    public double getTimeSinceCreated() {
        return timeSinceCreated;
    }
    public void setTimeSinceCreated(double timeSinceCreated) {
        this.timeSinceCreated = timeSinceCreated;
    }
    public double getHeight() {
        return height;
    }
    public double getSpeed() {
        return speed;
    }
    public int length() {
        return length;
    }
    public double getX() {
        return x[0];
    }
    public double getY(int index) {
        return y[index];
    }
    // Sets the x of all letters so that the word moves together
    public void setX(double x) {
        for (int i = 0; i < length; i++) {
            this.x[i] =  x;
        }
    }

    // Sets the y of all letters with spacing so the word moves together
    public void setY(double y) {
        for (int i = 0; i < length; i++) {
            this.y[i] = y + (i * SPACING);
        }
    }
    public String getText(int index) {
        return chars[index];
    }
    public void disable() {
        active = false;
    }
    public void caught() {
        caught = true;
    }
    public boolean active() {
        return active;
    }
    public boolean isCaught() {
        return caught;
    }
    public double width() {
        Font font = new Font("Comic Sans MS", 18);
        Text text = new Text("A");
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }
    private double charWidth(String character) {
        Font font = new Font("Comic Sans MS", 18);
        Text text = new Text(character);
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }
    public String getString() {
        return String.join("", chars);
    }
}