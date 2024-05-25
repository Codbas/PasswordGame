// Creates the Scene for the PasswordBuilder Scene.
package com.example.passwordgame.scenes;
import com.example.passwordgame.resources.PasswordPieces;
import com.example.passwordgame.util.Password;
import com.example.passwordgame.util.RandomWriteFile;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.List;

public class PasswordBuilder implements SceneBuilder{
    private final String title = "Password Builder";
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 480;
    private final int NUMBER_OF_COMBOBOXES = 7;
    private final int NUMBER_OF_PASSWORDS = 5; // Number of passwords that can be added
    private final int REQUIRED_PASSWORDS = 1; // Number of passwords required to continue

    // Used to hold the password being built
    private List<String> currentPassword; // List of password pieces for current password

    private PasswordPieces passwordPieces;
    private ObservableList<String>[] passwordPiecesOL; // Observable list for comboBoxes

    /*         Labels for Scene       */

    // Scene instructions
    private Label instructionLabel;

    // Labels for ComboBoxes
    private Label[] comboBoxLabels;

    // Label for passwordBuilder
    private Label passwordLabel;

    // Holds password currently being built
    private Label passwordBuilderText;

    // passwordBuilder error message label
    private Label errorMessageText;

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

    // Labels for password 1-5
    private Label[] passwordLabels;

    // Labels to display passwords 1-5
    private Label[] passwordsText;


    /*         ComboBoxes for Scene          */
    private ComboBox[] passwordPieceComboBoxes;


    /*          Buttons for Scene         */

    // ComboBox Buttons
    private Button[] comboBoxButtons;

    // Password builder Buttons
    private Button clearPasswordButton;
    private Button addPasswordButton;

    // Buttons to remove passwords 1-5
    private Button[] removePasswordButtons;

    // Continue to next Scene button
    private Button continueButton;


    /*         HBox and VBox for Scene layout     */

    // ComboBox layout
    private HBox comboBoxHBox;
    private VBox[] comboBoxVBoxes;

    // Password builder layout (bottom left of Scene)
    private VBox passwordBuilderVBox;
    private HBox validLengthHBox;
    private HBox validWordsHBox;
    private HBox validNumbersHBox;
    private HBox validSymbolsHBox;
    private HBox passwordBuilderButtonsHBox;

    // Passwords 1-5 layout (bottom right of Scene)
    private HBox[] passwordsHBox;
    private VBox passwordsVBox;

    //  HBox for password builder and passwords 1-5 (bottom of scene)
    private HBox passwordBuilderHBox;

    // Scene VBox. Root node
    private  VBox containerVBox;

    // Constructor
    public PasswordBuilder() {} // Not sure that this is actually needed

    // Initialize arrays
    private void initArrays() {
        passwordPieces = new PasswordPieces(); // PasswordPieces Object

        // List to hold currently being built password
        currentPassword = new ArrayList<>();

        // create ObservableList<String> for each PasswordPiece array to populate ComboBoxes
        passwordPiecesOL = new ObservableList[NUMBER_OF_COMBOBOXES];
        passwordPiecesOL[0] = passwordPieces.getNounObservableList();
        passwordPiecesOL[1] = passwordPieces.getPronounObservableList();
        passwordPiecesOL[2] = passwordPieces.getAdjectiveObservableList();
        passwordPiecesOL[3] = passwordPieces.getVerbObservableList();
        passwordPiecesOL[4] = passwordPieces.getPrepositionObservableList();
        passwordPiecesOL[5] = passwordPieces.getSymbolObservableList();
        passwordPiecesOL[6] = passwordPieces.getNumberObservableList();

        // Initialize control arrays
        comboBoxLabels = new Label[NUMBER_OF_COMBOBOXES];
        passwordPieceComboBoxes = new ComboBox[NUMBER_OF_COMBOBOXES];
        comboBoxButtons = new Button[NUMBER_OF_COMBOBOXES];
        comboBoxVBoxes = new VBox[NUMBER_OF_COMBOBOXES];
        passwordLabels = new Label[NUMBER_OF_PASSWORDS];
        passwordsText = new Label[NUMBER_OF_PASSWORDS];
        removePasswordButtons = new Button[NUMBER_OF_PASSWORDS];
        passwordsHBox = new HBox[NUMBER_OF_PASSWORDS];
    }

    // initialize and set Labels, Buttons, and ComboBoxes
    private void initControls() {
        // Instruction Label
        instructionLabel = new Label("Create " + REQUIRED_PASSWORDS + " or more passwords between " +
                Password.MIN_LENGTH + " and " + Password.MAX_LENGTH + " characters long that meet the password " +
                "requirements\nClicking \"Add Password\" will randomize the order and capitalize one letter " +
                "to increase password strength\n" +
                "Create at least " + REQUIRED_PASSWORDS + " password and click Continue");

        // ComboBox Labels
        comboBoxLabels[0] = new Label("Nouns");
        comboBoxLabels[1] = new Label("Pronouns");
        comboBoxLabels[2] = new Label("Adjectives");
        comboBoxLabels[3] = new Label("Verbs");
        comboBoxLabels[4] = new Label("Prepositions");
        comboBoxLabels[5] = new Label("Symbols");
        comboBoxLabels[6] = new Label("Numbers");

        //ComboBox Button text
        for (int i = 0; i < comboBoxButtons.length; i++) {
            comboBoxButtons[i] = new Button("Add");
        }

        // ComboBox text content
        for (int i = 0; i < passwordPieceComboBoxes.length; i++) {
            passwordPieceComboBoxes[i] = new ComboBox(passwordPiecesOL[i]);
        }

        // Password builder Labels
        passwordLabel = new Label("Password");
        lengthCheckLabel = new Label("Length (0 of " + Password.MAX_LENGTH + ")");
        wordsCheckLabel = new Label("Words (0 of " + Password.MAX_WORDS + ")");
        numberCheckLabel = new Label("Number");
        symbolCheckLabel = new Label("Symbol");
        wordsIconLabel = new Label("X");
        lengthIconLabel = new Label("X");
        numberIconLabel = new Label("X");
        symbolIconLabel = new Label("X");

        // Password builder Buttons text
        clearPasswordButton = new Button("Clear Password");
        addPasswordButton = new Button("Add Password");

        // Password 1-5 Label, Button, and Label for displaying passwords
        for (int i = 0; i < passwordLabels.length; i++) {
            passwordLabels[i] = new Label("Password " + (i + 1));
            removePasswordButtons[i] = new Button("Remove");
            passwordsText[i] = new Label();
        }

        // Continue button text
        continueButton = new Button("Continue");
        continueButton.setDisable(true);

        // Label used to display password being built.
        passwordBuilderText = new Label();

        // Label for password builder error messages
        errorMessageText = new Label();
    }

    // Add event listeners to controls
    private void addEventListeners() {
        /*                        ComboBox Button event listeners                            */
        for (int i = 0; i < passwordPieceComboBoxes.length; i++) {
            int buttonIndex = i; // to track which button was pressed
            comboBoxButtons[i].setOnAction(event -> {
                // Return if ComboBox is empty
                if (passwordPieceComboBoxes[buttonIndex].getValue() == null) {
                    setErrorMessage("A value must be selected");
                    return;
                }

                // Get length of current password
                int currentPassLength = Password.length(currentPassword);
                // Get length of passwordPiece to add
                int passwordPieceLength = passwordPieceComboBoxes[buttonIndex].getValue().toString().length();

                // If password length is > 30 characters return
                if (currentPassLength + passwordPieceLength > Password.MAX_LENGTH) {
                    setErrorMessage("Password length cannot exceed " + Password.MAX_LENGTH + " characters");
                    return;
                }

                // Get the text of passwordBuilder and ComboBox
                String comboBoxText = passwordPieceComboBoxes[buttonIndex].getValue().toString();
                currentPassword.add(comboBoxText);

                // Add comboBoxText to passwordBuilderText
                this.passwordBuilderText.setText(Password.toString(currentPassword));

                // Update label requirements
                updateRequirementLabels();

                // Clear error message
                clearErrorMessage();
            });
        }


        /*              Passwords 1-5 Remove Button listeners                            */
        for (int i = 0; i < removePasswordButtons.length; i++) {
            int buttonIndex = i; // Track which remove button was pressed

            removePasswordButtons[i].setOnAction(event -> {
                passwordsText[buttonIndex].setText("");
                setErrorMessage(""); // Clear error message
                continueButton.setDisable(!readyForNextScene());
            });
        }


        /*            Clear Password Button event listener                          */
        clearPasswordButton.setOnAction(event -> {
            currentPassword.clear();
            passwordBuilderText.setText("");
            updateRequirementLabels();
            clearErrorMessage();
        });


        /*          Add password Button event listener                              */
        addPasswordButton.setOnAction(event -> {
            // Return if password is not valid
            if (!Password.isValid(currentPassword)) {
                setErrorMessage("Password does not meet requirements!");
                return;
            }


            // Return if there is no more room for passwords
            if (getNumberOfPasswords() >= NUMBER_OF_PASSWORDS) {
                setErrorMessage("Cannot enter more than " + NUMBER_OF_PASSWORDS + " passwords");
                return;
            }

            // Shuffle the password
            Password.shuffle(currentPassword);

            // Randomly capitalize one letter of a piece
            Password.randomlyCapitalizeLetter(currentPassword);

            // Set currentPassword to first empty password
            for (int i = 0; i < NUMBER_OF_PASSWORDS; i++) {
                if (passwordsText[i].getText().equals("")) {
                    passwordsText[i].setText(Password.toString(currentPassword));
                    currentPassword.clear();
                    passwordBuilderText.setText("");
                    i = NUMBER_OF_PASSWORDS; // break; instead?
                }
            }
            clearErrorMessage();
            updateRequirementLabels();
            continueButton.setDisable(!readyForNextScene());
        });


        /*            Continue Button event listener                              */
        continueButton.setOnAction(event -> {
            // Check that enough passwords are filled out
            if (readyForNextScene()) {
                savePasswords();
                SceneManager.nextScene(event);
            }
            else
                setErrorMessage("Create " + REQUIRED_PASSWORDS + " passwords before continuing");
        });
    }

    // Return true if all enough passwords are filled out
    private boolean readyForNextScene() {
        return getNumberOfPasswords() >= REQUIRED_PASSWORDS;
    }

    // Get number of passwords that are filled out
    private int getNumberOfPasswords() {
        int filledOutPasswords = 0;
        for (int i = 0; i < NUMBER_OF_PASSWORDS; i++) {
            if (!passwordsText[i].getText().equals(""))
                filledOutPasswords++;
        }
        return filledOutPasswords;
    }

    // Update password requirements Labels
    private void updateRequirementLabels() {
        // Update number of characters in currentPassword
        int length = Password.length(currentPassword);
        lengthCheckLabel.setText("Length (" + length + " of " + Password.MAX_LENGTH + ")");

        // Update words label
        int words = Password.numberOfWords(currentPassword);
        wordsCheckLabel.setText("Words (" + words + " of " + Password.MAX_WORDS + ")");

        // Update password check labels to ✓ or X
        if (Password.hasWords(currentPassword))
            updateValidLabels(wordsIconLabel);
        else
            updateInvalidLabels(wordsIconLabel);

        if (Password.validLength(currentPassword))
            updateValidLabels(lengthIconLabel);
        else
            updateInvalidLabels(lengthIconLabel);

        if (Password.hasNumbers(currentPassword))
            updateValidLabels(numberIconLabel);
        else
            updateInvalidLabels(numberIconLabel);

        if (Password.hasSymbols(currentPassword))
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

    // Sets the error message Label
    private void setErrorMessage(String message) {
        errorMessageText.setText(message);
    }

    // Clears the error message label
    private void clearErrorMessage() {
        errorMessageText.setText("");
    }

    // Build the Scene
    private void buildScene() {
        // Add controls to VBoxes for ComboBoxes
        for (int i = 0; i < comboBoxVBoxes.length; i++) {
            comboBoxVBoxes[i] = new VBox(comboBoxLabels[i], passwordPieceComboBoxes[i], comboBoxButtons[i]);
        }

        // Add ComboBox VBoxes to ComboBox HBox
        comboBoxHBox = new HBox();
        for (VBox comboBoxVBox : comboBoxVBoxes) {
            comboBoxHBox.getChildren().add(comboBoxVBox);
        }

        // Add controls to password check HBoxes
        validLengthHBox = new HBox(lengthIconLabel, lengthCheckLabel);
        validWordsHBox = new HBox(wordsIconLabel, wordsCheckLabel);
        validNumbersHBox = new HBox(numberIconLabel, numberCheckLabel);
        validSymbolsHBox = new HBox(symbolIconLabel, symbolCheckLabel);
        passwordBuilderButtonsHBox = new HBox(addPasswordButton, clearPasswordButton);

        // Add password check HBoxes to password check VBox
        passwordBuilderVBox = new VBox(passwordLabel, passwordBuilderText, errorMessageText, validLengthHBox,
                validWordsHBox, validNumbersHBox, validSymbolsHBox, passwordBuilderButtonsHBox);

        // Add controls to passwords 1-5 HBoxes
        passwordsVBox = new VBox(); // VBox to hold password HBoxes
        for (int i = 0; i < passwordsHBox.length; i++) {
            passwordsHBox[i] = new HBox(passwordLabels[i], passwordsText[i], removePasswordButtons[i]);
            passwordsVBox.getChildren().add(passwordsHBox[i]);
        }
        // Add continueButton
        passwordsVBox.getChildren().add(continueButton);

        // Add passwordBuilderVBox and passwordsVBox to HBox
        passwordBuilderHBox = new HBox(passwordBuilderVBox, passwordsVBox);

        // Add VBox and HBox to main scene container
        containerVBox = new VBox(instructionLabel, comboBoxHBox, passwordBuilderHBox);
    }

    private void savePasswords() {
        try {
            RandomWriteFile fileWriter = new RandomWriteFile("passwords.txt");
            for (Label passwordText : passwordsText) {
                String password = passwordText.getText();

                // Write all passwords
                if(password != null && !password.equals(""))
                    fileWriter.writeRecord(passwordText.getText());
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing passwords");
            throw new RuntimeException(e);
        }
    }


    public String getTitle() {
        return title;
    }

    // Returns the scene
    public Scene getScene() {
        initArrays();
        initControls();
        addEventListeners();
        buildScene();
        setLayout();
        return new Scene(containerVBox, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // Sets the layout of the scene
    private void setLayout() {
        containerVBox.setAlignment(Pos.TOP_CENTER);
        containerVBox.setSpacing(30);
        comboBoxHBox.setAlignment(Pos.CENTER);

        instructionLabel.setPadding(new Insets(10));
        instructionLabel.setFont(new Font("Arial", 13));
        instructionLabel.setStyle("-fx-font-weight: bold;");
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setWrapText(true);
        instructionLabel.setMinHeight(70);

        comboBoxHBox.setSpacing(20);

        for (VBox comboBoxVBox : comboBoxVBoxes) {
            comboBoxVBox.setAlignment(Pos.CENTER);
            comboBoxVBox.setSpacing(10);
        }

        passwordBuilderHBox.setAlignment(Pos.CENTER);
        passwordBuilderHBox.setSpacing(100);
        passwordBuilderVBox.setAlignment(Pos.CENTER);
        passwordsVBox.setAlignment(Pos.CENTER);

        passwordBuilderVBox.setSpacing(10);
        passwordsVBox.setSpacing(10);

        passwordLabel.setAlignment(Pos.CENTER);

        passwordBuilderText.setStyle("-fx-border-color: black;");
        passwordBuilderText.setPadding(new Insets(3));
        passwordBuilderText.setPrefWidth(230);

        passwordBuilderButtonsHBox.setSpacing(30);
        passwordBuilderButtonsHBox.setPadding(new Insets(10));

        validLengthHBox.setSpacing(15);
        validWordsHBox.setSpacing(15);
        validNumbersHBox.setSpacing(15);
        validSymbolsHBox.setSpacing(15);

        passwordsVBox.setSpacing(15);
        passwordsVBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < passwordsHBox.length; i++) {
            passwordsHBox[i].setSpacing(10);
            passwordsHBox[i].setAlignment(Pos.CENTER);

            passwordsText[i].setStyle("-fx-border-color: black;");
            passwordsText[i].setPadding(new Insets(3));
            passwordsText[i].setPrefWidth(230);
        }

        errorMessageText.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        lengthIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        wordsIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        numberIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        symbolIconLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
}