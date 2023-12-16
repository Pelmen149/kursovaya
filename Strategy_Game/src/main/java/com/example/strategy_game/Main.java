package com.example.strategy_game;

import javafx.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Главный класс, с которого начинается запуск приложения
 */
public class Main {
    private static final Logger logger = LogManager.getLogger("Strategy Game");
    /**
     * Функция запускающая программу
     */
    public static void main(String[] args){
        logger.info("Program has been successfully launched");
        Application.launch(HelloApplication.class, args);
        logger.fatal("Program was closed\n");
    }
}
