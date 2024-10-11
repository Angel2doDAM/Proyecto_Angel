package org.example.proyecto_angel.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.proyecto_angel.DAO.UsuarioDAO;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.ChangeStage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.example.proyecto_angel.util.ChangeStage.cambioEscena;


public class EditarController {


    public AnchorPane Fondo_Editar;
    @FXML
    private AnchorPane Fondo_registro;

    @FXML
    private TextField contraText;

    @FXML
    private TextField usuarioText;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usu1 = new Usuario();
    String nombreViejo;
    PrincipalController controller;

    public EditarController() {
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
                usuarioDAO.actualizarUsuario(usu1.getNombre(), usu1.getContrasenia(), nombreViejo);
                AlertUtils.mostrarAcierto("El usuario ha sido actualizado correctamente");
                controller.cargarDatos();
                ChangeStage.cerrarEscena(Fondo_Editar);
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL mostrando un mensaje al usuario
            AlertUtils.mostrarError("Ocurrió un error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void OnLimpiarClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        usuarioText.setText("");
        contraText.setText("");
    }

    @FXML
    void OnSalirClic(javafx.event.ActionEvent actionEvent) throws SQLException {
        ChangeStage.cerrarEscena(Fondo_Editar);
    }

    @FXML
    public void cargarDatos(Usuario usu1, PrincipalController controller){
        usuarioText.setText(usu1.getNombre());
        contraText.setText(usu1.getContrasenia());
        nombreViejo = usu1.getNombre();
        this.controller = controller;
    }
}