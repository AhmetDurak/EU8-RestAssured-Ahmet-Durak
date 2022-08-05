package com.cybertek.POJOconverter;

import org.junit.jupiter.api.Test;

import java.util.*;

public class POJO_Converter {
    private static String javaFile = "";
    private static String mainTextFile = "{\n" +
            "    \"items\": \"This has all the items\",\n" +
            "    \"hasMore\": true,\n" +
            "    \"limit\": 25,\n" +
            "    \"offset\": 0,\n" +
            "    \"count\": 25,\n" +
            "    \"links\": [  \n" +
            "        {\n" +
            "            \"rel\": \"self\",\n" +
            "            \"href\": \"http://54.89.215.84:1000/ords/hr/employees/\",\n" +
            "            \"links2\": [\n" +
            "]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static void JSONtoJAVA(String className, String jsonFile) {
        Scanner jsonText = new Scanner(jsonFile);
        String variableType = "";
        String nestedJsonFile = "";
        boolean hasList = false;
        boolean hasJsonProperty = false;
        boolean hasJsonIgnoreProperties = false;
        int counterCurly = 0;
        int indexCurly = 0;
        int counterBracket = 0;
        int indexBracket = 0;


        String javaText = "---------------------------------- " + className + " ----------------------------------\n" +
                "package com.cybertek.pojo;\n" +
                "@Getter\n" +
                "@Setter\n" +
                "@ToString\n" +
                "";


        // TODO ASKING USER IGNORE UNKNOWN
        //System.out.println("Is there any key you want to ignore? Y/N");
        /*if (jsonText.nextLine().equalsIgnoreCase("y")) */
        javaText = javaText.concat("@JsonIgnoreProperties(ignoreUnknown = true)\n");
        hasJsonIgnoreProperties = true;

        // TODO ADDING CLASS NAME
        javaText = javaText.concat("public class MainClassName {\n");

        // TODO CREATING CLASS CONTENT
        while (jsonText.hasNextLine()) {
            String line = jsonText.nextLine();

            if (line.contains("]")) counterBracket--;
            if (line.contains("}")) counterCurly--;

            // this line skip the array content
            if (indexBracket != counterBracket) continue;

            if (line.contains("{")) counterCurly++;
            if (line.contains("[")) counterBracket++;


            if (line.contains("\":")) {
                String element = line.substring(line.indexOf("\"") + 1, (line.indexOf(":") - 1));

                //todo how to determine variable type
                String value = line.substring(line.indexOf(":") + 2, line.length() - 1);
                if (value.trim().equals("true") || value.trim().equals("false")) variableType = "boolean";
                else if (value.matches("[0-9]")) variableType = "int";
                else if (value.matches("\".*\"")) variableType = "String";
                else if (value.matches("\\[")) {
                    // creating a new class name
                    if (element.charAt(element.length() - 1) == 's') {
                        className = element.substring(0, 1).toUpperCase() + element.substring(1, element.length() - 1);
                    }
                    variableType = "List<" + className + ">";
                    hasList = true;

                    // this line skips the array content
                    indexBracket = counterBracket;
                }
                //else if (value.matches("\\{+")) JSONtoJAVA();


                javaText = javaText.concat("\t" + "@JsonProperty(" + element + ")\n");
                hasJsonProperty = true;
                javaText = javaText.concat("\t" + "private " + variableType + " " + element + ";\n\n");
            }


        }

        javaText = javaText.concat("}");

        //TODO IMPORT CLASSES
        if (hasList) javaText = importFor("List", javaText);
        if (hasJsonProperty) javaText = importFor("@JsonProperty", javaText);
        if (hasJsonIgnoreProperties) javaText = importFor("@JsonIgnoreProperties", javaText);

        //TODO SAVE FILES
        javaFile = javaText.concat("\n\n---------------------------------- CLASS ENDS ----------------------------------");
        System.out.println("counterCurly = " + counterCurly);
        System.out.println("counterBracket = " + counterBracket);
        System.out.println("nestedJsonFile = " + nestedJsonFile);
    }

    private static String importFor(String importName, String javaText) {
        String willBeImported = "";
        switch (importName) {
            case "@JsonIgnoreProperties":
                willBeImported = "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;";
                break;
            case "@JsonProperty":
                willBeImported = "import com.fasterxml.jackson.annotation.JsonProperty;\n";
                break;
            case "List":
                willBeImported = "import java.util.List;\n";
                break;
        }

        return javaText.substring(0, (javaText.indexOf(";") + 1))
                + "\n" + willBeImported
                + javaText.substring(javaText.indexOf(";") + 1);
    }

    public void jsonFile(String jsonFile) {
        JSONtoJAVA("MainClassName", jsonFile);
        System.out.println(javaFile);
    }

    @Test
    public void test() {
        jsonFile(mainTextFile);
    }

    @Test
    public void test2() {
        String text = "{\n" +
                "    \"items\": \"This has all the items\",\n" +
                "    \"hasMore\": true,\n" +
                "    \"limit\": 25,\n" +
                "    \"offset\": 0,\n" +
                "    \"count\": 25,\n" +
                "    \"links\": \"This line has all the links\"\n" +
                "} +\n" +
                "                \"@Getter\\n\" +\n" +
                "                \"@Setter\\n\" +\n" +
                "                \"@ToString\\n\" +\n" +
                "                \"\";";
        Scanner scanner = new Scanner(text);
        //while (scanner.hasNextLine()) {
        //    String line = scanner.nextLine();
        //    System.out.println(line + "    " + line.lastIndexOf(","));
        //}
        text = text.substring(0, text.indexOf("@Getter") + 1)
                + "THIS IS DEMO";
        System.out.println(text);
    }
}
