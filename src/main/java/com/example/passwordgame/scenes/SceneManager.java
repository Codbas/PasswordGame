// SceneManager manages the Scenes for the Stage of the program
package com.example.passwordgame.scenes;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneManager {

    // Array of all scenes for program
    private static final SceneBuilder[] sceneBuilders = {
            new PasswordBuilder(),
            new PasswordGame(),
            new DisplayPasswords()
    };

    // Prevent instantiation
    private SceneManager() {}

    // Load the first Scene
    public static void firstScene(Stage stage) {
        stage.setScene(sceneBuilders[0].getScene());
        stage.setTitle(sceneBuilders[0].getTitle());
    }

    // Loads the next scene in the sceneBuilders list
    public static void nextScene(ActionEvent event) {
        Stage stage = getStage(event);

        // Find index of current scene
        int currentIndex = getSceneIndex(stage);
        int nextIndex = currentIndex + 1;

        // If last scene... do something
        if (nextIndex >= sceneBuilders.length) {
            nextIndex = sceneBuilders.length - 1;
            System.out.println("No more Scenes left!");
        }

        // Load next scene
        stage.setScene(sceneBuilders[nextIndex].getScene());
        stage.setTitle(sceneBuilders[nextIndex].getTitle());
    }

    // Reload current scene
    public static void resetScene(ActionEvent event) {
        Stage stage = getStage(event);
        System.out.println("Resetting Scene: " + stage.getTitle());
        int currentIndex = getSceneIndex(stage);
        stage.setScene(sceneBuilders[currentIndex].getScene());
    }

    // Get the stage from an ActionEvent
    public static Stage getStage(ActionEvent event) {
        Node node = (Node) event.getSource();
        return (Stage) node.getScene().getWindow();
    }

    // Get the index of the scene in a Stage
    private static int getSceneIndex(Stage stage) {
        int currentIndex = 0;
        // Loop through sceneBuilders to find current scene by matching titles
        for (SceneBuilder sceneBuilder : sceneBuilders) {
            if (stage.getTitle().equals(sceneBuilder.getTitle()))
                return currentIndex;
            currentIndex++;
        }

        // Scene not found...
        System.out.println("SceneManager unable to find current Scene");
        System.out.println("Current Scene Title: " + stage.getTitle());
        return -1; // This will probably crash the program. Shouldn't ever happen though
    }
}