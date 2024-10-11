package org.example.proyecto_angel.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.ChangeStage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    public AnchorPane PrincipalFondo;
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private ListView<?> LaTabla;
    Usuario usu1 = null;
    List usuarios = null;
    EditarController editarController;


    public void OnEditarClic(ActionEvent actionEvent) throws IOException, SQLException {

        if (usu1 == null) {
            AlertUtils.mostrarError("EY tio, tienes que seleccionar el nombre de usuario");
        } else {
            editarController = ChangeStage.crearEscena("EditarUsuarios.fxml");
            editarController.cargarDatos(usu1, this);
        }

    }

    public void OnEliminarClic(ActionEvent actionEvent) throws SQLException {

        if (usu1 == null) {
            AlertUtils.mostrarError("EY tio, tienes que seleccionar el nombre de usuario");
        } else {
            usuarioDAO.eliminarUsuario(usu1.getNombre());

            try {
                cargarDatos();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void cargarDatos() throws SQLException {
        usuarios = usuarioDAO.obtenerUsuarios();
        LaTabla.setItems(FXCollections.observableList(usuarios));
    }

    public void OnSalirClic(ActionEvent actionEvent) throws IOException {
        ChangeStage.cambioEscena("hello-view.fxml", PrincipalFondo);
    }

    public void OnMauseClic(MouseEvent mouseEvent) {
        usu1 = (Usuario) LaTabla.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            usuarioDAO.conectar();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al cargar la configuración");
        }

        try {
            usuarios = usuarioDAO.obtenerUsuarios();
            LaTabla.setItems(FXCollections.observableList(usuarios));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
