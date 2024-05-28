package com.example.demo6;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fishing extends Application {
    private StackPane root;
    private List<Rectangle> fishes = new ArrayList<>();
    private Rectangle guy;
    boolean isHoldingGreen = false;
    private StackPane menu;
    private Rectangle catchCheck;
    private Button started;
    private int catches = 0;
    private AnimationTimer timer;
    private VBox box;
    private Text score;
    private boolean isHolding;
    public Parent createContent()
    {
        root = new StackPane();
        VBox bg = new VBox();
        HBox top = new HBox();
        Rectangle ice0 = new Rectangle(920,120,Color.WHITE);
        Rectangle hole = new Rectangle(80,120,Color.LIGHTBLUE);
        Rectangle ice1 = new Rectangle(920,120,Color.WHITE);
        Rectangle road = new Rectangle(1920,840, Color.BLUE);
        Rectangle grass1 = new Rectangle(1920,120,Color.WHEAT);
        score = new Text(-80,-540, "Fish: " + catches);
        score.setFill(Color.YELLOW);
        score.setTabSize(40);
        top.getChildren().add(ice0);
        top.getChildren().add(hole);
        top.getChildren().add(ice1);
        bg.getChildren().add(top);
        bg.getChildren().add(road);
        bg.getChildren().add(grass1);
        root.getChildren().add(bg);
        root.setPrefSize(1920,1080);
        catchCheck = new Rectangle(80,20,Color.GRAY);
        catchCheck.setTranslateY(-530);

        guy = initguy();
        root.getChildren().add(catchCheck);
        root.getChildren().add(guy);
        root.getChildren().add(score);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }
    private Rectangle spawnfish() {
        Rectangle rect;
        if(Math.random() < 0.95){
         rect = new Rectangle(80, 40, Color.CORAL);
    } else {
            rect = new Rectangle(40, 20, Color.LIME);
        }
        rect.setTranslateY(-320 + (int)(Math.random() * 19) * 40);
        rect.setTranslateX(-1000);

        root.getChildren().add(rect);
        return rect;
    }
    private void checkCatch()
    {
        for(Rectangle fish : fishes)
        {
            if(fish.getBoundsInParent().intersects(guy.getBoundsInParent()))
            {
                if(!isHolding){

                    isHolding = true;
                    if(fish.getFill() == Color.LIME){
                        isHoldingGreen = true;
                        guy.setFill(Color.LIME);
                    } else {
                        guy.setFill(Color.CORAL);
                    }
                    fish.setOpacity(0);
                }

            }
            if(isHolding && guy.getBoundsInParent().intersects(catchCheck.getBoundsInParent())){
                //guy.setScaleY(1/2);
                guy.setFill(Color.RED);
                if(!isHoldingGreen){
                    catches++;
                } else {
                    catches += 5;
                }
                isHolding = false;
                isHoldingGreen = false;
            }
            score.setText("Fish: " + catches);
        }

    }
    private void onUpdate() {
        for (Node fish : fishes)
            fish.setTranslateX(fish.getTranslateX() + Math.random() * 10);

        if (Math.random() < 0.015) {
            fishes.add(spawnfish());
        }
        checkCatch();


    }
    private Rectangle initguy()
    {
        Rectangle rect = new Rectangle(40,40, Color.RED);
        rect.setTranslateY(-540);
        return rect;
    }

    @Override
    public void start(Stage stage) throws IOException {

            stage.setScene(new Scene(createContent()));


            stage.getScene().setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W:
                        guy.setTranslateY(guy.getTranslateY() - 40);
                        break;
                    case S:
                        guy.setTranslateY(guy.getTranslateY() + 40);
                        break;
                    default:
                        break;
                }
            });



        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}