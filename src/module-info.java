module PetStore {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires json.simple;





    opens com.Pets;
    opens com.Pets.view;
    opens com.Pets.controller ;
//    opens com.Pets.model;
}