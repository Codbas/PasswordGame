// Final scene to display passwords that were created
package com.example.passwordgame.scenes;
import com.example.passwordgame.Main;
import com.example.passwordgame.util.RandomReadFile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DisplayPasswords implements SceneBuilder{
    private final String title = "Passwords";
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 400;

    private Label instructionsLabel;
    private Label passwordsLabel;
    private Button endButton;
    private Button restartButton;
    private HBox buttonHBox;
    private VBox container; // Root node

    private void initControls() {
        instructionsLabel = new Label("""
                Here are the passwords you created
                They will be PERMANENTLY deleted when you leave this screen""");

        passwordsLabel = new Label();

        endButton = new Button("Exit");
        restartButton = new Button("Restart Program");

        buttonHBox = new HBox(restartButton, endButton);
        container = new VBox(instructionsLabel, passwordsLabel, buttonHBox);

        restartButton.setOnAction(event -> {
            Main.clearPasswordFile();
            SceneManager.firstScene(SceneManager.getStage(event));
        });

        endButton.setOnAction(event-> {
            Main.clearPasswordFile();
            System.exit(0);
        });
    }

    private void setLayout() {
        instructionsLabel.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");
        passwordsLabel.setStyle("-fx-font-size: 18px; -fx-padding-left: 100px;");

        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(30);

        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(35);
    }

    private void getPasswords() {
        try {
            RandomReadFile readFile = new RandomReadFile("passwords.txt");
            int numberOfPasswords = (int)readFile.numberOfRecords();
            StringBuilder passwords = new StringBuilder();

            // Read passwords from file
            for (int i = 0; i < numberOfPasswords; i++) {
                passwords.append("Password ").append(i + 1).append(": ");
                passwords.append(readFile.getRecordAt(i));
                passwords.append("\n");
            }
            readFile.close();

            // No passwords found
            if (numberOfPasswords == 0) {
                passwords.append("passwords.txt is empty");
            }

            // Set the passwordLabel text to saved passwords
            passwordsLabel.setText(passwords.toString());
        }
        catch(Exception e) {
            System.out.println("Error opening or reading file");
        }
    }

    public Scene getScene() {
        initControls();
        setLayout();
        getPasswords();
        return new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public String getTitle() {
        return title;
    }
}
