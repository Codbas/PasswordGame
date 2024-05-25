// Utility class used to manipulate password List and check password validity
package com.example.passwordgame.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {
    public static final int MIN_LENGTH = 16;
    public static final int MAX_LENGTH = 30;
    public static final int MIN_WORDS = 3;
    public static final int MAX_WORDS = 10;

    // Private constructor to prevent instantiation
    private Password(){}

    // Return true if password meets criteria
    public static boolean isValid(List<String> password) {
        return (validLength(password) && hasNumbers(password) && hasWords(password) && hasSymbols(password));
    }

    // Return true if password List has a number
    public static boolean hasNumbers(List<String> password) {
        for (String str : password) {
            if (containsNumber(str))
                return true;
        }
        return false;
    }

    // Returns true if a number is in the string
    private static boolean containsNumber(String str) {
        Pattern number = Pattern.compile("\\d");
        Matcher numberMatcher = number.matcher(str);
        return numberMatcher.matches();
    }

    // Return true if password has a symbol
    public static boolean hasSymbols(List<String> password) {
        for (String str : password) {
            if (containsSymbol(str))
                return true;
        }
        return false;
    }

    // Return true if string contains a symbol
    private static boolean containsSymbol(String str) {
        Pattern symbol = Pattern.compile("[!@#$%^&*()\\-_=/<>?~.+,;:\\[\\]{}]");
        Matcher symbolMatcher = symbol.matcher(str);
        return symbolMatcher.matches();
    }

    // Return true if password is between MIN_LENGTH and MAX_LENGTH
    public static boolean validLength(List<String> password) {
        String passStr = String.join("", password);
        return (passStr.length() >= MIN_LENGTH && passStr.length() <= MAX_LENGTH);
    }

    public static int length(List<String> password) {
        return String.join("", password).length();
    }

    // Return true if password has between MIN_WORDS and MAX_WORDS
    public static boolean hasWords(List<String> password) {
        int words = numberOfWords(password);
        return (words >= MIN_WORDS && words <= MAX_WORDS);
    }

    // Returns number of words in password List
    public static int numberOfWords(List<String> password) {
        int words = 0;
        for (String str : password) {
            // If current str in password isn't a number or symbol
            if (!containsNumber(str) && !containsSymbol(str))
                words++; // Add one to words
        }
        return words;
    }

    // Shuffles the List indexes in a random order
    public static void shuffle(List<String> password) {
        Collections.shuffle(password);
    }

    // Randomly adds one capital letter to a piece of the password
    public static void randomlyCapitalizeLetter(List<String> password) {
        Pattern letters = Pattern.compile("^[a-zA-Z]+$");

        // Create a List of indexes for each passwordPiece in currentPassword
        int numberOfPieces = password.size();
        List<Integer> randomPiece = new ArrayList<>();
        for (int i = 0; i < numberOfPieces; i++) {
            randomPiece.add(i);
        }

        // Shuffle the indexes to find random string to capitalize
        Collections.shuffle(randomPiece);

        // Loop through currentPassword pieces until a piece is found that is not a number or symbol
        for (int pieceIndex : randomPiece) {
            Matcher matcher = letters.matcher(password.get(pieceIndex));

            // If passwordPiece is a word, randomly capitalize one letter
            if (matcher.matches()) {
                // randomly pick character to capitalize in string
                int letterIndex = new SplittableRandom().nextInt(0, password.get(pieceIndex).length());
                StringBuilder strBuffer = new StringBuilder(password.get(pieceIndex));
                char upperChar = Character.toUpperCase(strBuffer.charAt(letterIndex));
                strBuffer.setCharAt(letterIndex, upperChar);
                password.set(pieceIndex, strBuffer.toString());

                // Only capitalize one of the pieces
                return;
            }
        }
    }

    // Return String of password List
    public static String toString(List<String> password) {
        return String.join("", password);
    }
}