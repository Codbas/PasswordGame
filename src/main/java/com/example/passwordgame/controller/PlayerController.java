// Controller for player in PasswordGameScene
package com.example.passwordgame.controller;
import javafx.scene.input.KeyCode;

public class PlayerController {
    private double x;
    private double y;
    private final double speed;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public PlayerController() {
        this.x = 0;
        this.y = 0;
        this.speed = 0.6;
    }

    public void handleKeyPress(KeyCode keyCode) {
        switch (keyCode) {
            case A -> moveLeft = true;
            case D -> moveRight = true;
            default -> {
            }
        }
    }

    public void handleKeyRelease(KeyCode keyCode) {
        switch (keyCode) {
            case A -> moveLeft = false;
            case D -> moveRight = false;
            default -> {
            }
        }
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public boolean moveLeft() {
        return moveLeft;
    }
    public boolean moveRight() {
        return moveRight;
    }
    public double getSpeed() {
        return speed;
    }
}
