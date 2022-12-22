package com.company;

import java.util.Random;

public class Enemy {
    Random r = new Random();

    String m_name;
    int m_maxHealth;
    int m_health;
    int m_difficulty;

    public Enemy(String name, int maxHealth, int health, int difficulty) {
        m_name = name;
        m_maxHealth = maxHealth;
        m_health = health;
        m_difficulty = difficulty;
    }

    public int attack() {
        return r.nextInt(m_difficulty) - 2;
    }

    public int defense() {
        return r.nextInt(m_difficulty) + 1;
    }
}
