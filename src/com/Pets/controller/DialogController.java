package com.Pets.controller;

import com.Pets.Global;
import com.Pets.Pet;

import com.Pets.view.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController extends BaseController implements Initializable {
    APIConnector apiConnector = new APIConnector();
    String methodType;
    Pet petToUpdate;
    ViewFactory viewFactory;

    public DialogController(ViewFactory viewFactory, String fxmlName, String methodType, Pet petToUpdate) {
        super(viewFactory, fxmlName);
        this.methodType = methodType;
        this.petToUpdate = petToUpdate;
        this.viewFactory = viewFactory;
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAction;

    @FXML
    private Button btnClose;

    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhotoUrl;


    @FXML
    private ComboBox<String> cmbStatus;


    @FXML
    void actions() {
        btnAction.setOnAction(e -> {

            if (methodType == Global.UPDATE_DIALOG) {
                apiConnector.setJSONObject(Global.PUT_REQUEST, initPetObject());
                System.out.println(apiConnector.getResponseResult());

                String title;
                String massageHeader;
                Alert.AlertType alertType;
                if (apiConnector.getResponseResult() == Global.SUCCESSFULLY_RESPONSE_RESULT) {
                    title = ("Updated");
                    massageHeader = ("The pet has been updated successfully");
                    alertType = Alert.AlertType.INFORMATION;
                } else {
                    title = ("Something wont Wrong");
                    massageHeader = ("Error : the response result is " + apiConnector.getResponseResult());
                    alertType = Alert.AlertType.ERROR;
                }
                Global.showAlertMassage(title, massageHeader, alertType);
            } else if (methodType == Global.ADD_DIALOG) {
                apiConnector.setJSONObject(Global.POST_REQUEST, initPetObject());
                Alert alert;
                String title;
                String massageHeader;
                Alert.AlertType alertType;
                if (apiConnector.getResponseResult() == Global.SUCCESSFULLY_RESPONSE_RESULT) {
                    title = ("Add");
                    massageHeader = ("The pet has been Add successfully");
                    alertType = Alert.AlertType.INFORMATION;
                } else {
                    title = ("Something wont Wrong");
                    massageHeader = ("Error : the response result is " + apiConnector.getResponseResult());
                    alertType = Alert.AlertType.ERROR;

                }
                Global.showAlertMassage(title, massageHeader, alertType);

            }

        });
    }


    @FXML
    void btnCloseAction() {

    }

    @FXML
    void initialize() {
        assert btnAction != null : "fx:id=\"btnAction\" was not injected: check your FXML file 'dialog.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'dialog.fxml'.";
        assert lblTitle != null : "fx:id=\"lblTitle\" was not injected: check your FXML file 'dialog.fxml'.";
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'dialog.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'dialog.fxml'.";
        assert txtPhotoUrl != null : "fx:id=\"txtPhotoUrl\" was not injected: check your FXML file 'dialog.fxml'.";

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.initCmbStatus(cmbStatus);
        setTitleName(methodType);
        initActions();

    }

    private void initActions() {
        btnCloseAction();
        actions();
    }

    private void setTitleName(String methodType) {

        if (methodType == Global.UPDATE_DIALOG) {
            lblTitle.setText("Update Pet details");
            txtId.setText(petToUpdate.getId());
            txtName.setText(petToUpdate.getName());
            txtPhotoUrl.setText(petToUpdate.getPhotoUrls());

            cmbStatus.valueProperty().set(petToUpdate.getStatus());

            btnAction.setText("Update");
            txtId.setEditable(false);


        } else if (methodType == Global.ADD_DIALOG) {
            lblTitle.setText("Add new Pet");
            btnAction.setText("Add");


        } else if (methodType == Global.DETAILS_DIALOG) {
            lblTitle.setText("Show Pet details");
            txtId.setText(petToUpdate.getId());
            txtName.setText(petToUpdate.getName());
            txtPhotoUrl.setText(petToUpdate.getPhotoUrls());
            cmbStatus.valueProperty().set(petToUpdate.getStatus());
            txtId.setEditable(false);
            txtName.setEditable(false);
            txtPhotoUrl.setEditable(false);
            cmbStatus.setDisable(true);
            btnAction.setVisible(false);

        }
    }


    private Pet initPetObject() {
        Pet pet = new Pet(txtId.getText().toString(), txtName.getText(), txtPhotoUrl.getText().toLowerCase(), cmbStatus.getValue().toString());
        return pet;
    }
}
