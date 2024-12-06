package adventofcode24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class App {
    static ArrayList<ArrayList<Integer>> UPDATES = new ArrayList<ArrayList<Integer>>();
    static Map<Integer, Set<Integer>> RULEBOOK = new HashMap<Integer, Set<Integer>>();

    public static ArrayList<ArrayList<Integer>> loadUpdates(String path) {
        ArrayList<ArrayList<Integer>> updates = new ArrayList<ArrayList<Integer>>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] splitLine = line.split(",");
                ArrayList<Integer> currentUpdate = new ArrayList<Integer>();
                for (String i : splitLine)
                    currentUpdate.add(Integer.valueOf(i));
                updates.add(currentUpdate);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return updates;
    }

    public static Map<Integer, Set<Integer>> loadRules(String path) {
        Map<Integer, Set<Integer>> ruleBook = new HashMap<Integer, Set<Integer>>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] splitLine = line.split("\\|");

                Integer key = Integer.valueOf(splitLine[0]);
                Integer value = Integer.valueOf(splitLine[1]);
                if (ruleBook.containsKey(key)) {
                    ruleBook.get(key).add(value);
                } else {
                    ruleBook.put(key, new HashSet<Integer>(Arrays.asList(value)));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return ruleBook;
    }

    public static long fixUpdates() {
        ArrayList<Integer> middleIndicesCorrect = new ArrayList<>();
        ArrayList<Integer> middleIndicesIncorrect = new ArrayList<>();

        for (ArrayList<Integer> update : UPDATES) {
            Boolean isCorrect = true;
            for (Integer i = 0; i < update.size(); i++) {
                Integer currentKey = update.get(i);
                if (RULEBOOK.containsKey(currentKey)) {
                    Set<Integer> singleRule = RULEBOOK.get(currentKey);
                    int smallestIndex = i;
                    for (Integer rule : singleRule) {
                        // check if rule is before current key
                        int indexOfRule = update.subList(0, i).indexOf(rule);
                        if (indexOfRule != -1 && indexOfRule < smallestIndex) {
                            isCorrect = false;
                            smallestIndex = indexOfRule;
                        }
                        // swap key with the earliest instance of a rule;
                    }
                    if (smallestIndex < i) {
                        update.remove(currentKey);
                        update.add(smallestIndex, currentKey);
                        // start over from smallest index
                        // make sure there is no circular rule in rulebook
                        i = smallestIndex;
                    }
                }
            }
            if (isCorrect)
                middleIndicesCorrect.add(update.get(update.size() / 2));
            if (!isCorrect)
                middleIndicesIncorrect.add(update.get(update.size() / 2));
        }
        long sumCorrect = middleIndicesCorrect.stream().mapToLong(Integer::longValue).sum();
        long sumIncorrect = middleIndicesIncorrect.stream().mapToLong(Integer::longValue).sum();
        System.out.println("correct/incorrect "+sumCorrect +"/"+sumIncorrect);
        return middleIndicesCorrect.stream().mapToLong(Integer::longValue).sum();
    }

    public static void main(String[] args) {
        RULEBOOK = loadRules("rules.txt");
        UPDATES = loadUpdates("updates.txt");

        long result = fixUpdates();
    }
}
