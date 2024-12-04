package com.aoc24.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static ArrayList<LinkedList<Integer>> loadFile(String path) {

        ArrayList<LinkedList<Integer>> levels = new ArrayList<LinkedList<Integer>>();

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] splitLine = line.split("\\s");
                LinkedList<Integer> level = new LinkedList<Integer>();
                for (String s : splitLine) {
                    level.add(Integer.valueOf(s));
                }
                levels.add(level);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return levels;
    }

    public static Boolean determineIncreasing(LinkedList<Integer> report) {
        Boolean increasing = true;
        // return increasing once we determine two consecutive increases or decreases
        Integer next = report.pop();
        Boolean potentiallyIncreasing = next - report.getFirst() < 0;
        while (report.size() > 1) {
            next = report.pop();
            if (potentiallyIncreasing == (next - report.getFirst() < 0)) {
                increasing = potentiallyIncreasing;
                break;
            } else {
                potentiallyIncreasing = !potentiallyIncreasing;
            }
        }
        return increasing;
    }

    public static int calculateSafeness(ArrayList<LinkedList<Integer>> input) {
        int safeAmount = 0;
        try {
            FileWriter myWriter = new FileWriter("filename.txt");

            for (LinkedList<Integer> report : input) {
                Boolean safe = true;
                Boolean buffer = false;

                for (Integer r : report) {
                    myWriter.write(r.toString() + " ");
                }
                myWriter.write("\n");

                Boolean increasing = determineIncreasing((LinkedList<Integer>) report.clone());
                Integer lastSeen = report.pop();
                LinkedList<Integer> bckpReport = (LinkedList<Integer>) report.clone();
                while (report.size() > 0) {
                    int difference = lastSeen - report.getFirst();
                    if (increasing && (difference >= 0) || !increasing && difference <= 0 || Math.abs(difference) > 3) {
                        if (!buffer) {
                            buffer = true;
                            myWriter.write("BUFFER ACTIVATED\n");
                            report.removeFirst();
                        } else {
                            if (bckpReport.isEmpty()) {
                                safe = false;
                                break;
                            } else {
                                report = (LinkedList<Integer>) bckpReport.clone();
                                bckpReport.clear();
                                lastSeen = report.pop();

                                myWriter.write("INCREASING " + increasing.toString() + " BACKUP\n");
                                for (Integer r : report) {

                                    myWriter.write(r.toString() + " ");
                                }
                                myWriter.write("\n");
                                
                            }
                        }
                    } else {
                        lastSeen = report.pop();
                    }
                }

                if (safe) {
                    safeAmount++;
                }
                myWriter.write(safe + "\n============\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return safeAmount;
    }

    public static void main(String[] args) {
        ArrayList<LinkedList<Integer>> levels = loadFile("input.txt");
        int safeAmount = calculateSafeness(levels);
        System.out.println("safeAmount: " + safeAmount);
    }
}
