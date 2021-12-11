package com.Pets.controller;

import com.Pets.Global;
import com.Pets.Pet;

import com.Pets.view.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController extends BaseController implements Initializable {
    ObservableList<Pet> observableList = FXCollections.observableArrayList();
    ObservableList<Pet> newList = FXCollections.observableArrayList();

    List<JSONObject> availablePetsList = new ArrayList<>();
    List<JSONObject> soldPetsList = new ArrayList<>();
    List<JSONObject> pendingPetsList = new ArrayList<>();
    List<JSONObject> statusList = new ArrayList<>();
    APIConnector apiConnector = new APIConnector();
    int avaListSize = 0;
    int soldListSize = 0;
    int penListSize = 0;
    ViewFactory viewFactory;


    public HomeController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        this.viewFactory = viewFactory;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDetails;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;


    @FXML
    private Button btnPieChart;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<Pet, String> colId;

    @FXML
    private TableColumn<Pet, String> colName;

    @FXML
    private TableColumn<Pet, String> colPhotoUrls;

    @FXML
    private TableColumn<Pet, String> colStatus;

    @FXML
    private TableView<Pet> tblPets;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblNo;

    @FXML
    void btnAddAction() {
        btnAdd.setOnAction(e -> {

            viewFactory.showDialog(Global.ADD_DIALOG, null);
        });
    }

    @FXML
    void btnClearAction() {
        btnClear.setOnAction(e -> {
            tblPets.setItems(null);
            txtId.setText("");
            cmbStatus.valueProperty().set(null);
            lblNo.setText("Number of results : 0");


        });
    }

    @FXML
    void btnCloseAction() {
        btnClose.setOnAction(e -> {
            Platform.exit();


        });
    }

    @FXML
    void btnDeleteAction() {
        btnDelete.setOnAction(e -> {
            Pet pet = tblPets.getSelectionModel().getSelectedItem();
            if (pet == null) {
                String title = "Invalid input";
                String massageHeader = "PLease select record";
                Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                Global.showAlertMassage(title, massageHeader, alertType);

            } else {
                try {
                    apiConnector.deleteJSONObject(pet.getId());
                    System.out.println(apiConnector.getResponseResult());
                    String title;
                    String massageHeader;
                    Alert.AlertType alertType;
                    if (apiConnector.getResponseResult() == Global.SUCCESSFULLY_RESPONSE_RESULT) {
                        title = "Done";
                        massageHeader = "Pet has been deleted successfully";
                        alertType = Alert.AlertType.INFORMATION;

                    } else {

                        title = "Something wont Wrong";
                        massageHeader = "Error : the response result is " + apiConnector.getResponseResult();
                        alertType = Alert.AlertType.ERROR;

                    }
                    Global.showAlertMassage(title, massageHeader, alertType);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

    }

    @FXML
    void btnDetailsAction() {
        btnDetails.setOnAction(e -> {
            Pet pet = tblPets.getSelectionModel().getSelectedItem();
            if (pet == null) {

                String title = "Invalid input";
                String massageHeader = "PLease select record";
                Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                Global.showAlertMassage(title, massageHeader, alertType);
            } else {
                viewFactory.showDialog(Global.DETAILS_DIALOG, pet);
            }
        });


    }

    @FXML
    void btnSearch() {

        btnSearch.setOnAction(e -> {
            if (txtId.getText().trim().isEmpty() && cmbStatus.getValue() == null) {

                String title = "Invalid input";
                String massageHeader = "Please Fill any search criteria";
                Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                Global.showAlertMassage(title, massageHeader, alertType);
            } else {


                tblPets.setItems(null);
                if (!txtId.getText().trim().isEmpty()) {
                    JSONObject petObject = apiConnector.getJSONObject(txtId.getText(), Global.GET_REQUEST);
                    if (apiConnector.getResponseResult() == Global.SUCCESSFULLY_RESPONSE_RESULT) {
                        getById(petObject);

                    } else {

                        String title = "Invalid input";
                        String massageHeader = "There's no pets with the provided id number";
                        Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                        Global.showAlertMassage(title, massageHeader, alertType);

                    }

                } else if (cmbStatus.getValue() != null && cmbStatus.getValue().equals(Global.STATUS_AVAILABLE) || cmbStatus.getValue().equals(Global.STATUS_SOLD) || cmbStatus.getValue().equals(Global.STATUS_PENDING)) {
                    JSONArray tempList = apiConnector.getJSONArray(cmbStatus.getValue().toString().toLowerCase());
                    fillPetsList(tempList);
                }

            }
        });
    }


    @FXML
    void btnUpdateAction() {
        btnUpdate.setOnAction(e -> {
            Pet pet = tblPets.getSelectionModel().getSelectedItem();
            if (pet == null) {

                String title = "Invalid input";
                String massageHeader = "PLease select record";
                Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                Global.showAlertMassage(title, massageHeader, alertType);

            } else {

                viewFactory.showDialog(Global.UPDATE_DIALOG, pet);
            }
        });
    }


    @FXML
    void PieChartAction() {

        btnPieChart.setOnAction(e -> {

            viewFactory.showDashBord(avaListSize, soldListSize, penListSize);
        });
    }

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'home.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'home.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'home.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'home.fxml'.";
        assert btnDetails != null : "fx:id=\"btnDetails\" was not injected: check your FXML file 'home.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'home.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'home.fxml'.";
        assert cmbStatus != null : "fx:id=\"cmbStatus\" was not injected: check your FXML file 'home.fxml'.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file 'home.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'home.fxml'.";
        assert colPhotoUrls != null : "fx:id=\"colPhotoUrls\" was not injected: check your FXML file 'home.fxml'.";
        assert colStatus != null : "fx:id=\"colStatus\" was not injected: check your FXML file 'home.fxml'.";
        assert tblPets != null : "fx:id=\"tblPets\" was not injected: check your FXML file 'home.fxml'.";
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'home.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPhotoUrls.setCellValueFactory(new PropertyValueFactory<>("PhotoUrls"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        fillPetsList(null);
        Global.initCmbStatus(cmbStatus);

        initActions();
    }


    public void fillPetsList(JSONArray tempList) {
        observableList.clear();
        tblPets.setItems(null);
        if (tempList != null) {
            if (tempList.size() > 0) {
                statusList = tempList;
                fillObservableList(statusList);
            } else {
                String title = "No data";
                String massageHeader = "No data found with " + cmbStatus.getValue().toString().toLowerCase() + " status";
                Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                Global.showAlertMassage(title, massageHeader, alertType);


            }
        } else {
            soldPetsList = apiConnector.getJSONArray(Global.STATUS_SOLD.toLowerCase());
            availablePetsList = apiConnector.getJSONArray(Global.STATUS_AVAILABLE.toLowerCase());
            pendingPetsList = apiConnector.getJSONArray(Global.STATUS_PENDING.toLowerCase());

            fillObservableList(availablePetsList);
            fillObservableList(soldPetsList);
            fillObservableList(pendingPetsList);
            avaListSize = availablePetsList.size();
            soldListSize = soldPetsList.size();
            penListSize = pendingPetsList.size();
        }
        tblPets.setItems(observableList);
        lblNo.setText("Number of results : " + observableList.size());


    }

    private int fillObservableList(List<JSONObject> list) {
        for (int i = 0; i < list.size(); i++) {
            observableList.add(new Pet(list.get(i).get("id").toString(), list.get(i).get("name").toString(), list.get(i).get("photoUrls").toString(), list.get(i).get("status").toString()));
        }
        return observableList.size();
    }

    private void initActions() {
        btnSearch();
        btnClearAction();
        btnCloseAction();
        btnDeleteAction();
        btnUpdateAction();
        btnAddAction();
        btnDetailsAction();
        PieChartAction();
    }

    public void getById(JSONObject petObject) {
        newList.clear();
        tblPets.setItems(null);
        newList.add(new Pet(petObject.get("id").toString(), petObject.get("name").toString(), petObject.get("photoUrls").toString(), petObject.get("status").toString()));
        tblPets.setItems(newList);
        lblNo.setText("Number of results : " + newList.size());

    }
}
