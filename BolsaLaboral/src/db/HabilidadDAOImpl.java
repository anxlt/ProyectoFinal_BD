package db;

import logico.Habilidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabilidadDAOImpl implements HabilidadDAO {
    @Override
    public List<Habilidad> listarTodas() {
        List<Habilidad> lista = new ArrayList<>();
        String sql = "SELECT id_habilidad, nombre_habilidad FROM Habilidad ORDER BY nombre_habilidad";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Habilidad(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    @Override
    public Habilidad buscarPorId(int idHabilidad) {
        String sql = "SELECT id_habilidad, nombre_habilidad FROM Habilidad WHERE id_habilidad = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHabilidad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Habilidad(rs.getInt("id_habilidad"), rs.getString("nombre_habilidad"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}