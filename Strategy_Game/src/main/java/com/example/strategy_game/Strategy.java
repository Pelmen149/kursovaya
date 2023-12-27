package com.example.strategy_game;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;
import java.util.Base64;

import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Главный класс в котором происходят все действия
 */
public class Strategy extends Application {
    /**
     * Переменная отвечающая за определение режима карты
     */
    static boolean map;
    /**
     * Главный объект в котором происходят все действия в игре
     */
    private static Game game;
    /**
     * Текстовое отображение номера хода
     */
    private static Label move;
    /**
     * Массив кнопок для однотипных действий
     */
    private static final Button[] BUTTONS = new Button[10];
    /**
     * Кнопка Вкл/Выкл музыки на главном меню
     */
    private static final ToggleButton MUSIC_BUTTON_1 = new ToggleButton("Музыка Вкл/Выкл");
    /**
     * Кнопка Вкл/Выкл музыки на игровом меню
     */
    private static final ToggleButton MUSIC_BUTTON_2 = new ToggleButton("Музыка Вкл/Выкл");
    /**
     * Поле ввода названия сохранения
     */
    private static final TextField SAVE_FIELD = new TextField();
    /**
     * Массив объектов соответствующий параметрам поля с таким же номером
     */
    private static final fieldCost[][] FIELD_COSTS = new fieldCost[10][10];
    /**
     * Массив полей
     */
    private static final TextArea[][] FIELD_POINT = new TextArea[10][10];
    /**
     * Массив надписей для однотипных задач
     */
    private static final Label[] LABELS = new Label[10];
    /**
     * Окна сохранения
     */
    private static Stage saveWindow;
    /**
     * Окно поля
     */
    private static Stage fieldStage;
    /**
     * Окно покупки полей
     */
    private static Stage fieldBuy;
    /**
     * Окно главного меню и игры
     */
    private static Stage stagee;
    /**
     * Окно графика
     */
    private static Stage graph;
    /**
     * Массив кнопок для покупки полей с соответствующим номером
     */
    private static final Button[][] FIELD_BUTTON = new Button[10][10];
    /**
     * Объект для генерации случайных чисел
     */
    static final Random RANDOM = new Random();
    /**
     * Базовая вероятность противника захватить поле
     */
    static double chance = 0.14;
    /**
     * Массив для значений номера в первом массиве при выборе поля, захваченного врагом
     */
    static int[] boti = new int[100];
    /**
     * Массив для значений номера во втором массиве при выборе поля, захваченного врагом
     */
    static int[] botj = new int[100];
    /**
     * Окна ошибки при покупке поля
     */
    static Alert alertBuy;
    /**
     * Ошибка загрузки игры
     */
    static Alert alertLoad;
    /**
     * Ошибка отсутствия файла сохранения
     */
    static Alert alertNoFile;
    /**
     * Оповещение о поражении
     */
    static Alert lose;
    /**
     * Оповещение о победе
     */
    static Alert win;
    /**
     * Ошибка отсутствия файла музыки
     */
    static Alert alertLoadMusic;
    /**
     * Ошибка сохранения игры
     */
    static Alert alertSave;
    /**
     * Интерфейс для окна карты
     */
    private static TilePane field1;
    /**
     * Интерфейс для окна покупки поля
     */
    private static TilePane field2;
    /**
     * Интерфейс для главного меню
     */
    private static VBox mainMenu;
    /**
     * Интерфейс для окна сохранения
     */
    private static VBox save;
    /**
     * Интерфейс для окна с графиком
     */
    private static VBox graphicWindow;
    /**
     * Интерфейс для игрового меню
     */
    private static GridPane gameMenu;
    /**
     * Кнопка следующего хода
     */
    private static Button next;
    /**
     * Кнопка загруки игры
     */
    private static Button load;
    /**
     * Кнопка новой игры
     */
    private static Button newGame;
    /**
     * Кнопка смены режима карты
     */
    private static Button change;
    /**
     * Кнопка выхода из игрового меню
     */
    private static Button exit;
    /**
     * Кнопка выхода из главного меню
     */
    private static Button exitMenu;
    /**
     * Кнопка показа графика
     */
    private static Button showingGraph;
    /**
     * Кнопка выбора директории сохранения
     */
    private static Button directoryChooseButton;
    /**
     * Сцена для окна игры
     */
    private static Scene sceneGame;
    /**
     * Сцена для окна графика
     */
    private static Scene graphicScene;
    /**
     * Логгер предназначенный для логгирования игры
     */
    private static final Logger LOGGER = LogManager.getLogger("Strategy Game");
    /**
     * Музыка
     */
    private static Media sound;
    /**
     * Проигрыватель для музыки
     */
    private static MediaPlayer mediaPlayer;
    /**
     * Координаты х для графика
     */
    static NumberAxis x;
    /**
     * Координаты y для графика
     */
    static NumberAxis y;
    /**
     * Лист для сбора всех данных для графика
     */
    static ObservableList graphic = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества крестьян для графика
     */
    static ObservableList graphicPeasants = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества риса для графика
     */
    static ObservableList graphicRise = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества воды для графика
     */
    static ObservableList graphicWater = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества домов для графика
     */
    static ObservableList graphicHome = FXCollections.observableArrayList();
    /**
     * Объект демонстрирующий график
     */
    static LineChart lineGraphic;
    /**
     * Строка для хранения значений графиков за всю игру
     */
    static StringBuilder saveGraphs = new StringBuilder();
    /**
     * Путь сохранения игры
     */
    static String path = new String();
    /**
     * Переменная отвечающая за выбранный файл сохранения
     */
    static File file;
    /**
     * Объект выбора файла
     */
    static FileChooser fileChooser;
    /**
     * Объект выбора директории
     */
    static DirectoryChooser directoryChooser;

    /**
     * Функция создания главного окна
     * с её помощью создаётся главное окно при запуске программы
     */
    public static void MainMenuWindow() {
        stagee.setResizable(false);
        mainMenu.getChildren().addAll(newGame, load, exitMenu, MUSIC_BUTTON_1);
        Scene scene = new Scene(mainMenu, 800, 600);
        stagee.setTitle("Strategy Game");
        stagee.setScene(scene);
        stagee.show();
    }

    /**
     * Генерация окна и сцены полей для двух режимов карт
     */
    public static void FieldWindows() {
        fieldStage = new Stage();
        Scene field1 = new Scene(Strategy.field1, 570, 580);
        fieldStage.setResizable(false);
        fieldStage.setTitle("Поле игры");
        fieldStage.setScene(field1);
        fieldStage.setX(stagee.getX() + 200);
        fieldStage.setY(stagee.getY() + 100);

        fieldBuy = new Stage();
        Scene field2 = new Scene(Strategy.field2, 550, 550);
        fieldBuy.setResizable(false);
        fieldBuy.setTitle("Поле игры");
        fieldBuy.setScene(field2);
        fieldBuy.setX(stagee.getX() + 200);
        fieldBuy.setY(stagee.getY() + 100);
    }

    /**
     * Добавление элементов интерфейса в игровое меню
     */
    public static void GameInterface() {
        gameMenu.add(next, 1, 0);
        gameMenu.add(BUTTONS[1], 2, 0);
        gameMenu.add(BUTTONS[2], 3, 0);
        gameMenu.add(change, 4, 0);
        gameMenu.add(BUTTONS[3], 1, 1);
        gameMenu.add(BUTTONS[4], 3, 1);
        gameMenu.add(LABELS[0], 4, 1);
        gameMenu.add(move, 1, 2);
        gameMenu.add(BUTTONS[5], 3, 2);
        gameMenu.add(LABELS[1], 4, 2);
        gameMenu.add(BUTTONS[6], 3, 3);
        gameMenu.add(LABELS[2], 4, 3);
        gameMenu.add(BUTTONS[7], 3, 4);
        gameMenu.add(LABELS[3], 4, 4);
        gameMenu.add(exit, 1, 6);
        gameMenu.add(MUSIC_BUTTON_2, 1, 5);
        gameMenu.add(showingGraph, 2, 1);
    }

    /**
     * Генерация окна сохранения игры
     */
    public static void SaveWindowVoid() {
        Scene save_scene = new Scene(save, 250, 180);
        saveWindow = new Stage();
        saveWindow.setResizable(false);
        saveWindow.setTitle("Сохранение игры");
        saveWindow.setScene(save_scene);
        saveWindow.setX(stagee.getX() - 200);
        saveWindow.setY(stagee.getY() - 100);
        save.getChildren().addAll(directoryChooseButton, LABELS[4], SAVE_FIELD, BUTTONS[0]);
        save.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Создание и настройка служебных кнопок при запуске программы
     */
    public static void SettingsOfNewGame() {
        FIELD_POINT[9][9].setText(" Вы");
        FIELD_COSTS[9][9].fraction = 1;
        FIELD_POINT[9][9].setStyle("-fx-background-color: green;");
        FIELD_POINT[0][0].setText("Враг");
        FIELD_COSTS[0][0].fraction = 2;
        FIELD_POINT[0][0].setStyle("-fx-background-color: red;");
        BUTTONS[0].setPrefSize(60, 35);
        move = new Label();
        BUTTONS[3].setText("Купить поле");
        BUTTONS[4].setText("Построить дом ");
        BUTTONS[5].setText("Добыть воды ");
        BUTTONS[6].setText("Полить рис ");
        BUTTONS[7].setText("Собрать рис ");
        next = new Button("Следующий ход");
        next.setPrefWidth(200);
        exit = new Button("Выйти из игры");
        exit.setPrefWidth(200);
        exitMenu = new Button("Выйти из игры");
        exitMenu.setPrefSize(200, 80);
        showingGraph = new Button("Показать график");
        showingGraph.setPrefWidth(200);
        MUSIC_BUTTON_2.setPrefWidth(200);
        newGame = new Button("Новая игра");
        load = new Button("Загрузить сохранение");
        change = new Button("Изменить режим карты");
        newGame.setPrefSize(200, 80);
        load.setPrefSize(200, 80);
        directoryChooseButton = new Button("Выбрать директорию");
    }

    /**
     * Создание и настройка окон и сцен в игре
     */
    public static void WindowsGeneration() {
        stagee = new Stage();
        field1 = new TilePane();
        field2 = new TilePane();
        field1.setOrientation(Orientation.HORIZONTAL);
        field2.setOrientation(Orientation.HORIZONTAL);
        mainMenu = new VBox();
        mainMenu.setPadding(new Insets(10, 10, 10, 10));
        mainMenu.setSpacing(10);
        mainMenu.setAlignment(Pos.CENTER);
        gameMenu = new GridPane();
        gameMenu.setPadding(new Insets(10, 10, 10, 10));
        gameMenu.setAlignment(Pos.TOP_CENTER);
        save = new VBox();
        save.setSpacing(10);
        save.setAlignment(Pos.TOP_CENTER);
        graphicWindow = new VBox(lineGraphic);
        graphicScene = new Scene(graphicWindow, 600, 400);
        graph = new Stage();
        graph.setScene(graphicScene);
        graph.setTitle("График ресурсов");
    }

    /**
     * Генерация двойного массива кнопок и текстовых полей для меню покупки и отображения игровых провинций
     */
    public static void FieldsAndButtons() {
        for (int i = 0; i < 10; i++) {
            BUTTONS[i] = new Button();
            BUTTONS[i].setPrefWidth(200);
        }
        for (int i = 0; i < 10; i++)
            LABELS[i] = new Label();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                FIELD_COSTS[i][j] = new fieldCost();
                FIELD_POINT[i][j] = new TextArea(null);
                FIELD_POINT[i][j].setPrefColumnCount(2);
                FIELD_POINT[i][j].setPrefRowCount(2);
                FIELD_POINT[i][j].setEditable(false);
                field1.getChildren().add(FIELD_POINT[i][j]);
            }
        }
    }

    /**
     * Создание окон с ошибками, которые могут произойти при использовании программы
     */
    public static void Alerts() {
        alertBuy = new Alert(Alert.AlertType.ERROR, "Недостаточно ресурсов", ButtonType.OK);
        alertLoad = new Alert(Alert.AlertType.ERROR, "Файл содержит запрещенные ресурсы", ButtonType.OK);
        alertSave = new Alert(Alert.AlertType.ERROR, "Название содержит запрещенные символы, потому может быть не сохранён", ButtonType.OK);
        alertNoFile = new Alert(Alert.AlertType.ERROR, "Файл не выбран", ButtonType.OK);
        alertLoadMusic = new Alert(Alert.AlertType.ERROR, "Файл музыки отсутствует. Поместите файл 'theme1.mp3' в папку с jar файлом", ButtonType.OK);
        lose = new Alert(Alert.AlertType.NONE, "Вы проиграли", ButtonType.OK);
        win = new Alert(Alert.AlertType.NONE, "Вы выиграли", ButtonType.OK);

    }

    /**
     * Открытие окна с выбором файла сохранения
     */
    public static void FileChoose() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SAVE", "*.save"), new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(stagee);
    }

    /**
     * Выбор директории для файла сохранения
     */
    public static void DirectoryChoose() {
        directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(saveWindow);
        if (dir != null) {
            path = dir.getAbsolutePath();
            SAVE_FIELD.setDisable(false);
            BUTTONS[0].setDisable(false);
        } else {
            alertNoFile.showAndWait();
        }
    }

    /**
     * Кнопка, отвечающая за загрузку игры
     */
    public static void LoadButton() {
        LOGGER.info("Load button was clicked");
        FileChoose();
        if (file == null) {
            alertNoFile.showAndWait();
            LOGGER.error("File of save was not choosen");
        } else {
            game = new Game();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line = reader.readLine();
                try {
                    game.move = Integer.parseInt(new String(Base64.getDecoder().decode(line)));
                    game.home.count = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                    game.rise.count = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                    game.peasants.count = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                    game.water.count = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++) {
                            for (int k = 0; k < 3; k++) {
                                FIELD_COSTS[i][j].cost[k] = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                            }
                            FIELD_COSTS[i][j].fraction = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        }
                    BUTTONS[4].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    BUTTONS[5].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    BUTTONS[6].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    BUTTONS[7].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    game.rise.watered = Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine())));
                    for (int i = 0; i < game.move; i++) {
                        int x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphicPeasants.add(new XYChart.Data(i, x));
                        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphicRise.add(new XYChart.Data(i, x));
                        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphicWater.add(new XYChart.Data(i, x));
                        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphicHome.add(new XYChart.Data(i, x));
                        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                    }
                    map = true;

                    sceneGame = new Scene(gameMenu, 800, 600);

                    move.setText("Ход: " + game.move);
                    BUTTONS[2].setDisable(true);
                    BUTTONS[2].setText("Показать карту");
                    BUTTONS[1].setText("Сохранить игру");
                    next.setText("Следующий ход ");
                    LABELS[0].setText("Количество крестьян: " + game.peasants.count);
                    LABELS[1].setText("Количество риса: " + game.rise.count);
                    LABELS[2].setText("Количество воды: " + game.water.count);
                    LABELS[3].setText("Количество домов: " + game.home.count);
                    stagee.setScene(sceneGame);
                    stagee.show();
                    BUTTONS[2].setDisable(true);
                    BUTTONS[3].setDisable(true);
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++) {
                            if (FIELD_COSTS[i][j].fraction == 0) {
                                FIELD_POINT[i][j].setText(null);
                            } else if (FIELD_COSTS[i][j].fraction == 1) {
                                FIELD_POINT[i][j].setText(" Вы");
                                FIELD_POINT[i][j].setStyle("-fx-background-color: green;");
                            } else if (FIELD_COSTS[i][j].fraction == 2) {
                                FIELD_POINT[i][j].setText("Враг");
                                FIELD_POINT[i][j].setStyle("-fx-background-color: red;");
                            }
                        }

                    fieldStage.show();
                } catch (Exception e) {
                    alertLoad.showAndWait();
                    LOGGER.error("File of save has forbidden characters");
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Кнопка, отвечающая за новую игру
     */
    public static void NewGameButton() {
        LOGGER.info("New game button was clicked");

        sceneGame = new Scene(gameMenu, 800, 600);

        fieldStage.show();

        map = true;
        game = new Game();
        move.setText("Ход: " + game.move);
        BUTTONS[2].setDisable(true);
        BUTTONS[3].setDisable(true);
        BUTTONS[2].setText("Показать карту");
        BUTTONS[1].setText("Сохранить игру");
        next.setText("Следующий ход ");
        LABELS[0].setText("Количество крестьян: " + game.peasants.count);
        LABELS[1].setText("Количество риса: " + game.rise.count);
        LABELS[2].setText("Количество воды: " + game.water.count);
        LABELS[3].setText("Количество домов: " + game.home.count);
        stagee.setScene(sceneGame);
        stagee.show();
    }

    /**
     * Кнопки предназначенные для покупки полей
     */
    public static void BuyFieldButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int finalJ = j;
                int finalI = i;
                FIELD_BUTTON[i][j].setOnAction(event -> {
                    LOGGER.info("Buy field button was clicked");
                    if (game.peasants.count >= FIELD_COSTS[finalI][finalJ].cost[0] & game.rise.count >= FIELD_COSTS[finalI][finalJ].cost[1] & game.water.count >= FIELD_COSTS[finalI][finalJ].cost[2]) {
                        game.peasants.count = game.peasants.count - FIELD_COSTS[finalI][finalJ].cost[0];
                        game.rise.count = game.rise.count - FIELD_COSTS[finalI][finalJ].cost[1];
                        game.water.count = game.water.count - FIELD_COSTS[finalI][finalJ].cost[2];
                        FIELD_COSTS[finalI][finalJ].fraction = 1;
                        FIELD_POINT[finalI][finalJ].setStyle("-fx-background-color: green;");
                        fieldBuy.close();
                        BUTTONS[2].setDisable(false);
                        BUTTONS[3].setDisable(false);
                        change.setDisable(false);
                    } else {
                        alertBuy.showAndWait();
                        LOGGER.error("There are not enough resources to purchase a field");
                    }
                    LABELS[0].setText("Количество крестьян: " + game.peasants.count);
                    LABELS[1].setText("Количество риса: " + game.rise.count);
                    LABELS[2].setText("Количество воды: " + game.water.count);
                });
            }
        }
    }

    /**
     * Функция отвечающая за работу простейшего искусственного интеллекта
     */
    public static void AI() {
        if (chance > RANDOM.nextInt(100)) {
            int k = 0;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (i != 9 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[1 + i][j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (j != 9 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i][1 + j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (i != 0 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i - 1][j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (j != 0 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i][j - 1].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    }
                }
            int choose = new Random().nextInt(k);
            FIELD_COSTS[boti[choose]][botj[choose]].fraction = 2;
            FIELD_POINT[boti[choose]][botj[choose]].setStyle("-fx-background-color: red;");
            chance = 1;
        } else chance *= 1.4;
    }

    /**
     * Проверка условия на победу или поражение
     */
    public static void WinOrLose() {
        int count_enemy = 0, count_you = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (FIELD_COSTS[i][j].fraction == 2) {
                    count_enemy += 1;
                } else if (FIELD_COSTS[i][j].fraction == 1) {
                    count_you += 1;
                }
            }
        if (count_enemy >= 51) {
            stagee.close();
            lose.showAndWait();
        } else if (count_you >= 51) {
            stagee.close();
            win.showAndWait();
        }
    }

    /**
     * Функция возвращения основных кнопок к исходному состоянию
     */
    public static void ResetButtons() {
        for (int i = 4; i < 8; i++)
            BUTTONS[i].setDisable(false);
        BUTTONS[4].setText("Построить дом ");
        BUTTONS[5].setText("Добыть воды ");
        BUTTONS[6].setText("Полить рис ");
        BUTTONS[7].setText("Собрать рис ");
    }

    /**
     * Кнопка следующего хода
     */
    public static void NextMoveButton() {
        LOGGER.info("Next move button was clicked");
        graphicPeasants.add(new XYChart.Data(game.move, game.peasants.count));
        graphicRise.add(new XYChart.Data(game.move, game.rise.count));
        graphicWater.add(new XYChart.Data(game.move, game.water.count));
        graphicHome.add(new XYChart.Data(game.move, game.home.count));
        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.peasants.count).getBytes()));
        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.rise.count).getBytes()));
        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.water.count).getBytes()));
        saveGraphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.home.count).getBytes()));


        game.move++;
        fieldStage.close();
        fieldBuy.close();
        BUTTONS[2].setDisable(false);
        BUTTONS[3].setDisable(false);
        change.setDisable(false);

        ResetButtons();

        AI();

        WinOrLose();

        game.peasants.count += game.home.count;
        move.setText("Ход: " + game.move);
        LABELS[0].setText("Количество крестьян: " + game.peasants.count);
        LABELS[1].setText("Количество риса: " + game.rise.count);
        LABELS[2].setText("Количество воды: " + game.water.count);
        LABELS[3].setText("Количество домов: " + game.home.count);
    }

    /**
     * Кнопка смены режима карты
     */
    public static void ChangeMapButton() {
        LOGGER.info("Change map mode button was clicked");
        if (map) {
            map = !map;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (FIELD_COSTS[i][j].fraction == 0) {
                        FIELD_POINT[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    } else if (FIELD_COSTS[i][j].fraction == 2) {
                        FIELD_POINT[i][j].setText(" ");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: red;");
                    } else {
                        FIELD_POINT[i][j].setText(" ");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: green;");
                    }
                }
        } else {
            map = !map;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (FIELD_COSTS[i][j].fraction == 0) {
                        FIELD_POINT[i][j].setText(null);
                    } else if (FIELD_COSTS[i][j].fraction == 1) {
                        FIELD_POINT[i][j].setText(" Вы");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: green;");
                    } else if (FIELD_COSTS[i][j].fraction == 2) {
                        FIELD_POINT[i][j].setText("Враг");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: red;");
                    }
                }
        }
        fieldStage.show();
        BUTTONS[2].setDisable(true);
        BUTTONS[3].setDisable(true);
    }

    /**
     * Кнопка подтверждения сохранения игры
     */
    public static void AcceptSaveButton() {
        LOGGER.info("Confirmation of saving button was clicked");

        WrongSymbols();

        try (FileWriter writer = new FileWriter(path + "\\" + SAVE_FIELD.getText() + ".save", false)) {
            StringBuilder text = new StringBuilder();
            text.append(Base64.getEncoder().encodeToString(Integer.toString(game.move).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.home.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.rise.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.peasants.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.water.count).getBytes()));
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(FIELD_COSTS[i][j].cost[0]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(FIELD_COSTS[i][j].cost[1]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(FIELD_COSTS[i][j].cost[2]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(FIELD_COSTS[i][j].fraction).getBytes()));

                }
            for (int i = 4; i < 8; i++) {
                text.append("\n" + Base64.getEncoder().encodeToString(Boolean.toString(BUTTONS[i].isDisable()).getBytes()));
            }
            text.append("\n" + Base64.getEncoder().encodeToString(Boolean.toString(game.rise.watered).getBytes()));
            text.append(saveGraphs);
            writer.write(text.toString());
            writer.flush();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        stagee.show();
        BUTTONS[2].setDisable(false);
        BUTTONS[3].setDisable(false);
        change.setDisable(false);
        showingGraph.setDisable(false);
        saveWindow.close();
    }

    /**
     * Кнопка для открытия окна сохранения игры
     */
    public static void SaveButton() {
        LOGGER.info("Save button was clicked");
        LABELS[4].setText("Введите название сохранения");
        BUTTONS[0].setText("Готово");
        SAVE_FIELD.setDisable(true);
        BUTTONS[0].setDisable(true);
        saveWindow.show();
        stagee.close();
        fieldBuy.close();
        fieldStage.close();
        graph.close();
    }

    /**
     * Кнопка показа карты игры
     */
    public static void ShowMapButton() {
        LOGGER.info("Show map button was clicked");
        BUTTONS[2].setDisable(true);
        BUTTONS[3].setDisable(true);
        if (map == false) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (FIELD_COSTS[i][j].fraction == 0)
                        FIELD_POINT[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    else if (FIELD_COSTS[i][j].fraction == 2) {
                        FIELD_POINT[i][j].setText(" ");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: red;");
                    } else {
                        FIELD_POINT[i][j].setText(" ");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: green;");
                    }
                }
        } else {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (FIELD_COSTS[i][j].fraction == 0) {
                        FIELD_POINT[i][j].setText(null);
                    } else if (FIELD_COSTS[i][j].fraction == 1) {
                        FIELD_POINT[i][j].setText(" Вы");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: green;");
                    } else if (FIELD_COSTS[i][j].fraction == 2) {
                        FIELD_POINT[i][j].setText("Враг");
                        FIELD_POINT[i][j].setStyle("-fx-background-color: red;");
                    }
                }
        }
        fieldStage.show();
    }

    /**
     * Кнопка перехода в меню покупки полей
     */
    public static void BuyButtonsMenuButton() {
        LOGGER.info("Menu of buy buttons button was clicked");
        fieldStage.close();
        BUTTONS[2].setDisable(true);
        BUTTONS[3].setDisable(true);
        change.setDisable(true);
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (FIELD_COSTS[i][j].fraction == 1) {
                    FIELD_BUTTON[i][j].setText(" Вы");
                    FIELD_BUTTON[i][j].setDisable(true);
                } else if (FIELD_COSTS[i][j].fraction == 2) {
                    FIELD_BUTTON[i][j].setText("Враг");
                    FIELD_BUTTON[i][j].setDisable(true);
                } else if (i != 9 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[1 + i][j].fraction == 1) {
                    FIELD_BUTTON[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    FIELD_BUTTON[i][j].setDisable(false);
                } else if (j != 9 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i][1 + j].fraction == 1) {
                    FIELD_BUTTON[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    FIELD_BUTTON[i][j].setDisable(false);
                } else if (i != 0 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i - 1][j].fraction == 1) {
                    FIELD_BUTTON[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    FIELD_BUTTON[i][j].setDisable(false);
                } else if (j != 0 && FIELD_COSTS[i][j].fraction == 0 && FIELD_COSTS[i][j - 1].fraction == 1) {
                    FIELD_BUTTON[i][j].setText(String.valueOf(FIELD_COSTS[i][j].cost[0]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[1]) + " " + String.valueOf(FIELD_COSTS[i][j].cost[2]));
                    FIELD_BUTTON[i][j].setDisable(false);
                } else FIELD_BUTTON[i][j].setDisable(true);

            }
        fieldBuy.show();
    }

    /**
     * Функция генерации кнопок для покупки полей
     */
    public static void GenerationFieldButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                FIELD_BUTTON[i][j] = new Button();
                FIELD_BUTTON[i][j].setPrefSize(55, 55);
                FIELD_BUTTON[i][j].setDisable(true);
                field2.getChildren().add(FIELD_BUTTON[i][j]);
            }
        } //Создание покупки полей
    }

    /**
     * Кнопка постройки дома
     */
    public static void BuyHomeButton() {
        LOGGER.info("Build home button was clicked");
        if (game.peasants.count > 0 & game.rise.count > 0 & game.water.count > 0) {
            game.home.count += 1;
            game.peasants.count -= 1;
            game.rise.count -= 1;
            game.water.count -= 1;
            for (int i = 4; i < 8; i++)
                BUTTONS[i].setDisable(true);
        } else {
            BUTTONS[4].setText("Недостаточно ресурсов");
        }
        LABELS[0].setText("Количество крестьян: " + game.peasants.count);
        LABELS[1].setText("Количество риса: " + game.rise.count);
        LABELS[2].setText("Количество воды: " + game.water.count);
        LABELS[3].setText("Количество домов: " + game.home.count);
    }

    /**
     * Кнопка сбора воды
     */
    public static void GetWaterButton() {
        LOGGER.info("Get water button was clicked");
        if (game.peasants.count > 0) {
            game.water.count += 1;
            game.peasants.count -= 1;
            for (int i = 4; i < 8; i++)
                BUTTONS[i].setDisable(true);
        } else {
            BUTTONS[5].setText("Недостаточно крестьян для сбора воды");
        }
        LABELS[0].setText("Количество крестьян: " + game.peasants.count);
        LABELS[2].setText("Количество воды: " + game.water.count);
    }

    /**
     * Кнопка полития риса
     */
    public static void WateredRiseButton() {
        LOGGER.info("Watered rise button was clicked");
        if (game.water.count > 0) {
            game.water.count -= 1;
            game.rise.ChangeWatered();
            BUTTONS[6].setText("Рис полит");
            for (int i = 4; i < 8; i++)
                BUTTONS[i].setDisable(true);
        } else {
            BUTTONS[6].setText("Недостаточно воды для полива");
        }
        LABELS[2].setText("Количество воды: " + game.water.count);
    }

    /**
     * Кнопка сбора риса
     */
    public static void CollectingRiseButton() {
        LOGGER.info("Collecting rise button was clicked");
        if (game.peasants.count > 0) {
            game.peasants.count -= 1;
            if (game.rise.watered) {
                game.rise.count += 3;
                game.rise.ChangeWatered();
            } else {
                game.rise.count += 1;
            }
            for (int i = 4; i < 8; i++)
                BUTTONS[i].setDisable(true);
        } else {
            BUTTONS[5].setText("Недостаточно крестьян для сбора риса");
        }
        LABELS[0].setText("Количество крестьян: " + game.peasants.count);
        LABELS[1].setText("Количество риса: " + game.rise.count);
    }

    /**
     * Кнопка для выхода из игры в игровом меню
     */
    public static void ExitButton() {
        LOGGER.info("Exit button was clicked");
        saveWindow.close();
        fieldStage.close();
        fieldBuy.close();
        stagee.close();
        graph.close();
        LOGGER.fatal("Program was closed\n");
    }

    /**
     * Кнопка для выхода из игры в главном меню
     */
    public static void ExitMenuButton() {
        LOGGER.info("Exit button was clicked");
        saveWindow.close();
        fieldStage.close();
        fieldBuy.close();
        stagee.close();
        graph.close();
        LOGGER.fatal("Program was closed\n");
    }

    /**
     * Функция для проверки запрещенных символов в названии файла сохранения
     */
    public static void WrongSymbols() {
        String[] wrond = new String[]{"|", "con", "/", "\\", ">", "<", ":", "*", "?", "'",};
        for (int i = 0; i < wrond.length; i++) {
            if (SAVE_FIELD.getText().contains(wrond[i])) {
                alertSave.showAndWait();
                break;
            }
        }
    }

    /**
     * Функция запуска музыки
     */
    public static void Music() {
        try {
            String musicFile = "theme1.mp3";
            sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            alertLoadMusic.showAndWait();
        }
    }

    /**
     * Функция зацикливания музыки
     */
    public static void MusicRetry() {
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }
    }

    /**
     * Кнопка включения/выключения музыки в главном меню
     */
    public static void MusicButtonOne() {
        MUSIC_BUTTON_1.setOnAction(event -> {
            if (mediaPlayer != null) {
                if (MUSIC_BUTTON_1.isSelected()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.play();
                }
            }
        });
    }

    /**
     * Кнопка включения/выключения музыки в игровом меню
     */
    public static void MusicButtonTwo() {
        MUSIC_BUTTON_2.setOnAction(event -> {
            if (mediaPlayer != null) {
                if (MUSIC_BUTTON_2.isSelected()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.play();
                }
            }
        });
    }

    /**
     * Функция настройки работы графиков
     */
    public static void GraphicsSettings() {
        x = new NumberAxis();
        x.setLabel("Ход");
        y = new NumberAxis();
        y.setLabel("Ресурсы");
        lineGraphic = new LineChart(x, y, graphic);
        lineGraphic.setTitle("Ресурсы за ход");
        graphic.add(new XYChart.Series("Крестьяне", graphicPeasants));
        graphic.add(new XYChart.Series("Рис", graphicRise));
        graphic.add(new XYChart.Series("Вода", graphicWater));
        graphic.add(new XYChart.Series("Дома", graphicHome));
    }

    /**
     * Кнопка показа графики
     */
    public static void ShowGraphButton() {
        LOGGER.info("Show graphic button was clicked");
        showingGraph.setDisable(true);
        graph.show();
    }

    /**
     * Главная функция, в которой реализованы все остальные функции и триггеры кнопок
     *
     * @param stage определяет главное окно, которое будет создано при запуске приложения
     * @throws IOException ошибка при невыбранном файле сохранения, при загрузки с файла сохранения запрещенных символов, ошибка чтения файла
     */
    @Override
    public void start(Stage stage) throws IOException {
        GraphicsSettings();

        MusicButtonOne();

        MusicButtonTwo();

        WindowsGeneration();

        FieldsAndButtons();

        GenerationFieldButtons();

        Alerts();

        Music();

        MusicRetry();

        SettingsOfNewGame();

        FieldWindows();

        BuyFieldButtons();

        MainMenuWindow();

        GameInterface();

        SaveWindowVoid();

        load.setOnAction(event -> {
            LoadButton();
        });

        newGame.setOnAction(event -> {
            NewGameButton();
        });

        next.setOnAction(event -> {
            NextMoveButton();
        });

        change.setOnAction(event -> {
            ChangeMapButton();
        });

        BUTTONS[0].setOnAction(event -> {
            AcceptSaveButton();
        });

        BUTTONS[1].setOnAction(event -> {
            SaveButton();
        });

        BUTTONS[2].setOnAction(event -> {
            ShowMapButton();
        });

        BUTTONS[3].setOnAction(event -> {
            BuyButtonsMenuButton();
        });

        BUTTONS[4].setOnAction(event -> {
            BuyHomeButton();
        });

        BUTTONS[5].setOnAction(event -> {
            GetWaterButton();
        });

        BUTTONS[6].setOnAction(event -> {
            WateredRiseButton();
        });

        BUTTONS[7].setOnAction(event -> {
            CollectingRiseButton();
        });

        exitMenu.setOnAction(event -> {
            ExitMenuButton();
        });

        directoryChooseButton.setOnAction(event -> {
            DirectoryChoose();
        });

        exit.setOnAction(event -> {
            ExitButton();
        });

        showingGraph.setOnAction(event -> {
            ShowGraphButton();
        });

        graph.setOnCloseRequest(event -> {
            showingGraph.setDisable(false);
        });

        fieldStage.setOnCloseRequest(event -> {
            BUTTONS[2].setDisable(false);
            BUTTONS[3].setDisable(false);
        });

        fieldBuy.setOnCloseRequest(event -> {
            BUTTONS[2].setDisable(false);
            BUTTONS[3].setDisable(false);
            change.setDisable(false);
        });

        stagee.setOnCloseRequest(event -> {
            ExitButton();
        });

        saveWindow.setOnCloseRequest(event -> {
            stagee.show();
            BUTTONS[2].setDisable(false);
            BUTTONS[3].setDisable(false);
            change.setDisable(false);
            showingGraph.setDisable(false);
        });
    }
}