package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    Random r = new Random();

    String m_name;
    String m_playerClass;
    String m_special;
    int m_maxHealth;
    int m_health;
    int m_specialDamage;
    int m_attack;
    int m_defense;
    int m_level;
    int m_xp;
    int m_gold;

    public ArrayList<String> inventory = new ArrayList<>();

    public Player(String name, String playerClass, String special, int maxHealth, int health, int specialDamage, int attack, int defense, int level, int xp, int gold) {
        inventory.add("Health Potion");
        inventory.add("Health Potion");

        m_name = name;
        m_playerClass = playerClass;
        m_special = special;
        m_maxHealth = maxHealth;
        m_health = health;
        m_specialDamage = specialDamage;
        m_attack = attack;
        m_defense = defense;
        m_level = level;
        m_xp = xp;
        m_gold = gold;
    }

    public int attack() { return r.nextInt(m_xp + m_attack); }

    public int specialAttack() {
        return r.nextInt(m_xp + m_specialDamage);
    }

    public int defense() {
        return r.nextInt(m_defense);
    }

    public String getSave() {
        return m_name + "," + m_playerClass + "," + m_special + "," + m_maxHealth + "," + m_health + "," + m_specialDamage + "," + m_attack + "," + m_defense + "," + m_level + "," + m_xp + "," + m_gold;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }
}
