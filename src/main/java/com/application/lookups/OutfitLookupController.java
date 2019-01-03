package com.application.lookups;

import com.application.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class OutfitLookupController implements Initializable {
    @FXML
    SceneController sceneController;
    @FXML
    private TextField outfitTag;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outfitTag.setOnKeyPressed(key -> {
            if(key.getCode().equals(KeyCode.ENTER)){
                search();
            }
        });
    }

    public void setRootController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void search() {
        if (!outfitTag.getText().isEmpty()) {
            sceneController.createOutfitGeneralStats(outfitTag.getText());
        }
        outfitTag.setText(null);
    }
}
