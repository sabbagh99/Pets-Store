package com.Pets;

import com.Pets.controller.BaseController;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Global {

// All constants shared in the project

    public static final String UPDATE_DIALOG = "1";
    public static final String ADD_DIALOG = "2";
    public static final String DETAILS_DIALOG = "3";
    public static final String STATUS_AVAILABLE = "Available";
    public static final String STATUS_SOLD = "Sold";
    public static final String STATUS_PENDING = "Pending";
    public static final String GET_REQUEST = "GET";
    public static final String PUT_REQUEST = "PUT";
    public static final String POST_REQUEST = "POST";
    public static final String DELETE_REQUEST = "DELETE";
    public static final int SUCCESSFULLY_RESPONSE_RESULT = 200;



//    All methods shared in the project
    public static void showAlertMassage(String title, String massageHeader, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(massageHeader);
        alert.showAndWait();

    }

    public static Scene Scene(BaseController controller,FXMLLoader fxmlLoader) {

        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Scene scene = new Scene(parent);

        return scene;
    }

    public static void initCmbStatus(ComboBox cmb) {
        cmb.setItems(FXCollections.observableArrayList(
                Global.STATUS_AVAILABLE,
                Global.STATUS_SOLD,
                Global.STATUS_PENDING
        ));
    }
}
