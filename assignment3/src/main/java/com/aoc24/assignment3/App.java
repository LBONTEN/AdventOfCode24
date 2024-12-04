package com.aoc24.assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static ArrayList<Instruction> loadInstructions(String path) {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            String regex = "(?<method>mul)\\((?<operand1>[0-9]{1,3})\\,(?<operand2>[0-9]{1,3})\\)";
            String regexDo = "(do\\(\\))|(don't\\(\\))";

            Pattern pattern = Pattern.compile(regex);
            Pattern patternDo = Pattern.compile(regexDo);

            Boolean enabled = true;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                Matcher matcher = pattern.matcher(line);

                int lastInstructionIndex = 0;
                while (matcher.find()) {
                    String instructionSnippet = line.substring(lastInstructionIndex, matcher.end());
                    lastInstructionIndex = matcher.end();
                    Matcher matcherDo = patternDo.matcher(instructionSnippet);

                    while (matcherDo.find()) {
                        String enabler = matcherDo.group();
                        if (enabler.equals("do()")) {
                            enabled = true;
                        } else if (enabler.equals("don't()")) {
                            enabled = false;
                        }
                    }

                    Instruction fetchedInstruction = new Instruction(
                        matcher.group("method"), 
                        Integer.valueOf(matcher.group("operand1")), 
                        Integer.valueOf(matcher.group("operand2")),
                        enabled
                    );
                    instructions.add(fetchedInstruction);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return instructions;
    }

    public static int execute(ArrayList<Instruction> instructions) {
        int endResult = 0;
        for (Instruction i : instructions) {
            if (i.enabled) {
                int result = 0;
                switch (i.method) {
                    case "mul":
                    result = i.operand1 * i.operand2;
                    break;
                    default:
                    break;
                }
                endResult = endResult + result;
            }
        }
        return endResult;
    }

    public static void main(String[] args) {
        ArrayList<Instruction> instructions = loadInstructions("input.txt");
        int result = execute(instructions);
        System.out.println(result);
    }
}
