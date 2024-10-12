package org.example.proyecto_angel.DAO;

import org.example.proyecto_angel.clases.Usuario;
import org.example.proyecto_angel.util.AlertUtils;
import org.example.proyecto_angel.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//Clase para conectar y editar la base de datos de los Cubos de la tienda
public class UsuarioDAO {

    private Connection conexion;

//    Función para conectar a la base de datos
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

//    Funcion para desconectar
    public void desconectar() throws SQLException {
        conexion.close();
    }

//    Función para guardar los usuarios de la base de datos en un list
    public void guardarUsuario (Usuario usuario) throws SQLException {

        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;

//        Compruebo si el usuario ya existía
        for (Usuario usu1 : usuariosComp){
            if (usu1.getNombre().equals(usuario.getNombre())){
                encontrado=true;
                AlertUtils.mostrarError("Usuario creado con anterioridad");
            }
//            Si no existía antes lo crea y añade a la base de datos
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

//    Función para el inicio de sesión de usuarios
    public int comprobarInicio(Usuario usuario) throws SQLException {

//        Guardo todos los usuarios de la tabla sql en una lista
        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;
        boolean admin=false;

//        recorro la lista
        for (Usuario usu1 : usuariosComp){
//            Compruebo si existe un usuario con el nombre introducido
            if (usu1.getNombre().equals(usuario.getNombre())){
//                Compruebo si ese usuario tiene la contraseña igual a la introducida
                if (usu1.getContrasenia().equals(usuario.getContrasenia())){
                    encontrado = true;
                    break;
                }
            }
        }
//        Si el usuario está bien introducido compruebo si es admin o root
        if (encontrado) {
            if (usuario.getNombre().equals("root") || usuario.getNombre().equals("admin")) {
                admin = true;
            }
        }
//        Dependiendo si el usuario existe y si es o no administador devuelve distintos valores
        if (!encontrado){
//            Si el usuario no existe
            return 0;
        } else if (!admin) {
//            Si existe pero no es admin
            return 1;
        } else {
//            Si existe y es admin
            return 2;
        }
    }

//    Función que almacena los usuarios de la base de datos en una lista
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

//    Funcion creada para eliminar un usuario concreto de la base de datos
    public void eliminarUsuario(String nombreUsu)throws SQLException {

//        Guardo los usuarios en una lista
        List<Usuario> usuariosComp = obtenerUsuarios();

        boolean encontrado=false;

        for (Usuario usu1 : usuariosComp){
//            Compruebo si el usuario a eliminar existe
            if (usu1.getNombre().equals(nombreUsu)){
//                Si efectivamente exite se elimina de la base de datos
                encontrado=true;
                String sql = "DELETE FROM usuarios WHERE nombre = ?";

                PreparedStatement sentencia = conexion.prepareStatement(sql);
                sentencia.setString(1, nombreUsu);
                sentencia.executeUpdate();

                AlertUtils.mostrarAcierto("El usuario ha sido eliminado de la base de datos");
            }
        }
//        Si no existe se muestra una alerta correspondiente
        if (!encontrado){
            AlertUtils.mostrarError("El nombre ingresado no tiene asociado ningún usuario");
        }
    }

//    Funcion creada para editar tanto el nombre como la contraseña de un usuario en concreto
    public void actualizarUsuario(String nombreUsuario, String contraUsuario, String nombreViejo) throws SQLException {

        String sql = "UPDATE usuarios SET nombre = ?, contrasenia = ? WHERE nombre = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, nombreUsuario);
        sentencia.setString(2, contraUsuario);
        sentencia.setString(3, nombreViejo);
        sentencia.executeUpdate();

    }
}
