package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> list = new ArrayList<>();
    static List<String> mass = new ArrayList<>();
    static Map<String, List<Integer>> emptyMap = new HashMap<>();

    public static void main(String[] args) {
        String path = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data")) {
                path = args[i+1];
            }
        }
        startLogic(path);
        menu();
    }

    private static void startLogic(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNext()) {
                mass.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createMap();
    }

    private static void createMap() {
        for (int i = 0; i < mass.size(); i++) {
            String[] word = mass.get(i).split(" ");
            for (int j = 0; j < word.length; j++) {
                if (!emptyMap.containsKey(word[j])) {
                    emptyMap.put(word[j], Collections.singletonList(i));
                }
                else {
                    List<Integer> it = new ArrayList(emptyMap.get(word[j]));
                    it.add(i);
                    emptyMap.put(word[j], it);
                }
            }
        }
    }

    private static void menu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
        String str = scanner.nextLine();
        switch (str){
            case "1":
                chooseMode();
                break;
            case "2":
                printAll();
                break;
            case "0":
                System.exit(0);
            default:
                System.out.println("Incorrect option! Try again.");
                menu();
                break;
        }
    }

    private static void chooseMode() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String str = scanner.nextLine();
        switch (str){
            case "ALL":
                ask();
                break;
            case "ANY":
                ask2();
                break;
            default:
                System.out.println("Incorrect option! Try again.");
                chooseMode();
                break;
        }
    }

    private static void ask() {
        System.out.println("Enter a name or email to search all suitable people.");
        String askName = scanner.nextLine();
        String[] word = askName.split(" ");
        if (!askName.isEmpty()) {
            for (String s : mass) {
                for (int i = 0; i < word.length; i++) {
                    if (s.toLowerCase().contains(word[i].toLowerCase()) || s.toUpperCase().contains(word[i].toUpperCase())) {
                        list.add(s);
                    }
                }
            }
        }
        writeAnswer();
    }

    private static void ask2() {
        System.out.println("Enter a name or email to search all suitable people.");
        String askName = scanner.nextLine();
        if (!askName.isEmpty()) {
            if (emptyMap.containsKey(askName)) {
                List<Integer> in = emptyMap.get(askName);
                for (int i =0; i < in.size(); i++) {
                    list.add(mass.get(in.get(i)));
                }
            }
        }
        writeAnswer();
    }

    private static void printAll() {
        System.out.println("=== List of people ===");
        for (String s : mass) {
            System.out.println(s);
        }
        menu();
    }

    private static void writeAnswer() {
        if (!list.isEmpty()) {
            System.out.println("Found people:");
            for (String s : list) {
                System.out.println(s);
            }
        }
        else {
            System.out.println("No matching people found.");
        }
        System.out.println();
        list.clear();
        menu();
    }
}
