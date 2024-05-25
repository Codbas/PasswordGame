// Utility class to detect collisions between player and TextObjects
package com.example.passwordgame.util;
import com.example.passwordgame.controller.PlayerController;
import com.example.passwordgame.resources.TextObject;

public class Collision {
    private Collision() {} // Prevent instantiation

    // Return true if passed objects intersect
    public static boolean intersects(TextObject text, PlayerController player, double playerWidth, double playerHeight) {
        // Player -> GraphicsContext.fillRect() : Upper left corner = (x, y)
        // Text Object : Bottom left corner = (x, y)
        double playerX1 = player.getX();                        // Left X
        double playerX2 = player.getX() + playerWidth;          // Right X
        double playerY1 = player.getY();                        // Top Y
        double playerY2 = player.getY() - playerHeight;         // Bottom Y
        double textX1 = text.getX();                     // Left X
        double textX2 = text.getX() + text.width();      // Right X
        double textY2 = text.getY(text.length() - 1);     // Bottom Y

        // if left corner of text is between player and text is at or below player height
        if ((textX1 > playerX1 && textX1 < playerX2) && textY2 >= playerY1)
            return true;

        // if right corner of text is between player and text is at or below player height
        if ((textX2 > playerX1 && textX2 < playerX2) && textY2 >= playerY1)
            return true;

        return false;
    }
}