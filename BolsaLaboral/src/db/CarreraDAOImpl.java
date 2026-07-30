package db;
import logico.Carrera;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAOImpl implements CarreraDAO {
    @Override
    public List<Carrera> listarTodas() {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT id_carrera, nombre_carrera FROM Carrera ORDER BY nombre_carrera";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Carrera(rs.getInt("id_carrera"), rs.getString("nombre_carrera")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public Carrera buscarPorId(int idCarrera) {
        String sql = "SELECT id_carrera, nombre_carrera FROM Carrera WHERE id_carrera = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCarrera);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Carrera(rs.getInt("id_carrera"), rs.getString("nombre_carrera"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}