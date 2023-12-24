package com.example.strategy_game;

import java.util.Random;

/**
 * Класс, отвечающий за количество домов в игре
 */
class Home {
    /**
     * Количество домов
     */
    int count;

    /**
     * @param count Устанавливает количество домов
     */
    public Home(int count) {
        this.count = count;
    }
}

/**
 * Класс, отвечающий за количество крестьян в игре
 */
class Peasants {
    /**
     * Количество крестьян
     */
    int count;

    /**
     * @param count Устанавливает количество крестьян
     */
    public Peasants(int count) {
        this.count = count;
    }
}

/**
 * Класс, отвечающий за количество риса в игре
 */
class Rise {
    /**
     * Количество риса
     */
    int count;
    /**
     * Значение политости риса
     */
    boolean watered;

    /**
     * @param count Устанавливает количество риса и статус поливки
     */
    public Rise(int count) {
        this.count = count;
        watered = false;
    }

    /**
     * Функция смены состояния риса. Полит/Не полит
     */
    public void ChangeWatered() {
        watered = !watered;
    }
}

/**
 * Класс, отвечающий за количество воды в игре
 */
class Water {
    /**
     * Количество воды
     */
    int count;

    /**
     * @param count Устанавливает количество воды
     */
    public Water(int count) {
        this.count = count;
    }
}

/**
 * Класс, отвечающий за параметры провинции в игре. Имеется стоимость и принадлежность по фракции
 */
class Field_cost {
    /**
     * Стоимость поля
     */
    int[] cost = new int[3];
    /**
     * Принадлежность к фракции
     */
    int fraction;

    /**
     * Генерирует случайную стоимость поля и его принадлежность
     */
    public Field_cost() {
        Random random = new Random();
        cost[0] = random.nextInt(9) + 1;
        cost[1] = random.nextInt(9) + 1;
        cost[2] = random.nextInt(9) + 1;
        fraction = 0;
    }
}