package com.example.strategy_game;


/**
 * Класс предназначенный для удобного хранения всех параметров игры
 */
public class Game {
    /**
     * Количество ходов в игре
     */
    int move;
    /**
     * Количество воды в игре
     */
    Water water = new Water(1);
    /**
     * Количество домов в игре
     */
    Home home = new Home(1);
    /**
     * Количество крестьян в игре
     */
    Peasants peasants = new Peasants(2);
    /**
     * Количество риса в игре
     */
    Rise rise = new Rise(1);

    /**
     * Устанавливает количество ходов равное нулю
     */
    public Game() {
        move = 0;
    }
}
