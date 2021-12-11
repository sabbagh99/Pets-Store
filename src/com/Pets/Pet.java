package com.Pets;

import javafx.beans.property.SimpleStringProperty;


public class Pet {
    private SimpleStringProperty  id;
    private SimpleStringProperty name;
    private SimpleStringProperty photoUrls;
    private SimpleStringProperty status;

    public Pet(String id, String name, String photoUrls, String status) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty (name);
        this.photoUrls = new SimpleStringProperty(photoUrls);
        this.status = new SimpleStringProperty(status);
    }


    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getPhotoUrls() {
        return photoUrls.get();
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = new SimpleStringProperty (photoUrls);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status =new  SimpleStringProperty(status);
    }
}
