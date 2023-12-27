package com.example.strategy_game;

import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Главный класс, с которого начинается запуск приложения
 */
public class Main {
    /**
     * Логгер для логгирования игры
     */
    private static final Logger LOGGER = LogManager.getLogger("Strategy Game");

    /**
     * Функция запускающая программу
     */
    public static void main(String[] args) {
        LOGGER.info("Program has been successfully launched");
        Application.launch(Strategy.class, args);
        LOGGER.fatal("Program was closed\n");
    }
}
