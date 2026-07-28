package db;

import logico.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void insertar(Usuario u) {
        // CAMBIO: 'nombreUsuario' por 'nombre_usuario' y 'tipo' por 'tipo_usuario'
        String sql = "INSERT INTO Usuario (nombre_usuario, contrasena, tipo_usuario) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContrasena());
            ps.setString(3, u.getTipo());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario buscarPorNombre(String nombre) {
        // CAMBIO: Se actualizaron los nombres de las columnas en el SELECT y en el WHERE
        String sql = "SELECT nombre_usuario, contrasena, tipo_usuario FROM Usuario WHERE nombre_usuario = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        // CAMBIO: Se actualizaron los nombres de las columnas
        String sql = "SELECT nombre_usuario, contrasena, tipo_usuario FROM Usuario";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                // CAMBIO: Extraer datos con los nuevos nombres de las columnas
                rs.getString("nombre_usuario"),
                rs.getString("contrasena"),
                rs.getString("tipo_usuario")
        );
    }
}