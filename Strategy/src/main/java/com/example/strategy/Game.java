package com.example.strategy;


public class Game {
    int move;
    Water water = new Water(1);
    Home home = new Home(1);
    Peasants peasants = new Peasants(2);
    Rise rise = new Rise(1);
    public Game(){
        move = 0;
    }
}
