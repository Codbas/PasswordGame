// SceneBuilders need these methods to work with SceneManager
package com.example.passwordgame.scenes;
import javafx.scene.Scene;

public interface SceneBuilder {
    Scene getScene();

    String getTitle();
}