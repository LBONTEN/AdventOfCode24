package com.aoc24.assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class App {

    public static ArrayList<ArrayList<Character>> loadFile(String path) {

        ArrayList<ArrayList<Character>> matrix = new ArrayList<ArrayList<Character>>();

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                ArrayList<Character> row = new ArrayList<>(
                        line.chars()
                                .mapToObj(e -> (char) e)
                                .collect(
                                        Collectors.toList()));
                matrix.add(row);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return matrix;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Character>> matrix = loadFile("input.txt");
        WordSeeker wordSeeker = new WordSeeker(matrix);

        int wordsFound = wordSeeker.findWords("XMAS");
        System.out.println("words found: " + wordsFound);

        int xmasFound = wordSeeker.findXmas();
        System.out.println("xmas found: " + xmasFound);
    }
}
