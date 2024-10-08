package org.example.proyecto_angel.util;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public static void mostrarAcierto(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
