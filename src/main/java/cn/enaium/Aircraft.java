package cn.enaium;

import cn.enaium.utility.Timer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Enaium
 */
public class Aircraft extends Application {

    public static final Logger LOGGER = LogManager.getLogger("Aircraft");

    public static final InGame inGame = new InGame();
    public static final Timer timer = new Timer();

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aircraft");
        primaryStage.setScene(new Scene(new BorderPane().<BorderPane>apply(pane -> {
            pane.setTop(new HBox().<HBox>apply(hBox -> {
                hBox.getChildren().add(new Button("Create a group").<Button>apply(button -> {
                    button.setOnAction(event -> {
                        inGame.generateBomber();
                        inGame.createBase();
                        inGame.createTarget();
                    });
                }));
                hBox.getChildren().add(new Button("Generate Bomber").<Button>apply(button -> {
                    button.setOnAction(event -> inGame.generateBomber());
                }));
                hBox.getChildren().add(new Button("Create Base").<Button>apply(button -> {
                    button.setOnAction(event -> inGame.createBase());
                }));
                hBox.getChildren().add(new Button("Create Target").<Button>apply(button -> {
                    button.setOnAction(event -> inGame.createTarget());
                }));
            }));
            pane.setCenter(new Pane().<Pane>apply(parent -> {
                parent.getChildren().add(new Canvas().<Canvas>apply(canvas -> {
                    canvas.widthProperty().bind(parent.widthProperty());
                    canvas.heightProperty().bind(parent.heightProperty());
                    final var gc = canvas.getGraphicsContext2D();
                    new AnimationTimer() {

                        private long last = 0;

                        private int fps = 0;

                        private final Timer timer = new Timer();

                        @Override
                        public void handle(long now) {
                            long interval = 1_000_000_000 / 1000;
                            if (now - last >= interval) {
                                fps++;
                                last = now;
                            }

                            inGame.width = canvas.getWidth();
                            inGame.height = canvas.getHeight();
                            inGame.draw(gc);
                            inGame.update();

                            timer.use(() -> {
                                inGame.fps = fps;
                                fps = 0;
                            }, 1000);
                        }
                    }.start();
                }));
            }));
        }), WIDTH, HEIGHT).apply(scene -> {
            scene.setOnKeyPressed(inGame::onKeyPressed);
            scene.setOnKeyReleased(inGame::onKeyReleased);
        }));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
