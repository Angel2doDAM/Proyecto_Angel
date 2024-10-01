package org.example.proyecto_angel.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField contraText;

    @FXML
    private TextField usuarioText;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usu1 = new Usuario();

    @FXML
    public void OnEntrarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        if (usuarioText.getText().isEmpty() || contraText.getText().isEmpty()){
            AlertUtils.mostrarError("EY tio, tienes que completar ambos campos");
        } else {
            usu1.setNombre(usuarioText.getText());
            usu1.setContrasenia(contraText.getText());
            usuarioDAO.guardarUsuario(usu1);
        }
    }

    @FXML
    void OnBorrarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        if (usuarioText.getText().isEmpty()){
            AlertUtils.mostrarError("EY tio, tienes que poner el nombre de usuario");
        } else {
            usu1.setNombre(usuarioText.getText());
            usuarioDAO.eliminarUsuario(usu1);
        }
    }

    @FXML
    void OnSalirClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        System.exit(0);
    }
}