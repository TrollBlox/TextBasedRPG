package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    static Scanner s = new Scanner(System.in);
    static Random r = new Random();

    static Player player;

    static char charOfChoice;

    static int returnable;
    static int dmgToEnemy;
    static int dmgToPlayer;
    static int invSize;
    static int shopItem;
    static int sure;
    static int xpReward;
    static int goldReward;
    static int invChoice;
    static int enemy;
    static int loopRandom;
    static int encounter;
    static int specialCooldown = 3;
    static int whatToUpgrade;
    static int playAgainChoice;
    static int areYouSure;

    static ArrayList<String> encounters = new ArrayList<>();
    static ArrayList<String> enemies = new ArrayList<>();

    static Enemy enemyInBattle;

    static boolean halfWayReached = false;

    public static BufferedWriter saveWriter;

    public GameLogic() {
        encounters.add("Trader");
        encounters.add("Trader");
        encounters.add("Quiet Day");

        enemies.add("Grassy Bug");
        enemies.add("Meadow Giant");
    }

    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public static void header() {
        for (int i = 0; i < 100; i++) {
            System.out.print("-");
        }
    }

    public static void pressAnything() {
        System.out.print("\nPress enter to continue... ");
        String button = s.nextLine();
        if (button.equals("s")) { save(); }
    }

    public static void pressAnythingNoSave() {
        System.out.print("\nPress enter to continue... ");
        String button = s.nextLine();
    }

    public static int choiceNoSave(int choices) {
        System.out.print("\n> ");
        try {
            charOfChoice = s.nextLine().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print("Please enter something!");
            charOfChoice = '\0';
            choice(choices);
        }
        try {
            if (Integer.parseInt(String.valueOf(charOfChoice)) <= 0 || Integer.parseInt(String.valueOf(charOfChoice)) > choices) {
                System.out.print("Please enter a valid choice!");
                choice(choices);
            } else {
                returnable = Integer.parseInt(String.valueOf(charOfChoice));
                charOfChoice = '\0';
            }
        } catch (NumberFormatException e) {
            System.out.print("Please enter a number!");
            choice(choices);
        }
        return returnable;
    }

    public static int choice(int choices) {
        System.out.print("\n> ");
        try {
            charOfChoice = s.nextLine().charAt(0);
            if (charOfChoice == 's') { save(); }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print("Please enter something!");
            charOfChoice = '\0';
            choice(choices);
        }
        try {
            if (Integer.parseInt(String.valueOf(charOfChoice)) < 0 || Integer.parseInt(String.valueOf(charOfChoice)) > choices) {
                System.out.print("Please enter a valid choice!");
                choice(choices);
            } else {
                returnable = Integer.parseInt(String.valueOf(charOfChoice));
                charOfChoice = '\0';
            }
        } catch (NumberFormatException e) {
            System.out.print("Please enter a number!");
            choice(choices);
        }
        return returnable;
    }

    public static void randomEncounter() {
        encounter = r.nextInt(encounters.size());
        if (encounter == 0 || encounter == 1) {
            clearConsole();
            header();
            System.out.println("\nOn your travels, you encounter a trader. You decide to trade with him.");
            header();
            pressAnything();
            shop();
        } else if (encounter == 2) {
            clearConsole();
            header();
            System.out.println("\nIt was a quiet day...");
            System.out.println(  "You used this time to heal some more health.");
            header();
            player.m_health += 5;
            if (player.m_health > player.m_maxHealth) {
                player.m_health = player.m_maxHealth;
            }
            System.out.println("\nYour health is now " + player.m_health + " / " + player.m_maxHealth + ".");
            pressAnything();
            gameLoop();
        }
    }

    public static void battle() {
        enemy = r.nextInt(1, enemies.size());
        if (enemy == 1) {
            enemyInBattle = new Enemy("Grassy Bug", 10, 10, 1);
        } else if (enemy == 2) {
            enemyInBattle = new Enemy("Meadow Giant", 15, 15, 2);
        } else if (enemy == 3) {
            enemyInBattle = new Enemy("Tumble Weed", 17, 17, 3);
        } else if (enemy == 4) {
            enemyInBattle = new Enemy("Angry Bird", 20, 20, 4);
        }
        clearConsole();
        System.out.println("Your travels were interrupted by a " + enemyInBattle.m_name);
        pressAnything();
        while (player.m_health > 0 && enemyInBattle.m_health > 0) {
            int battleChoice = battleMenu(enemyInBattle.m_name);
            if (battleChoice == 1) {
                turnSummary();
            } else if (battleChoice == 2){
                if (specialCooldown <= 0) {
                    specialTurnSummary();
                } else {
                    clearConsole();
                    System.out.println("Turns left to use special: " + specialCooldown);
                    pressAnything();
                }
            } else if (battleChoice == 3) {
                clearConsole();
                if (player.inventory.size() == 0) {
                    System.out.println("You have no items!");
                    pressAnything();
                } else {
                    header();
                    System.out.println("\nInventory");
                    header();
                    invSize = player.inventory.size();
                    System.out.println();
                    int i;
                    for (i = 0; i <= invSize - 1; i++) {
                        System.out.println(i + 1 + " - " + player.inventory.get(i));
                    }
                    System.out.println(i + 1 + " - Exit");
                    header();
                    invChoice = choice(invSize + 1);
                    try {
                        if (player.inventory.get(invChoice - 1).equals("Health Potion")) {
                            player.inventory.remove((invChoice - 1));
                            clearConsole();
                            player.m_health += 5;
                            if (player.m_health > player.m_maxHealth) player.m_health = player.m_maxHealth;
                            header();
                            System.out.println("\n+5 health");
                            System.out.println(player.m_name + ": " + player.m_health + " / " + player.m_maxHealth);
                            header();
                        } else if (player.inventory.get(invChoice - 1).equals("Super Health Potion")) {
                            player.inventory.remove((invChoice - 1));
                            clearConsole();
                            player.m_maxHealth += 10;
                            player.m_health = player.m_maxHealth;
                            header();
                            System.out.println("\n+10 max health");
                            System.out.println(player.m_name + ": " + player.m_health + " / " + player.m_maxHealth);
                            header();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        battleMenu(enemyInBattle.m_name);
                    }
                    pressAnything();
                    clearConsole();
                    denyTurnSummary();
                }
                clearConsole();
            } else if (battleChoice == 4) {
                int escapeChance = r.nextInt(3);
                if (escapeChance == 0) {
                    clearConsole();
                    System.out.println("You successfully escaped from the " + enemyInBattle.m_name + "!");
                    pressAnything();
                    gameLoop();
                } else {
                    System.out.println("You failed to escape!");
                    denyTurnSummary();
                }
            }
        }
        if (player.m_health <= 0) {
            gameOver();
        } else {
            win();
        }

    }

    public static int battleMenu(String enemy) {
        clearConsole();
        header();
        System.out.println();
        System.out.println(enemy);
        header();
        System.out.println("\n1 - Fight");
        System.out.println(  "2 - Special attack");
        System.out.println(  "3 - Item");
        System.out.println(  "4 - Escape");
        header();
        return choice(4);
    }

    public static void gameLoop() {
        loopRandom = r.nextInt(3);
        if (loopRandom < 2) {
            battle();
        } else {
            randomEncounter();
        }
    }

    public static void gameOver() {
        clearConsole();
        Lore.youDied();
    }

    public static void win() {
        clearConsole();
        xpReward = r.nextInt((enemyInBattle.m_difficulty + 10)) + 5;
        player.m_xp += xpReward;
        goldReward = r.nextInt((enemyInBattle.m_difficulty + 10));
        player.m_gold += goldReward;
        header();
        System.out.println("\nBattle Summary:");
        header();
        System.out.println("\n+" + xpReward + "xp");
        System.out.println(  "+" + goldReward + "G");
        System.out.println("You now have " + player.m_xp + "xp and " + player.m_gold + "G");
        header();
        pressAnything();
        if (!halfWayReached) {
            if (player.m_level > 4) {
                clearConsole();
                Lore.halfWayPoint();
                pressAnything();
            }
        }
        checkLevelUp();
        gameLoop();
    }

    public static void shop() {
        clearConsole();
        header();
        System.out.println("\nTrader");
        header();
        System.out.println("\n1 - Health Potion");
        System.out.println(  "2 - Super Health Potion");
        System.out.println(  "3 - Exit");
        header();
        shopItem = choice(3);
        if (shopItem == 1) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nA Health Potion heals 5 health.");
            GameLogic.header();
            GameLogic.pressAnything();
            if (player.m_gold >= 5) {
                clearConsole();
                header();
                System.out.println("\nAre you sure? This will cost 5G, and you have " + player.m_gold + "G");
                header();
                System.out.println("\n1 - Yes");
                System.out.println("2 - No");
                header();
                sure = choice(2);
                if (sure == 1) {
                    clearConsole();
                    player.m_gold -= 5;
                    player.inventory.add("Health Potion");
                    System.out.println("+1 Health Potion");
                    pressAnything();
                    shop();
                } else if (sure == 2) {
                    shop();
                }
            } else {
                clearConsole();
                System.out.println("You don't have enough Gs");
                pressAnything();
                shop();
            }
        } else if (shopItem == 2) {
            GameLogic.clearConsole();
            GameLogic.header();
            System.out.println("\nA Super Health Potion increases your max health by 10, and then heals you to max health.");
            GameLogic.header();
            GameLogic.pressAnything();
            if (player.m_gold >= 10) {
                clearConsole();
                header();
                System.out.println("\nAre you sure? This will cost 10G, and you have " + player.m_gold + "G");
                header();
                System.out.println("\n1 - Yes");
                System.out.println("2 - No");
                header();
                sure = choice(2);
                if (sure == 1) {
                    player.m_gold -= 10;
                    player.inventory.add("Super Health Potion");
                    System.out.println("+1 Super Health Potion");
                    pressAnything();
                    shop();
                } else if (sure == 2) {
                    shop();
                }
            } else {
                clearConsole();
                System.out.println("You don't have enough Gs");
                pressAnything();
                shop();
            }
        } else if (shopItem == 3) {
            gameLoop();
        }
    }

    public static void turnSummary() {
        specialCooldown--;
        clearConsole();
        dmgToEnemy = (player.attack() - enemyInBattle.defense()) + 2;
        if (dmgToEnemy < 0) {
            dmgToEnemy = -dmgToEnemy;
        }
        dmgToPlayer = (enemyInBattle.attack() - player.defense());
        if (dmgToPlayer < 0) {
            dmgToPlayer = -dmgToPlayer;
        }
        header();
        System.out.println("\nYou did " + dmgToEnemy + " damage to the " + enemyInBattle.m_name + "!");
        System.out.println(  "The " + enemyInBattle.m_name + " did " + dmgToPlayer + " damage to you!");
        enemyInBattle.m_health -= dmgToEnemy;
        player.m_health -= dmgToPlayer;
        header();
        System.out.println("\n" + enemyInBattle.m_name + ": " + enemyInBattle.m_health + " / " + enemyInBattle.m_maxHealth + " health");
        System.out.println(       player.m_name + ": " + player.m_health + " / " + player.m_maxHealth + " health");
        header();
        pressAnything();
    }

    public static void denyTurnSummary() {
        specialCooldown--;
        clearConsole();
        dmgToPlayer = (enemyInBattle.attack() - player.defense()) + 2;
        if (dmgToPlayer < 0) {
            dmgToPlayer = -dmgToPlayer;
        }
        header();
        System.out.println("\nThe " + enemyInBattle.m_name + " did " + dmgToPlayer + " damage to you!");
        player.m_health -= dmgToPlayer;
        header();
        System.out.println("\n" + enemyInBattle.m_name + ": " + enemyInBattle.m_health + " / " + enemyInBattle.m_maxHealth + " health");
        System.out.println(       player.m_name + ": " + player.m_health + " / " + player.m_maxHealth + " health");
        header();
        pressAnything();
    }

    public static void specialTurnSummary() {
        specialCooldown = 3;
        clearConsole();
        dmgToEnemy = (player.specialAttack() - enemyInBattle.defense());
        if (dmgToEnemy < 0) {
            dmgToEnemy = -dmgToEnemy;
        }
        dmgToPlayer = (enemyInBattle.attack() - player.defense());
        if (dmgToPlayer < 0) {
            dmgToPlayer = -dmgToPlayer;
        }
        header();
        System.out.println("\nYou used " + player.m_special + " and did " + dmgToEnemy + " damage to the " + enemyInBattle.m_name + "!");
        System.out.println(  "The " + enemyInBattle.m_name + " did " + dmgToPlayer + " damage to you!");
        enemyInBattle.m_health -= dmgToEnemy;
        player.m_health -= dmgToPlayer;
        header();
        System.out.println("\n" + enemyInBattle.m_name + ": " + enemyInBattle.m_health + " / " + enemyInBattle.m_maxHealth + " health");
        System.out.println(       player.m_name + ": " + player.m_health + " / " + player.m_maxHealth + " health");
        header();
        pressAnything();
    }

    public static void checkLevelUp() {
        if (player.m_level > 10) {
            Lore.finalBoss();
            finalBoss();
        } else if ((player.m_xp / 20) > player.m_level) {
            player.m_level++;
            clearConsole();
            header();
            System.out.println("\nYou leveled up to level " + player.m_level + "! What would you like to upgrade?");
            header();
            System.out.println("\n1 - Attack");
            System.out.println(  "2 - Special Attack");
            System.out.println(  "3 - Defense");
            header();
            whatToUpgrade = choice(3);
            if (whatToUpgrade == 1) {
                player.m_attack++;
            } else if (whatToUpgrade == 2) {
                player.m_specialDamage++;
            } else if (whatToUpgrade == 3) {
                player.m_defense++;
            }
            gameLoop();
        }
    }

    public static void playAgain() {
        clearConsole();
        header();
        System.out.println("\nWould you like to play again?");
        header();
        System.out.println("\n1 - Yes");
        System.out.println("2 - No");
        header();
        playAgainChoice = choice(2);
        if (playAgainChoice == 1) {
            Main.gameMenu();
        } else {
            clearConsole();
            header();
            System.out.println("\nAre you sure?");
            header();
            System.out.println("\n1 - Yes");
            System.out.println("2 - No");
            header();
            areYouSure = choice(2);
            if (areYouSure == 1) {
                System.exit(0);
            } else {
                playAgain();
            }
        }
    }

    public static void finalBoss() {
        enemyInBattle = new Enemy("King Magrius The Great", 200, 200, 10);
        while (player.m_health > 0 && enemyInBattle.m_health > 0) {
            int battleChoice = battleMenu(enemyInBattle.m_name);
            if (battleChoice == 1) {
                turnSummary();
            } else if (battleChoice == 2){
                if (specialCooldown <= 0) {
                    specialTurnSummary();
                } else {
                    clearConsole();
                    System.out.println("Turns left to use special: " + specialCooldown);
                    pressAnything();
                }
            } else if (battleChoice == 3) {
                clearConsole();
                if (player.inventory.size() == 0) {
                    System.out.println("You have no items!");
                    pressAnything();
                } else {
                    header();
                    System.out.println("\nInventory");
                    header();
                    invSize = player.inventory.size();
                    System.out.println();
                    int i;
                    for (i = 0; i <= invSize - 1; i++) {
                        System.out.println(i + 1 + " - " + player.inventory.get(i));
                    }
                    System.out.println(i + 1 + " - Exit");
                    header();
                    invChoice = choice(invSize);
                    try {
                        if (player.inventory.get(invChoice - 1).equals("Health Potion")) {
                            player.inventory.remove((invChoice - 1));
                            clearConsole();
                            player.m_health += 5;
                            if (player.m_health > player.m_maxHealth) player.m_health = player.m_maxHealth;
                            header();
                            System.out.println("\n+5 health");
                            System.out.println(player.m_name + ": " + player.m_health + " / " + player.m_maxHealth);
                            header();
                        } else if (player.inventory.get(invChoice - 1).equals("Super Health Potion")) {
                            player.inventory.remove((invChoice - 1));
                            clearConsole();
                            player.m_maxHealth += 10;
                            player.m_health += player.m_maxHealth;
                            if (player.m_health > player.m_maxHealth) player.m_health = player.m_maxHealth;
                            header();
                            System.out.println("\n+5 max health");
                            System.out.println(player.m_name + ": " + player.m_health + " / " + player.m_maxHealth);
                            header();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        battleMenu(enemyInBattle.m_name);
                    }
                    pressAnything();
                    clearConsole();
                    denyTurnSummary();
                }
            clearConsole();
            } else if (battleChoice == 4) {
                clearConsole();
                header();
                System.out.println("\nYOU CANNOT ESCAPE FROM KING MAGRIUS THE GREAT!");
                header();
                pressAnything();
                denyTurnSummary();
            }
        }
        if (player.m_health <= 0) {
            gameOver();
        } else {
            Lore.win();
        }

    }

    public static void save() {
        try {
            BufferedReader saveReader = new BufferedReader((new FileReader(Main.getSaveFile())));
            if (saveReader.readLine() != null) {
                clearConsole();
                header();
                System.out.println("\nAre you sure? This will overwrite your current save!");
                header();
                System.out.println("\n1 - Yes");
                System.out.println(  "2 - No");
                header();
                int sure = choice(2);
                if (sure == 2) {
                    return;
                }
            }
            saveWriter = new BufferedWriter(new FileWriter(Main.getSaveFile()));
            StringBuilder inv = new StringBuilder();
            for (int j = 0; j < player.getInventory().size(); j++) {
                if (j == 0) {
                    inv.append(player.getInventory().get((j)));
                } else {
                    inv.append(",").append(player.getInventory().get(j));
                }

            }
            saveWriter.write(player.getSave() + "\n" + inv);
            saveWriter.close();
            System.out.println("Save successful!");
            pressAnything();
            clearConsole();
            Main.gameMenu();
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get("Saves.txt"));
                save();
            } catch (IOException ex) {
                System.out.println("Something went wrong!\nError: " + e);
                pressAnything();
                clearConsole();
                Main.gameMenu();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!\nError: " + e);
            pressAnything();
            clearConsole();
            Main.gameMenu();
        }

    }

}