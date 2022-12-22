package com.company;

import java.util.Scanner;

public class GameInit {
    Scanner s = new Scanner(System.in);

    int nameCorrect;
    int SpecialChoice;

    public void gameSetup() {
        GameLogic.clearConsole();
        System.out.print("Hello! What is your name?\n> ");
        String name = s.next();
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("\nWarrior is the class most useful for taking hits, at the cost of attack damage.");
        System.out.println(  "Mage is the class most useful for dealing damage, at the cost of health.");
        System.out.println(  "Rouge is the most balanced class.");
        GameLogic.header();
        GameLogic.pressAnythingNoSave();
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("\nPick your class!");
        GameLogic.header();
        System.out.println("\n1 - Warrior");
        System.out.println(  "2 - Mage");
        System.out.println(  "3 - Rouge");
        GameLogic.header();
        int ClassChoice = GameLogic.choiceNoSave(3);

        if (ClassChoice == 1) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nThe Slice special deals more damage, but you get less defense.");
            System.out.println(  "The Chop special deals less damage, but you get more defense.");
            GameLogic.header();
            GameLogic.pressAnythingNoSave();
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nPick your special attack!");
            GameLogic.header();
            System.out.println("\n1 - Chop");
            System.out.println(  "2 - Slice");
            GameLogic.header();
            SpecialChoice = GameLogic.choiceNoSave(2);
            if (SpecialChoice == 1) {
                GameLogic.player = new Player(name, "Warrior", "Chop", 25, 25, 7, 37, 6, 0, 0, 0);
            }
            if (SpecialChoice == 2) {
                GameLogic.player = new Player(name, "Warrior", "Slice", 25, 25, 8, 3, 5, 0, 0, 0);
            }
        } else if (ClassChoice == 2) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nThe Cast special deals less damage, but you get more defense.");
            System.out.println( "The Ward special deals more damage, but you get less defense.");
            GameLogic.header();
            GameLogic.pressAnythingNoSave();
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nPick your special attack!");
            GameLogic.header();
            System.out.println("\n1 - Cast");
            System.out.println(  "2 - Ward");
            GameLogic.header();
            SpecialChoice = GameLogic.choiceNoSave(2);
            if (SpecialChoice == 1) {
                GameLogic.player = new Player(name, "Mage", "Cast", 15, 15, 9, 5, 2, 0, 0, 0);
            }
            if (SpecialChoice == 2) {
                GameLogic.player = new Player(name, "Mage", "Ward", 15, 15, 10, 5, 1, 0, 0, 0);
            }
        } else if (ClassChoice == 3) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nThe Sneak special deals slightly less damage, but you get slightly more defense.");
            System.out.println(  "The Evade special deals slightly more damage, but you get slightly less defense.");
            GameLogic.header();
            GameLogic.pressAnythingNoSave();
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nPick your special attack!");
            GameLogic.header();
            System.out.println("\n1 - Sneak");
            System.out.println(  "2 - Evade");
            GameLogic.header();
            SpecialChoice = GameLogic.choiceNoSave(2);
            if (SpecialChoice == 1) {
                GameLogic.player = new Player(name, "Rouge", "Sneak", 20, 20, 7, 3, 4, 0, 0, 0);
            }
            if (SpecialChoice == 2) {
                GameLogic.player = new Player(name, "Rouge", "Evade", 20, 20, 8, 4, 3, 0, 0, 0);
            }
        }

        GameLogic.clearConsole();
        System.out.println("You are " + GameLogic.player.m_name + " the " + GameLogic.player.m_playerClass + " with the special attack " + GameLogic.player.m_special + ".");
        GameLogic.header();
        System.out.println("\nIs this correct?");
        GameLogic.header();
        System.out.println("\n1 - Yes");
        System.out.println("2 - No");
        GameLogic.header();
        nameCorrect = GameLogic.choiceNoSave(2);
        if (nameCorrect == 1) {
            gameStart();
        } else if (nameCorrect == 2) {
            gameSetup();
        }
    }

    private void gameStart() {
        GameLogic.clearConsole();
        System.out.println("Loading...");
        GameLogic.header();
        System.out.println("\nTip: Type 's' at any time to save your game!");
        GameLogic.header();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Error loading game!\nError: " + e);
        }
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("\nHello, " + GameLogic.player.m_name + " the " + GameLogic.player.m_playerClass + ".");
        GameLogic.header();
        Lore.intro();
        GameLogic.pressAnythingNoSave();
        GameLogic.gameLoop();
    }

}
