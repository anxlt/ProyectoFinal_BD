package db;
import logico.Universidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniversidadDAOImpl implements UniversidadDAO {
    @Override
    public List<Universidad> listarTodas() {
        List<Universidad> lista = new ArrayList<>();
        String sql = "SELECT id_universidad, nombre_universidad FROM Universidad ORDER BY nombre_universidad";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Universidad(rs.getInt("id_universidad"), rs.getString("nombre_universidad")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public Universidad buscarPorId(int idUniversidad) {
        String sql = "SELECT id_universidad, nombre_universidad FROM Universidad WHERE id_universidad = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUniversidad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Universidad(rs.getInt("id_universidad"), rs.getString("nombre_universidad"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}