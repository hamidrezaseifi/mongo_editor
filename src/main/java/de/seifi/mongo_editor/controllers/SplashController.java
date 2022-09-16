package de.seifi.mongo_editor.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class SplashController implements Initializable, ControllerBase {


    @FXML private GridPane rootPane;

    @FXML private Label lblMessages;

    @FXML private ProgressBar pgLoading;


    @Override
    public boolean isDirty() {

        return false;
    }

    @Override
    public String getDirtyMessage() {

        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void newSpringInitialEvent(ApplicationEvent event) {

        pgLoading.setProgress(pgLoading.getProgress() + 0.1);
        lblMessages.setText(event.getSource().toString());
    }

}
