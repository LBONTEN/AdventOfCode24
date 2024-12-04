package com.aoc2024.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static ArrayList<ArrayList<Integer>> parseLists(String path) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> leftList = new ArrayList<Integer>();
        ArrayList<Integer> rightList = new ArrayList<Integer>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] splitLine = line.split("\\s");
                leftList.add(Integer.valueOf(splitLine[0]));
                rightList.add(Integer.valueOf(splitLine[splitLine.length - 1]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        result.add(leftList);
        result.add(rightList);
        return result;
    }

    public static int calculateDistance(ArrayList<ArrayList<Integer>> inputList) {
        ArrayList<Integer> leftList = inputList.get(0);
        ArrayList<Integer> rightList = inputList.get(1);
        leftList.sort(null);
        rightList.sort(null);

        int index = 0;
        int result = 0;
        while (index != leftList.size()) {
            Integer distance = Math.abs(rightList.get(index) - leftList.get(index));
            result = result + distance;
            index++;
        }
        return result;
    }

    public static int calculateSimilarity(ArrayList<ArrayList<Integer>> inputList) {
        ArrayList<Integer> leftList = inputList.get(0);
        ArrayList<Integer> rightList = inputList.get(1);

        Integer totalSimilarity = 0;
        for (Integer l : leftList) {
            Integer appearances = 0;
            for (Integer r : rightList)
                if (l.equals(r))
                    appearances++;
            totalSimilarity = totalSimilarity + (l * appearances);
        }

        return totalSimilarity;
    }

    public static void main(String[] args) {
        try {
            String filePath = "input.txt";
            ArrayList<ArrayList<Integer>> inputList = parseLists(filePath);
            int distance = calculateDistance(inputList);
            System.out.println("distance: " + distance);

            int similarity = calculateSimilarity(inputList);
            System.out.println("similarity: " + similarity);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
