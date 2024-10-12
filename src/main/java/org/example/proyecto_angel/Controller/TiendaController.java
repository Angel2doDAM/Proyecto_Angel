package org.example.proyecto_angel.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.proyecto_angel.DAO.TiendaDAO;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Cubo;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.ChangeStage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {

    @FXML
    private TableView<Cubo> TableCubo;

    @FXML
    private ComboBox ChuseCubo;

    @FXML
    private TableColumn ColumCubo;

    @FXML
    private TableColumn ColumPrecio;

    @FXML
    private TableColumn ColumTipo;

    @FXML
    private TextArea  TextoMostrar;

    @FXML
    private AnchorPane TiendaFondo;

    @FXML
    private Label TotalActual;

    ArrayList<Cubo> cubosArray = null;

    private String[] cubos={"2x2 normal", "2x2 mirror", "3x3 normal", "3x3 mirror", "3x3 gear", "3x3 windmill", "2x2x3 normal", "2x2x3 banana", "piraminx", "megaminx"};

    private String compra = "Articulos a pagar:\n";
    private boolean primeraVez=true;
    private double totalPagar=0;

    TiendaDAO tiendaDAO = new TiendaDAO();

    ObservableList<Cubo> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tiendaDAO.conectar();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ChuseCubo.getItems().addAll(cubos);
    }

    public void cargarDatos() throws SQLException {
        cubosArray = tiendaDAO.obtenerCubos();
        ColumCubo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ColumTipo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        ColumPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        data = FXCollections.observableArrayList();

        for (int i = 0; i < cubosArray.size(); i++) {
            data.add(new Cubo(cubosArray.get(i).getTipo(), cubosArray.get(i).getModelo(), cubosArray.get(i).getPrecio())); //añade a "data" la info de los textField

            TableCubo.setItems(data); //añade a la tabla la info de "data"
        }
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
                case "2x2x3 normal":
                    totalPagar += 5.25;
                    break;
                case "2x2x3 banana":
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
            TotalActual.setText("Total actual: " +totalPagar + "€");
        }

    }

    public void OnComprarClic(ActionEvent actionEvent) {
        AlertUtils.mostrarAcierto("Gracias por su compra \nUn pago de " + totalPagar + "€ se añadirá a su cuenta \n" + compra);
        totalPagar = 0;
        ChuseCubo.setValue(null);
        TextoMostrar.setText("");
        TotalActual.setText("Total actual:");
        compra = "Articulos a pagar:\n";
        primeraVez=true;
    }

    public void OnSalirClic(ActionEvent actionEvent) throws IOException {
        ChangeStage.cambioEscena("hello-view.fxml", TiendaFondo);
    }
}