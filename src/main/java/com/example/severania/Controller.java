package com.example.severania;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Controller {

    public static int size = 12;
    private Parent offStage = null;

    //FILES PATH
    public static String photo = "file:" + System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\severania\\photos\\";
    public static String media = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\severania\\media\\";

    //MEDIA PLAYERS _ MUSIC
    public static Media menuMedia = new Media(new File(media + "mainMusic.mp3").toURI().toString());
    public static MediaPlayer menuMediaPlayer = new MediaPlayer(menuMedia);
    public static Media gameMedia = new Media(new File(media + "gameMusic.mp3").toURI().toString());
    public static MediaPlayer gameMediaPlayer = new MediaPlayer(gameMedia);
    public static boolean MusicWasSetMute = false;


    //CLICK EFFECT
    public static AudioClip buttonEffect = new AudioClip("file:src/main/resources/com/example/severania/media/click.wav");
    public static boolean SFXWasSetMute = false;

    //MEDIA PLAYER _ MOVIE
    private Media Movie = new Media(new File("src/main/resources/com/example/severania/media/story.mp4").toURI().toString());
    private MediaPlayer MoviePlayer = new MediaPlayer(Movie);

    private Media Lose = new Media(new File("src/main/resources/com/example/severania/media/lose.mp4").toURI().toString());
    private MediaPlayer LosePlayer = new MediaPlayer(Lose);

    private Media Win = new Media(new File("src/main/resources/com/example/severania/media/win.mp4").toURI().toString());
    private MediaPlayer WinPlayer = new MediaPlayer(Win);


    //MenuPage fields
    @FXML
    private Button play;
    @FXML
    private Button options;
    @FXML
    private Button exit;

    //OptionPage fields
    @FXML
    private Parent optionsRoot = null;
    @FXML
    private Button optionBack;
    @FXML
    private Slider volume;
    @FXML
    private CheckBox SFX;
    @FXML
    private CheckBox mute;

    //InputPage fields
    @FXML
    private Parent inputRoot = null;
    @FXML
    private Spinner<Integer> sizeSpinner;
    @FXML
    private TextField nameField;
    @FXML
    private Button startMoviePlay;
    @FXML
    private Spinner<Integer> limitRange;
    @FXML
    private CheckBox isLimited;
    private boolean gameIsLimited = false;
    private int limitSize = 0;

    //set exit action -> QUIT
    @FXML
    public void exitProgram() {
        clickSound();
        System.exit(0);
    }

    //set option button action -> OPEN OPTION SETTING
    @FXML
    public void optionsSetting() {
        clickSound();
        try {
            offStage = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            optionsRoot = FXMLLoader.load(getClass().getResource("OptionPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane optionMix = new AnchorPane(offStage, optionsRoot);
        optionMix.setCursor(new ImageCursor(Main.cursor_normal));

        if (offStage != null)
            offStage.setOpacity(0.5);

        optionsRoot.setLayoutX(350);
        optionsRoot.setLayoutY(200);
        Main.scene.setRoot(optionMix);
    }

    //set volume slider -> SET SOUNDS VOLUME
    @FXML
    public void changeVolume() {
        menuMediaPlayer.setVolume(volume.getValue() / 100);
        buttonEffect.setVolume(volume.getValue() / 100);
    }

    //set SFX checkBox action -> MUTE GAME EFFECTS
    @FXML
    public void setSFXMute() {
        if (SFX.isSelected()) {
            buttonEffect.setVolume(0);
            SFXWasSetMute = true;
        } else {
            buttonEffect.setVolume(volume.getValue() / 100);
            SFXWasSetMute = false;
        }
    }

    //set mute checkBox -> MUTE GAME MUSIC
    @FXML
    public void setMute() {
        clickSound();
        if (mute.isSelected()) {
            menuMediaPlayer.setMute(true);
            MusicWasSetMute = true;
        } else {
            menuMediaPlayer.setMute(false);
            MusicWasSetMute = false;
        }
    }

    //set optionBack button action -> CLOSE OPTION SETTING
    @FXML
    public void backToMenu() {
        clickSound();
        Main.scene.setRoot(Main.menuRoot);
    }

    //set play button action -> OPEN INPUT PAGE AND GET THE NAME AND SIZE
    @FXML
    public void inputGetting() {
        clickSound();
        try {
            offStage = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputRoot = FXMLLoader.load(getClass().getResource("InputPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pane inputMix = new Pane(offStage, inputRoot);
        inputMix.setCursor(new ImageCursor(Main.cursor_normal));

        if (offStage != null)
            offStage.setOpacity(0.5);

        inputRoot.setLayoutX(270);
        inputRoot.setLayoutY(100);
        Main.scene.setRoot(inputMix);
    }

    //set isLimited check box action -> ABLE THE LIMIT_RANGE SPINNER
    @FXML
    private void limit() {
        if (isLimited.isSelected()) {
            limitRange.setDisable(false);
            gameIsLimited = true;
        } else {
            limitRange.setDisable(true);
            gameIsLimited = false;
        }
    }

    //set startMoviePlay action -> SHOW THE MOVIE AND SET THE SIZE OF BOARD AND PLAYER NAME
    private double volumeLevelChanging = 0;

    @FXML
    public void moviePlay() {
        clickSound();
        size = sizeSpinner.getValue();
        if (gameIsLimited) limitSize = limitRange.getValue();

        try {
            offStage = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (offStage != null)
            offStage.setOpacity(0.5);

        MediaView MovieView = new MediaView(MoviePlayer);
        MoviePlayer.setVolume(0);

        Timeline fadeOutMusic = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
            volumeLevelChanging += 0.1;
            menuMediaPlayer.setVolume(1 - volumeLevelChanging);
        }));
        fadeOutMusic.setOnFinished(event -> {
            menuMediaPlayer.stop();
            if (!MusicWasSetMute) {
                menuMediaPlayer.setVolume(0.5);
                MoviePlayer.setVolume(1);
            } else {
                menuMediaPlayer.setVolume(0);
                MoviePlayer.setVolume(0);
            }
        });
        fadeOutMusic.setCycleCount(10);
        fadeOutMusic.play();

        FadeTransition fadein = new FadeTransition(Duration.millis(2000), MovieView);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
        fadein.setOnFinished(event -> {
            MoviePlayer.play();
        });

        Timeline fadeInMusics = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
            volumeLevelChanging += 0.1;
            if (!MusicWasSetMute) MoviePlayer.setVolume(volumeLevelChanging);
        }));
        fadeInMusics.setCycleCount(9);
        fadeInMusics.play();

        MovieView.setFitHeight(600);
        MovieView.setFitWidth(1000);
        MovieView.setY(100);
        MovieView.setX(150);

        StackPane movieMix = new StackPane(offStage, MovieView);
        movieMix.setCursor(new ImageCursor(Main.cursor_loading));
        Main.scene.setRoot(movieMix);

        //after finished movie -> LOADING
        MoviePlayer.setOnEndOfMedia(() -> {

            if (!MusicWasSetMute) {
                gameMediaPlayer.setCycleCount(1000);
                gameMediaPlayer.setVolume(0.5);
                gameMediaPlayer.play();
            }

            AnchorPane loading = new AnchorPane();
            ImageView loadingPhoto = new ImageView(new Image(Controller.photo + "5_loadGame.gif"));
            loading.setCursor(new ImageCursor(Main.cursor_loading));
            loadingPhoto.setFitWidth(1400);
            loadingPhoto.setFitHeight(1000);
            loadingPhoto.setY(-100);
            loading.getChildren().add(loadingPhoto);
            Main.scene.setRoot(loading);

            Timeline passingToGame = new Timeline(new KeyFrame(Duration.millis(5000)));
            passingToGame.setCycleCount(1);
            passingToGame.setOnFinished(event -> {
                start();
            });
            passingToGame.play();
        });

    }


    /*********************************************************GAME PART*****************************************************/

    private String[] randomSentences = {"Good shot", "Nice", "My grandma plays better than you", "Not a chance",
            "Wow", "Good job", "Call your mom for help", "You re the best", "Ha ha ha...", "You ll win", "You hit it...Yeah..",
            "Missed it", "You are a great player", "Never give up", "Let me win", "Here we go again", "OMG",
            "Go play with your dolls", "Who is the best", "Defeat me if you can", "Shame on you", "That s not your job",
            "Lucky", "You are crazy", "You dont have brain", "What a Noob player", "There is nobody can defeat you",
            "I can see your weakness", "You are scared", "Yozora...I can defeat you...", "We can beat Yozora", "I can hear Kaki voice"
    };


    private int step = 1;
    private int counter = 0;
    private Goal goal = null;
    public static int playerMoney = 0;
    public int playerTurnCounter = 0;

    public boolean running = false;
    private int friendsToPlace = 11;
    public boolean enemyTurn = false;
    private BooleansCollection booleans =
            new BooleansCollection();
    private Random random = new Random();
    private Board enemyBoard, playerBoard;

    private Button buy = new Button();
    private Label money = new Label();
    private Button attack = new Button();
    private Label sentences = new Label();
    private ImageView endGame = new ImageView();
    private ImageView endGame2 = new ImageView();
    private Button randomArrange = new Button();
    private AnchorPane MainPane = new AnchorPane();

    private Kino_king kino_king = new Kino_king();
    private Rooki_rook rooki_rook = new Rooki_rook();
    private Keni_knight keni_knight = new Keni_knight();
    private Soli_soldier soli_soldier = new Soli_soldier();

    private int playerAttackHeight = soli_soldier.getPowerHeight();
    private int playerAttackWidth = soli_soldier.getPowerWidth();


    //choose a bloc and shoot it by enemy
    private int x;
    private int y;

    private void enemyMove() {
        playerBoard.setDisable(true);
        Timeline turn = new Timeline(new KeyFrame(Duration.seconds(1)));
        turn.setOnFinished(event -> {
            while (enemyTurn) {
                int enemyAttackWidth;
                int enemyAttackHeight;

                //choose attacker piece
                if ((kino_king.enemyCounter == 0 || kino_king.enemyCounter >= kino_king.getPeriod()) && enemyBoard.kingCount > 0) {
                    enemyAttackHeight = kino_king.getPowerHeight();
                    enemyAttackWidth = kino_king.getPowerWidth();
                    kino_king.enemyCounter = 0;
                } else if ((rooki_rook.enemyCounter == 0 || rooki_rook.enemyCounter >= rooki_rook.getPeriod()) && enemyBoard.rookCount > 0) {
                    enemyAttackWidth = rooki_rook.getPowerWidth();
                    enemyAttackHeight = rooki_rook.getPowerHeight();
                    rooki_rook.enemyCounter = 0;
                } else if ((keni_knight.enemyCounter == 0 || keni_knight.enemyCounter >= keni_knight.getPeriod()) && enemyBoard.knightCount > 0) {
                    enemyAttackHeight = keni_knight.getPowerHeight();
                    enemyAttackWidth = keni_knight.getPowerWidth();
                    keni_knight.enemyCounter = 0;
                } else if (enemyBoard.pawnCount > 0) {
                    enemyAttackHeight = 1;
                    enemyAttackWidth = 1;
                } else {
                    return;
                }

                //handle neighbors blocs
                if (goal != null) {
                    if (goal.getRight() != null && !goal.getRight().isDead()) {
                        x = goal.getRight().getBlocX();
                        y = goal.getRight().getBlocY();

                    } else if (goal.getLeft() != null && !goal.getLeft().isDead()) {
                        x = goal.getLeft().getBlocX();
                        y = goal.getLeft().getBlocY();

                    } else if (goal.getUp() != null && !goal.getUp().isDead()) {
                        x = goal.getUp().getBlocX();
                        y = goal.getUp().getBlocY();

                    } else if (goal.getDown() != null && !goal.getDown().isDead()) {
                        x = goal.getDown().getBlocX();
                        y = goal.getDown().getBlocY();

                    } else if (goal.getRight_up() != null && !goal.getRight_up().isDead() && goal.getRight_up().getPiece() != null) {
                        x = goal.getRight_up().getBlocX();
                        y = goal.getRight_up().getBlocY();

                    } else if (goal.getRight_down() != null && !goal.getRight_down().isDead() && goal.getRight_down().getPiece() != null) {
                        x = goal.getRight_down().getBlocX();
                        y = goal.getRight_down().getBlocY();

                    } else if (goal.getLeft_up() != null && !goal.getLeft_up().isDead() && goal.getLeft_up().getPiece() != null) {
                        x = goal.getLeft_up().getBlocX();
                        y = goal.getLeft_up().getBlocY();

                    } else if (goal.getLeft_down() != null && !goal.getLeft_down().isDead() && goal.getLeft_down().getPiece() != null) {
                        x = goal.getLeft_down().getBlocX();
                        y = goal.getLeft_down().getBlocY();

                    } else {
                        goal = null;
                        x = random.nextInt(size - enemyAttackHeight + 1);
                        y = random.nextInt(size - enemyAttackWidth + 1);
                    }
                } else {
                    x = random.nextInt(size - enemyAttackHeight + 1);
                    y = random.nextInt(size - enemyAttackWidth + 1);
                }

                Bloc[][] blocs = new Bloc[enemyAttackHeight][enemyAttackWidth];
                blocs[0][0] = playerBoard.getBloc(x, y);

                if (blocs[0][0].isDead())
                    continue;

                //in case bloc belongs to a ship, there is another turn
                enemyTurn = blocs[0][0].shoot();

                //after find a goal bloc, check neighbors
                if (enemyTurn) {
                    goal = new Goal(x, y, playerBoard);
                }

                //shoot a rectangle of blocs with enemyWidth and enemyHeight sides
                for (int i = 0; i < enemyAttackHeight; i++) {
                    for (int j = 0; j < enemyAttackWidth; j++) {
                        if (i + j == 0 || i + x >= size || j + y >= size) continue;
                        blocs[i][j] = playerBoard.getBloc(x + i, y + j);
                        if (blocs[i][j].isDead()) continue;
                        if (blocs[i][j].shoot() && goal == null)
                            goal = new Goal(x, y, playerBoard);
                    }
                }
                //losing you and winning enemy
                if (playerBoard.pieceCount == 0) {
                    LOSE();
                    System.out.println("YOU LOSE!");
                }

                if (gameIsLimited && playerTurnCounter >= limitSize) {
                    System.out.println("YOU COULDN'T WIN IN LIMITED RANGE!");
                    LOSE();
                }

                keni_knight.enemyCounter++;
                rooki_rook.enemyCounter++;
                kino_king.enemyCounter++;

                System.out.println("\n# enemy information:\n" + "Kino count = " + enemyBoard.kingCount);
                System.out.println("Rooki count = " + enemyBoard.rookCount);
                System.out.println("Keni count = " + enemyBoard.knightCount);
                System.out.println("Soli count = " + enemyBoard.pawnCount);
                System.out.println("friend count = " + enemyBoard.pieceCount);
                System.out.println("enemy shot bloc in row " + x + " and column " + y);

                //player could not attack with dead pieces
                if (playerBoard.pawnCount == 0) {
                    soli_soldier.button.setDisable(true);
                } else soli_soldier.button.setDisable(false);

                if (playerBoard.knightCount == 0) {
                    keni_knight.button.setDisable(true);
                } else keni_knight.button.setDisable(false);

                if (playerBoard.rookCount == 0) {
                    rooki_rook.button.setDisable(true);
                } else rooki_rook.button.setDisable(false);

                if (playerBoard.kingCount == 0) {
                    kino_king.button.setDisable(true);
                } else kino_king.button.setDisable(false);

            }
            playerBoard.setDisable(false);
        });
        turn.play();
    }

    //place enemy ships
    private void startGame() {
        sentences.setText("wait for your opponent...");
        randomArrange.setVisible(false);
        buy.setDisable(false);
        buy.setOpacity(1);
        attack.setDisable(false);
        attack.setOpacity(1);

        soli_soldier.button.setDisable(false);
        soli_soldier.button.setOpacity(1);

        int pieceCount = 11;

        while (pieceCount > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placePiece(new Piece(pieceCount, Math.random() < 0.5, true), x, y)) {
                pieceCount--;
            }
        }
        Timeline placeEnemy = new Timeline(new KeyFrame(new Duration(3000)));
        placeEnemy.setOnFinished(event -> {
            running = true;
            sentences.setText("start the game");
        });
        placeEnemy.play();
    }

    private Parent gameDisplay() {

        MainPane.getStylesheets().add(Main.class.getResource("styles/gameDisplay.css").toExternalForm());
        MainPane.setCursor(new ImageCursor(Main.cursor_normal));
        MainPane.setPrefWidth(1400);
        MainPane.setPrefHeight(800);


        //**back part*****************************************************************************************************//
        {
            Rectangle rectangle = new Rectangle(1410, 810);
            rectangle.setStyle("-fx-background-color: linear-gradient(to right, #145092, #572498)");

            ImageView background = new ImageView(new Image(photo + "6_game.png"));
            background.setFitHeight(810);
            background.setFitWidth(1410);

            MainPane.getChildren().addAll(rectangle, background);
        }

        //**option part***************************************************************************************************//
        {
            Button restart = new Button("Restart");
            buttons_optionSetting(restart);
            //set restart button action -> RELOAD GAME_DISPLAY_PAGE
            restart.setOnAction(event -> {
                clickSound();

                step = 1;
                counter = 0;
                goal = null;
                running = false;
                playerMoney = 0;
                enemyTurn = false;
                friendsToPlace = 11;
                playerTurnCounter = 0;
                playerAttackWidth = 0;
                playerAttackHeight = 0;

                money = new Label();
                sentences = new Label();
                MainPane = new AnchorPane();
                kino_king = new Kino_king();
                rooki_rook = new Rooki_rook();
                keni_knight = new Keni_knight();
                soli_soldier = new Soli_soldier();
                booleans = new BooleansCollection();

                start();
            });

            Button game_menu = new Button("Menu");
            buttons_optionSetting(game_menu);
            //set game_menu button action -> LOAD MENU_PAGE
            game_menu.setOnAction(event -> {
                clickSound();
                AnchorPane loading = new AnchorPane();
                ImageView loadingPhoto = new ImageView(new Image(Controller.photo + "1_loading.png"));
                loadingPhoto.setFitWidth(1400);
                loadingPhoto.setFitHeight(800);
                loading.getChildren().add(loadingPhoto);
                Main.scene.setRoot(loading);

                Timeline backToMainMenu = new Timeline(new KeyFrame(Duration.millis(2000)));
                backToMainMenu.setCycleCount(1);
                backToMainMenu.setOnFinished(event0 -> {
                    backToMenu();
                });
                backToMainMenu.play();
            });

            Button game_mute = new Button("Mute");
            buttons_optionSetting(game_mute);
            //set game_mute button action -> SET THE GAME_MEDIA_PAYER MUTE
            game_mute.setOnAction(event -> {
                clickSound();
                if (counter % 2 == 0) {
                    gameMediaPlayer.setMute(true);
                } else {
                    gameMediaPlayer.setMute(false);
                }
                counter++;
            });

            Button game_exit = new Button("Exit");
            buttons_optionSetting(game_exit);
            //set game_exit button action -> QUIT PROGRAM
            game_exit.setOnAction(event -> {
                clickSound();
                exitProgram();
            });

            Button game_OptionSetting = new Button();
            game_OptionSetting.setId("container");
            game_OptionSetting.setLayoutX(1270);
            game_OptionSetting.setPrefWidth(155);
            game_OptionSetting.setPrefHeight(90);

            ImageView optionPhoto = new ImageView(new Image(photo + "openSetting.png"));
            optionPhoto.setFitWidth(100);
            optionPhoto.setFitHeight(90);
            optionPhoto.setLayoutX(1275);

            //set game_options button action -> OPEN OPTION_PAGE ON THE GAME PAGE
            game_OptionSetting.setOnAction(event -> {
                clickSound();
                if (!booleans.optionIsComposed) {
                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), restart);
                    translateTransition1.setByX(+160);
                    translateTransition1.setByY(-10);

                    TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), game_menu);
                    translateTransition2.setByX(+130);
                    translateTransition2.setByY(-60);

                    TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), game_mute);
                    translateTransition3.setByX(+85);
                    translateTransition3.setByY(-107);

                    TranslateTransition translateTransition4 = new TranslateTransition(Duration.seconds(1), game_exit);
                    translateTransition4.setByX(+35);
                    translateTransition4.setByY(-153);

                    translateTransition1.play();
                    translateTransition2.play();
                    translateTransition3.play();
                    translateTransition4.play();

                    booleans.optionIsComposed = !booleans.optionIsComposed;
                } else {
                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), restart);
                    translateTransition1.setByX(-160);
                    translateTransition1.setByY(+10);

                    TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), game_menu);
                    translateTransition2.setByX(-130);
                    translateTransition2.setByY(+60);

                    TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), game_mute);
                    translateTransition3.setByX(-85);
                    translateTransition3.setByY(+107);

                    TranslateTransition translateTransition4 = new TranslateTransition(Duration.seconds(1), game_exit);
                    translateTransition4.setByX(-35);
                    translateTransition4.setByY(+153);

                    translateTransition1.play();
                    translateTransition2.play();
                    translateTransition3.play();
                    translateTransition4.play();

                    booleans.optionIsComposed = !booleans.optionIsComposed;
                }
            });
            optionPhoto.setOnMouseClicked(event -> {
                clickSound();
                if (!booleans.optionIsComposed) {
                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), restart);
                    translateTransition1.setByX(+160);
                    translateTransition1.setByY(-10);

                    TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), game_menu);
                    translateTransition2.setByX(+130);
                    translateTransition2.setByY(-60);

                    TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), game_mute);
                    translateTransition3.setByX(+85);
                    translateTransition3.setByY(-107);

                    TranslateTransition translateTransition4 = new TranslateTransition(Duration.seconds(1), game_exit);
                    translateTransition4.setByX(+35);
                    translateTransition4.setByY(-153);

                    translateTransition1.play();
                    translateTransition2.play();
                    translateTransition3.play();
                    translateTransition4.play();

                    booleans.optionIsComposed = !booleans.optionIsComposed;
                } else {
                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), restart);
                    translateTransition1.setByX(-160);
                    translateTransition1.setByY(+10);

                    TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), game_menu);
                    translateTransition2.setByX(-130);
                    translateTransition2.setByY(+60);

                    TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), game_mute);
                    translateTransition3.setByX(-85);
                    translateTransition3.setByY(+107);

                    TranslateTransition translateTransition4 = new TranslateTransition(Duration.seconds(1), game_exit);
                    translateTransition4.setByX(-35);
                    translateTransition4.setByY(+153);

                    translateTransition1.play();
                    translateTransition2.play();
                    translateTransition3.play();
                    translateTransition4.play();

                    booleans.optionIsComposed = !booleans.optionIsComposed;
                }
            });

            MainPane.getChildren().addAll(restart, game_menu, game_mute, game_exit, game_OptionSetting, optionPhoto);
        }

        //**guide part****************************************************************************************************//
        {
            AnchorPane guidePane = new AnchorPane();
            guidePane.getStylesheets().add(Main.class.getResource("styles/gameDisplay.css").toExternalForm());
            guidePane.setPrefWidth(405);
            guidePane.setPrefHeight(260);
            guidePane.setLayoutX(-45);
            guidePane.setLayoutY(460);

            ImageView guideBackground = new ImageView(new Image(photo + "guide.png"));
            guideBackground.setFitWidth(450);
            guideBackground.setFitHeight(310);
            guideBackground.setLayoutX(25);
            guideBackground.setLayoutY(10);

            ImageView guidePhoto = new ImageView(new Image(photo + "guidePage1.png"));
            guidePhoto.setFitWidth(245);
            guidePhoto.setFitHeight(270);
            guidePhoto.setLayoutX(195);
            guidePhoto.setLayoutY(25);

            Button Next = new Button("Next");
            Next.setId("buttonOnPane");
            Next.setPrefWidth(80);
            Next.setPrefHeight(25);
            Next.setLayoutX(55);
            Next.setLayoutY(255);

            //set next button action -> CHANGE GUIDE PAGE
            Next.setOnAction(event -> {
                clickSound();
                if (step == 1) {
                    guidePhoto.setImage(new Image(photo + "guidePage2.png"));
                } else if (step == 2) {
                    guidePhoto.setImage(new Image(photo + "guidePage3.png"));
                } else if (step == 3) {
                    guidePhoto.setImage(new Image(photo + "guidePage4.png"));
                } else if (step == 4) {
                    guidePhoto.setImage(new Image(photo + "guidePage5.png"));
                } else if (step == 5) {
                    guidePhoto.setImage(new Image(photo + "guidePage6.png"));
                } else if (step == 6) {
                    guidePhoto.setImage(new Image(photo + "guidePage7.png"));
                } else if (step > 6) {
                    sentences.setText("Now help my friend to arrange...");
                    guidePane.getChildren().removeAll(Next, guidePhoto);
                }
                step++;
            });

            sentences.setPrefWidth(230);
            sentences.setPrefHeight(260);
            sentences.setLayoutX(200);
            sentences.setLayoutY(40);
            sentences.getStyleClass().add("labelOnPane");
            sentences.setWrapText(true);
            sentences.setTextAlignment(TextAlignment.CENTER);
            sentences.setFont(Font.font(30));

            guidePane.getChildren().addAll(guideBackground, sentences, guidePhoto, Next);
            MainPane.getChildren().addAll(guidePane);
        }

        //**buy part******************************************************************************************************//

        AnchorPane buyPane = new AnchorPane();
        Rectangle rectangle0 = new Rectangle(1410, 810);

        buyPane.getStylesheets().add(Main.class.getResource("styles/gameDisplay.css").toExternalForm());
        buyPane.setLayoutX(-380);
        buyPane.setLayoutY(180);
        buyPane.setPrefWidth(320);
        buyPane.setPrefHeight(280);

        Label row = new Label("Choose the row");
        row.getStyleClass().add("labelOnPane");
        row.setLayoutX(40);
        row.setLayoutY(14);
        Spinner<Integer> rowSpinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> rowValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, size, 1);

        rowSpinner.setValueFactory(rowValueFactory);
        rowSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        rowSpinner.setPrefWidth(180);
        rowSpinner.setPrefHeight(30);
        rowSpinner.setLayoutX(17);
        rowSpinner.setLayoutY(35);


        Label column = new Label("Choose the column");
        column.getStyleClass().add("labelOnPane");
        column.setLayoutX(40);
        column.setLayoutY(71);
        Spinner<Integer> columnSpinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> columnValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, size, 1);

        columnSpinner.setValueFactory(columnValueFactory);
        columnSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        columnSpinner.setPrefWidth(180);
        columnSpinner.setPrefHeight(30);
        columnSpinner.setLayoutX(17);
        columnSpinner.setLayoutY(100);

        buyPane.getChildren().addAll(row, rowSpinner, column, columnSpinner);

        money.getStyleClass().add("labelOnPane");
        money.setLayoutX(235);
        money.setLayoutY(35);
        money.setFont(Font.font("Ace Records", 18));

        buyPane.getChildren().add(money);

        Button buySoli_soldier = new Button("Soli");
        buttons_buySetting(buySoli_soldier, 45, 140, "soldier", 35, 40);

        Button buyKeni_knight = new Button("Keni");
        buttons_buySetting(buyKeni_knight, 175, 140, "knight", 38, 40);

        Button buyRooki_rook = new Button("Rooki");
        buttons_buySetting(buyRooki_rook, 45, 210, "rook", 40, 33);

        Button buyKino_king = new Button("Kino");
        buttons_buySetting(buyKino_king, 175, 210, "king", 37, 37);

        buyPane.getChildren().addAll(buyKeni_knight, buyKino_king, buyRooki_rook, buySoli_soldier);

        buySoli_soldier.setOnAction(event -> {
            clickSound();
            Bloc bloc = (Bloc) playerBoard.getBloc(columnSpinner.getValue() - 1, rowSpinner.getValue() - 1);
            if (playerBoard.rookCount >= 1) {
                if (500 <= playerMoney) {
                    if (playerBoard.placePiece(new Piece(1, false, false), bloc.getBlocX(), bloc.getBlocY())) {
                        playerBoard.pieceCount++;
                        playerBoard.pawnCount++;
                        sentences.setText("Its done...You cant buy piece anymore");
                        booleans.friendAdded = true;

                        buySoli_soldier.setDisable(true);
                        buySoli_soldier.setOpacity(0.5);

                        buyKeni_knight.setDisable(true);
                        buyKeni_knight.setOpacity(0.5);

                        buyRooki_rook.setDisable(true);
                        buyRooki_rook.setOpacity(0.5);

                        buyKino_king.setDisable(true);
                        buyKino_king.setOpacity(0.5);

                    } else {
                        sentences.setText("You cant put him here");
                        return;
                    }
                } else {
                    sentences.setText("You dont have enough money");
                    return;
                }
            } else {
                sentences.setText("You lost your rooks");
                return;
            }


            boolean isPossible = false;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    if (enemyBoard.isValidPoint(i, j) && !enemyBoard.getBloc(i, j).isDead()) isPossible = true;
                }

            if (!isPossible) {
                sentences.setText("Enemy doesnt have enough space");
                return;
            }

            boolean done = false;
            do {
                int x = random.nextInt(size);
                int y = random.nextInt(size);
                if (enemyBoard.placePiece(new Piece(1, false, true), x, y)) {
                    enemyBoard.pieceCount++;
                    done = true;
                }
            } while (!done);
        });
        buyKeni_knight.setOnAction(event -> {
            clickSound();
            Bloc bloc = (Bloc) playerBoard.getBloc(columnSpinner.getValue() - 1, rowSpinner.getValue() - 1);
            if (playerBoard.rookCount >= 1) {
                if (1000 <= playerMoney) {
                    if (playerBoard.placePiece(new Piece(6, false, false), bloc.getBlocX(), bloc.getBlocY())) {
                        playerBoard.pieceCount++;
                        playerBoard.knightCount++;
                        sentences.setText("Its done...You cant buy piece anymore");
                        booleans.friendAdded = true;

                        buySoli_soldier.setDisable(true);
                        buySoli_soldier.setOpacity(0.5);

                        buyKeni_knight.setDisable(true);
                        buyKeni_knight.setOpacity(0.5);

                        buyRooki_rook.setDisable(true);
                        buyRooki_rook.setOpacity(0.5);

                        buyKino_king.setDisable(true);
                        buyKino_king.setOpacity(0.5);

                    } else {
                        sentences.setText("You cant put him here");
                        return;
                    }
                } else {
                    sentences.setText("You dont have enough money");
                    return;
                }
            } else {
                sentences.setText("You lost your rooks");
                return;
            }

            boolean isPossible = false;
            int counter = 0;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size - 1; j++) {
                    if (enemyBoard.isValidPoint(i, j) && !enemyBoard.getBloc(i, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i + 1, j) && !enemyBoard.getBloc(i + 1, j).isDead()) counter++;

                    if (counter == 4) isPossible = true;
                }

            if (!isPossible) {
                sentences.setText("Enemy doesnt have enough space");
                return;
            }

            boolean done = false;
            do {
                int x = random.nextInt(size);
                int y = random.nextInt(size - 1);
                if (enemyBoard.placePiece(new Piece(6, false, true), x, y)) {
                    enemyBoard.pieceCount++;
                    done = true;
                }
            } while (!done);
        });
        buyRooki_rook.setOnAction(event -> {
            clickSound();
            Bloc bloc = (Bloc) playerBoard.getBloc(columnSpinner.getValue() - 1, rowSpinner.getValue() - 1);
            if (playerBoard.rookCount >= 1) {
                if (2000 <= playerMoney) {
                    if (playerBoard.placePiece(new Piece(9, false, false), bloc.getBlocX(), bloc.getBlocY())) {
                        playerBoard.pieceCount++;
                        playerBoard.rookCount++;
                        sentences.setText("Its doneYou cant buy piece anymore");
                        booleans.friendAdded = true;

                        buySoli_soldier.setDisable(true);
                        buySoli_soldier.setOpacity(0.5);

                        buyKeni_knight.setDisable(true);
                        buyKeni_knight.setOpacity(0.5);

                        buyRooki_rook.setDisable(true);
                        buyRooki_rook.setOpacity(0.5);

                        buyKino_king.setDisable(true);
                        buyKino_king.setOpacity(0.5);

                    } else {
                        sentences.setText("You cant put her here");
                        return;
                    }
                } else {
                    sentences.setText("You dont have enough money");
                    return;
                }
            } else {
                sentences.setText("You lost your rooks");
                return;
            }

            boolean isPossible = false;
            int counter = 0;
            for (int i = 0; i < size - 1; i++)
                for (int j = 0; j < size - 1; j++) {
                    if (enemyBoard.isValidPoint(i, j) && !enemyBoard.getBloc(i, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i, j + 1) && !enemyBoard.getBloc(i, j + 1).isDead()) counter++;

                    if (enemyBoard.isValidPoint(i + 1, j) && !enemyBoard.getBloc(i + 1, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i + 1, j + 1) && !enemyBoard.getBloc(i + 1, j + 1).isDead())
                        counter++;

                    if (counter == 4) isPossible = true;
                }

            if (!isPossible) {
                sentences.setText("Enemy doesnt have enough space");
                return;
            }

            boolean done = false;
            do {
                int x = random.nextInt(size - 1);
                int y = random.nextInt(size - 1);
                if (enemyBoard.placePiece(new Piece(9, false, true), x, y)) {
                    enemyBoard.pieceCount++;
                    done = true;
                }
            } while (!done);
        });
        buyKino_king.setOnAction(event -> {
            clickSound();
            Bloc bloc = (Bloc) playerBoard.getBloc(columnSpinner.getValue() - 1, rowSpinner.getValue() - 1);
            if (playerBoard.rookCount >= 1) {
                if (3000 <= playerMoney) {
                    if (playerBoard.placePiece(new Piece(11, false, false), bloc.getBlocX(), bloc.getBlocY())) {
                        playerBoard.pieceCount++;
                        playerBoard.kingCount++;
                        sentences.setText("Its done...You cant buy piece anymore");
                        booleans.friendAdded = true;

                        buySoli_soldier.setDisable(true);
                        buySoli_soldier.setOpacity(0.5);

                        buyKeni_knight.setDisable(true);
                        buyKeni_knight.setOpacity(0.5);

                        buyRooki_rook.setDisable(true);
                        buyRooki_rook.setOpacity(0.5);

                        buyKino_king.setDisable(true);
                        buyKino_king.setOpacity(0.5);

                    } else {
                        sentences.setText("You cant put her here");
                        return;
                    }
                } else {
                    sentences.setText("You dont have enough money");
                    return;
                }
            } else {
                sentences.setText("You lost your rooks");
                return;
            }


            boolean isPossible = false;
            int counter = 0;
            for (int i = 0; i < size - 2; i++)
                for (int j = 0; j < size - 2; j++) {

                    if (enemyBoard.isValidPoint(i - 1, j - 1) && !enemyBoard.getBloc(i - 1, j - 1).isDead())
                        counter++;
                    if (enemyBoard.isValidPoint(i - 1, j) && !enemyBoard.getBloc(i - 1, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i - 1, j + 1) && !enemyBoard.getBloc(i - 1, j + 1).isDead())
                        counter++;

                    if (enemyBoard.isValidPoint(i, j - 1) && !enemyBoard.getBloc(i, j - 1).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i, j) && !enemyBoard.getBloc(i, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i, j + 1) && !enemyBoard.getBloc(i, j + 1).isDead()) counter++;

                    if (enemyBoard.isValidPoint(i + 1, j - 1) && !enemyBoard.getBloc(i + 1, j - 1).isDead())
                        counter++;
                    if (enemyBoard.isValidPoint(i + 1, j) && !enemyBoard.getBloc(i + 1, j).isDead()) counter++;
                    if (enemyBoard.isValidPoint(i + 1, j + 1) && !enemyBoard.getBloc(i + 1, j + 1).isDead())
                        counter++;

                    if (counter == 9) isPossible = true;
                }

            if (!isPossible) {
                sentences.setText("Enemy doesnt have enough space");
                return;
            }

            boolean done = false;
            do {
                int x = random.nextInt(size - 2);
                int y = random.nextInt(size - 2);
                if (enemyBoard.placePiece(new Piece(11, false, true), x, y)) {
                    enemyBoard.pieceCount++;
                    done = true;
                }
            } while (!done);
        });

        MainPane.getChildren().add(buyPane);

        buy.setText("Ask for another help");
        buy.getStyleClass().add("button-big");
        buy.setDisable(true);
        buy.setOpacity(0.5);
        buy.setPrefWidth(190);
        buy.setPrefHeight(50);
        buy.setLayoutX(40);
        buy.setLayoutY(60);

        //set buyOpen button action -> TRANSLATE BUY_PANE INTO MAIN PAGE
        buy.setOnAction(event -> {
            clickSound();
            if (!booleans.buyIsOpened) {
                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), buyPane);
                translateTransition.setByX(+405);
                translateTransition.play();

                booleans.buyIsOpened = !booleans.buyIsOpened;
            } else {
                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), buyPane);
                translateTransition.setByX(-405);
                translateTransition.play();
                booleans.buyIsOpened = !booleans.buyIsOpened;
            }
        });
        MainPane.getChildren().add(buy);


        //**attack part***************************************************************************************************//
        {
            AnchorPane attackPane = new AnchorPane();
            attackPane.setLayoutX(-380);
            attackPane.setLayoutY(250);
            attackPane.setPrefWidth(310);
            attackPane.setPrefHeight(210);
            attackPane.getStylesheets().add(Main.class.getResource("styles/gameDisplay.css").toExternalForm());

            Label attackLabel = new Label("Choose a friend");
            attackLabel.getStyleClass().add("labelOnPane");
            attackLabel.setLayoutX(14);
            attackLabel.setLayoutY(4);

            attackPane.getChildren().addAll(soli_soldier.button, keni_knight.button, rooki_rook.button, kino_king.button, attackLabel);
            MainPane.getChildren().add(attackPane);

            //before start the game -> ATTACK BUTTONS ARE DISABLED
            soli_soldier.button.setDisable(true);
            soli_soldier.button.setOpacity(0.5);
            keni_knight.button.setDisable(true);
            keni_knight.button.setOpacity(0.5);
            rooki_rook.button.setDisable(true);
            rooki_rook.button.setOpacity(0.5);
            kino_king.button.setDisable(true);
            kino_king.button.setOpacity(0.5);

            //set attack buttons -> CHANGE PLAYER HEIGHT AND WIDTH
            soli_soldier.button.setOnAction(event -> {
                clickSound();
                playerAttackHeight = 1;
                playerAttackWidth = 1;
                sentences.setText("Your click destroys 1 bloc");
            });
            keni_knight.button.setOnAction(event -> {
                clickSound();
                playerAttackHeight = keni_knight.getPowerHeight();
                playerAttackWidth = keni_knight.getPowerWidth();
                keni_knight.playerCounter = 0;
                keni_knight.button.setDisable(true);
                keni_knight.button.setOpacity(0.5);
                sentences.setText("Your click destroys 2 to 1 blocs");
            });
            rooki_rook.button.setOnAction(event -> {
                clickSound();
                playerAttackHeight = rooki_rook.getPowerHeight();
                playerAttackWidth = rooki_rook.getPowerWidth();
                rooki_rook.playerCounter = 0;
                rooki_rook.button.setDisable(true);
                rooki_rook.button.setOpacity(0.5);
                sentences.setText("Your click destroys 3 to 2 blocs");
            });
            kino_king.button.setOnAction(event -> {
                clickSound();
                playerAttackHeight = kino_king.getPowerHeight();
                playerAttackWidth = kino_king.getPowerWidth();
                kino_king.playerCounter = 0;
                kino_king.button.setDisable(true);
                kino_king.button.setOpacity(0.5);
                sentences.setText("Your click destroys 2 to 4 blocs");
            });

            attack.setText("Get help from");
            attack.getStyleClass().add("button-big");
            attack.setDisable(true);
            attack.setOpacity(0.5);
            attack.setPrefWidth(150);
            attack.setPrefHeight(50);
            attack.setLayoutX(40);
            attack.setLayoutY(120);

            //set buyOpen button action -> TRANSLATE BUY_PANE INTO MAIN PAGE
            attack.setOnAction(event -> {
                clickSound();
                if (booleans.attackIsOpened) {
                    TranslateTransition attackTransition = new TranslateTransition(Duration.seconds(1), attackPane);
                    attackTransition.setByX(-405);
                    attackTransition.play();
                    booleans.attackIsOpened = !booleans.attackIsOpened;

                } else {
                    TranslateTransition attackTransition = new TranslateTransition(Duration.seconds(1), attackPane);
                    attackTransition.setByX(+405);
                    attackTransition.play();
                    booleans.attackIsOpened = !booleans.attackIsOpened;
                }
            });
            MainPane.getChildren().add(attack);
        }

        //**randomArrange part********************************************************************************************//
        {
            randomArrange.setText("Random Arrangement");
            randomArrange.getStyleClass().add("button-big");
            randomArrange.setPrefWidth(220);
            randomArrange.setPrefHeight(50);
            randomArrange.setLayoutX(40);
            randomArrange.setLayoutY(180);

            //set randomArrange button action -> ARRANGE FRIENDS BY RANDOM
            randomArrange.setOnAction(event -> {
                clickSound();
                randomArrange.setVisible(false);
                booleans.randomArranged = true;

                int pieceCount = 11;
                while (pieceCount > 0) {
                    int x = random.nextInt(10);
                    int y = random.nextInt(10);

                    if (playerBoard.placePiece(new Piece(pieceCount, Math.random() < 0.5, false), x, y))
                        if (--pieceCount == 0)
                            startGame();
                }
            });

            MainPane.getChildren().add(randomArrange);
        }

        //**board part****************************************************************************************************//

        enemyBoard = new Board(true, event -> {
            //after start the game, do this
            if (!running) return;

            //losing you and winning enemy
            if (playerBoard.pieceCount == 0) {
                LOSE();
                System.out.println("YOU LOSE!");
            }

            //check if the attacker piece is alive
            if (playerBoard.kingCount < 0 || (kino_king.playerCounter % kino_king.getPeriod() != 0 && kino_king.playerCounter < kino_king.getPeriod())) {
                kino_king.button.setDisable(true);
                kino_king.button.setOpacity(0.5);
            }
            if (playerBoard.rookCount < 0 || (rooki_rook.playerCounter % rooki_rook.getPeriod() != 0 && rooki_rook.playerCounter < rooki_rook.getPeriod())) {
                rooki_rook.button.setDisable(true);
                rooki_rook.button.setOpacity(0.5);
            }
            if (playerBoard.knightCount < 0 || (keni_knight.playerCounter % keni_knight.getPeriod() != 0 && keni_knight.playerCounter < keni_knight.getPeriod())) {
                keni_knight.button.setDisable(true);
                keni_knight.button.setOpacity(0.5);
            }
            if (playerBoard.pawnCount < 0) {
                soli_soldier.button.setDisable(true);
                soli_soldier.button.setOpacity(0.5);
            }

            //player should choose a attacker piece
            if (playerAttackHeight == 0 && (soli_soldier.button.isDisable() || keni_knight.button.isDisable() || rooki_rook.button.isDisable() || kino_king.button.isDisable())) {
                sentences.setText("Choose your attacker");
                return;
            }

            //if are pieces are not valid, skip a turn
            if (soli_soldier.button.isDisable() && keni_knight.button.isDisable()
                    && rooki_rook.button.isDisable() && kino_king.button.isDisable()) {

                sentences.setText("You cant attack this turn");
                return;
            }


            Bloc[][] blocs = new Bloc[playerAttackHeight][playerAttackWidth];
            blocs[0][0] = (Bloc) event.getSource();
            int x = blocs[0][0].getBlocX();
            int y = blocs[0][0].getBlocY();
            if (blocs[0][0].isDead()) return;

            System.out.println("\n# your information in your " + playerTurnCounter + "th turn :\n" + "Kino count = " + playerBoard.kingCount);
            System.out.println("Rooki count = " + playerBoard.rookCount);
            System.out.println("Keni count = " + playerBoard.knightCount);
            System.out.println("Soli count = " + playerBoard.pawnCount);
            System.out.println("friend count = " + playerBoard.pieceCount);
            System.out.println("you shot bloc in row " + x + " and column " + y);

            enemyTurn = !blocs[0][0].shoot();

            //shoot a rectangle of blocs with playerWidth and playerHeight sides
            for (int i = 0; i < playerAttackHeight; i++) {
                for (int j = 0; j < playerAttackWidth; j++) {
                    if (i + j == 0 || i + x >= size || j + y >= size) continue;
                    blocs[i][j] = enemyBoard.getBloc(i + x, j + y);
                    if (blocs[i][j].isDead()) continue;
                    blocs[i][j].shoot();
                }
            }

            kino_king.playerCounter++;
            rooki_rook.playerCounter++;
            keni_knight.playerCounter++;
            soli_soldier.playerCounter++;
            playerAttackHeight = playerAttackWidth = 0;

            //recovery periods
            if (playerBoard.pawnCount > 0) {
                soli_soldier.button.setDisable(false);
                soli_soldier.button.setOpacity(0.9);
            }
            if ((keni_knight.playerCounter % keni_knight.getPeriod() == 0 || keni_knight.playerCounter > keni_knight.getPeriod()) && playerBoard.knightCount > 0) {
                keni_knight.button.setDisable(false);
                keni_knight.button.setOpacity(0.9);
            }
            if ((rooki_rook.playerCounter % rooki_rook.getPeriod() == 0 || rooki_rook.playerCounter > rooki_rook.getPeriod()) && playerBoard.rookCount > 0) {
                rooki_rook.button.setDisable(false);
                rooki_rook.button.setOpacity(0.9);
            }
            if ((kino_king.playerCounter % kino_king.getPeriod() == 0 || kino_king.playerCounter > kino_king.getPeriod()) && playerBoard.kingCount > 0) {
                kino_king.button.setDisable(false);
                kino_king.button.setOpacity(0.9);
            }

            if (playerMoney < 500) {
                buySoli_soldier.setDisable(true);
                buySoli_soldier.setOpacity(0.5);
            }
            if (playerMoney < 1000) {
                buyKeni_knight.setDisable(true);
                buyKeni_knight.setOpacity(0.5);
            }
            if (playerMoney < 2000) {
                buyRooki_rook.setDisable(true);
                buyRooki_rook.setOpacity(0.5);
            }
            if (playerMoney < 3000) {
                buyKino_king.setDisable(true);
                buyKino_king.setOpacity(0.5);
            }

            money.setText(Integer.toString(playerMoney));
            if (booleans.friendAdded) {
                buySoli_soldier.setDisable(true);
                buySoli_soldier.setOpacity(0.5);
                buyKeni_knight.setDisable(true);
                buyKeni_knight.setOpacity(0.5);
                buyRooki_rook.setDisable(true);
                buyRooki_rook.setOpacity(0.5);
                buyKino_king.setDisable(true);
                buyKino_king.setOpacity(0.5);
            } else {
                if (playerMoney >= 500) {
                    buySoli_soldier.setDisable(false);
                    buySoli_soldier.setOpacity(0.9);
                }
                if (playerMoney >= 1000) {
                    buyKeni_knight.setDisable(false);
                    buyKeni_knight.setOpacity(0.9);
                }
                if (playerMoney >= 2000) {
                    buyRooki_rook.setDisable(false);
                    buyRooki_rook.setOpacity(0.9);
                }
                if (playerMoney >= 3000) {
                    buyKino_king.setDisable(false);
                    buyKino_king.setOpacity(0.9);
                }

            }
            //random sentences
            int number = random.nextInt(randomSentences.length - 1);
            if (playerTurnCounter % 5 == 0)
                sentences.setText(randomSentences[number]);

            //loosing enemy and winning you!
            if (enemyBoard.pieceCount == 0) {
                WIN();
                System.out.println("YOU WIN!");
            }
            playerTurnCounter++;

            if (enemyTurn)
                enemyMove();
        });
        playerBoard = new Board(false, event -> {
            //update money label
            money.setText(Integer.toString(playerMoney));

            //before start the game, do this
            if (running)
                return;

            //if player placed even 1 friend, remove randomArrange button
            randomArrange.setVisible(false);

            if (booleans.randomArranged)
                return;

            Bloc bloc = (Bloc) event.getSource();
            if (playerBoard.placePiece(new Piece(friendsToPlace, event.getButton().equals(MouseButton.PRIMARY), false), bloc.getBlocX(), bloc.getBlocY())) {
                if (--friendsToPlace == 0) {
                    startGame();
                }
            }
        });
        HBox boards = new HBox(30, enemyBoard, playerBoard);

        boards.setLayoutX(435);
        boards.setLayoutY(180);

        MainPane.getChildren().add(boards);

        return MainPane;
    }

    public void start() {
        Main.scene.setRoot(gameDisplay());
    }

    @FXML
    public void LOSE() {

        //after end the game show a gif on player board
        endGame.setFitHeight(410);
        endGame.setFitWidth(410);
        endGame.setLayoutX(875);
        endGame.setLayoutY(180);
        endGame.setOpacity(0.5);

        endGame.setImage(new Image(photo + "lose.gif"));
        MainPane.getChildren().add(endGame);

        Timeline showGif = new Timeline(new KeyFrame(Duration.millis(5000)));
        showGif.setOnFinished(event -> {
            volumeLevelChanging = 0;
            try {
                offStage = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (offStage != null)
                offStage.setOpacity(0.5);

            MediaView LoseView = new MediaView(LosePlayer);
            LosePlayer.setVolume(0);

            Timeline fadeOutMusic = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
                volumeLevelChanging += 0.1;
                gameMediaPlayer.setVolume(1 - volumeLevelChanging);
            }));
            fadeOutMusic.setOnFinished(event0 -> {
                gameMediaPlayer.stop();
                if (!MusicWasSetMute) {
                    gameMediaPlayer.setVolume(0.5);
                    LosePlayer.setVolume(1);
                } else {
                    gameMediaPlayer.setVolume(0);
                    LosePlayer.setVolume(0);
                }
            });
            fadeOutMusic.setCycleCount(10);
            fadeOutMusic.play();

            FadeTransition fadein = new FadeTransition(Duration.millis(2000), LoseView);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
            fadein.setOnFinished(event0 -> {
                LosePlayer.play();
            });

            Timeline fadeInMusics = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
                volumeLevelChanging += 0.1;
                if (!MusicWasSetMute) LosePlayer.setVolume(volumeLevelChanging);
            }));
            fadeInMusics.setCycleCount(9);
            fadeInMusics.play();

            LoseView.setFitHeight(600);
            LoseView.setFitWidth(1000);
            LoseView.setY(100);
            LoseView.setX(150);

            StackPane LoseMix = new StackPane(offStage, LoseView);
            LoseMix.setCursor(new ImageCursor(Main.cursor_loading));
            Main.scene.setRoot(LoseMix);

            //after finished movie -> LOADING
            LosePlayer.setOnEndOfMedia(() -> {

                if (!MusicWasSetMute) {
                    menuMediaPlayer.setVolume(0.5);
                    menuMediaPlayer.play();
                }
                backToMenu();
            });
        });
        showGif.play();
    }

    @FXML
    public void WIN() {

        endGame.setFitHeight(410);
        endGame.setFitWidth(410);
        endGame.setLayoutX(875);
        endGame.setLayoutY(180);
        endGame.setOpacity(0.5);

        endGame.setImage(new Image(photo + "win.gif"));
        MainPane.getChildren().add(endGame);

        endGame2.setFitHeight(410);
        endGame2.setFitWidth(410);
        endGame2.setLayoutX(875);
        endGame2.setLayoutY(180);
        endGame2.setOpacity(0.5);

        endGame.setImage(new Image(photo + "win2.gif"));
        MainPane.getChildren().add(endGame2);

        Timeline showGif = new Timeline(new KeyFrame(Duration.millis(5000)));
        showGif.setOnFinished(event -> {
            volumeLevelChanging = 0;
            try {
                offStage = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (offStage != null)
                offStage.setOpacity(0.5);

            MediaView WinView = new MediaView(WinPlayer);
            WinPlayer.setVolume(0);

            Timeline fadeOutMusic = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
                volumeLevelChanging += 0.1;
                gameMediaPlayer.setVolume(1 - volumeLevelChanging);
            }));
            fadeOutMusic.setOnFinished(event0 -> {
                gameMediaPlayer.stop();
                if (!MusicWasSetMute) {
                    gameMediaPlayer.setVolume(0.5);
                    WinPlayer.setVolume(1);
                } else {
                    gameMediaPlayer.setVolume(0);
                    WinPlayer.setVolume(0);
                }
            });
            fadeOutMusic.setCycleCount(10);
            fadeOutMusic.play();

            FadeTransition fadein = new FadeTransition(Duration.millis(2000), WinView);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.play();
            fadein.setOnFinished(event0 -> {
                WinPlayer.play();
            });

            Timeline fadeInMusics = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
                volumeLevelChanging += 0.1;
                if (!MusicWasSetMute) WinPlayer.setVolume(volumeLevelChanging);
            }));
            fadeInMusics.setCycleCount(9);
            fadeInMusics.play();

            WinView.setFitHeight(600);
            WinView.setFitWidth(1000);
            WinView.setY(100);
            WinView.setX(150);

            StackPane LoseMix = new StackPane(offStage, WinView);
            LoseMix.setCursor(new ImageCursor(Main.cursor_loading));
            Main.scene.setRoot(LoseMix);

            //after finished movie -> LOADING
            WinPlayer.setOnEndOfMedia(() -> {

                if (!MusicWasSetMute) {
                    menuMediaPlayer.setVolume(0.5);
                    menuMediaPlayer.play();
                }
                backToMenu();
            });
        });
        showGif.play();
    }


    /**
     * to summarize code
     *****************************************************************************************************/

    public void setCycle(MediaPlayer mediaPlayer) {
        mediaPlayer.setCycleCount(1000);
    }

    //play sfx sounds
    public void clickSound() {
        buttonEffect.play();
    }

    public void buttons_optionSetting(Button button) {
        button.setLayoutX(1305);
        button.setLayoutY(0);
    }

    public void buttons_buySetting(Button button, double x, double y, String iconName, double w, double h) {
        button.setPrefWidth(120);
        button.setPrefHeight(65);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.getStyleClass().add("buttonOnPane");

        ImageView view = new ImageView(new Image(photo + iconName + ".png"));
        view.setFitWidth(w);
        view.setFitHeight(h);
        button.setGraphic(view);

    }
}
