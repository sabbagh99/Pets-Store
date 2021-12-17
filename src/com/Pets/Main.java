package com.Pets;

import com.Pets.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        System.out.print("Yazan Was Here.");

    }

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showHomeWindow();

    }
}
