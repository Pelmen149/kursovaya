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
public class HelloApplication extends Application {
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
    private static final Button[] buttons = new Button[10];
    /**
     * Кнопка Вкл/Выкл музыки на главном меню
     */
    private static final ToggleButton musicButton_1 = new ToggleButton("Музыка Вкл/Выкл");
    /**
     * Кнопка Вкл/Выкл музыки на игровом меню
     */
    private static final ToggleButton musicButton_2 = new ToggleButton("Музыка Вкл/Выкл");
    /**
     * Поле ввода названия сохранения
     */
    private static final TextField save_field = new TextField();
    /**
     * Массив объектов соответствующий параметрам поля с таким же номером
     */
    private static final Field_cost[][] field_cost = new Field_cost[10][10];
    /**
     * Массив полей
     */
    private static final TextArea[][] field_point = new TextArea[10][10];
    /**
     * Массив надписей для однотипных задач
     */
    private static final Label[] lables = new Label[10];
    /**
     * Окна сохранения
     */
    private static Stage save_window;
    /**
     * Окно поля
     */
    private static Stage field_stage;
    /**
     * Окно покупки полей
     */
    private static Stage field_buy;
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
    static final Button[][] field_button = new Button[10][10];
    /**
     * Объект для генерации случайных чисел
     */
    static final Random random = new Random();
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
    static Alert alert_buy;
    /**
     * Ошибка загрузки игры
     */
    static Alert alert_load;
    /**
     * Ошибка отсутствия файла сохранения
     */
    static Alert alert_no_file;
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
    static Alert alert_load_music;
    /**
     * Ошибка сохранения игры
     */
    static Alert alert_save;
    /**
     * Интерфейс для окна карты
     */
    private static TilePane field_1;
    /**
     * Интерфейс для окна покупки поля
     */
    private static TilePane field_2;
    /**
     * Интерфейс для главного меню
     */
    private static VBox main_menu;
    /**
     * Интерфейс для окна сохранения
     */
    private static VBox save;
    /**
     * Интерфейс для окна с графиком
     */
    private static VBox graphic_window;
    /**
     * Интерфейс для игрового меню
     */
    private static GridPane game_menu;
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
    private static Button newgame;
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
    private static Button exit_menu;
    /**
     * Кнопка показа графика
     */
    private static Button showing_graph;
    /**
     * Кнопка выбора директории сохранения
     */
    private static Button directory_choose_button;
    /**
     * Сцена для окна игры
     */
    private static Scene scene_game;
    /**
     * Сцена для окна графика
     */
    private static Scene graphic_scene;
    /**
     * Логгер предназначенный для логгирования игры
     */
    private static final Logger logger = LogManager.getLogger("Strategy Game");
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
    static ObservableList graphic_peasants = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества риса для графика
     */
    static ObservableList graphic_rise = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества воды для графика
     */
    static ObservableList graphic_water = FXCollections.observableArrayList();
    /**
     * Лист сбора всех значений количества домов для графика
     */
    static ObservableList graphic_home = FXCollections.observableArrayList();
    /**
     * Объект демонстрирующий график
     */
    static LineChart line_graphic;
    /**
     * Строка для хранения значений графиков за всю игру
     */
    static StringBuilder save_graphs = new StringBuilder();
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
        main_menu.getChildren().addAll(newgame, load, exit_menu, musicButton_1);
        Scene scene = new Scene(main_menu, 800, 600);
        stagee.setTitle("Strategy Game");
        stagee.setScene(scene);
        stagee.show();
    }

    /**
     * Генерация окна и сцены полей для двух режимов карт
     */
    public static void FieldWindows() {
        field_stage = new Stage();
        Scene field1 = new Scene(field_1, 570, 580);
        field_stage.setResizable(false);
        field_stage.setTitle("Поле игры");
        field_stage.setScene(field1);
        field_stage.setX(stagee.getX() + 200);
        field_stage.setY(stagee.getY() + 100);

        field_buy = new Stage();
        Scene field2 = new Scene(field_2, 550, 550);
        field_buy.setResizable(false);
        field_buy.setTitle("Поле игры");
        field_buy.setScene(field2);
        field_buy.setX(stagee.getX() + 200);
        field_buy.setY(stagee.getY() + 100);
    }

    /**
     * Добавление элементов интерфейса в игровое меню
     */
    public static void GameInterface() {
        game_menu.add(next, 1, 0);
        game_menu.add(buttons[1], 2, 0);
        game_menu.add(buttons[2], 3, 0);
        game_menu.add(change, 4, 0);
        game_menu.add(buttons[3], 1, 1);
        game_menu.add(buttons[4], 3, 1);
        game_menu.add(lables[0], 4, 1);
        game_menu.add(move, 1, 2);
        game_menu.add(buttons[5], 3, 2);
        game_menu.add(lables[1], 4, 2);
        game_menu.add(buttons[6], 3, 3);
        game_menu.add(lables[2], 4, 3);
        game_menu.add(buttons[7], 3, 4);
        game_menu.add(lables[3], 4, 4);
        game_menu.add(exit, 1, 6);
        game_menu.add(musicButton_2, 1, 5);
        game_menu.add(showing_graph, 2, 1);
    }

    /**
     * Генерация окна сохранения игры
     */
    public static void SaveWindowVoid() {
        Scene save_scene = new Scene(save, 250, 180);
        save_window = new Stage();
        save_window.setResizable(false);
        save_window.setTitle("Сохранение игры");
        save_window.setScene(save_scene);
        save_window.setX(stagee.getX() - 200);
        save_window.setY(stagee.getY() - 100);
        save.getChildren().addAll(directory_choose_button, lables[4], save_field, buttons[0]);
        save.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Создание и настройка служебных кнопок при запуске программы
     */
    public static void SettingsOfNewGame() {
        field_point[9][9].setText(" Вы");
        field_cost[9][9].fraction = 1;
        field_point[9][9].setStyle("-fx-background-color: green;");
        field_point[0][0].setText("Враг");
        field_cost[0][0].fraction = 2;
        field_point[0][0].setStyle("-fx-background-color: red;");
        buttons[0].setPrefSize(60, 35);
        move = new Label();
        buttons[3].setText("Купить поле");
        buttons[4].setText("Построить дом ");
        buttons[5].setText("Добыть воды ");
        buttons[6].setText("Полить рис ");
        buttons[7].setText("Собрать рис ");
        next = new Button("Следующий ход");
        next.setPrefWidth(200);
        exit = new Button("Выйти из игры");
        exit.setPrefWidth(200);
        exit_menu = new Button("Выйти из игры");
        exit_menu.setPrefSize(200, 80);
        showing_graph = new Button("Показать график");
        showing_graph.setPrefWidth(200);
        musicButton_2.setPrefWidth(200);
        newgame = new Button("Новая игра");
        load = new Button("Загрузить сохранение");
        change = new Button("Изменить режим карты");
        newgame.setPrefSize(200, 80);
        load.setPrefSize(200, 80);
        directory_choose_button = new Button("Выбрать директорию");
    }

    /**
     * Создание и настройка окон и сцен в игре
     */
    public static void WindowsGeneration() {
        stagee = new Stage();
        field_1 = new TilePane();
        field_2 = new TilePane();
        field_1.setOrientation(Orientation.HORIZONTAL);
        field_2.setOrientation(Orientation.HORIZONTAL);
        main_menu = new VBox();
        main_menu.setPadding(new Insets(10, 10, 10, 10));
        main_menu.setSpacing(10);
        main_menu.setAlignment(Pos.CENTER);
        game_menu = new GridPane();
        game_menu.setPadding(new Insets(10, 10, 10, 10));
        game_menu.setAlignment(Pos.TOP_CENTER);
        save = new VBox();
        save.setSpacing(10);
        save.setAlignment(Pos.TOP_CENTER);
        graphic_window = new VBox(line_graphic);
        graphic_scene = new Scene(graphic_window, 600, 400);
        graph = new Stage();
        graph.setScene(graphic_scene);
        graph.setTitle("График ресурсов");
    }

    /**
     * Генерация двойного массива кнопок и текстовых полей для меню покупки и отображения игровых провинций
     */
    public static void FieldsAndButtons() {
        for (int i = 0; i < 10; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefWidth(200);
        }
        for (int i = 0; i < 10; i++)
            lables[i] = new Label();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field_cost[i][j] = new Field_cost();
                field_point[i][j] = new TextArea(null);
                field_point[i][j].setPrefColumnCount(2);
                field_point[i][j].setPrefRowCount(2);
                field_point[i][j].setEditable(false);
                field_1.getChildren().add(field_point[i][j]);
            }
        }
    }

    /**
     * Создание окон с ошибками, которые могут произойти при использовании программы
     */
    public static void Alerts() {
        alert_buy = new Alert(Alert.AlertType.ERROR, "Недостаточно ресурсов", ButtonType.OK);
        alert_load = new Alert(Alert.AlertType.ERROR, "Файл содержит запрещенные ресурсы", ButtonType.OK);
        alert_save = new Alert(Alert.AlertType.ERROR, "Название содержит запрещенные символы, потому может быть не сохранён", ButtonType.OK);
        alert_no_file = new Alert(Alert.AlertType.ERROR, "Файл не выбран", ButtonType.OK);
        alert_load_music = new Alert(Alert.AlertType.ERROR, "Файл музыки отсутствует. Поместите файл 'theme1.mp3' в папку с jar файлом", ButtonType.OK);
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
        File dir = directoryChooser.showDialog(save_window);
        if (dir != null) {
            path = dir.getAbsolutePath();
            save_field.setDisable(false);
            buttons[0].setDisable(false);
        } else {
            alert_no_file.showAndWait();
        }
    }

    /**
     * Кнопка, отвечающая за загрузку игры
     */
    public static void LoadButton() {
        logger.info("Load button was clicked");
        FileChoose();
        if (file == null) {
            alert_no_file.showAndWait();
            logger.error("File of save was not choosen");
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
                                field_cost[i][j].cost[k] = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                            }
                            field_cost[i][j].fraction = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        }
                    buttons[4].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    buttons[5].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    buttons[6].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    buttons[7].setDisable(Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine()))));
                    game.rise.watered = Boolean.parseBoolean(new String(Base64.getDecoder().decode(reader.readLine())));
                    for (int i = 0; i < game.move; i++) {
                        int x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphic_peasants.add(new XYChart.Data(i, x));
                        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphic_rise.add(new XYChart.Data(i, x));
                        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphic_water.add(new XYChart.Data(i, x));
                        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                        x = Integer.parseInt(new String(Base64.getDecoder().decode(reader.readLine())));
                        graphic_home.add(new XYChart.Data(i, x));
                        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(x).getBytes()));
                    }
                    map = true;

                    scene_game = new Scene(game_menu, 800, 600);

                    move.setText("Ход: " + game.move);
                    buttons[2].setDisable(true);
                    buttons[2].setText("Показать карту");
                    buttons[1].setText("Сохранить игру");
                    next.setText("Следующий ход ");
                    lables[0].setText("Количество крестьян: " + game.peasants.count);
                    lables[1].setText("Количество риса: " + game.rise.count);
                    lables[2].setText("Количество воды: " + game.water.count);
                    lables[3].setText("Количество домов: " + game.home.count);
                    stagee.setScene(scene_game);
                    stagee.show();
                    buttons[2].setDisable(true);
                    buttons[3].setDisable(true);
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++) {
                            if (field_cost[i][j].fraction == 0) {
                                field_point[i][j].setText(null);
                            } else if (field_cost[i][j].fraction == 1) {
                                field_point[i][j].setText(" Вы");
                                field_point[i][j].setStyle("-fx-background-color: green;");
                            } else if (field_cost[i][j].fraction == 2) {
                                field_point[i][j].setText("Враг");
                                field_point[i][j].setStyle("-fx-background-color: red;");
                            }
                        }

                    field_stage.show();
                } catch (Exception e) {
                    alert_load.showAndWait();
                    logger.error("File of save has forbidden characters");
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
        logger.info("New game button was clicked");

        scene_game = new Scene(game_menu, 800, 600);

        field_stage.show();

        map = true;
        game = new Game();
        move.setText("Ход: " + game.move);
        buttons[2].setDisable(true);
        buttons[3].setDisable(true);
        buttons[2].setText("Показать карту");
        buttons[1].setText("Сохранить игру");
        next.setText("Следующий ход ");
        lables[0].setText("Количество крестьян: " + game.peasants.count);
        lables[1].setText("Количество риса: " + game.rise.count);
        lables[2].setText("Количество воды: " + game.water.count);
        lables[3].setText("Количество домов: " + game.home.count);
        stagee.setScene(scene_game);
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
                field_button[i][j].setOnAction(event -> {
                    logger.info("Buy field button was clicked");
                    if (game.peasants.count >= field_cost[finalI][finalJ].cost[0] & game.rise.count >= field_cost[finalI][finalJ].cost[1] & game.water.count >= field_cost[finalI][finalJ].cost[2]) {
                        game.peasants.count = game.peasants.count - field_cost[finalI][finalJ].cost[0];
                        game.rise.count = game.rise.count - field_cost[finalI][finalJ].cost[1];
                        game.water.count = game.water.count - field_cost[finalI][finalJ].cost[2];
                        field_cost[finalI][finalJ].fraction = 1;
                        field_point[finalI][finalJ].setStyle("-fx-background-color: green;");
                        field_buy.close();
                        buttons[2].setDisable(false);
                        buttons[3].setDisable(false);
                        change.setDisable(false);
                    } else {
                        alert_buy.showAndWait();
                        logger.error("There are not enough resources to purchase a field");
                    }
                    lables[0].setText("Количество крестьян: " + game.peasants.count);
                    lables[1].setText("Количество риса: " + game.rise.count);
                    lables[2].setText("Количество воды: " + game.water.count);
                });
            }
        }
    }

    /**
     * Функция отвечающая за работу простейшего искусственного интеллекта
     */
    public static void AI() {
        if (chance > random.nextInt(100)) {
            int k = 0;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (i != 9 && field_cost[i][j].fraction == 0 && field_cost[1 + i][j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (j != 9 && field_cost[i][j].fraction == 0 && field_cost[i][1 + j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (i != 0 && field_cost[i][j].fraction == 0 && field_cost[i - 1][j].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    } else if (j != 0 && field_cost[i][j].fraction == 0 && field_cost[i][j - 1].fraction == 2) {
                        boti[k] = i;
                        botj[k] = j;
                        k++;
                    }
                }
            int choose = new Random().nextInt(k);
            field_cost[boti[choose]][botj[choose]].fraction = 2;
            field_point[boti[choose]][botj[choose]].setStyle("-fx-background-color: red;");
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
                if (field_cost[i][j].fraction == 2) {
                    count_enemy += 1;
                } else if (field_cost[i][j].fraction == 1) {
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
            buttons[i].setDisable(false);
        buttons[4].setText("Построить дом ");
        buttons[5].setText("Добыть воды ");
        buttons[6].setText("Полить рис ");
        buttons[7].setText("Собрать рис ");
    }

    /**
     * Кнопка следующего хода
     */
    public static void NextMoveButton() {
        logger.info("Next move button was clicked");
        graphic_peasants.add(new XYChart.Data(game.move, game.peasants.count));
        graphic_rise.add(new XYChart.Data(game.move, game.rise.count));
        graphic_water.add(new XYChart.Data(game.move, game.water.count));
        graphic_home.add(new XYChart.Data(game.move, game.home.count));
        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.peasants.count).getBytes()));
        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.rise.count).getBytes()));
        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.water.count).getBytes()));
        save_graphs.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.home.count).getBytes()));


        game.move++;
        field_stage.close();
        field_buy.close();
        buttons[2].setDisable(false);
        buttons[3].setDisable(false);
        change.setDisable(false);

        ResetButtons();

        AI();

        WinOrLose();

        game.peasants.count += game.home.count;
        move.setText("Ход: " + game.move);
        lables[0].setText("Количество крестьян: " + game.peasants.count);
        lables[1].setText("Количество риса: " + game.rise.count);
        lables[2].setText("Количество воды: " + game.water.count);
        lables[3].setText("Количество домов: " + game.home.count);
    }

    /**
     * Кнопка смены режима карты
     */
    public static void ChangeMapButton() {
        logger.info("Change map mode button was clicked");
        if (map) {
            map = !map;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (field_cost[i][j].fraction == 0) {
                        field_point[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    } else if (field_cost[i][j].fraction == 2) {
                        field_point[i][j].setText(" ");
                        field_point[i][j].setStyle("-fx-background-color: red;");
                    } else {
                        field_point[i][j].setText(" ");
                        field_point[i][j].setStyle("-fx-background-color: green;");
                    }
                }
        } else {
            map = !map;
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (field_cost[i][j].fraction == 0) {
                        field_point[i][j].setText(null);
                    } else if (field_cost[i][j].fraction == 1) {
                        field_point[i][j].setText(" Вы");
                        field_point[i][j].setStyle("-fx-background-color: green;");
                    } else if (field_cost[i][j].fraction == 2) {
                        field_point[i][j].setText("Враг");
                        field_point[i][j].setStyle("-fx-background-color: red;");
                    }
                }
        }
        field_stage.show();
        buttons[2].setDisable(true);
        buttons[3].setDisable(true);
    }

    /**
     * Кнопка подтверждения сохранения игры
     */
    public static void AcceptSaveButton() {
        logger.info("Confirmation of saving button was clicked");

        WrongSymbols();

        try (FileWriter writer = new FileWriter(path + "\\" + save_field.getText() + ".save", false)) {
            StringBuilder text = new StringBuilder();
            text.append(Base64.getEncoder().encodeToString(Integer.toString(game.move).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.home.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.rise.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.peasants.count).getBytes()));
            text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(game.water.count).getBytes()));
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[0]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[1]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[2]).getBytes()));
                    text.append("\n" + Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].fraction).getBytes()));

                }
            for (int i = 4; i < 8; i++) {
                text.append("\n" + Base64.getEncoder().encodeToString(Boolean.toString(buttons[i].isDisable()).getBytes()));
            }
            text.append("\n" + Base64.getEncoder().encodeToString(Boolean.toString(game.rise.watered).getBytes()));
            text.append(save_graphs);
            writer.write(text.toString());
            writer.flush();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        stagee.show();
        buttons[2].setDisable(false);
        buttons[3].setDisable(false);
        change.setDisable(false);
        showing_graph.setDisable(false);
        save_window.close();
    }

    /**
     * Кнопка для открытия окна сохранения игры
     */
    public static void SaveButton() {
        logger.info("Save button was clicked");
        lables[4].setText("Введите название сохранения");
        buttons[0].setText("Готово");
        save_field.setDisable(true);
        buttons[0].setDisable(true);
        save_window.show();
        stagee.close();
        field_buy.close();
        field_stage.close();
        graph.close();
    }

    /**
     * Кнопка показа карты игры
     */
    public static void ShowMapButton() {
        logger.info("Show map button was clicked");
        buttons[2].setDisable(true);
        buttons[3].setDisable(true);
        if (map == false) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (field_cost[i][j].fraction == 0)
                        field_point[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    else if (field_cost[i][j].fraction == 2) {
                        field_point[i][j].setText(" ");
                        field_point[i][j].setStyle("-fx-background-color: red;");
                    } else {
                        field_point[i][j].setText(" ");
                        field_point[i][j].setStyle("-fx-background-color: green;");
                    }
                }
        } else {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (field_cost[i][j].fraction == 0) {
                        field_point[i][j].setText(null);
                    } else if (field_cost[i][j].fraction == 1) {
                        field_point[i][j].setText(" Вы");
                        field_point[i][j].setStyle("-fx-background-color: green;");
                    } else if (field_cost[i][j].fraction == 2) {
                        field_point[i][j].setText("Враг");
                        field_point[i][j].setStyle("-fx-background-color: red;");
                    }
                }
        }
        field_stage.show();
    }

    /**
     * Кнопка перехода в меню покупки полей
     */
    public static void BuyButtonsMenuButton() {
        logger.info("Menu of buy buttons button was clicked");
        field_stage.close();
        buttons[2].setDisable(true);
        buttons[3].setDisable(true);
        change.setDisable(true);
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (field_cost[i][j].fraction == 1) {
                    field_button[i][j].setText(" Вы");
                    field_button[i][j].setDisable(true);
                } else if (field_cost[i][j].fraction == 2) {
                    field_button[i][j].setText("Враг");
                    field_button[i][j].setDisable(true);
                } else if (i != 9 && field_cost[i][j].fraction == 0 && field_cost[1 + i][j].fraction == 1) {
                    field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    field_button[i][j].setDisable(false);
                } else if (j != 9 && field_cost[i][j].fraction == 0 && field_cost[i][1 + j].fraction == 1) {
                    field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    field_button[i][j].setDisable(false);
                } else if (i != 0 && field_cost[i][j].fraction == 0 && field_cost[i - 1][j].fraction == 1) {
                    field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    field_button[i][j].setDisable(false);
                } else if (j != 0 && field_cost[i][j].fraction == 0 && field_cost[i][j - 1].fraction == 1) {
                    field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " " + String.valueOf(field_cost[i][j].cost[1]) + " " + String.valueOf(field_cost[i][j].cost[2]));
                    field_button[i][j].setDisable(false);
                } else field_button[i][j].setDisable(true);

            }
        field_buy.show();
    }

    /**
     * Функция генерации кнопок для покупки полей
     */
    public static void GenerationFieldButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field_button[i][j] = new Button();
                field_button[i][j].setPrefSize(55, 55);
                field_button[i][j].setDisable(true);
                field_2.getChildren().add(field_button[i][j]);
            }
        } //Создание покупки полей
    }

    /**
     * Кнопка постройки дома
     */
    public static void BuyHomeButton() {
        logger.info("Build home button was clicked");
        if (game.peasants.count > 0 & game.rise.count > 0 & game.water.count > 0) {
            game.home.count += 1;
            game.peasants.count -= 1;
            game.rise.count -= 1;
            game.water.count -= 1;
            for (int i = 4; i < 8; i++)
                buttons[i].setDisable(true);
        } else {
            buttons[4].setText("Недостаточно ресурсов");
        }
        lables[0].setText("Количество крестьян: " + game.peasants.count);
        lables[1].setText("Количество риса: " + game.rise.count);
        lables[2].setText("Количество воды: " + game.water.count);
        lables[3].setText("Количество домов: " + game.home.count);
    }

    /**
     * Кнопка сбора воды
     */
    public static void GetWaterButton() {
        logger.info("Get water button was clicked");
        if (game.peasants.count > 0) {
            game.water.count += 1;
            game.peasants.count -= 1;
            for (int i = 4; i < 8; i++)
                buttons[i].setDisable(true);
        } else {
            buttons[5].setText("Недостаточно крестьян для сбора воды");
        }
        lables[0].setText("Количество крестьян: " + game.peasants.count);
        lables[2].setText("Количество воды: " + game.water.count);
    }

    /**
     * Кнопка полития риса
     */
    public static void WateredRiseButton() {
        logger.info("Watered rise button was clicked");
        if (game.water.count > 0) {
            game.water.count -= 1;
            game.rise.ChangeWatered();
            buttons[6].setText("Рис полит");
            for (int i = 4; i < 8; i++)
                buttons[i].setDisable(true);
        } else {
            buttons[6].setText("Недостаточно воды для полива");
        }
        lables[2].setText("Количество воды: " + game.water.count);
    }

    /**
     * Кнопка сбора риса
     */
    public static void CollectingRiseButton() {
        logger.info("Collecting rise button was clicked");
        if (game.peasants.count > 0) {
            game.peasants.count -= 1;
            if (game.rise.watered) {
                game.rise.count += 3;
                game.rise.ChangeWatered();
            } else {
                game.rise.count += 1;
            }
            for (int i = 4; i < 8; i++)
                buttons[i].setDisable(true);
        } else {
            buttons[5].setText("Недостаточно крестьян для сбора риса");
        }
        lables[0].setText("Количество крестьян: " + game.peasants.count);
        lables[1].setText("Количество риса: " + game.rise.count);
    }

    /**
     * Кнопка для выхода из игры в игровом меню
     */
    public static void ExitButton() {
        logger.info("Exit button was clicked");
        save_window.close();
        field_stage.close();
        field_buy.close();
        stagee.close();
        graph.close();
        logger.fatal("Program was closed\n");
    }

    /**
     * Кнопка для выхода из игры в главном меню
     */
    public static void ExitMenuButton() {
        logger.info("Exit button was clicked");
        save_window.close();
        field_stage.close();
        field_buy.close();
        stagee.close();
        graph.close();
        logger.fatal("Program was closed\n");
    }

    /**
     * Функция для проверки запрещенных символов в названии файла сохранения
     */
    public static void WrongSymbols() {
        String[] wrond = new String[]{"|", "con", "/", "\\", ">", "<", ":", "*", "?", "'",};
        for (int i = 0; i < wrond.length; i++) {
            if (save_field.getText().contains(wrond[i])) {
                alert_save.showAndWait();
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
            alert_load_music.showAndWait();
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
        musicButton_1.setOnAction(event -> {
            if (mediaPlayer != null) {
                if (musicButton_1.isSelected()) {
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
        musicButton_2.setOnAction(event -> {
            if (mediaPlayer != null) {
                if (musicButton_2.isSelected()) {
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
        line_graphic = new LineChart(x, y, graphic);
        line_graphic.setTitle("Ресурсы за ход");
        graphic.add(new XYChart.Series("Крестьяне", graphic_peasants));
        graphic.add(new XYChart.Series("Рис", graphic_rise));
        graphic.add(new XYChart.Series("Вода", graphic_water));
        graphic.add(new XYChart.Series("Дома", graphic_home));
    }

    /**
     * Кнопка показа графики
     */
    public static void ShowGraphButton() {
        logger.info("Show graphic button was clicked");
        showing_graph.setDisable(true);
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

        newgame.setOnAction(event -> {
            NewGameButton();
        });

        next.setOnAction(event -> {
            NextMoveButton();
        });

        change.setOnAction(event -> {
            ChangeMapButton();
        });

        buttons[0].setOnAction(event -> {
            AcceptSaveButton();
        });

        buttons[1].setOnAction(event -> {
            SaveButton();
        });

        buttons[2].setOnAction(event -> {
            ShowMapButton();
        });

        buttons[3].setOnAction(event -> {
            BuyButtonsMenuButton();
        });

        buttons[4].setOnAction(event -> {
            BuyHomeButton();
        });

        buttons[5].setOnAction(event -> {
            GetWaterButton();
        });

        buttons[6].setOnAction(event -> {
            WateredRiseButton();
        });

        buttons[7].setOnAction(event -> {
            CollectingRiseButton();
        });

        exit_menu.setOnAction(event -> {
            ExitMenuButton();
        });

        directory_choose_button.setOnAction(event -> {
            DirectoryChoose();
        });

        exit.setOnAction(event -> {
            ExitButton();
        });

        showing_graph.setOnAction(event -> {
            ShowGraphButton();
        });

        graph.setOnCloseRequest(event -> {
            showing_graph.setDisable(false);
        });

        field_stage.setOnCloseRequest(event -> {
            buttons[2].setDisable(false);
            buttons[3].setDisable(false);
        });

        field_buy.setOnCloseRequest(event -> {
            buttons[2].setDisable(false);
            buttons[3].setDisable(false);
            change.setDisable(false);
        });

        stagee.setOnCloseRequest(event -> {
            ExitButton();
        });

        save_window.setOnCloseRequest(event -> {
            stagee.show();
            buttons[2].setDisable(false);
            buttons[3].setDisable(false);
            change.setDisable(false);
            showing_graph.setDisable(false);
        });
    }
}