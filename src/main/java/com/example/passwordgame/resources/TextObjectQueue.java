// Queue for TextObjects
package com.example.passwordgame.resources;
import java.util.*;

public class TextObjectQueue {
    private Queue<TextObject> textObjectQueue;
    private final PasswordPieces passwordPieces; // Used to get strings for passwords
    private int size; // Size of queue

    public TextObjectQueue(int size, int[] spawnXPositions, int spawnYPosition) {
        this.size = size;
        passwordPieces = new PasswordPieces();
        textObjectQueue = new LinkedList<>();
        populateQueue(spawnXPositions, spawnYPosition);
    }

    private void populateQueue(int[] spawnXPositions, int spawnYPosition) {
        String[] passwordStrings = new String[size]; // Random String array of passwordPiece
        createTextObjectStrings(passwordStrings);

        // Shuffle passwordStrings order
        Collections.shuffle(Arrays.asList(passwordStrings));

        int[] spawnXCoords = new int[size]; // Coordinates for each TextObject spawn
        setSpawnXCoordinates(spawnXPositions, spawnXCoords);

        // Create Queue of TextObjects
        for(int i = 0; i < size; i++) {
            textObjectQueue.add(new TextObject(passwordStrings[i], spawnXCoords[i], spawnYPosition));
        }
    }

    private void createTextObjectStrings(String[] passwordStrings) {
        int pieceTypes = 7; // number of types of pieces... Noun, adjective, number, etc.
        int numberOfPieces = size / pieceTypes; // Number of Strings for each type
        int leftovers = size % pieceTypes; // Remainder when divided by 7
        int numberOfNouns = numberOfPieces + leftovers; // Give nouns the remainder

        // Create array of Strings from passwordPieces
        int index = 0; // Index of String
        for (int i = 0; i < pieceTypes; i++) {
            // Nouns might have more Strings to add..
            int count = i == 0 ? numberOfNouns : numberOfPieces;
            for (int j = 0; j < count; j++) {
                String piece = switch (i) {
                    case 0 -> passwordPieces.nounAt(randomInt(0, passwordPieces.nounSize() - 1));
                    case 1 -> passwordPieces.pronounAt(randomInt(0, passwordPieces.pronounSize() - 1));
                    case 2 -> passwordPieces.adjectiveAt(randomInt(0, passwordPieces.adjectiveSize() - 1));
                    case 3 -> passwordPieces.verbAt(randomInt(0, passwordPieces.verbSize() - 1));
                    case 4 -> passwordPieces.prepositionAt(randomInt(0, passwordPieces.prepositionSize() - 1));
                    case 5 -> passwordPieces.symbolAt(randomInt(0, passwordPieces.symbolSize() - 1));
                    case 6 -> passwordPieces.numberAt(randomInt(0, passwordPieces.numberSize() - 1));
                    default -> null;
                };
                passwordStrings[index++] = piece;
            }
        }
    }

    // Sets spawn coords of passed int[]
    private void setSpawnXCoordinates(int[] spawnXPositions, int[] spawnXCoords) {
        int positions = spawnXPositions.length - 1; // Number of spawn positions
        Queue<Integer> previousSpawn = new LinkedList<>(); // queue to prevent overlapping spawns
        int newSpawn; // Potential new spawn


        for (int i = 0; i < spawnXCoords.length; i++) {
            // Assign new random x spawn position
            newSpawn = spawnXPositions[randomInt(0, positions)];

            // Generate new spawn coords until queue has no duplicates
            while (previousSpawn.contains(newSpawn)) {
                newSpawn = spawnXPositions[randomInt(0, positions)];
            }

            previousSpawn.add(newSpawn); // Add new spawn to queue
            // Max previousSpawn queue size = 10
            if (previousSpawn.size() > 10) {
                previousSpawn.poll();
            }
            spawnXCoords[i] = newSpawn; // Set spawn position
        }
    }

    // Generate random int between min and max
    private int randomInt(int min, int max) {
        return new SplittableRandom().nextInt((max - min) + 1) + min;
    }

    // Poll a TextObject from the queue
    public TextObject poll() {
        size--;
        return textObjectQueue.poll();
    }

    // Peak a TextObject
    public TextObject peek() {
        return textObjectQueue.peek();
    }

    public int size() {
        return size;
    }
}
