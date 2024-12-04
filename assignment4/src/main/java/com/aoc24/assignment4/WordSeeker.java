package com.aoc24.assignment4;

import java.util.ArrayList;

public class WordSeeker {
    private static ArrayList<ArrayList<Character>> MATRIX;
    private static char[] word;
    private static int wordCount;
    public static final String[] DIRECTIONS = {
            "UP", "DOWN", "LEFT", "RIGHT",
            "UP_LEFT", "UP_RIGHT", "DOWN_LEFT", "DOWN_RIGHT"
    };

    public WordSeeker(ArrayList<ArrayList<Character>> pMatrix) {
        MATRIX = pMatrix;
    }

    public static char[] getWord() {
        return word;
    }

    public static void setWord(char[] newWord) {
        word = newWord;
    }

    public int findWords(String word) {
        setWord(word.toCharArray());
        resetWordCount();

        // Loop over matrix
        for (int row = 0; row < MATRIX.size(); row++) {
            ArrayList<Character> currentRow = MATRIX.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                // if we encounter the first character of the input word
                Character currentChar = currentRow.get(col);
                if (currentChar.equals(getWord()[0]))
                    checkDirections(row, col);
            }
        }
        return wordCount;
    }

    public int findXmas() {
        int xmasFound = 0;
        for (int row = 0; row < MATRIX.size(); row++) {
            ArrayList<Character> currentRow = MATRIX.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                // if we encounter the first character of the input word
                Character currentChar = currentRow.get(col);
                if (currentChar.equals('A')) {
                    // look around the current character starting top left to bottom right;
                    if (isXmas(row, col))
                        xmasFound++;
                }
            }
        }
        return xmasFound;
    }

    private static void resetWordCount() {
        wordCount = 0;
    }

    private static void increaseWordCount() {
        wordCount += 1;
    }

    private static void checkDirections(int row, int col) {
        // check all directions for next character
        for (String direction : DIRECTIONS) {
            switch (direction) {
                case "UP_LEFT":
                    if (row > 0 && col > 0 && MATRIX.get(row - 1).get(col - 1).equals(getWord()[1])) {
                        if (traceWord(row - 1, col - 1, 2, direction))
                            increaseWordCount();
                    }
                    break;
                case "UP":
                    if (row > 0 && MATRIX.get(row - 1).get(col).equals(getWord()[1])) {
                        if (traceWord(row - 1, col, 2, direction))
                            increaseWordCount();
                    }
                    break;
                case "UP_RIGHT":
                    if (row > 0 && col < MATRIX.get(row).size() - 1
                            && MATRIX.get(row - 1).get(col + 1).equals(getWord()[1])) {
                        if (traceWord(row - 1, col + 1, 2, direction))
                            increaseWordCount();
                    }
                    break;

                case "LEFT":
                    if (col > 0
                            && MATRIX.get(row).get(col - 1).equals(getWord()[1])) {
                        if (traceWord(row, col - 1, 2, direction))
                            increaseWordCount();
                    }
                    break;
                case "RIGHT":
                    if (col < MATRIX.get(row).size() - 1
                            && MATRIX.get(row).get(col + 1).equals(getWord()[1])) {
                        if (traceWord(row, col + 1, 2, direction))
                            increaseWordCount();
                    }
                    break;

                case "DOWN_LEFT":
                    if (row < MATRIX.size() - 1 && col > 0 && MATRIX.get(row + 1).get(col - 1).equals(getWord()[1])) {
                        if (traceWord(row + 1, col - 1, 2, direction))
                            increaseWordCount();
                    }
                    break;
                case "DOWN":
                    if (row < MATRIX.size() - 1 && MATRIX.get(row + 1).get(col).equals(getWord()[1])) {
                        if (traceWord(row + 1, col, 2, direction))
                            increaseWordCount();
                    }
                    break;
                case "DOWN_RIGHT":
                    if (row < MATRIX.size() - 1 && col < MATRIX.get(row).size() - 1
                            && MATRIX.get(row + 1).get(col + 1).equals(getWord()[1])) {
                        if (traceWord(row + 1, col + 1, 2, direction))
                            increaseWordCount();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private static Boolean traceWord(int row, int col, int index, String direction) {
        if (getWord().length == index)
            return true;

        switch (direction) {
            case "UP_LEFT":
                if (row > 0 && col > 0 && MATRIX.get(row - 1).get(col - 1).equals(getWord()[index])) {
                    return traceWord(row - 1, col - 1, index + 1, direction);
                }
                break;
            case "UP":
                if (row > 0 && MATRIX.get(row - 1).get(col).equals(getWord()[index])) {
                    return traceWord(row - 1, col, index + 1, direction);
                }
                break;
            case "UP_RIGHT":
                if (row > 0 && col < MATRIX.get(row).size() - 1
                        && MATRIX.get(row - 1).get(col + 1).equals(getWord()[index])) {
                    return traceWord(row - 1, col + 1, index + 1, direction);
                }
                break;
            case "LEFT":
                if (col > 0
                        && MATRIX.get(row).get(col - 1).equals(getWord()[index])) {
                    return traceWord(row, col - 1, index + 1, direction);
                }
                break;
            case "RIGHT":
                if (col < MATRIX.get(row).size() - 1
                        && MATRIX.get(row).get(col + 1).equals(getWord()[index])) {
                    return traceWord(row, col + 1, index + 1, direction);
                }
                break;
            case "DOWN_LEFT":
                if (row < MATRIX.size() - 1 && col > 0 && MATRIX.get(row + 1).get(col - 1).equals(getWord()[index])) {
                    return traceWord(row + 1, col - 1, index + 1, direction);
                }
                break;
            case "DOWN":
                if (row < MATRIX.size() - 1 && MATRIX.get(row + 1).get(col).equals(getWord()[index])) {
                    return traceWord(row + 1, col, index + 1, direction);
                }
                break;
            case "DOWN_RIGHT":
                if (row < MATRIX.size() - 1 && col < MATRIX.get(row).size() - 1
                        && MATRIX.get(row + 1).get(col + 1).equals(getWord()[index])) {
                    return traceWord(row + 1, col + 1, index + 1, direction);
                }
                break;

            default:
                break;
        }
        return false;
    }

    private static Boolean isXmas(int row, int col) {
        if (row <= 0 || col <= 0 || row >= MATRIX.size() - 1 || col >= MATRIX.get(row).size() - 1) {
            return false;
        }

        Character upleft = MATRIX.get(row - 1).get(col - 1);
        Character upright = MATRIX.get(row - 1).get(col + 1);
        Character downleft = MATRIX.get(row + 1).get(col - 1);
        Character downright = MATRIX.get(row + 1).get(col + 1);

        if ((upleft.equals('M') && downright.equals('S') || upleft.equals('S') && downright.equals('M')) &&
                (upright.equals('M') && downleft.equals('S') || upright.equals('S') && downleft.equals('M'))) {
            return true;
        }
        return false;
    }
}
