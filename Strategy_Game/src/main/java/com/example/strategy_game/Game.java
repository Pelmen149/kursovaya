package com.example.strategy_game;


/**
 * Класс предназначенный для удобного хранения всех параметров игры
 */
public class Game {
    int move;
    Water water = new Water(1);
    Home home = new Home(1);
    Peasants peasants = new Peasants(2);
    Rise rise = new Rise(1);

    /**
     * Устанавливает количество ходов равное нулю
     */
    public Game(){
        move = 0;
    }
}
