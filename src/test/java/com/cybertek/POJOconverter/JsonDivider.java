package com.cybertek.POJOconverter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonDivider {
    @Test
    public void runTest() {
        try {
            checker(jsonFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Scanner jsonFile() throws FileNotFoundException {
        String path = "C:\\Users\\volka\\IdeaProjects\\EU8-RestAssured-Ahmet-Durak\\src\\test\\java\\com\\cybertek\\POJOconverter\\JsonText.json";
        Scanner scanner = new Scanner(new File(path));
        String text = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            text = text.concat(line + "\n");
        }
        return new Scanner(text);
    }

    public static void checker(Scanner scanner) {
        List<Map<String, String>> subJsonFiles = new ArrayList<>();
        int openCurly = 0;
        int closeCurly = 0;
        int openBracket = 0;
        int closeBracket = 0;

        String arrayName = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("]")) closeBracket++;
            if (line.contains("}")) closeCurly++;
            if (line.contains("[")) {
                openBracket++;
                arrayName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                //System.out.println(arrayName);

            }
            if (line.contains("{")) openCurly++;

            if (openBracket - closeBracket >= 1) {
                if (subJsonFiles.toString().contains(arrayName)){
                    for (Map<String,String> subJson: subJsonFiles){
                        subJson.put(arrayName,subJson.get(arrayName) + "\n" + line); // update the upcoming text
                    }
                }else {
                    Map<String,String> subJson = new HashMap<>();
                    subJson.put(arrayName,line);
                    subJsonFiles.add(subJson);
                }

            }
        }
        for (Map<String,String> sub: subJsonFiles){
            System.out.println(sub.toString());
        }
    }
}
