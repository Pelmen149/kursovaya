package com.example.strategy;

import java.util.Random;

class Home{
    int count;
    public Home(int count) {
        this.count = count;
    }
}
class Peasants{
    int count;
    public Peasants(int count){
        this.count = count;
    }
}
class Rise{
    int count;
    boolean watered;
    public Rise(int count){
        this.count = count;
        watered = false;
    }
    public void ChangeWatered() {
        watered=!watered;
    }
}
class Water{
    int count;
    public Water(int count){
        this.count = count;
    }
}
class Field_cost{
    int[] cost=new int[3];
    int fraction;
    public Field_cost(){
        Random random = new Random();
        cost[0] = random.nextInt(9)+1;
        cost[1] = random.nextInt(9)+1;
        cost [2] = random.nextInt(9)+1;
        fraction = 0;
    }
}