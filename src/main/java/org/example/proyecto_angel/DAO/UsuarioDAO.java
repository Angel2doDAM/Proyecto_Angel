package org.example.proyecto_angel.DAO;

import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.R;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class UsuarioDAO {

    private Connection conexion;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public void guardarUsuario (Usuario usuario) throws SQLException {

        String sql = "INSERT INTO Proyecto (nombre, contrasenia) VALUES (?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getContrasenia());
        sentencia.setString(3, coche.getModelo());
        sentencia.setString(4, coche.getTipo());
        sentencia.executeUpdate();

    }

}
