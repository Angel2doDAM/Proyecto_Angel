package org.example.proyecto_angel.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.proyecto_angel.Controller.EditarController;

import java.io.IOException;

public class ChangeStage {

    static public void cambioEscena(String fxmlnName, AnchorPane rootPane) throws IOException {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI(fxmlnName));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            newStage.sizeToScene();
            newStage.show();
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.close();

    }

    static public EditarController crearEscena(String fxmlnName) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI(fxmlnName));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(loader.load()));
        newStage.sizeToScene();
        newStage.show();
        return loader.getController();
    }

    static public void cerrarEscena(AnchorPane rootPane){
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        currentStage.close();
    }

}
