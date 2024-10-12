package org.example.proyecto_angel.DAO;

import org.example.proyecto_angel.clases.Cubo;
import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TiendaDAO {

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

    public ArrayList<Cubo> obtenerCubos() throws SQLException {

        ArrayList<Cubo> cubos = new ArrayList<>();
        String sql = "SELECT * FROM Cubos";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Cubo cubo1 = new Cubo();
            cubo1.setID(resultado.getInt(1));
            cubo1.setTipo(resultado.getString(2));
            cubo1.setModelo(resultado.getString(3));
            cubo1.setPrecio(resultado.getDouble(4));

            cubos.add(cubo1);
        }
        return cubos;
    }

}
