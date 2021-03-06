package com.Pets.controller;


import com.Pets.Global;
import com.Pets.view.ViewFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class PieChartController extends BaseController implements Initializable {
    int avaListSize;
    int soldListSize;
    int penListSize;
    @FXML
    private PieChart pieChart;

    public PieChartController( ViewFactory viewFactory, String fxmlName, int avaListSize, int soldListSize, int penListSize) {
        super( viewFactory, fxmlName);
        this.avaListSize = avaListSize;
        this.soldListSize = soldListSize;
        this.penListSize = penListSize;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(Global.STATUS_AVAILABLE, avaListSize),
                        new PieChart.Data(Global.STATUS_SOLD, soldListSize),
                        new PieChart.Data(Global.STATUS_PENDING, penListSize));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " pets: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
    }
}
