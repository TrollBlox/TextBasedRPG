package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    private static final GameInit m_gameInit = new GameInit();

    private static File saveFile;

    public static void main(String[] args) {
        saveFile = new File("Saves.txt");
	    new GameLogic();
        gameMenu();
    }

    public static void gameMenu() {
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("\nThe Quest To Kill Magrius");
        GameLogic.header();
        System.out.println("\n1 - New Game");
        System.out.println(  "2 - Continue");
        System.out.println(  "3 - Quit");
        GameLogic.header();
        int menuChoice = GameLogic.choiceNoSave(3);
        if (menuChoice == 1) {
            m_gameInit.gameSetup();
        } else if (menuChoice == 2) {
            loadSave();
        } else if (menuChoice == 3) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nAre you sure?");
            GameLogic.header();
            System.out.println("\n1 - Yes");
            System.out.println(  "2 - No");
            GameLogic.header();
            GameLogic.areYouSure = GameLogic.choiceNoSave(2);
            if (GameLogic.areYouSure == 1) {
                System.exit(0);
            } else {
                gameMenu();
            }

        }

    }

    public static File getSaveFile() {
        return saveFile;
    }

    public static void loadSave() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            String readSave = reader.readLine();
            StringTokenizer saveTokenizer = new StringTokenizer(readSave, ",");
            ArrayList<String> save = new ArrayList<>();

            while (saveTokenizer.hasMoreTokens()) {
                save.add(saveTokenizer.nextToken());
            }

            GameLogic.player = new Player(save.get(0), save.get(1), save.get(2), Integer.parseInt(save.get(3)), Integer.parseInt(save.get(4)), Integer.parseInt(save.get(5)), Integer.parseInt(save.get(6)), Integer.parseInt(save.get(7)), Integer.parseInt(save.get(8)), Integer.parseInt(save.get(9)), Integer.parseInt(save.get(10)));
            String readInv = reader.readLine();
            saveTokenizer = new StringTokenizer(readInv, ",");
            save = new ArrayList<>();

            while (saveTokenizer.hasMoreTokens()) {
                save.add(saveTokenizer.nextToken());
            }

            GameLogic.player.inventory = save;
            GameLogic.clearConsole();
            System.out.println("Loading...");
            GameLogic.header();
            System.out.println("\nTip: Type 's' at any time to save your game!");
            GameLogic.header();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("\nError loading game!\nError: " + e);
                System.exit(0);
            }
            GameLogic.gameLoop();
        } catch (FileNotFoundException | NullPointerException e) {
            GameLogic.clearConsole();
            System.out.println("You do not have a save file!");
            GameLogic.pressAnythingNoSave();
            gameMenu();
        } catch (Exception e) {
            System.out.println("Something went wrong!\nError: " + e);
            e.printStackTrace();
            GameLogic.pressAnythingNoSave();
            gameMenu();
        }
    }

}
