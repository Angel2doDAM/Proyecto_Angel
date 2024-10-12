package org.example.proyecto_angel.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.ChangeStage;

import static org.example.proyecto_angel.util.ChangeStage.cambioEscena;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;


public class HelloController {


    @FXML
    private AnchorPane Fondo_registro;

    @FXML
    private TextField contraText;

    @FXML
    private TextField usuarioText;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usu1 = new Usuario();

    TiendaController controller;

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
        try {
            // Verificar que los campos no estén vacíos
            if (usuarioText.getText().isEmpty() || contraText.getText().isEmpty()) {
                AlertUtils.mostrarError("Por favor, completa ambos campos");
            } else {
                usu1.setNombre(usuarioText.getText());
                usu1.setContrasenia(contraText.getText());
                if (usuarioDAO.comprobarInicio(usu1)==2){ //Compruebo si el usuario es administrador del sistema
                    usuarioDAO.desconectar();
                    cambioEscena("PantallaPrincipal.fxml", Fondo_registro);
                } else if (usuarioDAO.comprobarInicio(usu1)==1) { //Si no es administrador:
                    usuarioDAO.desconectar();

                    controller = ChangeStage.cambioEscena2("Tienda.fxml", Fondo_registro);
                    controller.cargarDatos();

                } else if (usuarioDAO.comprobarInicio(usu1)==0){ //Si no existe ese usuario:
                    AlertUtils.mostrarError("Usuario o contraseña erroneos");
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL mostrando un mensaje al usuario
            AlertUtils.mostrarError("Ocurrió un error al entrar: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void OnLimpiarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        usuarioText.setText("");
        contraText.setText("");
    }

    @FXML
    void OnSalirClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        System.exit(0);
    }
}