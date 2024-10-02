package org.example.proyecto_angel.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField contraText;

    @FXML
    private TextField usuarioText;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usu1 = new Usuario();

    public HelloController() {
        try {
            usuarioDAO.conectar();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al cargar la configuración");
        }

        System.out.println(System.getProperty("user.home"));
    }

    @FXML
    public void OnGuardarClic(javafx.event.ActionEvent actionEvent) {
        try {
            // Verificar que los campos no estén vacíos
            if (usuarioText.getText().isEmpty() || contraText.getText().isEmpty()) {
                AlertUtils.mostrarError("Por favor, completa ambos campos");
            } else {
                // Asignar los valores del formulario al objeto Usuario
                usu1.setNombre(usuarioText.getText());
                usu1.setContrasenia(contraText.getText());

                // Guardar el usuario en la base de datos
                usuarioDAO.guardarUsuario(usu1);

                usuarioText.setText("");
                contraText.setText("");
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL mostrando un mensaje al usuario
            AlertUtils.mostrarError("Ocurrió un error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void OnEntrarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        usu1.setNombre(usuarioText.getText());
        usu1.setContrasenia(contraText.getText());
        if (usuarioDAO.comprobarInicio(usu1)){
            //Aquí deberia cambiar de stage a el siguiente formulario o una tabla yo q se
        }
    }

    @FXML
    public void OnLimpiarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        usuarioText.setText("");
        contraText.setText("");
    }


    /*@FXML
    void OnBorrarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        if (usuarioText.getText().isEmpty()){
            AlertUtils.mostrarError("EY tio, tienes que poner el nombre de usuario");
        } else {
            String NombreUsu=usuarioText.getText();
        }
        usuarioDAO.eliminarUsuario(usuarioText.getText());

    }*/

    @FXML
    void OnSalirClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        System.exit(0);
    }
}