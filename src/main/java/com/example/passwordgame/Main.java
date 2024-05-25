/* Programmer: Cody Bassler
   Assignment: Final Project - Password Game
   Purpose: Create a password game using JavaFX
   Date modified: 03/22/2023
   IDE/Tool used: IntelliJ IDEA
   Java version: OpenJDK 17.0.6
   JavaFX version: 17.0.2
*/
package com.example.passwordgame;

import com.example.passwordgame.scenes.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setResizable(false);
        SceneManager.firstScene(stage);
        stage.show();

        // Prevent closing program password.txt is cleared
        setCloseFunctionality(stage);
    }

    private static void setCloseFunctionality(Stage stage) {
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event-> {
            event.consume(); // Don't exit program when X is pressed
                clearPasswordFile(); // Clear password text, then exit
                System.exit(0);
        }) ;
    }

    // Clear the passwords.txt file
    public static void clearPasswordFile() {
        try {
            RandomAccessFile file = new RandomAccessFile("passwords.txt", "rwd");
            file.setLength(0);
            file.close();
            System.out.println("Cleared passwords.txt");
        }
        catch (IOException e) {
            System.out.println("Unable to delete passwords.txt");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}