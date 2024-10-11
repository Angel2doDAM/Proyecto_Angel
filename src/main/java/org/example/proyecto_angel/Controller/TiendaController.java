package org.example.proyecto_angel.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.ChangeStage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {

    @FXML
    private ComboBox ChuseCubo;

    @FXML
    private TableColumn<?, ?> ColumCubo;

    @FXML
    private TableColumn<?, ?> ColumPrecio;

    @FXML
    private TableColumn<?, ?> ColumTipo;

    @FXML
    private TextArea  TextoMostrar;


    @FXML
    private AnchorPane TiendaFondo;

    private String[] cubos={"2x2 normal", "2x2 mirror", "3x3 normal", "3x3 mirror", "3x3 gear", "3x3 windmill", "2x2x3", "banana", "piraminx", "megaminx"};

    private String compra = "Articulos a pagar:\n";
    private boolean primeraVez=true;
    private double totalPagar=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChuseCubo.getItems().addAll(cubos);
    }

    public void OnAniadirClic(ActionEvent actionEvent) {
        if (ChuseCubo.getValue()==null){
            AlertUtils.mostrarError("Selecciona un cubo antes de añadirlo");
        } else {
            if (primeraVez){
                compra +=  ChuseCubo.getValue();
                primeraVez = false;
            } else {
                compra +=  ", " + ChuseCubo.getValue();
            }
            switch (ChuseCubo.getValue().toString()){
                case "2x2 normal":
                    totalPagar += 6.81;
                    break;
                case "2x2 mirror":
                    totalPagar += 8.99;
                    break;
                case "3x3 normal":
                    totalPagar += 12.84;
                    break;
                case "3x3 mirror":
                    totalPagar += 9.98;
                    break;
                case "3x3 gear":
                    totalPagar += 15;
                    break;
                case "3x3 windmill":
                    totalPagar += 10.99;
                    break;
                case "2x2x3":
                    totalPagar += 5.25;
                    break;
                case "banana":
                    totalPagar += 7.90;
                    break;
                case "piraminx":
                    totalPagar += 9.99;
                    break;
                case "megaminx":
                    totalPagar += 12.99;
                    break;
            }
            TextoMostrar.setText(compra);
        }

    }

    public void OnComprarClic(ActionEvent actionEvent) {
        AlertUtils.mostrarAcierto("Gracias por su compra \nUn pago de " + totalPagar + "€ se añadirá a su cuenta");
        totalPagar = 0;
    }

    public void OnSalirClic(ActionEvent actionEvent) throws IOException {
        ChangeStage.cambioEscena("hello-view.fxml", TiendaFondo);
    }
}