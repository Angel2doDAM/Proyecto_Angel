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

//Esta es la pestaña a la que solo se puede acceder si eres administrador
public class PrincipalController implements Initializable {

    public AnchorPane PrincipalFondo;
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private ListView<?> LaTabla;
    Usuario usu1 = null;
    List usuarios = null;
    EditarController editarController;


    public void OnEditarClic(ActionEvent actionEvent) throws IOException, SQLException {

//        Compruebo que seleccione un usuario de la lista
        if (usu1 == null) {
            AlertUtils.mostrarError("EY tio, tienes que seleccionar el nombre de usuario");
        } else {
//            Abre el fxml de edicion de usuarios
            editarController = ChangeStage.crearEscena("EditarUsuarios.fxml");
//            Carga los adtos del usuario en los campos del fxml de edicion
            editarController.cargarDatos(usu1, this);
        }

    }

    public void OnEliminarClic(ActionEvent actionEvent) throws SQLException {

//        Compruebo si se ha seleccionado un usuario
        if (usu1 == null) {
            AlertUtils.mostrarError("EY tio, tienes que seleccionar el nombre de usuario");
        } else {
//            Se elimina de la base de datos si hay un usuario con ese nombre
            usuarioDAO.eliminarUsuario(usu1.getNombre());

            try {
                cargarDatos();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

//    Funcion para cargar los datos en la lista
    public void cargarDatos() throws SQLException {
        usuarios = usuarioDAO.obtenerUsuarios();
        LaTabla.setItems(FXCollections.observableList(usuarios));
    }

//    Esta funcion no sale de la aplicación, solo va a la pantalla anetrior
    public void OnSalirClic(ActionEvent actionEvent) throws IOException {
        ChangeStage.cambioEscena("hello-view.fxml", PrincipalFondo);
    }

//    Recojo el usuario seleccionado de la lista pulsando con el ratón
    public void OnMauseClic(MouseEvent mouseEvent) {
        usu1 = (Usuario) LaTabla.getSelectionModel().getSelectedItem();
    }

//    Funcion que conecta con la base de datos
//    y guarda los usuarios en una lista
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            usuarioDAO.conectar();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos.");
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
