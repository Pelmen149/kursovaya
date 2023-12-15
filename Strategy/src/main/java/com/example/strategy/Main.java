package com.example.strategy;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.*;
import java.util.Random;
import java.util.Base64;


public class Main extends Application {
    boolean map; // Режим карты
    private Game game; // Новая игра
    private static Label move; // Ход
    private static final Button [] buttons = new Button[10]; // Кнопки
    private static final TextField save_field=new TextField(); //Имя файла сохранения
    private static final Field_cost[][] field_cost = new Field_cost[10][10];//Параметры поля
    private static final TextArea[][] field_point = new TextArea[10][10]; //Поле
    private static final Label[] lables = new Label[10];//Подписи
    private static ToggleButton musicButton = new ToggleButton("Музыка Вкл/Выкл");//Кнопка вкл/выкл музыки
    private static Stage save_window,field_stage, field_buy, stagee; //Окно полей
    final Button[][] field_button = new Button[10][10];//Кнопки покупки
    final Random random = new Random();//Функция для случайных чисел
    double chance=0.14;//Базовый шанс на захват врагом поля
    int[] boti = new int[100];//Выбор поля для врага
    int[] botj = new int[100];//Выбор поля для врага
    static Alert alert_buy, alert_load, alert_no_file, lose, win; // Окна ошибки
    private static TilePane field_1, field_2; //Карта
    private static VBox main_menu, save;
    private static GridPane game_menu;
    private static String musicFile;
    private static Media sound;
    private static MediaPlayer mediaPlayer;
    private static Button next, load, newgame, change;
    private static Scene field1, field2, save_scene, scene;



    public static void main_menu_window(){
        stagee.setResizable(false);
        main_menu.getChildren().addAll(newgame, load, musicButton);
        scene = new Scene(main_menu, 800, 600);
        stagee.setTitle("Strategy Game");
        stagee.setScene(scene);
        stagee.show();
    } // Окно главного меню

    public static void field_windows(){
        field_stage = new Stage();
        field1 = new Scene(field_1, 570, 580);
        field_stage.setResizable(false);
        field_stage.setTitle("Поле игры");
        field_stage.setScene(field1);
        field_stage.setX(stagee.getX() + 200);
        field_stage.setY(stagee.getY() + 100); // Поле игры

        field_buy = new Stage();
        field2 = new Scene(field_2, 550, 550);
        field_buy.setResizable(false);
        field_buy.setTitle("Поле игры");
        field_buy.setScene(field2);
        field_buy.setX(stagee.getX() + 200);
        field_buy.setY(stagee.getY() + 100); // Окно покупок
    } // Генерация полей

    public static void game_interface(){
        game_menu.add(next, 1, 0);      game_menu.add(buttons[1], 2, 0); game_menu.add(buttons[2], 3, 0); game_menu.add(change, 4, 0);
        game_menu.add(buttons[3], 1, 1);                                 game_menu.add(buttons[4], 3, 1); game_menu.add(lables[0], 4, 1);
        game_menu.add(move, 1, 2);                                       game_menu.add(buttons[5], 3, 2); game_menu.add(lables[1], 4, 2);
        game_menu.add(buttons[6], 3, 3); game_menu.add(lables[2], 4, 3);
        game_menu.add(buttons[7], 3, 4); game_menu.add(lables[3], 4, 4);
    }// Расположение элементов игрового меню

    public static void save_window_void(){
        save_scene = new Scene(save, 250, 120);
        save_window = new Stage();
        save_window.setResizable(false);
        save_window.setTitle("Сохранение игры");
        save_window.setScene(save_scene);
        save_window.setX(stagee.getX() - 200);
        save_window.setY(stagee.getY() - 100); // Окно сохранения
        save.getChildren().addAll(lables[4],save_field, buttons[0]);
    }// Сохранение игры

    public static void settings_of_new_game(){
        field_point[9][9].setText(" Вы"); field_cost[9][9].fraction=1; field_point[9][9].setStyle("-fx-background-color: green;"); // Начальное поле игрока
        field_point[0][0].setText("Враг");field_cost[0][0].fraction=2; field_point[0][0].setStyle("-fx-background-color: red;"); // Начальное поле врага
        buttons[0].setPrefSize(60, 35);
        move = new Label();
        buttons[3].setText("Купить поле");
        buttons[4].setText("Построить дом ");
        buttons[5].setText("Добыть воды ");
        buttons[6].setText("Полить рис ");
        buttons[7].setText("Собрать рис ");
        next = new Button("Следующий ход");
        next.setPrefWidth(200);
        newgame = new Button("Новая игра");
        load = new Button("Загрузить сохранение");
        change = new Button("Изменить режим карты");
        newgame.setPrefSize(200, 80);
        load.setPrefSize(200, 80);
    } //Настройка служебных кнопок

    public static void return_music(){
        musicFile = "theme1.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }// Повторение музыки

    public static void windows_generation() {
        stagee = new Stage();
        field_1 = new TilePane();
        field_2 = new TilePane(); // Поля
        field_1.setOrientation(Orientation.HORIZONTAL);
        field_2.setOrientation(Orientation.HORIZONTAL); // Поля
        main_menu = new VBox();
        main_menu.setPadding(new Insets(10, 10, 10, 10));
        main_menu.setSpacing(10);
        main_menu.setAlignment(Pos.CENTER); // Главное меню
        game_menu = new GridPane();
        game_menu.setPadding(new Insets(10, 10, 10, 10));
        game_menu.setAlignment(Pos.TOP_CENTER); // Игровое меню
        save = new VBox();
        save.setSpacing(10);
        save.setAlignment(Pos.TOP_CENTER); // Меню сохранения
    } // Создание окон и их настройка

    public static void fields_and_buttons() {
        for (int i = 0; i < 10; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefWidth(200);
        }// Создание кнопок
        for (int i = 0; i < 10; i++)
            lables[i] = new Label(); // Создание надписей
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field_cost[i][j] = new Field_cost();
                field_point[i][j] = new TextArea(null);
                field_point[i][j].setPrefColumnCount(2);
                field_point[i][j].setPrefRowCount(2);
                field_point[i][j].setEditable(false);
                field_1.getChildren().add(field_point[i][j]);
            }
        } // Создание полей
    } // Создание полей и кнопок

    public static void alerts(){
        alert_buy = new Alert(Alert.AlertType.ERROR, "Недостаточно ресурсов", ButtonType.OK);
        alert_load = new Alert(Alert.AlertType.ERROR, "Файл содержит запрещенные ресурсы", ButtonType.OK);
        alert_no_file = new Alert(Alert.AlertType.ERROR, "Файл не выбран", ButtonType.OK);
        lose = new Alert(Alert.AlertType.NONE, "Вы проиграли", ButtonType.OK);
        win = new Alert(Alert.AlertType.NONE, "Вы выиграли", ButtonType.OK);}// Ошибки и финальное окно


    @Override
    public void start(Stage stage) throws IOException {
        return_music(); // Повторение музыки

        windows_generation(); // Создание окон и их настройка

        fields_and_buttons(); // Создание полей и кнопок

        for (int i = 0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                field_button[i][j] = new Button();
                field_button[i][j].setPrefSize(55, 55);
                field_button[i][j].setDisable(true);
                field_2.getChildren().add(field_button[i][j]);
            }
        } //Создание покупки полей

        alerts();// Ошибки и финальное окно

        settings_of_new_game(); // Настройка парметров игры

        field_windows(); // Генерация полей

        for (int i = 0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                int finalJ = j;
                int finalI = i;
                field_button[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (game.peasants.count >= field_cost[finalI][finalJ].cost[0] &
                                game.rise.count >= field_cost[finalI][finalJ].cost[1] &
                                game.water.count >= field_cost[finalI][finalJ].cost[2]){
                            game.peasants.count = game.peasants.count-field_cost[finalI][finalJ].cost[0];
                            game.rise.count = game.rise.count-field_cost[finalI][finalJ].cost[1];
                            game.water.count = game.water.count-field_cost[finalI][finalJ].cost[2];
                            field_cost[finalI][finalJ].fraction = 1;
                            field_point[finalI][finalJ].setStyle("-fx-background-color: green;");
                            field_buy.close();
                        }
                        else{
                            alert_buy.showAndWait();
                        }
                        lables[0].setText("Количество крестьян: " + game.peasants.count);
                        lables[1].setText("Количество риса: " + game.rise.count);
                        lables[2].setText("Количество воды: " + game.water.count);
                    }
                });
            }
        } // Покупка поля

        musicButton.setOnAction(event -> {
            if (musicButton.isSelected()) {
                mediaPlayer.pause();
            }else {
                mediaPlayer.play();
            }
        }); //Включение отключение музыки

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        }); // Музыка

        main_menu_window(); // Окно главного меню

        game_interface();// Расположение элементов игрового меню

        save_window_void(); // Сохранение игры

        mediaPlayer.play(); // Запуск музыки

        load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("SAVE", "*.save"),
                            new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
                    fileChooser.setTitle("Open Resource File");
                    File file = fileChooser.showOpenDialog(stagee);
                    if (file == null) {
                        alert_no_file.showAndWait();
                    } else {
                        game = new Game();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                            String line = reader.readLine();
                            try {
                                line = new String(Base64.getDecoder().decode(line));
                                game.move=Integer.parseInt(line);
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                game.home.count = Integer.parseInt(line);
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                game.rise.count = Integer.parseInt(line);
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                game.peasants.count = Integer.parseInt(line);
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                game.water.count = Integer.parseInt(line);
                                for (int i = 0; i < 10; i++)
                                    for (int j = 0; j < 10; j++) {
                                        for (int k = 0; k < 3; k++) {
                                            line = reader.readLine();
                                            line = new String(Base64.getDecoder().decode(line));
                                            field_cost[i][j].cost[k] = Integer.parseInt(line);
                                        }
                                        line = reader.readLine();
                                        line = new String(Base64.getDecoder().decode(line));
                                        field_cost[i][j].fraction = Integer.parseInt(line);
                                    }
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                buttons[4].setDisable(Boolean.parseBoolean(line));
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                buttons[5].setDisable(Boolean.parseBoolean(line));
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                buttons[6].setDisable(Boolean.parseBoolean(line));
                                line = reader.readLine();
                                line = new String(Base64.getDecoder().decode(line));
                                buttons[7].setDisable(Boolean.parseBoolean(line));
                                map = true;

                                scene = new Scene(game_menu, 800, 600);

                                move.setText("Ход: " + String.valueOf(game.move));
                                buttons[2].setDisable(true);
                                buttons[2].setText("Показать карту");
                                buttons[1].setText("Сохранить игру");
                                next.setText("Следующий ход ");
                                lables[0].setText("Количество крестьян: " + game.peasants.count);
                                lables[1].setText("Количество риса: " + game.rise.count);
                                lables[2].setText("Количество воды: " + game.water.count);
                                lables[3].setText("Количество домов: " + game.home.count);
                                stagee.setScene(scene);
                                stagee.show();
                                buttons[2].setDisable(true);
                                for (int i = 0; i < 10; i++)
                                    for (int j = 0; j < 10; j++) {
                                        if (field_cost[i][j].fraction == 0)
                                            field_point[i][j].setText(null);
                                        else if (field_cost[i][j].fraction == 1){
                                            field_point[i][j].setText(" Вы");
                                            field_point[i][j].setStyle("-fx-background-color: green;");}
                                        else if (field_cost[i][j].fraction == 2){
                                            field_point[i][j].setText("Враг");
                                            field_point[i][j].setStyle("-fx-background-color: red;");}
                                    }

                                field_stage.show();
                            }
                            catch (NumberFormatException e){
                                alert_load.showAndWait();
                            }
                            reader.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }); // Загрузка игры


        newgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene = new Scene(game_menu, 800, 600);

                field_stage.show();

                map = true;
                game = new Game();
                move.setText("Ход: "+String.valueOf(game.move));
                buttons[2].setDisable(true);
                buttons[2].setText("Показать карту");
                buttons[1].setText("Сохранить игру");
                next.setText("Следующий ход ");
                lables[0].setText("Количество крестьян: " + game.peasants.count);
                lables[1].setText("Количество риса: " + game.rise.count);
                lables[2].setText("Количество воды: " + game.water.count);
                lables[3].setText("Количество домов: " + game.home.count);
                stagee.setScene(scene);
                stagee.show();
            }
        }); // Новая игра


        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.move++;
                field_stage.close();
                buttons[2].setDisable(false);
                move.setText("Ход: "+String.valueOf(game.move));
                for (int i=4; i<8;i++)
                    buttons[i].setDisable(false);
                buttons[4].setText("Построить дом ");
                buttons[5].setText("Добыть воды ");
                buttons[6].setText("Полить рис ");
                buttons[7].setText("Собрать рис ");
                if (chance > random.nextDouble(100)){
                    int k=0;
                        for (int i = 0; i<10; i++)
                            for (int j = 0; j<10; j++){
                                if (i!=9 && field_cost[i][j].fraction==0 && field_cost[1+i][j].fraction==2) {
                                    boti[k]=i;
                                    botj[k]=j;
                                    k++;
                                }
                                else if (j!=9 && field_cost[i][j].fraction==0 && field_cost[i][1+j].fraction==2) {
                                    boti[k]=i;
                                    botj[k]=j;
                                    k++;
                                }
                                else if (i!=0 && field_cost[i][j].fraction==0 && field_cost[i-1][j].fraction==2) {
                                    boti[k]=i;
                                    botj[k]=j;
                                    k++;
                                }
                                else if (j!=0 && field_cost[i][j].fraction==0 && field_cost[i][j-1].fraction==2) {
                                    boti[k]=i;
                                    botj[k]=j;
                                    k++;
                                }
                            }
                        int choose = new Random().nextInt(k);
                        field_cost[boti[choose]][botj[choose]].fraction=2;
                        field_point[boti[choose]][botj[choose]].setStyle("-fx-background-color: red;");
                        chance=1;
                }
                else
                    chance*=1.4;
                int count_enemy=0, count_you=0;
                for (int i = 0; i<10; i++)
                    for (int j = 0; j < 10; j++){
                        if(field_cost[i][j].fraction==2)
                            count_enemy+=1;
                        else if (field_cost[i][j].fraction==1)
                            count_you+=1;
                    }
                if (count_enemy>=51){
                    stage.close();
                    lose.showAndWait();
                } else if (count_you>=51) {
                    stage.close();
                    win.showAndWait();
                }
                game.peasants.count+=game.home.count;
                move.setText("Ход: "+String.valueOf(game.move));
                lables[0].setText("Количество крестьян: " + game.peasants.count);
                lables[1].setText("Количество риса: " + game.rise.count);
                lables[2].setText("Количество воды: " + game.water.count);
                lables[3].setText("Количество домов: " + game.home.count);
            }
        }); // Следующий ход


        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (map==true) {
                    map=!map;
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++) {
                            if (field_cost[i][j].fraction == 0)
                                field_point[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                        + String.valueOf(field_cost[i][j].cost[1]) + " "
                                        + String.valueOf(field_cost[i][j].cost[2]));
                            else
                                if (field_cost[i][j].fraction == 2){
                                    field_point[i][j].setText(" ");
                                    field_point[i][j].setStyle("-fx-background-color: red;");}
                                else{
                                    field_point[i][j].setText(" ");
                                    field_point[i][j].setStyle("-fx-background-color: green;");}
                        }
                }
                else{
                    map=!map;
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++){
                            if (field_cost[i][j].fraction==0)
                                field_point[i][j].setText(null);
                            else if (field_cost[i][j].fraction == 1){
                                field_point[i][j].setText(" Вы");
                                field_point[i][j].setStyle("-fx-background-color: green;");}
                            else if (field_cost[i][j].fraction == 2){
                                field_point[i][j].setText("Враг");
                                field_point[i][j].setStyle("-fx-background-color: red;");}
                        }
                }
                field_stage.show();
                buttons[2].setDisable(true);
            }
        }); // Смена карты


        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try(FileWriter writer = new FileWriter("Saves\\"+ save_field.getText() +".save", false))
                {
                    String text="";
                    text=text+Base64.getEncoder().encodeToString(Integer.toString(game.move).getBytes())+"\n"+Base64.getEncoder().encodeToString(Integer.toString(game.home.count).getBytes())+"\n"+Base64.getEncoder().encodeToString(Integer.toString(game.rise.count).getBytes());
                    text=text+"\n"+Base64.getEncoder().encodeToString(Integer.toString(game.peasants.count).getBytes())+"\n"+Base64.getEncoder().encodeToString(Integer.toString(game.water.count).getBytes());
                    for (int i = 0; i<10; i++)
                        for (int j = 0; j<10; j++){
                            text=text+"\n"+Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[0]).getBytes())+"\n"+Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[1]).getBytes())
                                    +"\n"+Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].cost[2]).getBytes()) +"\n"+Base64.getEncoder().encodeToString(Integer.toString(field_cost[i][j].fraction).getBytes());
                        }
                    text=text+"\n"+Base64.getEncoder().encodeToString(Boolean.toString(buttons[4].isDisable()).getBytes())+"\n"+Base64.getEncoder().encodeToString(Boolean.toString(buttons[5].isDisable()).getBytes())
                            +"\n"+Base64.getEncoder().encodeToString(Boolean.toString(buttons[6].isDisable()).getBytes())+"\n"+Base64.getEncoder().encodeToString(Boolean.toString(buttons[7].isDisable()).getBytes());
                    writer.write(text);
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                save_window.close();
            }
        }); //Подтверждение сохранения


        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lables[4].setText("Введите название сохранения");
                buttons[0].setText("Готово");
                save_window.show();
            }
        }); //Сохранить игру


        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttons[2].setDisable(true);
                if (map==false) {
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++) {
                            if (field_cost[i][j].fraction == 0)
                                field_point[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                        + String.valueOf(field_cost[i][j].cost[1]) + " "
                                        + String.valueOf(field_cost[i][j].cost[2]));
                            else
                            if (field_cost[i][j].fraction == 2){
                                field_point[i][j].setText(" ");
                                field_point[i][j].setStyle("-fx-background-color: red;");}
                            else{
                                field_point[i][j].setText(" ");
                                field_point[i][j].setStyle("-fx-background-color: green;");}
                        }
                }
                else{
                    for (int i = 0; i < 10; i++)
                        for (int j = 0; j < 10; j++){
                            if (field_cost[i][j].fraction==0)
                                field_point[i][j].setText(null);
                            else if (field_cost[i][j].fraction == 1){
                                field_point[i][j].setText(" Вы");
                                field_point[i][j].setStyle("-fx-background-color: green;");}
                            else if (field_cost[i][j].fraction == 2){
                                field_point[i][j].setText("Враг");
                                field_point[i][j].setStyle("-fx-background-color: red;");}
                        }
                }
                field_stage.show();
            }
        }); //Показ карты


        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                field_stage.close();
                buttons[2].setDisable(false);
                for (int i = 0; i<10; i++)
                    for (int j = 0; j<10; j++){
                        if (field_cost[i][j].fraction==1){
                            field_button[i][j].setText(" Вы");
                            field_button[i][j].setDisable(true);
                        } else if (field_cost[i][j].fraction==2) {
                            field_button[i][j].setText("Враг");
                            field_button[i][j].setDisable(true);
                        } else if (i!=9 && field_cost[i][j].fraction==0 && field_cost[1+i][j].fraction==1) {
                            field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                    + String.valueOf(field_cost[i][j].cost[1]) + " "
                                    + String.valueOf(field_cost[i][j].cost[2]));
                            field_button[i][j].setDisable(false);}
                        else if (j!=9 && field_cost[i][j].fraction==0 && field_cost[i][1+j].fraction==1) {
                            field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                    + String.valueOf(field_cost[i][j].cost[1]) + " "
                                    + String.valueOf(field_cost[i][j].cost[2]));
                            field_button[i][j].setDisable(false);}
                        else if (i!=0 && field_cost[i][j].fraction==0 && field_cost[i-1][j].fraction==1) {
                            field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                    + String.valueOf(field_cost[i][j].cost[1]) + " "
                                    + String.valueOf(field_cost[i][j].cost[2]));
                            field_button[i][j].setDisable(false);}
                        else if (j!=0 && field_cost[i][j].fraction==0 && field_cost[i][j-1].fraction==1) {
                            field_button[i][j].setText(String.valueOf(field_cost[i][j].cost[0]) + " "
                                    + String.valueOf(field_cost[i][j].cost[1]) + " "
                                    + String.valueOf(field_cost[i][j].cost[2]));
                            field_button[i][j].setDisable(false);}
                            else
                                field_button[i][j].setDisable(true);

                    }
                field_buy.show();
            }
        }); // Кнопки для покупки


        buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (game.peasants.count > 0 & game.rise.count > 0 & game.water.count > 0){
                    game.home.count+=1;
                    game.peasants.count-=1;
                    game.rise.count-=1;
                    game.water.count-=1;
                    for (int i = 4; i<8; i++)
                        buttons[i].setDisable(true);
                }
                else{
                    buttons[4].setText("Недостаточно ресурсов");
                }
                lables[0].setText("Количество крестьян: " + game.peasants.count);
                lables[1].setText("Количество риса: " + game.rise.count);
                lables[2].setText("Количество воды: " + game.water.count);
                lables[3].setText("Количество домов: " + game.home.count);
            }
        }); // Постройка дома


        buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (game.peasants.count > 0){
                    game.water.count+=1;
                    game.peasants.count-=1;
                    for (int i = 4; i<8; i++)
                        buttons[i].setDisable(true);
                }
                else{
                    buttons[5].setText("Недостаточно крестьян для сбора воды");
                }
                lables[0].setText("Количество крестьян: " + game.peasants.count);
                lables[2].setText("Количество воды: " + game.water.count);
            }
        }); // Сбор воды


        buttons[6].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (game.water.count > 0){
                    game.water.count-=1;
                    game.rise.ChangeWatered();
                    buttons[6].setText("Рис полит");
                    for (int i = 4; i<8; i++)
                        buttons[i].setDisable(true);
                }
                else{
                    buttons[6].setText("Недостаточно воды для полива");
                }
            }
        }); // Поливка риса


        buttons[7].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (game.peasants.count > 0){
                    game.peasants.count-=1;
                    if (game.rise.watered == true){
                        game.rise.count+=3;
                        game.rise.ChangeWatered();}
                    else
                        game.rise.count+=1;
                    for (int i = 4; i<8; i++)
                        buttons[i].setDisable(true);
                }
                else{
                    buttons[5].setText("Недостаточно крестьян для сбора риса");
                }
                lables[0].setText("Количество крестьян: " + game.peasants.count);
                lables[1].setText("Количество риса: " + game.rise.count);
            }
        }); // Сбор риса


        field_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                buttons[2].setDisable(false);
            }
        }); // Блокировка кнопки показа карты


    }

    public static void main(String[] args) {
        launch();
    }
}