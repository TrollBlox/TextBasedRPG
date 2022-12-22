package com.company;

public class Lore {
    public static void intro() {
        System.out.print("""

                King Magrius The Great is the current ruler of the Kingdom of Lestfallen.
                He is a horrible king, with no respect for anyone else.
                You are determined to take him down, and make the Kingdom of Lestfallen a better place.
                In order to reach him, you first have to have to cross the Grassy Plains full of monsters.
                """);
        GameLogic.header();
    }

    public static void finalBoss() {
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("""
                
                Well, its finally time, you made it through the Grassy Plains,
                and made it to the castle successfully. It is now finally time
                to fight King Magrius The Great.""");
        GameLogic.header();
        GameLogic.pressAnything();
    }

    public static void halfWayPoint() {
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("""
                
                You have traveled very far, and you can tell that you are almost halfway there...

                New enemies unlocked!""");
        GameLogic.header();
        GameLogic.enemies.add("Angry Bird");
        GameLogic.enemies.add("Tumble Weed");
        GameLogic.halfWayReached = true;
    }

    public static void win() {
        GameLogic.clearConsole();
        GameLogic.header();
        System.out.println("""
                
                You did it!
                You beat King Magrius The Great, and have become the new ruler of the Kingdom of Lestfallen!
                You have saved the people of Lestfallen from the clutches of King Magrius The Great.
                You decide to become king and live out the rest of your life in peace. Thanks for playing!""");
        GameLogic.header();
        GameLogic.pressAnything();
        GameLogic.playAgain();
    }

    public static void youDied() {
        GameLogic.clearConsole();
        System.out.println("You died.");
        GameLogic.pressAnything();
        GameLogic.playAgain();
    }

}
