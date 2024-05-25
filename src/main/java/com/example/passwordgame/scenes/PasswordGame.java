// Builds the Scene for the PasswordGame
package com.example.passwordgame.scenes;
import com.example.passwordgame.controller.PlayerController;
import com.example.passwordgame.resources.TextObject;
import com.example.passwordgame.resources.TextObjectQueue;
import com.example.passwordgame.util.Collision;
import com.example.passwordgame.util.Password;
import com.example.passwordgame.util.RandomWriteFile;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.List;

public class PasswordGame implements SceneBuilder {
    private final String title = "Password Game";
    private Scene scene;
    private List<String> password;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 800;
    private final int UI_HEIGHT = 100; // window height - ui height
    private final int MS_TO_NS = 1000000; // Multiply to calculate stuff in ms
    private final int PIECE_START_DELAY = 1500 * MS_TO_NS; // delay before pieces start to drop. 1000 = 1 second
    private final int PIECE_DELETE_DELAY = 1000 * MS_TO_NS; // delay before pieces disappear in ms
    private final int PIECE_CAUGHT_DELETE_DELAY = 400 * MS_TO_NS; // Delay before pieces disappear in ms after catching
    private final int SPAWN_DELAY = 700 * MS_TO_NS; // delay before each piece spawns in ms
    private final int PASSWORD_PIECES = 60;
    private final int MAX_PASSWORD_LENGTH = 45;
    private long lastSpawnDeltaTime; // Time since last spawn
    private boolean playing;


    // x coordinates that password pieces can spaw
    private final int[] SPAWN_X_POSITIONS = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250,
                                            275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
                                            525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775};
    private PlayerController player;
    private final int PLAYER_WIDTH = 60; // Width of player
    private final int PLAYER_HEIGHT = 30; // Size of player
    private final Color PLAYER_COLOR = Color.LAWNGREEN;

    private Button tryAgainButton;
    private Button continueButton;

    private Label fpsLabel;
    private Label fpsText;
    private VBox fpsVBox;

    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer timer;
    private StackPane container; // Root node


    // Track time between frames to adjust movement for variable framerate
    private long lastTimeStamp; // time in NS since last frame
    private long deltaTime; // time in NS between frames
    private int frames; // frames since last FPS update
    private long lastFpsUpdate; // time since last FPS update

    public PasswordGame()  {} // I don't think I need this

    TextObjectQueue textObjectQueue; // Queue for all TextObjects
    List<TextObject> currentWords; // List for all current words


    ///////////////////////////////////////////////
    /*                     TOP UI                */
    ///////////////////////////////////////////////

    // Label for Password
    private Label passwordLabel;
    private Label passwordText;

    // Label for pieces remaining
    private Label piecesRemainingLabel;
    private Label piecesRemainingText;

    // Labels for requirements X or ✓
    private Label lengthCheckLabel;
    private Label wordsCheckLabel;
    private Label numberCheckLabel;
    private Label symbolCheckLabel;

    // Labels displaying info for X or ✓
    private Label lengthIconLabel;
    private Label wordsIconLabel;
    private Label numberIconLabel;
    private Label symbolIconLabel;

    // HBoxes to hold X or ✓ labels
    private HBox validLengthHBox;
    private HBox validWordsHBox;
    private HBox validNumbersHBox;
    private HBox validSymbolsHBox;

    // VBox for Password labels
    private VBox passwordVBox;

    // VBox for valid password HBoxes
    private VBox passwordValidationVBox;

    // VBox for piecesRemaining labels
    private VBox piecesRemainingVBox;

    // HBox container for UI
    private HBox uiContainerHBox;


    // UI for game completion
    private VBox endScreenVBox;
    private HBox endScreenButtonsHBox;
    private Label endScreenPassword;
    private Label endScreenText;

    // UI for game start
    private Label instructionsLabel;

    private void initControls() {
        passwordLabel = new Label("Password");
        passwordText = new Label();
        piecesRemainingLabel = new Label("Pieces Remaining");
        piecesRemainingText = new Label();
        lengthCheckLabel = new Label("Length (0 of " + Password.MAX_LENGTH + ")");
        wordsCheckLabel = new Label("Words (0 of " + Password.MAX_WORDS + ")");
        numberCheckLabel = new Label("Number");
        symbolCheckLabel = new Label("Symbol");
        wordsIconLabel = new Label("X");
        lengthIconLabel = new Label("X");
        numberIconLabel = new Label("X");
        symbolIconLabel = new Label("X");

        passwordVBox = new VBox(passwordLabel, passwordText);

        // Add controls to password check HBoxes
        validLengthHBox = new HBox(lengthIconLabel, lengthCheckLabel);
        validWordsHBox = new HBox(wordsIconLabel, wordsCheckLabel);
        validNumbersHBox = new HBox(numberIconLabel, numberCheckLabel);
        validSymbolsHBox = new HBox(symbolIconLabel, symbolCheckLabel);

        passwordValidationVBox = new VBox(validLengthHBox, validWordsHBox, validNumbersHBox, validSymbolsHBox);
        piecesRemainingVBox = new VBox(piecesRemainingLabel, piecesRemainingText);
        uiContainerHBox = new HBox(passwordVBox, passwordValidationVBox, piecesRemainingVBox);

        fpsLabel = new Label("FPS");
        fpsText = new Label();
        fpsVBox = new VBox(fpsLabel, fpsText);

        // Reset button
        tryAgainButton = new Button("Try Again");
        tryAgainButton.setOnAction(SceneManager::resetScene);

        // Continue button
        continueButton = new Button("Continue");
        continueButton.setOnAction(this::nextScene);

        // End screen controls
        endScreenText = new Label();
        endScreenPassword = new Label();
        endScreenButtonsHBox = new HBox(tryAgainButton, continueButton);
        endScreenVBox = new VBox(endScreenPassword, endScreenText, endScreenButtonsHBox);

        instructionsLabel = new Label("""
                Move left with "A" and right with "D"
                Build a password that meets the requirements
                When you're done, SPACE to stop the game
                Press any key to start...""");
    }

    // Update password requirements Labels
    private void updateRequirementLabels() {
        // Update number of characters in currentPassword
        int length = Password.length(password);
        lengthCheckLabel.setText("Length (" + length + " of " + Password.MAX_LENGTH + ")");

        // Update words label
        int words = Password.numberOfWords(password);
        wordsCheckLabel.setText("Words (" + words + " of " + Password.MAX_WORDS + ")");

        // Update password check labels to ✓ or X
        if (Password.hasWords(password))
            updateValidLabels(wordsIconLabel);
        else
            updateInvalidLabels(wordsIconLabel);

        if (Password.validLength(password))
            updateValidLabels(lengthIconLabel);
        else
            updateInvalidLabels(lengthIconLabel);

        if (Password.hasNumbers(password))
            updateValidLabels(numberIconLabel);
        else
            updateInvalidLabels(numberIconLabel);

        if (Password.hasSymbols(password))
            updateValidLabels(symbolIconLabel);
        else
            updateInvalidLabels(symbolIconLabel);
    }

    // Change label text to a green ✓
    private void updateValidLabels(Label label) {
        label.setText("✓");
        label.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
    }

    // Change label text to a red X
    private void updateInvalidLabels(Label label) {
        label.setText("X");
        label.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }

    private void setLayout() {
        // Font sizes
        int fontSize = 16;

        container.setAlignment(Pos.TOP_CENTER);

        uiContainerHBox.setMaxHeight(UI_HEIGHT - 25);
        uiContainerHBox.setStyle("-fx-background-color: #ccc;");
        uiContainerHBox.setAlignment(Pos.TOP_CENTER);
        uiContainerHBox.setSpacing(50);
        uiContainerHBox.setPadding(new Insets(5));

        passwordVBox.setSpacing(10);
        passwordVBox.setAlignment(Pos.CENTER);

        passwordValidationVBox.setSpacing(2);
        passwordValidationVBox.setAlignment(Pos.CENTER);

        piecesRemainingVBox.setSpacing(10);
        piecesRemainingVBox.setAlignment(Pos.CENTER);

        validLengthHBox.setSpacing(15);
        validWordsHBox.setSpacing(15);
        validNumbersHBox.setSpacing(15);
        validSymbolsHBox.setSpacing(15);

        passwordText.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: 700;");
        passwordText.setPrefWidth(400);
        passwordText.setPrefHeight(30);

        passwordLabel.setStyle("-fx-font-size: " + fontSize + ";");
        piecesRemainingLabel.setStyle("-fx-font-size: " + fontSize + ";");
        piecesRemainingText.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: 700; -fx-text-alignment: center;");

        lengthIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        wordsIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        numberIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        symbolIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        fpsLabel.setStyle("-fx-font-size: 8px;");
        fpsText.setStyle("-fx-font-size: 8px;");

        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        instructionsLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: 700; -fx-background-color: rgba(50, 50, 50, 0.4); -fx-width: 800px; -fx-padding: 100 100;");
        instructionsLabel.setTranslateY(WINDOW_HEIGHT / 4);

        endScreenText.setTextAlignment(TextAlignment.CENTER);
        endScreenText.setStyle("-fx-font-size: 25px; -fx-font-weight: 700;");
        endScreenPassword.setTextAlignment(TextAlignment.CENTER);
        endScreenPassword.setStyle("-fx-font-size: 25px; -fx-font-weight: 700;");

        endScreenButtonsHBox.setAlignment(Pos.CENTER);
        endScreenButtonsHBox.setSpacing(100);

        endScreenVBox.setAlignment(Pos.CENTER);
        endScreenVBox.setSpacing(50);
        endScreenVBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.4); -fx-width: 800px; -fx-padding: 100 100;");
    }

    // Draw each frame
    private void draw() {
        gc.clearRect(0, 0, canvas.widthProperty().getValue(), canvas.heightProperty().getValue());

        gc.setFill(Color.PLUM);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Draw player
        gc.setFill(PLAYER_COLOR);
        gc.fillRect(player.getX(), player.getY(), PLAYER_WIDTH, PLAYER_HEIGHT);

        // Loop through currentWords and set position and text color
        for (TextObject currentWord : currentWords) {
            if (currentWord.isCaught())
                gc.setFill(Color.GREEN);
            else if (currentWord.active())
                gc.setFill(Color.BLACK);
            else
                gc.setFill(Color.RED);

            // Set position for each letter of currentWords
            for (int i = 0; i < currentWord.length(); i++) {
                gc.fillText(currentWord.getText(i), currentWord.getX(), currentWord.getY(i));
            }
        }
    }

    private void updateFPS(long deltaTime) {
        lastFpsUpdate += deltaTime;
        frames++;
        if (lastFpsUpdate >= 50 * MS_TO_NS) { // if 50 ms have elapsed
            double fps = frames / (double) lastFpsUpdate * 1_000_000_000L;
            fpsText.setText(String.format("%.2f", fps));
            lastFpsUpdate = 0;
            frames = 0;
        }
    }

    private void gameLoop(long timeStamp) {
        if (lastTimeStamp == 0)
            lastTimeStamp = timeStamp;

        deltaTime = timeStamp - lastTimeStamp;

        updateFPS(deltaTime);

        updateObjectPositions(deltaTime);

        spawnTextObjects(deltaTime);

        updateTextObjects();

        // Draw frame
        draw();

        // End game if password length gets too long
        if (Password.length(password) >= MAX_PASSWORD_LENGTH)
            endScreen();

        lastTimeStamp = timeStamp;
    }

    private void init() {
        playing = false;
        password = new ArrayList<>(); // List to hold password being built
        currentWords = new ArrayList<>(); // List of word TextObjects on screen
        // Queue for all TextObjects
        textObjectQueue = new TextObjectQueue(PASSWORD_PIECES, SPAWN_X_POSITIONS, UI_HEIGHT);

        initControls();

        // Create timer for gameLoop
        timer = new AnimationTimer() {
            @Override
            public void handle(long timeStamp) {
                if (playing)
                    gameLoop(timeStamp);
            }
        };
        timer.start();

        // Create controls
        container = new StackPane();

        // add Canvas and GraphicsContext
        scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        container.getChildren().addAll(canvas, uiContainerHBox, fpsVBox, instructionsLabel);
        gc = canvas.getGraphicsContext2D();
        gc.setFont(Font.font("Comic Sans MS", 18));
        gc.setTextAlign(TextAlignment.CENTER);

        // Create player
        player = new PlayerController();

        // Create key events to control player
        scene.setOnKeyPressed(keyEvent-> {
            // If not playing, take any keystroke to continue game
            if (!playing) {
                // Remove instruction label
                container.getChildren().remove(container.getChildren().size() - 1);
                playing = true;
                return;
            }

            switch (keyEvent.getCode()) {
                case A, D-> player.handleKeyPress(keyEvent.getCode());
                case SPACE -> endScreen();
                default -> {}
            }
        });
        scene.setOnKeyReleased(keyEvent -> player.handleKeyRelease(keyEvent.getCode()));

        // Set player at the bottom center of screen
        player.setX(((double) WINDOW_WIDTH - PLAYER_WIDTH) / 2);
        player.setY((WINDOW_HEIGHT - PLAYER_HEIGHT));

        setLayout();
    }

    private void endScreen() {
        currentWords.clear(); // Empty the word bank
        draw(); // Draw one frame to erase words from screen
        playing = false; // Pause the gameloop
        String text;

        Password.randomlyCapitalizeLetter(password); // Capitalize a letter in the password
        endScreenPassword.setText(String.join("", password));

        if (!Password.isValid(password)) { // Invalid password was built
            text = "This password does not meet the requirements\nPlease try again";
            endScreenPassword.setTextFill(Color.CRIMSON);
            continueButton.setDisable(true);
        }
        else { // Valid password was biult
            text = """
                    You created a strong password!
                    Continue to see the passwords you've built
                    or try to build a new one""";

            endScreenPassword.setTextFill(Color.GREEN);
        }
        // Set the text of the label and add to root node
        endScreenText.setText(text);
        container.getChildren().add(endScreenVBox);
    }

    // Write passwords and go to next scene
    private void nextScene(ActionEvent event) {
        try {
            RandomWriteFile fileWriter = new RandomWriteFile("passwords.txt");
                String password = endScreenPassword.getText();

                // Write all passwords
                if(password != null && !password.equals(""))
                    fileWriter.writeRecord(password);
                fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing passwords");
            throw new RuntimeException(e);
        }
        SceneManager.nextScene(event);
    }

    // Apply movement to GameObjects
    private void updateObjectPositions(long deltaTime) {
        double playerDeltaX = 0; // Movement to apply to player
        double deltaTimeMS = deltaTime * .000001; // Convert deltaTime from NS to MS

        // Calculate left or right movement for player
        if (player.moveLeft())
            playerDeltaX -= player.getSpeed() * deltaTimeMS;
        if (player.moveRight())
            playerDeltaX += player.getSpeed() * deltaTimeMS;


        // Move text
        for (TextObject text : currentWords) {
            // If text should fall, make it fall
            if (text.falling) {
                text.setY(text.getY(0) + text.getSpeed() * deltaTimeMS);
                continue;
            }

            // If text is caught, stick to player
            if (text.isCaught()) {
                text.setX(text.getX() + playerDeltaX);
            }
        }


        // Update player coordinates
        player.setX(player.getX() + playerDeltaX);

        // Don't allow player to move off-screen
        if (player.getX() > WINDOW_WIDTH - PLAYER_WIDTH)
            player.setX(WINDOW_WIDTH - PLAYER_WIDTH);
        if (player.getX() < 0)
            player.setX(0);
    }

    // catch TextObject if it is colliding with player
    private void catchTextObject(TextObject text) {
        if (checkCollision(text) && !text.isCaught()) {
            text.caught();
            text.disable();
            text.falling = false;
            password.add(text.getString());
            passwordText.setText(String.join("",password));
            updateRequirementLabels();
        }
    }

    // Update TextObject statuses. Determine if caught or disabled, ready to move. Delete disabled TextObjects
    private void updateTextObjects() {
        // keep track of indexes to remove after loop
        boolean[] textToRemove = new boolean[currentWords.size()];

        // Loop through all current words
        for (TextObject text : currentWords) {
            // If text has not waited PIECE_START_DELAY ms, add to ms and continue
            if (text.getTimeSinceCreated() < PIECE_START_DELAY) {
                text.setTimeSinceCreated(text.getTimeSinceCreated() + deltaTime);
                continue;
            }

            // If text is disabled, but has not waited PIECE_DELETE_DELAY ms, add to ms and continue
            if ((!text.active() && !text.isCaught()) && text.getTimeSinceDisabled() < PIECE_DELETE_DELAY) {
                text.setTimeSinceDisabled(text.getTimeSinceDisabled() + deltaTime);
                continue;
            }

            // If text is caught, but has not waited PIECE_CAUGHT_DELETE_DELAY ms, add to ms and continue
            if (text.isCaught() && text.getTimeSinceDisabled() < PIECE_CAUGHT_DELETE_DELAY) {
                text.setTimeSinceDisabled(text.getTimeSinceDisabled() + deltaTime);
                continue;
            }

            // If text is caught and has waited PIECE_DELETE_DELAY ms, delete the piece and continue
            if (!text.active()) {
                textToRemove[currentWords.indexOf(text)] = true;
                continue;
            }

            // If text is at the bottom of the screen disable it
            if (text.getY(0) > (WINDOW_HEIGHT - text.getHeight())) {
                text.setY(WINDOW_HEIGHT - text.getHeight());
                text.falling = false;
                text.disable();
                continue;
            }

            text.falling = true; // Text will fall on next frame

            // Catch TextObjects that should be caught
            catchTextObject(text);
        }

        // Remove disabled TextObjects
        for (int i = currentWords.size() - 1; i >= 0; i--) {
            if (textToRemove[i])
                currentWords.remove(i);
        }
    }

    // Spawn words
    private void spawnTextObjects(long deltaTime) {
        lastSpawnDeltaTime += deltaTime;
        // Only spawn a TextObject if it has been at least SPAWN_DELAY ms since last spawn
        if (textObjectQueue.size() > 0 && lastSpawnDeltaTime >= SPAWN_DELAY) {
            currentWords.add(textObjectQueue.poll());
            lastSpawnDeltaTime = 0;
            piecesRemainingText.setText(Integer.toString(textObjectQueue.size()));
        }
    }

    // Return true if word collided with player
    private boolean checkCollision(TextObject word) {
        // If bottom of word is at or below player and is active
        if ((word.getY(word.length() - 1) >= (player.getY())) && word.active()) {
            // Check for collision
            if (Collision.intersects(word, player, PLAYER_WIDTH, PLAYER_HEIGHT)) {
                return true;
            }
        }
        return false;
    }

    // Return new scene
    public Scene getScene() {
        init();
        return scene;
    }

    public String getTitle() {
        return title;
    }
}