package com.Pets.view;

import com.Pets.Global;
import com.Pets.Pet;

import com.Pets.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    public ViewFactory() {
    }

    public void showHomeWindow() {

        BaseController controller = new HomeController(this, "home.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        Scene scene = Global.Scene(controller, fxmlLoader);
        Stage stage = new Stage();
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setTitle("Pets store");
        stage.setScene(scene);
        stage.show();
    }

    public void showDialog(String dialogType, Pet pet) {

        BaseController controller = new DialogController(this, "dialog.fxml", dialogType, pet);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        Scene scene = Global.Scene(controller, fxmlLoader);
        Stage stage = new Stage();
        Button btnClose = (Button) scene.lookup("#btnClose");
        btnClose.setOnAction(e -> {
            stage.close();
        });
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setTitle("Pets store");
        stage.setScene(scene);
        stage.show();
    }

    public void showDashBord(int avaListSize, int soldListSize, int penListSize) {

        BaseController controller = new PieChartController(this, "dashBorad.fxml", avaListSize, soldListSize, penListSize);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        Scene scene = Global.Scene(controller, fxmlLoader);
        Stage stage = new Stage();
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setTitle("Pets store dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
