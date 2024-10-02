package org.example.proyecto_angel.DAO;

import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;

        for (Usuario usu1 : usuariosComp){
            if (usu1.getNombre().equals(usuario.getNombre())){
                encontrado=true;
                AlertUtils.mostrarError("Usuario creado con anterioridad");
            }
        }
        if(!encontrado) {
            String sql = "INSERT INTO usuarios (nombre, contrasenia) VALUES (?, ?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getContrasenia());
            sentencia.executeUpdate();
            AlertUtils.mostrarAcierto("Usuario guardado correctamente");
        }
    }

    public boolean comprobarInicio(Usuario usuario) throws SQLException {

        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;

        for (Usuario usu1 : usuariosComp){
            if (usu1.getNombre().equals(usuario.getNombre()) && usu1.getContrasenia().equals(usuario.getContrasenia())){
                encontrado = true;
                AlertUtils.mostrarAcierto("WAY");
            }
        }
        if (!encontrado){
            AlertUtils.mostrarError("El usuari con el que intenta iniciar sesión no existe aún.\n " +
                    "Permitame proponerle crear al usuario.");
        }
        return encontrado;
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Usuario usuario1 = new Usuario();
            usuario1.setId(resultado.getInt(1));
            usuario1.setNombre(resultado.getString(2));
            usuario1.setContrasenia(resultado.getString(3));

            usuarios.add(usuario1);
        }
        return usuarios;
    }

    public void eliminarUsuario(String nombreUsu)throws SQLException {

        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;

        for (Usuario usu1 : usuariosComp){
            if (usu1.getNombre().equals(nombreUsu)){
                encontrado=true;
                String sql = "DELETE FROM usuarios WHERE nombre = ?";

                PreparedStatement sentencia = conexion.prepareStatement(sql);
                sentencia.setString(1, nombreUsu);
                sentencia.executeUpdate();

                AlertUtils.mostrarAcierto("El usuario ha sido eliminado de la base de datos");
            }
        }
        if (!encontrado){
            AlertUtils.mostrarError("El nombre ingresado no tiene asociado ningún usuario");
        }
    }
}
